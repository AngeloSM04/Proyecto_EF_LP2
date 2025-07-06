package org.cibertec.edu.pe.servicio;

import java.util.List;

import org.cibertec.edu.pe.dtos.ResultadoResponse;
import org.cibertec.edu.pe.modelo.Boleta;
import org.cibertec.edu.pe.modelo.DetalleBoleta;
import org.cibertec.edu.pe.modelo.Producto;
import org.cibertec.edu.pe.repositorio.IBoletaRepositorio;
import org.cibertec.edu.pe.repositorio.IProductoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class BoletaServicio {

	@Autowired
	private IBoletaRepositorio _boletaRepositorio;

	@Autowired
	private IProductoRepositorio _productoRepositorio;

	public List<Boleta> search() {
		return _boletaRepositorio.findAllByOrderByNumBoletaDesc();
	}

	@Transactional
	public ResultadoResponse create(Boleta boleta) {
		try {
			StringBuilder errores = new StringBuilder();


			for (DetalleBoleta item : boleta.getLstDetalleBoleta()) {
				int idProd = item.getProducto().getIdProducto();
				Producto prod = _productoRepositorio.findById(idProd).orElse(null);

				if (prod == null) {
					errores.append(String.format("Producto con ID %d no existe<br>", idProd));
				} else if (prod.getStock() < item.getCantidad()) {
					errores.append(String.format("Stock insuficiente para %s<br>", prod.getDescripcion()));
				}
			}


			if (errores.length() > 0) {
				return new ResultadoResponse(false, errores.toString());
			}


			for (DetalleBoleta item : boleta.getLstDetalleBoleta()) {
				Producto prod = _productoRepositorio.findById(item.getProducto().getIdProducto()).orElseThrow();
				prod.setStock(prod.getStock() - item.getCantidad());
				_productoRepositorio.save(prod);
			}

			Boleta registrada = _boletaRepositorio.save(boleta);

			return new ResultadoResponse(true, "Boleta N° " + registrada.getNumBoleta() + " registrada con éxito");

			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultadoResponse(false, "Error al registrar boleta: " + e.getMessage());
		}
		
	}
	
	
	
	
	
	
	
	
	
	

}
