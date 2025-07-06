package org.cibertec.edu.pe.repositorio;

import org.cibertec.edu.pe.modelo.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGeneroRepositorio extends JpaRepository<Genero,Integer>{

}
