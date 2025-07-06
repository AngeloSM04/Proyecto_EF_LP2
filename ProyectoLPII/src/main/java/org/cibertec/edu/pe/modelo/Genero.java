package org.cibertec.edu.pe.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "genero")
public class Genero {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codGenero;
	private String nombreGenero;
	
	
	public Genero() {
	}

	public Genero(int codGenero, String nombreGenero) {
		this.codGenero = codGenero;
		this.nombreGenero = nombreGenero;
	}

	public int getCodGenero() {
		return codGenero;
	}

	public void setCodGenero(int codGenero) {
		this.codGenero = codGenero;
	}

	public String getNombreGenero() {
		return nombreGenero;
	}

	public void setNombreGenero(String nombreGenero) {
		this.nombreGenero = nombreGenero;
	}
	

}
