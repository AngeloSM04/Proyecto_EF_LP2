package org.cibertec.edu.pe.controlador;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.cibertec.edu.pe.dtos.ResultadoResponse;
import org.cibertec.edu.pe.modelo.Login;
import org.cibertec.edu.pe.modelo.Producto;
import org.cibertec.edu.pe.repositorio.IProductoRepositorio;
import org.cibertec.edu.pe.servicio.GeneroServicio;
import org.cibertec.edu.pe.servicio.IProductoServicio;
import org.cibertec.edu.pe.servicio.ProductoService;
import org.cibertec.edu.pe.utils.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class ProductoControlador {
	
	@Autowired
	private IProductoServicio servProducto;
	
	@Autowired
	private ProductoService ProductoService;
	
	@Autowired
	private IProductoRepositorio productoRepositorio;



	//Login
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String getLogin() {
		return "login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(@ModelAttribute(name="Login") Login login, Model m) {
		
		String usuario = login.getUsuario();
		String clave = login.getClave();
		
		if("admin".equals(usuario) && "admin".equals(clave)) {
			m.addAttribute("productos", servProducto.listarProductos());
			m.addAttribute("genero", servProducto.listarGenero());
			return "index";
		}else if (("cliente".equals(usuario) && "cliente".equals(clave))){
			List<Producto> lista = new ArrayList<>();		
			lista = servProducto.listarProductos(); 
			m.addAttribute("productos",lista);
			return "compra";
		}
		
		m.addAttribute("usuarioIncorrecto", true);
		return "login";
	}
	
	
	@PostMapping("/cambiar-estado/{id}")
	@ResponseBody
	public ResultadoResponse cambiarEstado(@PathVariable int id) {
	    return ProductoService.cambiarEstado(id);
	}
	

	
	
	

	@GetMapping({"/productos"})
	public String listarProductos(Model m) {
		m.addAttribute("productos", servProducto.listarProductos());
		m.addAttribute("genero", servProducto.listarGenero());
		return "index";
	}
	
	

	@GetMapping("/producto/nuevo") 
	public String crearProducto(Model m) {
		Producto prod = new Producto();
		m.addAttribute("genero", servProducto.listarGenero());
		m.addAttribute("producto", prod);
		return "nuevo_producto";
	}
	
	@PostMapping("/productos")
	public String guardarProducto(@Valid @ModelAttribute("producto") Producto producto, BindingResult result, Model model) {
	    if (result.hasErrors()) {
	        model.addAttribute("genero", servProducto.listarGenero()); 
	        return "nuevo_producto";
	    }

	    servProducto.guardarProducto(producto);
	    return "redirect:/productos";
	}

	
	

	@GetMapping("/producto/editar/{id}")
	public String buscarProducto(@PathVariable int id, Model model) {
	    if (!model.containsAttribute("producto")) {
	        model.addAttribute("producto", servProducto.obtenerProductoxId(id));
	    }
	    if (!model.containsAttribute("genero")) {
	        model.addAttribute("genero", servProducto.listarGenero());
	    }
	    return "editar_producto";
	}

	@PostMapping("/productos/{id}")
	public String actualizarProducto(@PathVariable int id,@Valid @ModelAttribute("producto") Producto producto,BindingResult result,Model model) {

	    if (result.hasErrors()) {
	        model.addAttribute("genero", servProducto.listarGenero());
	        return "editar_producto";
	    }

	    Producto productoExiste = servProducto.obtenerProductoxId(id);
	    productoExiste.setDescripcion(producto.getDescripcion());
	    productoExiste.setImagen(producto.getImagen());
	    productoExiste.setPrecio(producto.getPrecio());
	    productoExiste.setStock(producto.getStock());
	    productoExiste.setGenero(producto.getGenero());

	    servProducto.actualizarProducto(productoExiste);
	    return "redirect:/productos";
	}

	

	@GetMapping("/producto/eliminar/{id}")
	public String eliminarProducto(@PathVariable int id) {
		servProducto.eliminarProducto(id);
		return "redirect:/productos";
	}
}
