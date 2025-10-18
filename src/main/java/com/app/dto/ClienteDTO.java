package com.app.dto;

import com.app.model.ClienteEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@Builder
@AllArgsConstructor
public final class ClienteDTO implements UserDetails {

    @Schema(description = "Nome do cliente", example = "Renata",
            requiredMode = Schema.RequiredMode.REQUIRED)
//    @NotBlank
    private String nome;

    @Schema(description = "Nome de usuário do aplicação", example = "dev_ren",
            requiredMode = Schema.RequiredMode.REQUIRED)
//    @NotBlank
    private String username;

    @Schema(description = "Senha do usuário do aplicação", example = "dev_@xP12r",
            requiredMode = Schema.RequiredMode.REQUIRED)
//    @NotBlank
    private String password;

    @Schema(description = "Email do usuário do aplicação",
            example = "dev_re@teste.com",
            requiredMode = Schema.RequiredMode.REQUIRED)
//    @NotBlank
    private String email;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_USER")
        );
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public static ClienteDTO valueOf(ClienteEntity entity) {
        return new ClienteDTOBuilder()
                .username(entity.getUsername())
                .password(entity.getPassword())
                .nome(entity.getNome())
                .email(entity.getEmail())
                .build();
    }
}