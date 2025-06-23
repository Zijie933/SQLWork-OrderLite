package com.restaurant.backend.controller;

import com.restaurant.backend.entity.Customer;
import com.restaurant.backend.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.createCustomer(customer));
    }

    @PutMapping("/{wxOpenid}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable String wxOpenid, 
                                                 @RequestBody Customer customer) {
        customer.setWxOpenid(wxOpenid);
        return ResponseEntity.ok(customerService.updateCustomer(customer));
    }

    @GetMapping("/{wxOpenid}")
    public ResponseEntity<Customer> getCustomer(@PathVariable String wxOpenid) {
        return ResponseEntity.ok(customerService.getCustomerByOpenId(wxOpenid));
    }

    @GetMapping("/phone/{phone}")
    public ResponseEntity<Customer> getCustomerByPhone(@PathVariable String phone) {
        return ResponseEntity.ok(customerService.getCustomerByPhone(phone));
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @PutMapping("/{wxOpenid}/status")
    public ResponseEntity<Void> updateCustomerStatus(@PathVariable String wxOpenid, 
                                                   @RequestParam Integer status) {
        customerService.updateCustomerStatus(wxOpenid, status);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{wxOpenid}/phone")
    public ResponseEntity<Void> updateCustomerPhone(@PathVariable String wxOpenid, 
                                                  @RequestParam String phone) {
        customerService.updateCustomerPhone(wxOpenid, phone);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{wxOpenid}/address")
    public ResponseEntity<Void> updateCustomerAddress(@PathVariable String wxOpenid, 
                                                    @RequestParam String address) {
        customerService.updateCustomerAddress(wxOpenid, address);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/phone/{phone}/exists")
    public ResponseEntity<Boolean> isPhoneRegistered(@PathVariable String phone) {
        return ResponseEntity.ok(customerService.isPhoneRegistered(phone));
    }
}