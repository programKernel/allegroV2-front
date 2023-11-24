package com.allegromini.front.config;

import com.allegromini.front.session.CurrentUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
public class SessionConfig {
    @Bean
    @SessionScope
    public CurrentUser currentUserSession() {
        return new CurrentUser();
    }
}
