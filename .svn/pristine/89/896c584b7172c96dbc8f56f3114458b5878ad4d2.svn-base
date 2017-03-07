DROP PROCEDURE IF EXISTS sparrow.s_p_createTargetMonitor;
CREATE DEFINER=`phoe`@`%` PROCEDURE `s_p_createTargetMonitor`(in in_buzType VARCHAR(2),in in_houseBreedId int)
BEGIN
    /*参数说明：
        1、in_buzType=0，入雏保存的时候调用，in_houseBreedId 指 栋舍饲养批次Id
        2、in_buzType=1，报警保存或修改的时候调用，in_houseBreedId 指 栋舍报警设置主表Id
    */
  
    /*申明变量 */
    DECLARE p_res VARCHAR(5) DEFAULT 'Succ';-- 错误状态：Succ-成功；Fail-错误
    DECLARE p_farmId INT;-- 农场编码
    DECLARE p_houseId INT;-- 栋舍编码
    DECLARE p_farmBreedId INT;-- 农场饲养批次Id
    DECLARE p_housePlaceDate DATETIME;-- 栋舍入雏日期
  
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION ,SQLWARNING ,NOT FOUND SET p_res = 'Fail'; -- 异常捕获
    
    /* 开启事务控制 */
    START TRANSACTION ;
    -- 删除旧的计算数据  
    
    DELETE from s_b_temp1 where KeyInfo = in_houseBreedId;
    DELETE from s_b_temp2 where KeyInfo = in_houseBreedId;
    COMMIT;
    
  
    /* in_buzType=0 入雏 生成所有日龄24小时 高报、低报、目标 */
    IF in_buzType = '0' then
          -- 创建表存储该栋的设置数据
          SELECT farm_id,house_id,farm_breed_id,place_date INTO p_farmId,p_houseId,p_farmBreedId,p_housePlaceDate
          from s_b_house_breed where id = in_houseBreedId;

          SET @para_id1 = 0;
          -- 首先取该栋本身的设置数据
          INSERT INTO s_b_temp1(KeyInfo,id,houseid, dayage, targettemp, hightemp, lowtemp, humidity, place_date)
          SELECT in_houseBreedId,@para_id1 := @para_id1 + 1,p_houseId,T2.day_age,T2.set_temp,T2.high_alarm_temp,T2.low_alarm_temp,T2.set_humidity,p_housePlaceDate
          FROM s_b_temp_setting T1
          JOIN s_b_temp_setting_sub T2 ON T1.id = T2.uid_num
          WHERE T1.house_id = p_houseId
          ORDER BY T2.day_age;
          
          IF NOT EXISTS(SELECT 1 FROM s_b_temp1) THEN
              -- 如果无数据，则取默认数据
              INSERT INTO s_b_temp1(KeyInfo,id,houseid,dayage,targettemp,hightemp,lowtemp,humidity,place_date)
              SELECT in_houseBreedId,@para_id1 := @para_id1 + 1,p_houseId,T2.day_age,T2.set_temp,T2.high_alarm_temp,T2.low_alarm_temp,T2.set_humidity,p_housePlaceDate
              FROM s_b_temp_setting T1
              JOIN s_b_temp_setting_sub T2 ON T1.id = T2.uid_num
              WHERE T1.house_id = 0
              ORDER BY T2.day_age;
          END IF;
     END IF;

    /* 如果in_buzType =1 修改目标，更新相应的数据 */
    if in_buzType = '1' then
        -- 创建表存储该栋的设置数据
        SELECT t0.farm_id,t0.house_id,t1.farm_breed_id
        INTO p_farmId,p_houseId,p_farmBreedId
        from s_b_temp_setting t0
        join s_b_house_breed t1 on t1.id =  s_f_getHouseBreedId(t0.house_id) and t0.house_id = t1.house_id
        where t0.id = in_houseBreedId;
        
        SELECT T0.place_date into p_housePlaceDate
        from s_b_house_breed T0
        where T0.farm_id= p_farmid and T0.house_id = p_houseId and T0.farm_breed_id = p_farmBreedId;
    
        SET @para_id1 = 0;
        -- 首先取该栋本身的设置数据
        INSERT INTO s_b_temp1(KeyInfo,id,houseid, dayage, targettemp, hightemp, lowtemp, humidity, place_date)
        SELECT in_houseBreedId,@para_id1 := @para_id1 + 1,p_houseId,T2.day_age,T2.set_temp,T2.high_alarm_temp,T2.low_alarm_temp,T2.set_humidity,p_housePlaceDate
        FROM s_b_temp_setting T1
        JOIN s_b_temp_setting_sub T2 ON T1.id = T2.uid_num
        WHERE T1.house_id = p_houseId
        ORDER BY T2.day_age;
    END IF;
  
    -- 生成结果集，生成每一个时间间隔的目标以及下一个间隔的目标
    UPDATE s_b_temp1 T0
      JOIN s_b_temp1 T1 ON T1.id = T0.id+1 and T1.KeyInfo = T0.KeyInfo
    SET T0.nextdayage = T1.dayage,
         T0.nexttargettemp = T1.targettemp,
         T0.nexthightemp = T1.hightemp,
         T0.nextlowtemp= T1.lowtemp
    where T0.KeyInfo = in_houseBreedId;
    
    --  更新1日龄为0日龄
    UPDATE s_b_temp1 t0
    SET t0.dayage = 0
    where t0.KeyInfo = in_houseBreedId
    and t0.dayage = 1;  
  
    --  更新每一行记录前面的点数
    UPDATE s_b_temp1 t0
    SET t0.totalrow = t0.dayage * 24
    where t0.KeyInfo = in_houseBreedId;
    
    COMMIT;
  
    SET @para_curId = 1;
    --  每次报警数据多生成10天
    SELECT @para_maxId:=MAX(dayage+1+10)*24 FROM s_b_temp1 where KeyInfo = in_houseBreedId;

    WHILE @para_curId <= @para_maxId DO
        INSERT INTO s_b_temp2(KeyInfo,id,dayage,record_datetime,targettemp,hightemp,lowtemp,humidity)
        SELECT in_houseBreedId,99, FLOOR((@para_curId-1)/24),
              DATE_ADD(T0.place_date,INTERVAL @para_curId-1 HOUR),
              T0.targettemp + ifnull(ROUND((ifnull(T0.nexttargettemp,T0.targettemp) - T0.targettemp)/nullif(((ifnull(T0.nextdayage,T0.dayage) - T0.dayage)*24),0)*(@para_curId-T0.totalrow -1),2),0),
              T0.hightemp +ifnull( ROUND((ifnull(T0.nexthightemp,T0.hightemp) - T0.hightemp)/((ifnull(T0.nextdayage,T0.dayage)  - T0.dayage)*24)*(@para_curId-T0.totalrow -1),2),0),
              T0.lowtemp + ifnull(ROUND((ifnull(T0.nextlowtemp,T0.lowtemp) - T0.lowtemp)/((ifnull(T0.nextdayage,T0.dayage)  - T0.dayage)*24)*(@para_curId-T0.totalrow -1),2),0),
              T0.humidity
        FROM s_b_temp1 T0
        WHERE T0.dayage <= FLOOR((@para_curId-1)/24) AND ifnull(T0.nextdayage,99) > FLOOR((@para_curId-1)/24)
        and T0.KeyInfo = in_houseBreedId ;
        
        SET @para_curId = @para_curId +1 ;
    END WHILE;
  
    COMMIT;
  
    -- 入雏时候调用
    if in_buzType ='0'THEN
         -- 删除旧数据
        DELETE from s_b_dayage_temp_sub where exists(SELECT 1 from s_b_dayage_temp b where uid_num = b.id and b.house_id = p_houseId and b.feed_batch = p_farmBreedId );
        DELETE from s_b_dayage_temp where house_id = p_houseId and feed_batch = p_farmBreedId ;
        
        -- 插入 主表
        INSERT into s_b_dayage_temp(id, farm_id, house_id, feed_batch, create_person, create_date, modify_person, modify_date)
                SELECT null,p_farmId,p_houseId,p_farmBreedId,0,now(),0,now();
        SELECT @autoId := @@IDENTITY;
        
        -- 插入 子表
        INSERT into s_b_dayage_temp_sub(uid_num,day_age,record_datetime,set_temp,high_alarm_temp,low_alarm_temp,set_humidity,create_person,create_date,modify_person,modify_date)
                SELECT @autoId,dayage,record_datetime,targettemp,hightemp,lowtemp,humidity,0,now(),0,now()
                FROM s_b_temp2 
                where KeyInfo = in_houseBreedId;
    END IF;
    -- 报警修改时候调用
    if in_buzType ='1' THEN
        -- 删除 子表
        DELETE from s_b_dayage_temp_sub where 1=1 
             and exists(SELECT 1 from s_b_dayage_temp b where uid_num = b.id and b.house_id = p_houseId and b.feed_batch = p_farmBreedId ) 
             and date_format(record_datetime,'%Y-%m-%d')>=date_format(sysdate(),'%Y-%m-%d')
        ;

        SELECT b.id into @autoId FROM s_b_dayage_temp b
        where b.house_id = p_houseId and b.feed_batch = p_farmBreedId ;
        -- 插入 子表
        INSERT into s_b_dayage_temp_sub(uid_num,day_age,record_datetime,set_temp,high_alarm_temp,low_alarm_temp,set_humidity,create_person,create_date,modify_person,modify_date)
        SELECT @autoId,dayage,record_datetime,targettemp,hightemp,lowtemp,humidity,0,now(),0,now()
        FROM s_b_temp2
        WHERE  1=1 
        and date_format(record_datetime,'%Y-%m-%d')>=date_format(sysdate(),'%Y-%m-%d')
        and KeyInfo = in_houseBreedId;
    END IF;
    
    COMMIT;
  
    IF p_res = 'Fail' THEN
        ROLLBACK;
    END IF;
    select p_res;
 END;