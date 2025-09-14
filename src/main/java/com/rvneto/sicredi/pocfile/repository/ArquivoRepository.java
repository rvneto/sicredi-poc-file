package com.rvneto.sicredi.pocfile.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ArquivoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Long getNextArquivoSequence() {
        return ((Number) entityManager.createNativeQuery("SELECT SEQ_ARQUIVO.NEXTVAL FROM DUAL")
                .getSingleResult()).longValue();
    }
}
