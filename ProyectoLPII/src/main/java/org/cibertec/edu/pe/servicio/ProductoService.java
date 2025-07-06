package org.cibertec.edu.pe.servicio;

import java.util.List;

import org.cibertec.edu.pe.dtos.ProductoFilter;
import org.cibertec.edu.pe.dtos.ResultadoResponse;
import org.cibertec.edu.pe.modelo.Producto;
import org.cibertec.edu.pe.repositorio.IProductoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {

    @Autowired
    private IProductoRepositorio productoRepositorio;

    public List<Producto> getAll() {
        return productoRepositorio.findAllByOrderByIdProductoDesc();
    }

    public List<Producto> search(ProductoFilter filtro) {
        return productoRepositorio.findAllWithFilters(filtro.getIdGenero());
    }

    public ResultadoResponse create(Producto producto) {
        try {
            Producto registrado = productoRepositorio.save(producto);
            String mensaje = String.format("Producto con código %d registrado", registrado.getIdProducto());
            return new ResultadoResponse(true, mensaje);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResultadoResponse(false, "Error al registrar: " + ex.getMessage());
        }
    }

    public Producto getOne(int id) {
        return productoRepositorio.findById(id).orElseThrow();
    }

    public ResultadoResponse update(Producto producto) {
        try {
            Producto registrado = productoRepositorio.save(producto);
            String mensaje = String.format("Producto con código %d actualizado", registrado.getIdProducto());
            return new ResultadoResponse(true, mensaje);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResultadoResponse(false, "Error al actualizar: " + ex.getMessage());
        }
    }
    
    

    public ResultadoResponse cambiarEstado(int id) {
        Producto producto = productoRepositorio.findById(id).orElse(null);
        if (producto == null) {
            return new ResultadoResponse(false, "Producto no encontrado");
        }

        boolean nuevoEstado = !Boolean.TRUE.equals(producto.getIdEstado());
        producto.setIdEstado(nuevoEstado);

        try {
            productoRepositorio.save(producto);
            String mensaje = String.format("Producto con código %d %s",
                    producto.getIdProducto(),
                    nuevoEstado ? "activado" : "desactivado");

            return new ResultadoResponse(true, mensaje, nuevoEstado);  
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResultadoResponse(false, "Error al cambiar estado: " + ex.getMessage());
        }
    }
    
    
    public List<Producto> listarProductosActivos() {
        return productoRepositorio.findByIdEstadoTrueOrderByIdProductoDesc();
    }

    
    

}

    
    
    

