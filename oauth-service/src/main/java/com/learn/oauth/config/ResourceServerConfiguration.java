package com.learn.oauth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
@Order(-1)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

//    @Autowired
//    private TokenStore tokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        super.configure(resources);
//        resources.tokenStore(tokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http

//                .sessionManagement()
//                .sessionFixation().migrateSession()

//                .and()
                .authorizeRequests()
                .antMatchers("/**").access("#oauth2.hasScope('read')");
    }

}
