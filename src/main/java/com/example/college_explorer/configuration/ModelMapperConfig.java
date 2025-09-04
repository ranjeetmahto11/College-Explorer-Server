// File: src/main/java/com/example/college_explorer/configuration/ModelMapperConfig.java
package com.example.college_explorer.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.college_explorer.dto.CollegeRequest;
import com.example.college_explorer.model.College;

@Configuration
public class ModelMapperConfig {
    
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Configuration
        modelMapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.STRICT)
            .setFieldMatchingEnabled(true)
            .setSkipNullEnabled(true);

        // Custom Mapping
        modelMapper.typeMap(CollegeRequest.class, College.class)
            .addMappings(mapper -> {
                // If you have fields to map manually, do it here:
                // mapper.map(CollegeRequest::getSomeField, College::setSomeOtherField);
            });

        return modelMapper;
    }
}
