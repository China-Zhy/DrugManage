package com.nxu.mapper;

import com.nxu.entity.Vendor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VendorMapper {

    int insertVendor(Vendor vendor);

    int updateVendor(Vendor vendor);

    Vendor selectVendorById(int id);

    List<Vendor> selectAllVendor(Integer status);
}