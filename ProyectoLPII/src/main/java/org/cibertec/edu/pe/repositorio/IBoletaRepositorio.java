package org.cibertec.edu.pe.repositorio;

import java.util.List;

import org.cibertec.edu.pe.modelo.Boleta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBoletaRepositorio extends JpaRepository<Boleta, Integer> {
	List<Boleta> findAllByOrderByNumBoletaDesc();
}
