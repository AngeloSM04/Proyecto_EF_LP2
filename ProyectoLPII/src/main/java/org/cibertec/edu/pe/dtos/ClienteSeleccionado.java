package org.cibertec.edu.pe.dtos;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteSeleccionado {

    @NotNull(message = "Debe seleccionar un cliente")
    private Integer idCliente;

    private String nombre;
    private String apellido;
    private String email;
}
