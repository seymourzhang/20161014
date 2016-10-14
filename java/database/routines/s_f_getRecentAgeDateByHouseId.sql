DROP FUNCTION IF EXISTS sparrow.s_f_getRecentAgeDateByHouseId;
CREATE DEFINER=`phoe`@`%` FUNCTION `s_f_getRecentAgeDateByHouseId`(in_house_id int,in_date_time VARCHAR(10),in_farm_breed_id int) RETURNS varchar(10) CHARSET utf8
BEGIN
  
  DECLARE p_returnDate VARCHAR(10);
  IF in_date_time != 'NULL' THEN 
    RETURN in_date_time ;
  END IF;
    
  SELECT date_format(
      least(date_add(place_date,INTERVAL 45 day),ifnull(market_date,curdate()),curdate()),
                     '%Y-%m-%d')
            INTO p_returnDate 
    from s_b_house_breed where 1=1 and house_id = in_house_id and s_b_house_breed.farm_breed_id = in_farm_breed_id ;

  RETURN(p_returnDate);
  
END;