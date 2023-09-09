package com.fisa.tick3t.jwt;

import com.fisa.tick3t.domain.dto.UserDto;
import com.fisa.tick3t.domain.vo.User;
import com.fisa.tick3t.global.CustomException;
import com.fisa.tick3t.repository.UserRepository;
import com.fisa.tick3t.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
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
            if(userDto.getStatusCd().equals("D")){
                throw new CustomException(ResponseCode.WITHDRAWN_USER);
            }
            User user = userDto.ToUser(userDto);
            return new JwtUserDetails(user);
        } catch (UsernameNotFoundException | CustomException e ) {
            log.error(e.getMessage());
            return null;
        }

    }
}