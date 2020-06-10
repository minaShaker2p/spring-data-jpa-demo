package com.mina.springdata.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
public class DeleteByOriginRepositoryImpl implements DeleteByOriginRepository {
    @Autowired
    private final EntityManager entityManager;

    @Override
    public void deleteByOrigin(String origin) {
        entityManager.createNativeQuery("DELETE FROM flight WHERE origin = ?")
                .setParameter(1, origin)
                .executeUpdate();
    }
}
