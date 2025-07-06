package org.cibertec.edu.pe.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoFilter {
    private Integer idGenero; 
    private Double precioMin;
    private Double precioMax;
}
