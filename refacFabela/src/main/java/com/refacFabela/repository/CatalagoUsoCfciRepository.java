package com.refacFabela.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.refacFabela.model.TcUsocfdi;

@Repository
public interface CatalagoUsoCfciRepository extends JpaRepository<TcUsocfdi, Long> {

	public List<TcUsocfdi> findBynEstatus(int estatus);

}
