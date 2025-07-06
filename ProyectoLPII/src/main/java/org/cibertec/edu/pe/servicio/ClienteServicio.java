package org.cibertec.edu.pe.servicio;

import java.util.List;

import org.cibertec.edu.pe.dtos.ResultadoResponse;
import org.cibertec.edu.pe.modelo.Cliente;
import org.cibertec.edu.pe.repositorio.IClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServicio {

    @Autowired
    private IClienteRepositorio _clienteRepositorio;

    public Cliente obtenerClientePorId(Integer id) {
        return _clienteRepositorio.findById(id).orElseThrow();
    }
    
    
    public List<Cliente> getAll() {
        return _clienteRepositorio.findAllByOrderByIdClienteDesc();
    }


    public Cliente getOne(int id) {
        return _clienteRepositorio.findById(id).orElseThrow();
    }

    public ResultadoResponse create(Cliente cliente) {
        try {
            Cliente registrado = _clienteRepositorio.save(cliente);
            String mensaje = String.format("Cliente con ID %d registrado", registrado.getIdCliente());
            return new ResultadoResponse(true, mensaje);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResultadoResponse(false, "Error al registrar: " + ex.getMessage());
        }
    }


    public ResultadoResponse update(Cliente cliente) {
        try {
            Cliente actualizado = _clienteRepositorio.save(cliente);
            String mensaje = String.format("Cliente con ID %d actualizado", actualizado.getIdCliente());
            return new ResultadoResponse(true, mensaje);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResultadoResponse(false, "Error al actualizar: " + ex.getMessage());
        }
    }

    public List<Cliente> search(String filtro) {
        return _clienteRepositorio.buscarPorNombreApellidoEmail(filtro);
    }


    public Cliente obtenerClientePorId(int id) {
        return getOne(id); 
    }
    
}
