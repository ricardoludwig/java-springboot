package com.app.security;

import com.app.dto.ClienteDTO;
import com.app.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private ClienteService service;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/registrar")
    public Map<String, Object> registrar(@RequestBody ClienteDTO clienteDTO) {
        String encodedPass = passwordEncoder.encode(clienteDTO.getPassword());
        clienteDTO.setPassword(encodedPass);

        service.criar(clienteDTO);

        String token = jwtUtil.geraToken(clienteDTO.getUsername());
        return Collections.singletonMap("jwt-token", token);
    }

    @PostMapping("/login")
    public Map<String, Object> login( @RequestBody CredentialsModel body) {
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getUsername(),
                            body.getPassword());
            authenticationManager.authenticate(authInputToken);

            String token = jwtUtil.geraToken(body.getUsername());

            return Collections.singletonMap("jwt-token", token);

        } catch (AuthenticationException authExc) {
            throw new RuntimeException("Usuário ou senha inválidos");
        }
    }
}
