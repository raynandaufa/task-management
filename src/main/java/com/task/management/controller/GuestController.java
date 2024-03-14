package com.task.management.controller;

import com.task.management.dto.AuthenticationRequestDto;
import com.task.management.dto.SignupRequestDto;
import com.task.management.dto.TokenDto;
import com.task.management.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class GuestController {

    private final GuestService guestService;


    @PostMapping("/sign")
    public ResponseEntity<TokenDto> authentication(@Validated @RequestBody AuthenticationRequestDto requestDto) {
        return new ResponseEntity<>(guestService.generateToken(requestDto), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody @Validated SignupRequestDto requestDto) {
        guestService.signup(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
