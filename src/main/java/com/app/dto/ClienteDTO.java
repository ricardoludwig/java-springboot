package com.app.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

import com.app.model.ClienteEntity;
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

    private String nome;

    private String username;

    private String password;

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


