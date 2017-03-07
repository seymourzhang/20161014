package com.mtc.mapper.app;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import com.mtc.entity.app.SBChickenStandar;
import org.apache.ibatis.annotations.Param;

public interface SBChickenStandarMapperCustom {

    //用Parm方式传递参数
    void updateStandarByBreedAndFarmId(SBChickenStandar sSBChickenStandar);

    List<HashMap<String, Object>> select(@Param("farmId") int farmId,  @Param("breedName") String breedName);

    void insertByFarmIdAndBreedName(List<SBChickenStandar> sSBChickenStandar);

    List<SBChickenStandar> selectForInsert(@Param("breedName") String breedName);

	int updateStandardByBatch(List<SBChickenStandar> list);

	int updateStandardDIY(SBChickenStandar eggsell);

    List<SBChickenStandar> selectForOwner(@Param("farmId") int farmId);

    List<SBChickenStandar> selectByFarmId(@Param("farmId") int farmId);

}
