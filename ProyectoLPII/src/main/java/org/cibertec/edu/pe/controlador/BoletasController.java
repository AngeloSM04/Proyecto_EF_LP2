package org.cibertec.edu.pe.controlador;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.cibertec.edu.pe.dtos.ClienteSeleccionado;
import org.cibertec.edu.pe.dtos.ProductoSeleccionado;
import org.cibertec.edu.pe.dtos.ResultadoResponse;
import org.cibertec.edu.pe.modelo.Boleta;
import org.cibertec.edu.pe.modelo.Cliente;
import org.cibertec.edu.pe.modelo.DetalleBoleta;
import org.cibertec.edu.pe.modelo.Producto;
import org.cibertec.edu.pe.servicio.BoletaServicio;
import org.cibertec.edu.pe.servicio.ClienteServicio;
import org.cibertec.edu.pe.servicio.ProductoService;
import org.cibertec.edu.pe.utils.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/boletas")
@SessionAttributes({"seleccionados", "clienteSeleccionado"})
public class BoletasController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private BoletaServicio boletaService;

    @Autowired
    private ClienteServicio clienteService;


    @ModelAttribute("seleccionados")
    public List<ProductoSeleccionado> inicializarSeleccionados() {
        return new ArrayList<>();
    }

    @ModelAttribute("clienteSeleccionado")
    public ClienteSeleccionado clienteSeleccionado() {
        return new ClienteSeleccionado();
    }

    @GetMapping("/filtrado")
    public String filtrado(@RequestParam(name = "idCliente", required = false) Integer idCliente, Model model) {

        List<Boleta> todas = boletaService.search();
        List<Boleta> filtradas = todas;

        if (idCliente != null) {
            filtradas = todas.stream()
                    .filter(b -> b.getCliente() != null && b.getCliente().getIdCliente() == idCliente)
                    .toList();
        }

        model.addAttribute("lstBoletas", filtradas);
        model.addAttribute("clientes", clienteService.getAll());
        model.addAttribute("idCliente", idCliente);

        return "boletas/filtrado";
    }



    @GetMapping("/nuevo")
    public String nuevo(Model model,
                        @ModelAttribute("clienteSeleccionado") ClienteSeleccionado clienteSeleccionado) {

        model.addAttribute("productos", productoService.listarProductosActivos());

        model.addAttribute("productoSeleccionado", new ProductoSeleccionado());
        model.addAttribute("clientes", clienteService.getAll());
        model.addAttribute("clienteSeleccionado", clienteSeleccionado);
        return "boletas/nuevo";
    }


    @PostMapping("/agregar")
    public String agregar(@Valid @ModelAttribute ProductoSeleccionado seleccionado,
                          BindingResult bindingResult,
                          @ModelAttribute("seleccionados") List<ProductoSeleccionado> seleccionados,
                          @ModelAttribute("clienteSeleccionado") ClienteSeleccionado clienteSeleccionado,
                          Model model) {

        model.addAttribute("productos", productoService.getAll());
        model.addAttribute("productoSeleccionado", seleccionado);
        model.addAttribute("clienteSeleccionado", clienteSeleccionado);
        model.addAttribute("clientes", clienteService.getAll());

        if (bindingResult.hasErrors()) {
            model.addAttribute("alert", Alert.sweetAlertInfo("Complete correctamente los datos "));
            return "boletas/nuevo";
        }

        boolean existe = seleccionados.stream()
                .anyMatch(p -> p.getIdProducto() == seleccionado.getIdProducto());

        if (existe) {
            model.addAttribute("alert", Alert.sweetAlertInfo("el producto ya fue agregado"));
            return "boletas/nuevo";
        }

        seleccionados.add(seleccionado);
        model.addAttribute("productoSeleccionado", new ProductoSeleccionado());
        return "boletas/nuevo";
    }


    @PostMapping("/quitar")
    public String quitar(@RequestParam int codigo,
                         @ModelAttribute("seleccionados") List<ProductoSeleccionado> seleccionados,
                         @ModelAttribute("clienteSeleccionado") ClienteSeleccionado clienteSeleccionado,
                         Model model) {

        seleccionados.removeIf(p -> p.getIdProducto() == codigo);

        model.addAttribute("productos", productoService.getAll());
        model.addAttribute("productoSeleccionado", new ProductoSeleccionado());
        model.addAttribute("clienteSeleccionado", clienteSeleccionado); 
        model.addAttribute("clientes", clienteService.getAll()); 

        return "boletas/nuevo";
    }

    @PostMapping("/registrar")
    public String registrar(@Valid @ModelAttribute("clienteSeleccionado") ClienteSeleccionado clienteSeleccionado,
                            BindingResult clienteResult,
                            @ModelAttribute("seleccionados") List<ProductoSeleccionado> seleccionados,
                            Model model,
                            RedirectAttributes flash,
                            SessionStatus status) {

        model.addAttribute("productos", productoService.getAll());
        model.addAttribute("productoSeleccionado", new ProductoSeleccionado());
        model.addAttribute("clientes", clienteService.getAll());

        if (clienteResult.hasErrors() || clienteSeleccionado.getIdCliente() == null) {
            model.addAttribute("alert", Alert.sweetAlertError("Debe seleccionar un cliente"));
            return "boletas/nuevo";
        }

        if (seleccionados.isEmpty()) {
            model.addAttribute("alert", Alert.sweetAlertInfo("Agregue al menos un producto"));
            return "boletas/nuevo";
        }

        Cliente cliente = clienteService.obtenerClientePorId(clienteSeleccionado.getIdCliente());

        Boleta boleta = new Boleta();
        boleta.setCliente(cliente);

        
        List<DetalleBoleta> detalles = seleccionados.stream()
                .map(p -> {
                    Producto producto = productoService.getOne(p.getIdProducto());
                    double subtotal = producto.getPrecio() * p.getCantidad();
                    return new DetalleBoleta(boleta, producto, p.getCantidad(), subtotal);
                }).toList();

        boleta.setLstDetalleBoleta(detalles);

    
        double total = detalles.stream().mapToDouble(DetalleBoleta::getSubtotal).sum();

        boleta.setMontoTotal(total);

        try {
            ResultadoResponse response = boletaService.create(boleta);

            if (!response.isSuccess()) {
                model.addAttribute("alert", Alert.sweetAlertErrorHtml(response.getMensaje()));
                return "boletas/nuevo";
            }

            flash.addFlashAttribute("toast", Alert.sweetToast(response.getMensaje(), "success", 4000));
            status.setComplete(); 
            return "redirect:/boletas/filtrado";

        } catch (Exception e) {
            model.addAttribute("alert", Alert.sweetAlertError("Error al registrar: " + e.getMessage()));
            return "boletas/nuevo";
        }
    }

}
