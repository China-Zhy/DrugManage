package com.nxu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nxu.entity.Medicine;
import com.nxu.mapper.MedicineMapper;
import com.nxu.service.MedicineService;
import com.nxu.utils.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ZhangHongYe
 */
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

    /**
     * 使用 悲观锁（行级锁）修改药品信息：适合并发量不高的场景
     * 优点：实现简单，适合并发量低的场景，能 100% 保证一致性。
     * 缺点：锁持有时间随事务长度增加，高并发下可能导致锁竞争激烈，性能下降。
     *
     * @param medicine 药品信息
     * @return 修改结果
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)  // 至少读已提交，避免脏读
    public int setMedicine(Medicine medicine) {
        // 先通过SELECT ... FOR UPDATE查询并锁定该药品的库存记录（仅锁定当前行，不影响其他药品）
        Medicine oldMedicine = medicineMapper.lockMedicine(medicine.getId());
        // 各种条件判断
        if (oldMedicine == null) {
            throw new RuntimeException("药品信息不一致");
        }
        return medicineMapper.updateMedicine(medicine);
        // 事务提交后，锁自动释放，其他事务可继续操作
    }

    @Override
    public int delMedicine(int id) {
        return medicineMapper.deleteMedicine(id);
    }

    /**
     * 获取药品信息字典
     *
     * @return 药品信息字典，包含 nameList和codeList
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

            medicines.forEach(medicine -> {
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