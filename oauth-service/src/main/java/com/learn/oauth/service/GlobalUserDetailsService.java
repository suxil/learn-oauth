package com.learn.oauth.service;

import com.learn.oauth.common.GlobalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class GlobalUserDetailsService implements UserDetailsService {

    private static final String QUERY_SQL = "select account_id, account_name, password, true_name, email, phone, is_delete from usr_account where is_delete = 0 and account_name = ?";
    private String passwordName = "password";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.hasLength(username)) {
            Map<String, Object> userDetail = jdbcTemplate.queryForMap(QUERY_SQL, username);
            System.out.println(userDetail);

            if (userDetail.size() > 0) {
                String password = (String) userDetail.get(passwordName);

                GlobalUser user = new GlobalUser(username, password, AuthorityUtils.createAuthorityList("USER"));
                userDetail.remove(passwordName);
                user.setExtendAttr(userDetail);

                return user;
            }
        }
        return null;
    }

}
