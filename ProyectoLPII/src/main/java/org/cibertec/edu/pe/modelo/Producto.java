package org.cibertec.edu.pe.modelo;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProducto;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 100, message = "La descripción no debe superar los 100 caracteres")
    private String descripcion;

    @Min(value = 0, message = "El precio no puede ser negativo")
    @Digits(integer = 6, fraction = 2, message = "El precio debe ser un número válido ")
    private double precio;

    @Min(value = 1, message = "El stock debe ser al menos 1 unidad")
    private int stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codGenero")
    @NotNull(message = "Debe seleccionar un género")
    private Genero genero;

    @NotBlank(message = "Debe ingresar el nombre de la imagen")
    private String imagen;

    @Column(name = "est_prod", nullable = false)
    private Boolean idEstado = true;


    public Producto() {}


    public Producto(int idProducto, String descripcion, double precio, int stock, Genero genero, String imagen, Boolean idEstado) {
        this.idProducto = idProducto;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.genero = genero;
        this.imagen = imagen;
        this.idEstado = idEstado;
    }



    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Boolean getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Boolean idEstado) {
        this.idEstado = idEstado;
    }

    public boolean isActivo() {
        return Boolean.TRUE.equals(this.idEstado);
    }
}
