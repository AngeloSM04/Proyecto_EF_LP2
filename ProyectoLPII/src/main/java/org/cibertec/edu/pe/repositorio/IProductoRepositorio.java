package org.cibertec.edu.pe.repositorio;

import java.util.List;

import org.cibertec.edu.pe.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IProductoRepositorio extends JpaRepository<Producto, Integer> {

    List<Producto> findAllByOrderByIdProductoDesc();

    List<Producto> findAllByGenero_CodGeneroOrderByIdProductoDesc(Integer codGenero);

    @Query("""
        select p from Producto p
        where (:codGenero is null or p.genero.codGenero = :codGenero)
        order by p.idProducto desc
    """)
    List<Producto> findAllWithFilters(@Param("codGenero") Integer codGenero);
    
    List<Producto> findByIdEstadoTrueOrderByIdProductoDesc();

}
