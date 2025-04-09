package com.nxu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nxu.entity.Medicine;
import com.nxu.mapper.MedicineMapper;
import com.nxu.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineServiceImpl implements MedicineService {

    @Autowired
    private MedicineMapper medicineMapper;

    @Override
    public PageInfo<Medicine> getSomeMedicine(String name, String code, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<Medicine> medicines = medicineMapper.selectSomeMedicine(name, code);
        return new PageInfo<>(medicines);
    }

    @Override
    public Medicine getOneMedicine(int id) {
        return medicineMapper.selectOneMedicine(id);
    }

    @Override
    public int addMedicine(Medicine medicine) {
        return medicineMapper.insertMedicine(medicine);
    }

    @Override
    public int setMedicine(Medicine medicine) {
        return medicineMapper.updateMedicine(medicine);
    }

    @Override
    public int delMedicine(int id) {
        return medicineMapper.deleteMedicine(id);
    }
}