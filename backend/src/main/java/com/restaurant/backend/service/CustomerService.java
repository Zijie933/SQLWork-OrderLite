package com.restaurant.backend.service;

import com.restaurant.backend.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    
    Customer updateCustomer(Customer customer);
    
    Customer getCustomerByOpenId(String wxOpenid);
    
    Customer getCustomerByPhone(String phone);
    
    List<Customer> getAllCustomers();
    
    void updateCustomerStatus(String wxOpenid, Integer status);
    
    void updateCustomerPhone(String wxOpenid, String phone);
    
    void updateCustomerAddress(String wxOpenid, String address);
    
    boolean isPhoneRegistered(String phone);
}