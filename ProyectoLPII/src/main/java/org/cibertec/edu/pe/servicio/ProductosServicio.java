package org.cibertec.edu.pe.servicio;

import java.util.List;

import org.cibertec.edu.pe.modelo.Genero;
import org.cibertec.edu.pe.modelo.Producto;
import org.cibertec.edu.pe.repositorio.IGeneroRepositorio;
import org.cibertec.edu.pe.repositorio.IProductoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductosServicio implements IProductoServicio{

	@Autowired
	private IProductoRepositorio repoProducto;
	
	@Autowired
	private IGeneroRepositorio geneProducto;
	
	@Override
	public List<Producto> listarProductos() {
		return repoProducto.findAll();
	}

	@Override
	public List<Genero> listarGenero() {
		return geneProducto.findAll();
	}

	@Override
	public Producto guardarProducto(Producto producto) {
		return repoProducto.save(producto);
	}

	@Override
	public Producto obtenerProductoxId(int id) {
		return repoProducto.findById(id).get();
	}

	@Override
	public Producto actualizarProducto(Producto producto) {
		return repoProducto.save(producto);
	}

	@Override
	public void eliminarProducto(int id) {
		repoProducto.deleteById(id);
		
	}

}
