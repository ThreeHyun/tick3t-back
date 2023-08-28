package com.fisa.tick3t.jwt;

import com.fisa.tick3t.domain.vo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JwtUserDetails implements UserDetails {

    private User user;

    public JwtUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 권한 목록을 담을 리스트 생성
        List<GrantedAuthority> authorities = new ArrayList<>();

        // 사용자의 역할을 가져와 GrantedAuthority 객체를 생성하고 리스트에 추가
        String role = user.getRole();
        System.out.println(role);
        authorities.add(new SimpleGrantedAuthority(role));

        // 리스트 반환
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getUserPwd();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    public int getUserId() {
        return user.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return "E".equals(user.getStatusCd());
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

