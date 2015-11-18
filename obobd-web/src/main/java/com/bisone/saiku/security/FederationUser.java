package com.bisone.saiku.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by will on 15-8-31.
 */
public class FederationUser extends org.springframework.security.core.userdetails.User {

    private static final long serialVersionUID = -2231762973730849416L;



    public FederationUser(String username, String password, boolean enabled, boolean accountNonExpired,
                          boolean credentialsNonExpired, boolean accountNonLocked,
                          Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public FederationUser(String username, String password,
                          Collection<? extends GrantedAuthority> authorities) {
        super(username, password, true, true, true, true, authorities);
    }


}