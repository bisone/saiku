package com.bisone.saiku.security;

/**
 * Created by will on 15-8-24.
 */

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Will
 *
 */
public class UserToken implements UserDetails {

    private static final long serialVersionUID = -5604125276882353820L;

    private String userName;
    private String password;
    private Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;

    private User user;

    public UserToken(User user) {
        if (user == null)
            throw new IllegalArgumentException("user is null");
        this.user = user;
        if (user.isAdmin()) {
            authorities.add(new SimpleGrantedAuthority(User.ROLE_ADMIN));
            authorities.add(new SimpleGrantedAuthority(User.ROLE_USER));
        } else
            authorities.add(new SimpleGrantedAuthority(User.ROLE_USER));
    }

    public User getUser() {
        return user;
    }

    public UserToken(String userName, String password, Set auth) {
        this.userName = userName;
        this.password = password;
        this.authorities = auth;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return userName;
    }

    public Set<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

}
