package com.board.board.service;

import com.board.board.config.auth.SessionUser;
import com.board.board.domain.User;
import com.board.board.dto.UserDto;
import com.board.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;
    private HttpSession httpSession;

    /* email 중복 검사 */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("해당 사용자가 존재하지 않습니다. : " + username));
        httpSession.setAttribute("user",new SessionUser(user));
        return new CustomUserDetails(user);
    }


}