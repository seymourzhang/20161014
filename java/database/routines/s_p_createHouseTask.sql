DROP PROCEDURE IF EXISTS sparrow.s_p_createHouseTask;
CREATE DEFINER=`phoe`@`%` PROCEDURE `s_p_createHouseTask`(in in_farm_id int,in_apply_flag VARCHAR(20),in in_temp_id int)
label_at_start:BEGIN
    /*参数说明：
        1、in_farm_id : 农场Id
        2、in_apply_flag:  All-适用全部栋舍
                           TempTask-增添临时任务
                           Single-适用单个栋舍 
        3、in_temp_id  :  当in_apply_flag为 All 时，此变量不起作用，可以传0
                           当in_apply_flag为 TempTask 时，需要传入临时任务Id
                           当in_apply_flag为 Single 时，需要传入栋舍Id
    */
    DECLARE p_cur_time DATETIME;
    DECLARE p_farmBreedId INT;-- 农场饲养批次Id
    
    /*声明变量，用于异常情况的捕捉*/
    DECLARE p_res VARCHAR(5) DEFAULT 'Succ';
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION ,SQLWARNING ,NOT FOUND SET p_res = 'Fail'; -- 异常捕获
    START TRANSACTION ;
    
    SELECT ifnull(max(id),0) INTO p_farmBreedId from s_b_farm_breed where farm_id = in_farm_id and batch_status = '01' ;
    
    IF in_apply_flag = 'All' then
        INSERT INTO s_b_house_breed_tsk (id, house_id, farm_breed_id,
                                 age, task_id, task_code, task_name, task_type,deal_status, deal_time, deal_log,
                                 bak1, bak2, bak3, bak4,
                                 create_person, create_date, create_time, modify_person, modify_date, modify_time)
        SELECT
            null as id,sdh.id as houseid,p_farmBreedId as farmbreedid,
            (CASE when a.age_infos = '**' then
             b.IncreID-1
             else substring_index(substring_index(a.age_infos,'#',b.IncreID),'#',-1) end) as age,
             a.id as taskid,a.task_code,a.task_name,a.task_type,'00' as dealstatus,null as dealtime,null as deallog,a.bak1,a.bak2,a.bak3,a.bak4,
             0 as createperson,curdate() as createdate,now() as createtime,0 as modifyperson,curdate() as modifydate,now() as modifytime
        from s_b_farm_task a
        join s_d_house sdh on sdh.farm_id = in_farm_id and sdh.freeze_status = 0
        join s_d_serialno b on b.IncreID <= (CASE when a.age_infos = '**' then 99 else (length(a.age_infos)-length(replace(a.age_infos,'#',''))+1) end)
        where 1=1
        and a.farm_id = in_farm_id
        and NOT exists(SELECT 1 from s_b_house_breed_tsk sbh where sbh.farm_breed_id = p_farmBreedId and sbh.house_id = sdh.id and sbh.task_id = a.id)
        and a.task_status = 'Y'
        ORDER BY sdh.id,a.id,age ;
    ELSEIF in_apply_flag = 'TempTask' THEN
        -- 先删除已有提醒
        DELETE from s_b_house_breed_tsk where task_id = in_temp_id and farm_breed_id = p_farmBreedId and 
                    exists(SELECT 1 from s_d_house sdh where sdh.id = house_id and sdh.farm_id = in_farm_id) ;
        -- 再增加新的
        INSERT INTO s_b_house_breed_tsk (id, house_id, farm_breed_id,
                                 age, task_id, task_code, task_name, task_type,deal_status, deal_time, deal_log,
                                 bak1, bak2, bak3, bak4,
                                 create_person, create_date, create_time, modify_person, modify_date, modify_time)
        SELECT
            null as id,sdh.id as houseid,p_farmBreedId as farmbreedid,
            (CASE when a.age_infos = '**' then
             b.IncreID-1
             else substring_index(substring_index(a.age_infos,'#',b.IncreID),'#',-1) end) as age,
             a.id as taskid,a.task_code,a.task_name,a.task_type,'00' as dealstatus,null as dealtime,null as deallog,a.bak1,a.bak2,a.bak3,a.bak4,
             0 as createperson,curdate() as createdate,now() as createtime,0 as modifyperson,curdate() as modifydate,now() as modifytime
        from s_b_farm_task a
        join s_d_house sdh on sdh.farm_id = in_farm_id and sdh.freeze_status = 0
        join s_d_serialno b on b.IncreID <= (CASE when a.age_infos = '**' then 99 else (length(a.age_infos)-length(replace(a.age_infos,'#',''))+1) end)
        where 1=1
        and a.farm_id = in_farm_id and a.id = in_temp_id
        and a.task_status = 'Y'
        and NOT exists(SELECT 1 from s_b_house_breed_tsk sbh where sbh.farm_breed_id = p_farmBreedId and sbh.house_id = sdh.id and sbh.task_id = in_temp_id)
        ORDER BY sdh.id,a.id,age ;
        
    ELSEIF in_apply_flag = 'Single' THEN
        INSERT INTO s_b_house_breed_tsk (id, house_id, farm_breed_id,
                                 age, task_id, task_code, task_name, task_type, deal_status, deal_time, deal_log,
                                 bak1, bak2, bak3, bak4,
                                 create_person, create_date, create_time, modify_person, modify_date, modify_time)
        SELECT
            null as id,sdh.id as houseid,p_farmBreedId as farmbreedid,
            (CASE when a.age_infos = '**' then
             b.IncreID-1
             else substring_index(substring_index(a.age_infos,'#',b.IncreID),'#',-1) end) as age,
             a.id as taskid,a.task_code,a.task_name,a.task_type,'00' as dealstatus,null as dealtime,null as deallog,a.bak1,a.bak2,a.bak3,a.bak4,
             0 as createperson,curdate() as createdate,now() as createtime,0 as modifyperson,curdate() as modifydate,now() as modifytime
        from s_b_farm_task a
        join s_d_house sdh on sdh.farm_id = in_farm_id and sdh.freeze_status = 0 and sdh.id = in_temp_id
        join s_d_serialno b on b.IncreID <= (CASE when a.age_infos = '**' then 99 else (length(a.age_infos)-length(replace(a.age_infos,'#',''))+1) end)
        where 1=1
        and a.farm_id = in_farm_id
        and NOT exists(SELECT 1 from s_b_house_breed_tsk sbh where sbh.farm_breed_id = p_farmBreedId and sbh.house_id = sdh.id and sbh.task_id = a.id)
        and a.task_status = 'Y'
        ORDER BY sdh.id,a.id,age ;
    END IF;
    
    IF p_res = 'Fail' THEN
        ROLLBACK;
    ELSE
        COMMIT;
    END IF;
    select p_res;
END;