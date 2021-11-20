package com.refacFabela.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwCotizacione;
@Repository
public interface CotizacionRepository extends JpaRepository<TwCotizacione, Long> {

}
