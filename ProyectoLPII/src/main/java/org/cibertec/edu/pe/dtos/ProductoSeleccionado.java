package org.cibertec.edu.pe.dtos;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoSeleccionado {

    @NotNull(message = "Seleccione un producto")
    private Integer idProducto;

    private String descripcion;

    @NotNull(message = "El precio es requerido")
    @Positive(message = "El precio debe ser mayor a 0")
    private double precio;

    @NotNull(message = "La cantidad es requerida")
    @Positive(message = "Minimo 1")
    private int cantidad;

    public double getSubtotal() {
        return precio * cantidad;
    }
}
