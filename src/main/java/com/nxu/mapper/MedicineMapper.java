package com.nxu.mapper;

import com.nxu.entity.Medicine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MedicineMapper {

    /**
     * 根据药品名称模糊查询，根据国药准字精准查询
     *
     * @param name 药品名称
     * @param code 国药准字
     * @return 药品集合
     */
    List<Medicine> selectSomeMedicine(@Param("name") String name, @Param("code") String code);

    Medicine selectOneMedicine(int id);

    int insertMedicine(Medicine medicine);

    int updateMedicine(Medicine medicine);

    int deleteMedicine(int id);
}