package org.cibertec.edu.pe.servicio;

import java.util.List;

import org.cibertec.edu.pe.modelo.Genero;
import org.cibertec.edu.pe.repositorio.IGeneroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeneroServicio {

	@Autowired
	private IGeneroRepositorio _generoRepository;

	public List<Genero> getAll() {
		return _generoRepository.findAll();
	}
}
