package com.restaurant.backend.repository;

import com.restaurant.backend.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    Customer findByPhone(String phone);
    
    boolean existsByPhone(String phone);
    
    @Modifying
    @Query("UPDATE Customer c SET c.status = ?2 WHERE c.wxOpenid = ?1")
    void updateStatus(String wxOpenid, Integer status);
    
    @Modifying
    @Query("UPDATE Customer c SET c.phone = ?2 WHERE c.wxOpenid = ?1")
    void updatePhone(String wxOpenid, String phone);
    
    @Modifying
    @Query("UPDATE Customer c SET c.address = ?2 WHERE c.wxOpenid = ?1")
    void updateAddress(String wxOpenid, String address);
}