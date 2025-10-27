package com.nxu.service;

import com.github.pagehelper.PageInfo;
import com.nxu.entity.Medicine;

import java.util.HashMap;

/**
 * @author ZhangHongYe
 */
public interface MedicineService {

    PageInfo<Medicine> getSomeMedicine(String name, String code, Integer page, Integer limit);

    Medicine getOneMedicine(int id);

    int addMedicine(Medicine medicine);

    /**
     * 使用 悲观锁（行级锁）修改药品信息：适合并发量不高的场景
     *
     * @param medicine 药品信息
     * @return 修改结果
     */
    int setMedicine(Medicine medicine);

    int delMedicine(int id);    // 目前使用假删除（修改用户状态为不可用）

    /**
     * 获取药品信息字典
     *
     * @return 药品信息字典，包含 nameList codeList
     */
    HashMap<String, Object> getMedicineDictionary();

}