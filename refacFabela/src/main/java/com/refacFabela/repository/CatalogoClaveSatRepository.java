package com.refacFabela.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TcClavesat;

@Repository
public interface CatalogoClaveSatRepository extends JpaRepository<TcClavesat, Long> {

}
