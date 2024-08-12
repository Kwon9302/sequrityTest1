package com.cos.security1.config.auth;

import com.cos.security1.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {
    private static final long serialVersionUID = 1L;
    private User user;
    private Map<String, Object> attributes;

    // 일반 시큐리티 로그인시 사용
    public PrincipalDetails(User user) {
        this.user = user;
    }

    // OAuth2.0 로그인시 사용
    public PrincipalDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    public User getUser(){
        System.out.println("PrincipalDetails.getUser");
        return user;
    }

    @Override
    public String getPassword() {
        System.out.println("PrincipalDetails.getPassword");
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        System.out.println("PrincipalDetails.getUsername");
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        System.out.println("PrincipalDetails.isAccountNonExpired");
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        System.out.println("PrincipalDetails.isAccountNonLocked");
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        System.out.println("PrincipalDetails.isCredentialsNonExpired");
        return true;
    }

    @Override
    public boolean isEnabled() {
        System.out.println("PrincipalDetails.isEnabled");
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collet = new ArrayList<>();
        collet.add(()->{ return user.getRole();});
        System.out.println("PrincipalDetails.getAuthorities" + collet.toString());
        return collet;
    }

    // 리소스 서버로 부터 받는 회원정보
    @Override
    public Map<String, Object> getAttributes() {
        System.out.println("PrincipalDetails.getAttributes");
        return attributes;
    }

    // User의 PrimaryKey
    @Override
    public String getName() {
        System.out.println("PrincipalDetails.getName");
        return user.getId()+"";
    }
}
