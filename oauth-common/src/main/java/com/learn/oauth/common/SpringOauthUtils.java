package com.learn.oauth.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;

public class SpringOauthUtils {

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static GlobalUser getUser() {
        Authentication authentication = getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof String && authentication.isAuthenticated()) {
            String userInfoJson = (String) principal;
            Map<String, Object> map = JsonUtils.fromJson(userInfoJson, Map.class);
            GlobalUser user = new GlobalUser((String) map.get("account_name"), "N/A", authentication.getAuthorities());
            user.setExtendAttr(map);
            return user;
        }
        return null;
    }

}
