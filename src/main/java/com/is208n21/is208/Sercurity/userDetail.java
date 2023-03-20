package com.is208n21.is208.Sercurity;



import com.is208n21.is208.Entity.Model.Role;
import com.is208n21.is208.Entity.Model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
public class userDetail implements UserDetails {
    private String userId;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;


    public static UserDetails createUserDetail(User user) {
        Role role = user.getRole();
        List<SimpleGrantedAuthority> authority = Collections.singletonList(new SimpleGrantedAuthority(role.getNameRole()));
        return new userDetail(user.getUserId(), user.getNameUser(), user.getPassword(), authority);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

