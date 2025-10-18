package com.app.security;

import com.app.dto.ClienteDTO;
import com.app.rest.Response;
import com.app.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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


@Tag(name = "Autenticação com token JWT",
        description = "Endpoints para criação de usuário e senha e geração " +
                "de token JWT")
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

    @Operation(summary = "Buscar produto por ID", description = "Retorna um produto específico baseado no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado",
                    content = @Content(schema = @Schema(implementation = ClienteDTO.class))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content)
    })
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
    public ResponseEntity<Response> refreshToken( @RequestBody CredentialsDTO body) {
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
