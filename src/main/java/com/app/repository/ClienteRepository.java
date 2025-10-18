package com.app.repository;

import com.app.model.ClienteEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface ClienteRepository extends CrudRepository<ClienteEntity, BigInteger> {

    @EntityGraph(attributePaths = "cotacao")
    Optional<ClienteEntity> findWithCotacaoByUsername(String username);

    Optional<ClienteEntity> findByUsername(String username);

}