package com.restaurant.backend.controller;

import com.restaurant.backend.entity.SystemConfig;
import com.restaurant.backend.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/config")
public class SystemConfigController {

    @Autowired
    private SystemConfigService systemConfigService;

    @GetMapping
    public ResponseEntity<List<SystemConfig>> getAllConfigs() {
        return ResponseEntity.ok(systemConfigService.getAllConfigs());
    }

    @GetMapping("/{key}")
    public ResponseEntity<SystemConfig> getConfig(@PathVariable String key) {
        SystemConfig config = systemConfigService.getConfig(key);
        if (config != null) {
            return ResponseEntity.ok(config);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<SystemConfig> createConfig(@RequestBody SystemConfig config) {
        return ResponseEntity.ok(systemConfigService.createConfig(config));
    }

    @PutMapping("/{key}")
    public ResponseEntity<SystemConfig> updateConfigByKey(
            @PathVariable String key,
            @RequestParam String value) {
        SystemConfig config = systemConfigService.updateConfig(key, value);
        if (config != null) {
            return ResponseEntity.ok(config);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping
    public ResponseEntity<SystemConfig> updateConfig(@RequestBody SystemConfig config) {
        SystemConfig existingConfig = systemConfigService.getConfig(config.getConfigKey());
        if (existingConfig != null) {
            existingConfig.setConfigValue(config.getConfigValue());
            if (config.getDescription() != null) {
                existingConfig.setDescription(config.getDescription());
            }
            return ResponseEntity.ok(systemConfigService.createConfig(existingConfig));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{key}")
    public ResponseEntity<Void> deleteConfig(@PathVariable String key) {
        systemConfigService.deleteConfig(key);
        return ResponseEntity.ok().build();
    }
}