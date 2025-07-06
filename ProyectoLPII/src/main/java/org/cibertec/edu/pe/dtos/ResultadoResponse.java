package org.cibertec.edu.pe.dtos;

public class ResultadoResponse {
    private boolean success;
    private String mensaje;
    private boolean nuevoEstado;

    public ResultadoResponse(boolean success, String mensaje) {
        this.success = success;
        this.mensaje = mensaje;
    }

    public ResultadoResponse(boolean success, String mensaje, boolean nuevoEstado) {
        this.success = success;
        this.mensaje = mensaje;
        this.nuevoEstado = nuevoEstado;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMensaje() {
        return mensaje;
    }

    public boolean isNuevoEstado() {
        return nuevoEstado;
    }
}
