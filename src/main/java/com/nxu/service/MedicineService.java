package com.nxu.service;

import com.github.pagehelper.PageInfo;
import com.nxu.entity.Medicine;

public interface MedicineService {

    PageInfo<Medicine> getSomeMedicine(String name, String code, Integer page, Integer limit);

    Medicine getOneMedicine(int id);

    int addMedicine(Medicine medicine);

    int setMedicine(Medicine medicine);

    int delMedicine(int id);
}