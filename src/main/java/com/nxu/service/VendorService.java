package com.nxu.service;

import com.github.pagehelper.PageInfo;
import com.nxu.entity.Vendor;

public interface VendorService {

    int addVendor(Vendor vendor);

    int setVendor(Vendor vendor);

    Vendor getOneVendor(int vendorId);

    PageInfo<Vendor> getAllVendor(Integer pageNum, Integer pageSize);
}