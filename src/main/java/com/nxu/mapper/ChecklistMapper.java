package com.nxu.mapper;

import com.nxu.entity.Checklist;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ChecklistMapper {

    /**
     * 添加操作清单
     *
     * @param checklist 操作清单
     * @return 清单编号
     */
    int insertChecklist(Checklist checklist);

    /**
     * 审核操作清单
     *
     * @param id         清单编号
     * @param verifyUser 审核用户
     * @return 审核结果
     */
    int updateChecklist(@Param("id") int id, @Param("verifyUser") int verifyUser);

}