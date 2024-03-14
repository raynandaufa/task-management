package com.task.management.service;

import com.task.management.dto.AuthenticationRequestDto;
import com.task.management.dto.SignupRequestDto;
import com.task.management.dto.TokenDto;
import com.task.management.entity.User;
import com.task.management.repository.UserRepository;
import com.task.management.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GuestService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;


    public TokenDto generateToken(AuthenticationRequestDto requestDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken
                            (requestDto.getUsername().toLowerCase(), requestDto.getPassword())
            );
            return TokenDto
                    .builder()
                    .username(requestDto.getUsername())
                    .token(jwtService.generateToken(requestDto.getUsername()))
                    .build();

        }catch (UsernameNotFoundException | BadCredentialsException e){
            throw new UsernameNotFoundException("invalid user request !");
        }
    }


    public void signup(SignupRequestDto requestDto) {
        User user = modelMapper.map(requestDto, User.class);
        user.setUsername(user.getUsername().toLowerCase());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
