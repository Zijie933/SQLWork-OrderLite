package com.restaurant.backend.service.impl;

import com.restaurant.backend.entity.Customer;
import com.restaurant.backend.repository.CustomerRepository;
import com.restaurant.backend.service.CustomerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerByOpenId(String wxOpenid) {
        return customerRepository.findById(wxOpenid).orElse(null);
    }

    @Override
    public Customer getCustomerByPhone(String phone) {
        return customerRepository.findByPhone(phone);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    @Transactional
    public void updateCustomerStatus(String wxOpenid, Integer status) {
        customerRepository.updateStatus(wxOpenid, status);
    }

    @Override
    @Transactional
    public void updateCustomerPhone(String wxOpenid, String phone) {
        customerRepository.updatePhone(wxOpenid, phone);
    }

    @Override
    @Transactional
    public void updateCustomerAddress(String wxOpenid, String address) {
        customerRepository.updateAddress(wxOpenid, address);
    }

    @Override
    public boolean isPhoneRegistered(String phone) {
        return customerRepository.existsByPhone(phone);
    }
}