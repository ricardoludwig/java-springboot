package com.app.security;

import com.app.dto.ClienteDTO;
import com.app.rest.Response;
import com.app.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

import static org.springframework.http.HttpStatus.*;

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
    public ResponseEntity<Response> registrar(@RequestBody ClienteDTO clienteDTO) {
        String encodedPass = passwordEncoder.encode(clienteDTO.getPassword());
        clienteDTO.setPassword(encodedPass);

        boolean criado = service.criar(clienteDTO);
        if (criado) {
            String token = jwtUtil.geraToken(clienteDTO.getUsername());
            Response response = new Response(Collections.singletonMap("jwt-token", token),
                    "Usuário criado");
            return new ResponseEntity<>(response, OK);
        }
        Response response = new Response("Não foi possível criar o seu usuário");
        return new ResponseEntity<>(response, UNPROCESSABLE_ENTITY);

    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Response> refreshToken( @RequestBody CredentialsModel body) {
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getUsername(),
                            body.getPassword());
            authenticationManager.authenticate(authInputToken);

            String token = jwtUtil.geraToken(body.getUsername());
            Response response = new Response(Collections.singletonMap("jwt-token", token));
            return new ResponseEntity<>(response, OK);

        } catch (AuthenticationException authExc) {
            throw new RuntimeException("Usuário ou senha inválidos");
        }
    }
}
