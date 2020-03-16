package com.wiz.sample;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * テスト用Configクラス。
 * <br>
 * Configを読み込みたいテストクラスでは@Importをつけること。
 *
 * @version 1.00(2020/03/16)
 * @author C.Shinagawa
 */
@TestConfiguration
public class TestConfig implements WebMvcConfigurer {

    /**
     *
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.stream()
                .filter(converter -> converter instanceof MappingJackson2HttpMessageConverter)
                .findFirst()
                .ifPresent(converter -> ((MappingJackson2HttpMessageConverter) converter).setDefaultCharset(StandardCharsets.UTF_8));
    }
}
