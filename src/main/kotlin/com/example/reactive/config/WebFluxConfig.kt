package com.example.reactive.config

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.i18n.LocaleContext
import org.springframework.context.i18n.SimpleLocaleContext
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.validation.Validator
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
import org.springframework.web.reactive.config.DelegatingWebFluxConfiguration
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.i18n.LocaleContextResolver
import java.util.*

/**
 * WebFluxConfig
 *
 * @author archx
 * @since 2020/9/29 10:25
 */
@Configuration
class WebFluxConfig : DelegatingWebFluxConfiguration() {

    // ---------------------------------------
    // |    I 1 8 N  /  V A L I D A T O R    |
    // ---------------------------------------

    @Bean("messageSource")
    fun buildMessageSource(): MessageSource {
        val messageSource = ResourceBundleMessageSource()
        messageSource.setDefaultEncoding("UTF-8")
        messageSource.setCacheSeconds(10)
        messageSource.setBasenames("i18n/messages", "i18n/ValidationMessages")
        return messageSource;
    }

    override fun getValidator(): Validator {
        val validator = LocalValidatorFactoryBean()
        validator.setValidationMessageSource(buildMessageSource())
        return validator
    }

    override fun localeContextResolver(): LocaleContextResolver {

        return object : LocaleContextResolver {
            override fun resolveLocaleContext(exchange: ServerWebExchange): LocaleContext {
                val language = exchange.request.headers.getFirst("Accept-Language") ?: ""

                var targetLocale = Locale.getDefault()
                if (language.isNotEmpty()) {
                    targetLocale = Locale.forLanguageTag(language)
                }
                return SimpleLocaleContext(targetLocale)
            }

            override fun setLocaleContext(exchange: ServerWebExchange, localeContext: LocaleContext?) {
                throw UnsupportedOperationException("Not Supported")
            }
        }
    }
}