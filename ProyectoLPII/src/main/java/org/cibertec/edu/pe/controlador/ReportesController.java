package org.cibertec.edu.pe.controlador;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.cibertec.edu.pe.servicio.ReporteService;

import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

@RestController
@RequestMapping("/reportes")
public class ReportesController {

	@Autowired
	private ReporteService reporteService;

	@GetMapping("/boletas")
	public void boletaReporte(@RequestParam Integer idVenta, HttpServletResponse response) throws Exception {
		// Ruta del reporte (en resources/reportes)
		String reportPath = "/reportes/boletav.jrxml";

		// Parámetros
		Map<String, Object> params = new HashMap<>();
		params.put("idVenta", idVenta);

		// Get JasperPrint
		JasperPrint jasperPrint = reporteService.getJasperPrint(params, reportPath);

		// Configuración de respuesta HTTP
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", String.format("inline; filename=boleta-nro-%s.pdf", idVenta));

		// Exportar a PDF
		OutputStream outputStream = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

		outputStream.flush();
		outputStream.close();

	}

	@GetMapping("/productoGraficos")
	public void productoGraficosReporte(HttpServletResponse response) throws Exception {
		// Ruta del reporte (en resources/reportes)
		String reportPath = "/reportes/graficos.jrxml";

		// Parámetros
		Map<String, Object> params = new HashMap<>();

		// Get JasperPrint
		JasperPrint jasperPrint = reporteService.getJasperPrint(params, reportPath);

		// Configuración de respuesta HTTP
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", String.format("inline; filename=producto-graficos.pdf"));

		// Exportar a PDF
		OutputStream outputStream = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

		outputStream.flush();
		outputStream.close();

	}
}
