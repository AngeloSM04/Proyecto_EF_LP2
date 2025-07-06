package org.cibertec.edu.pe.modelo;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "detalle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetalleBoleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Integer idDetalle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_venta")
    private Boleta boleta;

    private Integer cantidad;
    private Double subtotal;



    
    
    
    
    public DetalleBoleta(Boleta boleta, Producto producto, Integer cantidad, Double subtotal) {
        this.boleta = boleta;
        this.producto = producto;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }
}
