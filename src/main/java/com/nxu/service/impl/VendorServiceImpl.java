package com.nxu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nxu.entity.Vendor;
import com.nxu.mapper.VendorMapper;
import com.nxu.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorServiceImpl implements VendorService {

    @Autowired
    private VendorMapper vendorMapper;

    @Override
    public int addVendor(Vendor vendor) {
        return vendorMapper.insertVendor(vendor);
    }

    @Override
    public int setVendor(Vendor vendor) {
        return vendorMapper.updateVendor(vendor);
    }

    @Override
    public Vendor getOneVendor(int vendorId) {
        return vendorMapper.selectVendorById(vendorId);
    }

    @Override
    public PageInfo<Vendor> getAllVendor(Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageSize == null) {
            return new PageInfo<>(vendorMapper.selectAllVendor());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Vendor> vendors = vendorMapper.selectAllVendor();
        return new PageInfo<>(vendors);
    }
}