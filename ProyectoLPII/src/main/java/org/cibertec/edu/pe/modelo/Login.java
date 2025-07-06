package org.cibertec.edu.pe.modelo;


public class Login {

	private String usuario;
	private String clave;
	
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public Login() {
	}

	public Login(String usuario, String clave) {
		this.usuario = usuario;
		this.clave = clave;
	}
	
}
