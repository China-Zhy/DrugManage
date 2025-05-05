package com.nxu.service;

import com.github.pagehelper.PageInfo;
import com.nxu.entity.Medicine;

import java.util.HashMap;

public interface MedicineService {

    PageInfo<Medicine> getSomeMedicine(String name, String code, Integer page, Integer limit);

    Medicine getOneMedicine(int id);

    int addMedicine(Medicine medicine);

    int setMedicine(Medicine medicine);

    int delMedicine(int id);    // 目前使用假删除

    /**
     * 获取药品信息字典
     *
     * @return 药品信息字典，包含 nameList codeList
     */
    HashMap<String, Object> getMedicineDictionary();
}