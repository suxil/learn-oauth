package com.learn.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
@Order(0)
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 定义授权和令牌端点以及令牌服务。
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    }

    // 定义令牌端点上的安全性约束。
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    // 用于定义客户端详细信息服务的配置程序。可以初始化客户端详细信息，也可以只引用现有商店。
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory()
                .withClient("clientId")
                .secret(passwordEncoder.encode("secret"))
                .authorizedGrantTypes("authorization_code")
                .scopes("read", "write")
                .autoApprove(true)
                .authorities("USER")
                .redirectUris(
                        "http://localhost:10101/auth/login",
                        "http://localhost:10201/client1/login",
                        "http://localhost:10202/client2/login"
                );
    }

}

