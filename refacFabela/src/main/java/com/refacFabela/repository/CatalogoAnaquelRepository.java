package com.refacFabela.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.refacFabela.model.TcAnaquel;

@Repository
public interface CatalogoAnaquelRepository extends JpaRepository<TcAnaquel, Long> {
	
	
	
	public TcAnaquel findBynId(Long nId);
	

}
