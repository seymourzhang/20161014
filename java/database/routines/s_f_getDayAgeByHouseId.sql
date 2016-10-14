DROP FUNCTION IF EXISTS sparrow.s_f_getDayAgeByHouseId;
CREATE DEFINER=`phoe`@`%` FUNCTION `s_f_getDayAgeByHouseId`(in_house_id int,in_date DATETIME) RETURNS int(11)
BEGIN
  DECLARE cur_day_age int;
  /*SELECT a.age INTO cur_day_age FROM s_b_breed_detail a where 1=1
    and date_format(a.growth_date,'%Y-%m-%d') = date_format(in_date,'%Y-%m-%d')
    and exists(SELECT 1 from s_b_house_breed b where b.id = a.house_breed_id 
                                                and b.house_id = in_house_id 
                                                and ifnull(b.market_date,a.growth_date) >= a.growth_date 
                                                              );*/
  --  先看已结束的批次有没有
  SELECT (to_days(in_date)-to_days(c.place_date)) INTO cur_day_age FROM s_b_house_breed c
  where 1=1 
  and c.house_id = in_house_id
  and c.batch_status = '02'
  and in_date BETWEEN c.place_date and c.market_date;
  if cur_day_age is null THEN 
      --  再看是否是当前批次      
      SELECT (to_days(in_date)-to_days(c.place_date)) INTO cur_day_age FROM s_b_house_breed c
      where 1=1 
      and c.house_id = in_house_id
      and c.batch_status = '01'
      and in_date >= c.place_date;
      
      if cur_day_age is null THEN 
          --  再看是否是当前批次与上一批次之间         
          SELECT (to_days(in_date)-to_days(c.place_date)) INTO cur_day_age FROM s_b_house_breed c
          where 1=1 
          and c.house_id = in_house_id
          and c.batch_status = '01'
          and not exists(
            SELECT 1 from s_b_house_breed bh where bh.house_id = c.house_id and bh.batch_status = '02'
            and bh.market_date >= in_date
          );
      END IF;
  END IF;  
  RETURN(cur_day_age);
END;