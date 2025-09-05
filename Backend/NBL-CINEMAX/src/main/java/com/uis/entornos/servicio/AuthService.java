package com.uis.entornos.servicio;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.uis.entornos.dto.AuthResponse;
import com.uis.entornos.dto.LoginRequest;
import com.uis.entornos.dto.RegisterRequest;
import com.uis.entornos.modelo.Usuario;
import com.uis.entornos.repositorio.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // Inyecta las dependencias marcadas como 'final' vía constructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        // 1. Usa el AuthenticationManager de Spring para validar el usuario y contraseña
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        
        // 2. Si la autenticación fue exitosa, busca el usuario
        UserDetails user = usuarioRepository.findByEmail(request.getEmail()).orElseThrow();
        
        // 3. Genera el token para ese usuario
        String token = jwtService.getToken(user);
        
        // 4. Devuelve el token en la respuesta
        return AuthResponse.builder()
            .token(token)
            .build();
    }

    public AuthResponse register(RegisterRequest request) {
        // 1. Crea un objeto Usuario a partir de los datos del DTO
        Usuario usuario = Usuario.builder()
            .nombre(request.getNombre())
            .apellido(request.getApellido())
            .email(request.getEmail())
            // ¡MUY IMPORTANTE! Cifra la contraseña antes de guardarla
            .password(passwordEncoder.encode(request.getPassword())) 
            .build();
        
        // 2. Guarda el nuevo usuario en la base de datos
        usuarioRepository.save(usuario);
        
        // 3. Genera el token para el nuevo usuario
        String token = jwtService.getToken(usuario);
        
        // 4. Devuelve el token
        return AuthResponse.builder()
            .token(token)
            .build();
    }
}


