package com.learn.oauth.config;

import com.learn.oauth.common.GlobalUser;
import com.learn.oauth.common.JsonUtils;
import com.learn.oauth.service.GlobalUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

/**
 * @Author: luxq
 * @Description:
 * @Date: Created in 2018/9/12 0012 13:22
 */
@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private GlobalUserDetailsService globalUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public GlobalAuthenticationManager authenticationManager() {
        return new GlobalAuthenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("login", "/oauth/*").permitAll()
                .anyRequest().authenticated()

                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .parentAuthenticationManager(authenticationManager());
//                .userDetailsService(globalUserDetailsService);
//                .inMemoryAuthentication()
//                .withUser("admin").password(passwordEncoder().encode("123")).roles("USER");
    }

    private class GlobalAuthenticationManager implements AuthenticationManager {
        @Override
        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            String username = (String) authentication.getPrincipal();
            UserDetails user = globalUserDetailsService.loadUserByUsername(username);
            if (user != null) {
                if (user instanceof GlobalUser) {
                    GlobalUser globalUser = (GlobalUser) user;
                    CharSequence rawPassword = (CharSequence) authentication.getPrincipal();
                    if (StringUtils.hasLength(rawPassword) && passwordEncoder().matches(rawPassword, globalUser.getPassword())) {
                        String userInfoJson = JsonUtils.toJson(globalUser.getExtendAttr());
                        // 认证成功
                        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(userInfoJson, "N/A", AuthorityUtils.createAuthorityList("ROLE_USER"));
                        result.setDetails(globalUser.getExtendAttr().toString());
                        SecurityContextHolder.getContext().setAuthentication(result);
                        return result;
                    }
                }
            }
            return null;
        }
    }

}