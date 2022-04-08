package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TcCp;

@Repository
public interface TcCpRepository extends JpaRepository<TcCp, Long> {
	
	@Query("Select c from TcCp c where c.sCp like :cp% order by c.sCp asc")
	public List<TcCp> obtenerCp(String cp);

}
