package com.nxu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nxu.entity.Medicine;
import com.nxu.mapper.MedicineMapper;
import com.nxu.service.MedicineService;
import com.nxu.utils.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class MedicineServiceImpl implements MedicineService {

    @Autowired
    private MedicineMapper medicineMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public PageInfo<Medicine> getSomeMedicine(String name, String code, Integer page, Integer limit) {
        if (page == null || limit == null) {
            return new PageInfo<>(medicineMapper.selectSomeMedicine(name, code));
        } else {
            PageHelper.startPage(page, limit);
            List<Medicine> medicines = medicineMapper.selectSomeMedicine(name, code);
            return new PageInfo<>(medicines);
        }
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

    /**
     * 获取药品信息字典
     *
     * @return 药品信息字典，包含 nameList codeList
     */
    @Override
    public HashMap<String, Object> getMedicineDictionary() {
        List<Object> nameList = redisService.getList("nameDictionary");
        List<Object> codeList = redisService.getList("codeDictionary");

        HashMap<String, Object> map = new HashMap<>();

        // 先判断redis中是否存在
        if (!nameList.isEmpty() && !codeList.isEmpty()) {
            map.put("nameList", nameList);
            map.put("codeList", codeList);
        } else {
            List<Object> nameDictionary = new ArrayList<>();
            List<Object> codeDictionary = new ArrayList<>();

            List<Medicine> medicines = medicineMapper.selectSomeMedicine(null, null);

            // 对于 CPU 密集型任务，可以使用并行流 parallelStream() 来利用多核 CPU，提高处理速度
            medicines.parallelStream().forEach(medicine -> {
                nameDictionary.add(medicine.getName());
                codeDictionary.add(medicine.getCode());
            });

            // 存入redis
            redisService.setList("nameDictionary", nameDictionary);
            redisService.setList("codeDictionary", codeDictionary);
            map.put("nameList", nameDictionary);
            map.put("codeList", codeDictionary);
        }
        return map;
    }
}