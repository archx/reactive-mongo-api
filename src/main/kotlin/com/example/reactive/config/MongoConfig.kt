package com.example.reactive.config

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper
import org.springframework.data.mongodb.core.convert.MappingMongoConverter

/**
 * MongoConfig
 *
 * @author archx
 * @since 2020/9/29 11:12
 */
@Configuration
class MongoConfig {

    @Bean
    @ConditionalOnMissingBean
    fun reactiveMongoTemplate(reactiveMongoDatabaseFactory: ReactiveMongoDatabaseFactory,
                              converter: MappingMongoConverter): ReactiveMongoTemplate {
        // remove _class
        converter.setTypeMapper(DefaultMongoTypeMapper(null))
        return ReactiveMongoTemplate(reactiveMongoDatabaseFactory, converter)
    }
}