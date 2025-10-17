package com.app.repository;

import com.app.model.CotacaoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface CotacaoRepository
        extends CrudRepository<CotacaoEntity, BigInteger> {
}