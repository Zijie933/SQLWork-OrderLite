package com.restaurant.backend.service.impl;

import com.restaurant.backend.entity.SystemConfig;
import com.restaurant.backend.repository.SystemConfigRepository;
import com.restaurant.backend.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemConfigServiceImpl implements SystemConfigService {

    @Autowired
    private SystemConfigRepository systemConfigRepository;

    @Override
    public SystemConfig getConfig(String key) {
        return systemConfigRepository.findByConfigKey(key)
                .orElse(null);
    }

    @Override
    public List<SystemConfig> getAllConfigs() {
        return systemConfigRepository.findAll();
    }

    @Override
    public SystemConfig updateConfig(String key, String value) {
        SystemConfig config = getConfig(key);
        if (config != null) {
            config.setConfigValue(value);
            return systemConfigRepository.save(config);
        }
        return null;
    }

    @Override
    public SystemConfig createConfig(SystemConfig config) {
        return systemConfigRepository.save(config);
    }

    @Override
    public void deleteConfig(String key) {
        SystemConfig config = getConfig(key);
        if (config != null) {
            systemConfigRepository.delete(config);
        }
    }
}