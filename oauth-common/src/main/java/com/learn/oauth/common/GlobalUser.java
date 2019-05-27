package com.learn.oauth.common;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Map;

public class GlobalUser extends User {

    private Map<String, Object> extendAttr;

    public GlobalUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public GlobalUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public Map<String, Object> getExtendAttr() {
        return extendAttr;
    }

    public void setExtendAttr(Map<String, Object> extendAttr) {
        this.extendAttr = extendAttr;
    }

}
