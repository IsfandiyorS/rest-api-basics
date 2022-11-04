package com.epam.esm.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Class {@code WebConfiguration} contains spring configuration for web subproject.
 *
 * @author Sultonov Isfandiyor
 * @version 1.0
 * @see WebMvcConfigurer
 */
@Configuration
@ComponentScan("com.epam.esm")
@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {
}
