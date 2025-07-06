package org.cibertec.edu.pe.controlador;

import java.util.List;

import javax.validation.Valid;

import org.cibertec.edu.pe.modelo.Cliente;
import org.cibertec.edu.pe.repositorio.IClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"cliente","editar"})
public class ClienteControler {
	
	@Autowired
	private IClienteRepositorio clienteRepositorio;
	
	@GetMapping("/cliente")
	public String listado(Model model) {
	    List<Cliente> lista = clienteRepositorio.findAll();
	    model.addAttribute("clientes", lista);
	    return "cliente";
	}
	
	@PostMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable("id") int idCliente) {
        clienteRepositorio.deleteById(idCliente);
        return "redirect:/cliente"; 
    }
	
	@GetMapping("/editar/{id}")
	public String editarCliente(@PathVariable("id") int id, Model model) {
	    Cliente cliente = clienteRepositorio.findById(id).orElse(null);
	    if (cliente == null) {
	        return "redirect:/cliente"; // por si no se encuentra el cliente
	    }
	    model.addAttribute("cliente", cliente);
	    return "editar_cliente";
	}

	@PostMapping("/guardar")
	public String guardarCambios(@Valid @ModelAttribute("cliente") Cliente cliente,
	                             BindingResult result,
	                             Model model) {
	    if (result.hasErrors()) {
	        // Si hay errores de validación, volver al formulario correspondiente
	        return (cliente.getIdCliente() == null) ? "agregar_cliente" : "editar_cliente";
	    }
	    clienteRepositorio.save(cliente);
	    return "redirect:/cliente";
	}

	@GetMapping("/agregar")
	public String agregarCliente(Model model) {
	    model.addAttribute("cliente", new Cliente());
	    return "agregar_cliente";
	}

	

	@GetMapping("/buscar")
	public String buscarClientes(@RequestParam(name = "query", required = false) String query, Model model) {
	    if (query != null && !query.isEmpty()) {
	        List<Cliente> resultados = clienteRepositorio.findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(query, query);
	        model.addAttribute("resultados", resultados);
	    }
	    return "cliente";
	}


}
