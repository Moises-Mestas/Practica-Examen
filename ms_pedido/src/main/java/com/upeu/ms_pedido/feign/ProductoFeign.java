package com.upeu.ms_pedido.feign;


import com.upeu.ms_pedido.dto.Producto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-catalogo1-service", path = "/productos")
public interface ProductoFeign {

    @GetMapping("/{id}")
    public ResponseEntity<Producto> listById(@PathVariable(required = true) Integer id);
    default ResponseEntity<Producto> fallbackProductoPorId(Integer id, Exception e) {
        // Manejo de fallback: retornar un Producto vacío o respuesta de error.
        return ResponseEntity.ok(new Producto(id, "Producto no disponible", null));
    }

}