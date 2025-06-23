package com.restaurant.backend.service;

import com.restaurant.backend.entity.SystemConfig;

import java.util.List;

public interface SystemConfigService {
    SystemConfig getConfig(String key);
    
    List<SystemConfig> getAllConfigs();
    
    SystemConfig updateConfig(String key, String value);
    
    SystemConfig createConfig(SystemConfig config);
    
    void deleteConfig(String key);
}