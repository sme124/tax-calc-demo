package com.demo.config;

import com.taxjar.Taxjar;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class TaxJarConfig {

    @Value("${tax.apiKey}")
    private String taxApiKey;

    @Bean
    public Taxjar getClient() {
        Map<String, Object> params = new HashMap<>();
        params.put("apiUrl", Taxjar.SANDBOX_API_URL);
        return new Taxjar(taxApiKey, params);
    }
}

