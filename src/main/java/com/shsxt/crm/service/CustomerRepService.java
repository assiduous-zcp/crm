package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.dao.CustomerRepMapper;
import com.shsxt.crm.vo.CustomerRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@SuppressWarnings("all")
@Service
public class CustomerRepService extends BaseService<CustomerRep,Integer> {

    @Autowired
    private CustomerRepMapper customerRepMapper;
}
