DROP PROCEDURE IF EXISTS sparrow.s_p_createFarmTask;
CREATE DEFINER=`phoe`@`%` PROCEDURE `s_p_createFarmTask`(in_apply_flag VARCHAR(20) ,in in_farm_id int)
label_at_start:BEGIN
    /*参数说明：
        1、in_apply_flag='All'：适用全部农场; in_apply_flag='Single'：适用单个农场;
        2、in_FarmId：当in_apply_flag为All时，此变量不起作用，可以传null，当in_apply_flag为Single时，需要传入农场Id
    */
    DECLARE p_cur_time DATETIME;
    /*声明变量，用于异常情况的捕捉*/
    DECLARE p_res VARCHAR(5) DEFAULT 'Succ';
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION ,SQLWARNING ,NOT FOUND SET p_res = 'Fail'; -- 异常捕获
    START TRANSACTION ;
        
    IF in_apply_flag = 'All' then
        INSERT INTO s_b_farm_task(id, farm_id, task_source, task_code, task_name, task_type, task_status, age_infos, bak1, bak2, bak3, bak4, create_person, create_date, create_time, modify_person, modify_date, modify_time)
        SELECT null,b.id,'01',a.task_code,a.task_name,a.task_type,a.task_status,a.age_infos,a.bak1,a.bak2,a.bak3,a.bak4,0,curdate(),now(),0,curdate(),now()
        from s_d_task a INNER join s_d_farm b on b.freeze_status = 0
        where 1=1
        and not exists(SELECT 1 from s_b_farm_task e where a.task_code = e.task_code and e.farm_id = b.id)
        and a.task_status = 'Y' ;
    ELSEIF in_apply_flag = 'Single' THEN
        INSERT INTO s_b_farm_task(id, farm_id, task_source, task_code, task_name, task_type, task_status, age_infos, bak1, bak2, bak3, bak4, create_person, create_date, create_time, modify_person, modify_date, modify_time)
        SELECT null,in_farm_id,'01',a.task_code,a.task_name,a.task_type,a.task_status,a.age_infos,a.bak1,a.bak2,a.bak3,a.bak4,0,curdate(),now(),0,curdate(),now()
        from s_d_task a where 1=1
        and not exists(SELECT 1 from s_b_farm_task b where a.task_code = b.task_code and b.farm_id = in_farm_id)
        and a.task_status = 'Y' ;
    END IF ;
        
    IF p_res = 'Fail' THEN
        ROLLBACK;
    ELSE
        COMMIT;
    END IF;
    select p_res;
END;