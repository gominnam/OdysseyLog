package com.example.odysseylog.config

import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ModelMapperConfig {

    @Bean
    fun modelMapper(): ModelMapper {
        return ModelMapper().apply {
            configuration.isFieldMatchingEnabled = true // getter/setter 메소드가 없는 필드도 매핑할 수 있다.
            configuration.isSkipNullEnabled = true // null 값을 가진 속성의 매핑을 건너뛴다.
            configuration.matchingStrategy = MatchingStrategies.STRICT
            configuration.fieldAccessLevel = org.modelmapper.config.Configuration.AccessLevel.PRIVATE
        }
    }
}