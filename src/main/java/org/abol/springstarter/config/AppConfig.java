package org.abol.springstarter.config;

import org.abol.springstarter.models.BaseUser;
import org.abol.springstarter.services.UserService;
import org.abol.springstarter.services.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public BaseUser baseUser() {
        return new BaseUser();
    }

    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }
}
