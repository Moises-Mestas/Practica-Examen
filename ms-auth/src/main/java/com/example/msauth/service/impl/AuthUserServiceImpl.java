package com.example.msauth.service.impl;

import com.example.msauth.dto.AuthUserDto;
import com.example.msauth.entity.AuthUser;
import com.example.msauth.entity.TokenDto;
import com.example.msauth.repository.AuthUserRepository;
import com.example.msauth.security.JwtProvider;
import com.example.msauth.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthUserServiceImpl implements AuthUserService {

    @Autowired
    AuthUserRepository authUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtProvider jwtProvider;

    @Override
    public AuthUser save(AuthUserDto authUserDto) {
        // Verificamos si el usuario ya existe
        Optional<AuthUser> user = authUserRepository.findByUsername(authUserDto.getUserName());
        if (user.isPresent()) {
            return null;  // Si el usuario ya existe, retornamos null
        }

        // Si el usuario no existe, lo creamos
        String password = passwordEncoder.encode(authUserDto.getPassword());
        AuthUser authUser = AuthUser.builder()
                .userName(authUserDto.getUserName())
                .password(password)
                .build();

        return authUserRepository.save(authUser);  // Guardamos el nuevo usuario
    }

    @Override
    public TokenDto login(AuthUserDto authUserDto) {
        // Buscamos al usuario por su nombre de usuario
        Optional<AuthUser> user = authUserRepository.findByUsername(authUserDto.getUserName());
        if (!user.isPresent()) {
            return null;  // Si el usuario no existe, retornamos null
        }

        // Verificamos si la contraseña es correcta
        if (passwordEncoder.matches(authUserDto.getPassword(), user.get().getPassword())) {
            // Si la contraseña es correcta, generamos y retornamos el token
            return new TokenDto(jwtProvider.createToken(user.get()));
        }

        return null;  // Si la contraseña no es correcta, retornamos null
    }

    @Override
    public TokenDto validate(String token) {
        // Validamos el token
        if (!jwtProvider.validate(token)) {
            return null;  // Si el token no es válido, retornamos null
        }

        // Obtenemos el nombre de usuario desde el token
        String username = jwtProvider.getUserNameFromToken(token);

        // Verificamos si el usuario existe en la base de datos
        Optional<AuthUser> user = authUserRepository.findByUsername(username);
        if (!user.isPresent()) {
            return null;  // Si el usuario no existe, retornamos null
        }

        // Si todo es válido, retornamos el token
        return new TokenDto(token);
    }
}
