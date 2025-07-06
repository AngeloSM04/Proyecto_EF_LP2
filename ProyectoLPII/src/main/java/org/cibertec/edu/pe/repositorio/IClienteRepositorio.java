package org.cibertec.edu.pe.repositorio;

import java.util.List;

import org.cibertec.edu.pe.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IClienteRepositorio extends JpaRepository<Cliente, Integer> {


    List<Cliente> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(String query, String query2);


    List<Cliente> findAllByOrderByIdClienteDesc();


    @Query("""
        select c from Cliente c
        where (:filtro is null or
               lower(c.nombre) like lower(concat('%', :filtro, '%')) or
               lower(c.apellido) like lower(concat('%', :filtro, '%')) or
               lower(c.email) like lower(concat('%', :filtro, '%')))
        order by c.idCliente desc
    """)
    List<Cliente> buscarPorNombreApellidoEmail(@Param("filtro") String filtro);
}
