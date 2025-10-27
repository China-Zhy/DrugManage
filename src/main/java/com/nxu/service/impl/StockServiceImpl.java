package com.nxu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nxu.entity.Checklist;
import com.nxu.entity.Record;
import com.nxu.entity.Stock;
import com.nxu.entity.User;
import com.nxu.mapper.ChecklistMapper;
import com.nxu.mapper.RecordMapper;
import com.nxu.mapper.StockMapper;
import com.nxu.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ZhangHongYe
 */
@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private ChecklistMapper checklistMapper;

    @Override
    public Stock getStockById(int id) {
        return stockMapper.getStockById(id);
    }

    @Override
    public PageInfo<Stock> getSomeStock(Integer medicineId, Integer page, Integer limit) {
        if (page != null && limit != null) {
            PageHelper.startPage(page, limit);
        }
        return new PageInfo<>(stockMapper.selectStock(medicineId));
    }

    /**
     * 药品入库(批量药品)
     * 使用 乐观锁：适合高并发场景→解决多人同时出库/入库导致的库存一致性问题
     * 优点：无锁竞争，高并发下性能好
     * 缺点：需要处理重试逻辑，冲突频繁时可能导致多次重试失败（需限制最大重试次数）
     *
     * @param records     入库信息集合
     * @param user        操作用户
     * @param totalAmount 药品总价
     * @return 批量入库结果
     */
    @Override
    @Transactional
    public int inputStock(List<com.nxu.entity.Record> records, User user, double totalAmount) {

        int maxRetry = 3;       // 最大重试次数
        int retryCount = 0;     // 当前重试次数

        while (retryCount < maxRetry) {
            // 使用线程安全的DateTimeFormatter替代SimpleDateFormat
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            // 使用AtomicInteger确保线程安全
            AtomicInteger isSuccess = new AtomicInteger(1); // 判断整个循环是否都没有错

            int parent;     // 操作清单的编号，添加后返回

            // 添加操作清单
            if (records != null && !records.isEmpty()) {
                Checklist checklist = new Checklist();
                checklist.setActionUser(user.getId());
                checklist.setVendorId(records.getFirst().getFrom());
                checklist.setTotalAmount(totalAmount);
                int result = checklistMapper.insertChecklist(checklist);
                if (result != 1) {
                    throw new RuntimeException("Tips：入库清单创建异常！");
                }
                parent = checklist.getId();     // 设置本批次入库的入库清单的ID
            } else {
                return 0;   // 如果空的记录，直接返回失败
            }

            records.forEach(record -> {
                // 将LocalDate格式化为字符串
                String recordBirthday = record.getBirthday().format(formatter); // 格式化页面提交过来的日期

                // 先查询是否存在相同的库存
                List<Stock> stocks = stockMapper.selectStock(record.getWhat());
                int isHave = 0;     // 标识是否有同样的库存
                for (Stock stock : stocks) {
                    if (stock.getPrice() == record.getPrice() && recordBirthday.equals(stock.getBirthday().format(formatter))) {
                        isHave = stock.getId();
                        break;
                    }
                }

                record.setWho(user.getId());        // 操作用户编号
                record.setType(1);                  // 入库-1 出库-2
                record.setParent(parent);           // 清单编号

                int i, j;   // 库存操作结果, 记录操作结果

                if (isHave == 0) {  // 没有相同的库存
                    Stock stock = new Stock();      // 创建新的库存
                    stock.setMedicineId(record.getWhat());
                    stock.setBirthday(record.getBirthday());
                    stock.setPrice(record.getPrice());
                    stock.setCount(record.getCount());
                    stock.setVersion(0);

                    i = stockMapper.insertStock(stock);     // 插入新的库存

                } else {    // 存在相同的库存

                    Stock stock = stockMapper.getStockById(isHave);
                    i = stockMapper.updateStock(stock.getId(), (stock.getCount() + record.getCount()), stock.getVersion());     // 入库 加法操作
                }

                j = recordMapper.insertRecord(record);      // 添加库存变动记录

                if (i != 1 || j != 1) {
                    throw new RuntimeException("Tips：入库失败！事务回滚！");
                }
            });

            return isSuccess.get();
        }
        throw new RuntimeException("Tips：操作频繁，请稍后重试！");
    }

    /**
     * 药品出库(单个药品)
     * 使用 乐观锁：适合高并发场景→解决多人同时出库/入库导致的库存一致性问题
     * 优点：无锁竞争，高并发下性能好
     * 缺点：需要处理重试逻辑，冲突频繁时可能导致多次重试失败（需限制最大重试次数）
     *
     * @param record 出库记录
     * @param user   操作用户
     * @return 出库结果
     */
    @Override
    @Transactional
    public int outputStock(Record record, User user) {

        int maxRetry = 3;       // 最大重试次数
        int retryCount = 0;     // 当前重试次数

        while (retryCount < maxRetry) {
            Stock stock = stockMapper.getStockById(record.getId());

            record.setWhat(stock.getMedicineId());
            record.setType(2);          // 入库-1 出库-2
            record.setBirthday(stock.getBirthday());
            record.setFrom(1);          // 目前出库均从本地医院仓库，故供应商编号为1

            record.setWho(user.getId());
            record.setParent(1);        // 目前出库默认走默认清单

            int i = recordMapper.insertRecord(record);

            int newCount = stock.getCount() - record.getCount();

            int j;

            if (newCount > 0) {
                j = stockMapper.updateStock(stock.getId(), newCount, stock.getVersion());   // 出库 减法操作
            } else if (newCount == 0) {
                j = stockMapper.deleteStock(stock.getId());     // 如果库存为零，则直接删除库存信息
            } else {
                throw new RuntimeException("Tips：入库失败！事务回滚！");
            }

            if (i != 1 || j != 1) {
                throw new RuntimeException("Tips：入库失败！事务回滚！");
            } else {
                return 1;
            }
        }
        throw new RuntimeException("Tips：操作频繁，请稍后重试！");
    }

}