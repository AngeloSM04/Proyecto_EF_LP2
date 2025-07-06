package org.cibertec.edu.pe.modelo;



import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "venta") 
@DynamicInsert
@Getter
@Setter
public class Boleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta")
    private int numBoleta;

    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP) 
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date fecha;


    @Column(name = "monto_total", nullable = false)
    private double montoTotal;
    
	@JoinColumn(name = "id_cliente", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Cliente cliente;

    @OneToMany(mappedBy = "boleta", cascade = CascadeType.ALL)
    private List<DetalleBoleta> lstDetalleBoleta;


    public Double getTotal() {
        if (lstDetalleBoleta == null) return 0.0;
        return lstDetalleBoleta.stream().mapToDouble(DetalleBoleta::getSubtotal).sum();
    }

  

    
    
    
}
