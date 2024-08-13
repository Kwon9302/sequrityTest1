package com.cos.security1.config.auth;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 시큐리티 설정에서 loginProcessUrl("/login");
// login요청이 오면 자동으로 UserDetailsService타입으로 IoC되어 있는 loadUserByUserName함수가 실행

@Service
@RequiredArgsConstructor
// 여기는 OAuth2방식이 아닌 일반 회원 로그인을 진행할때!
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("PrincipalDetailsService.loadUserByUsername");
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        } else {
            System.out.println(user.getUsername());
            return new PrincipalDetails(user);
        }
    }
}
