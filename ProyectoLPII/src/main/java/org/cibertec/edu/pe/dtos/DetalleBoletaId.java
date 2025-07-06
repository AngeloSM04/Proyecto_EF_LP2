package org.cibertec.edu.pe.dtos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DetalleBoletaId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer boleta;   
    private Integer producto;  
}
