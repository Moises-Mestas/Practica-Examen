package com.example.msauth.controller;

import com.example.msauth.dto.AuthUserDto;
import com.example.msauth.entity.AuthUser;
import com.example.msauth.entity.TokenDto;
import com.example.msauth.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthUserController {

    @Autowired
    AuthUserService authUserService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthUserDto authUserDto) {
        TokenDto tokenDto = authUserService.login(authUserDto);
        if (tokenDto == null) {
            return ResponseEntity.badRequest().body("Credenciales incorrectas");
        }
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validate(@RequestParam String token) {
        TokenDto tokenDto = authUserService.validate(token);
        if (tokenDto == null) {
            return ResponseEntity.badRequest().body("Token inválido o expirado");
        }
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody AuthUserDto authUserDto) {
        AuthUser authUser = authUserService.save(authUserDto);
        if (authUser == null) {
            return ResponseEntity.badRequest().body("El nombre de usuario ya está en uso o hubo un error al registrarse.");
        }
        return ResponseEntity.ok(authUser);
    }
}
