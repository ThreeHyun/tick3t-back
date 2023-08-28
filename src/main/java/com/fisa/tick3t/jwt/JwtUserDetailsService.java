package com.fisa.tick3t.jwt;

import com.fisa.tick3t.domain.dto.UserDto;
import com.fisa.tick3t.domain.vo.User;
import com.fisa.tick3t.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 데이터베이스에서 사용자 정보를 가져옴
        try {
            int userId = userRepository.checkEmail(username);
            UserDto userDto = userRepository.selectUser(userId);
            User user  =userDto.ToUser(userDto);
            return new JwtUserDetails(user);
        }catch (UsernameNotFoundException e){
            e.printStackTrace();
            return null;
        }

    }
}