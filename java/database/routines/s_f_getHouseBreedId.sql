DROP FUNCTION IF EXISTS sparrow.s_f_getHouseBreedId;
CREATE FUNCTION `s_f_getHouseBreedId`(in_house_id int ) RETURNS int(11)
BEGIN
  DECLARE house_breed_id int;
  SELECT id INTO  house_breed_id FROM (select id from s_b_house_breed where 1=1 and house_id = in_house_id order by place_date desc) data  LIMIT 1 ;
  IF ISNULL(house_breed_id)  THEN
    SET house_breed_id = 0;
  END if;
  RETURN(house_breed_id);
END;