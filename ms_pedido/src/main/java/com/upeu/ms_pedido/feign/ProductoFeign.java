package com.upeu.ms_pedido.feign;

import com.upeu.ms_pedido.dto.Producto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-catalogo1-service", path = "/productos")
public interface ProductoFeign {

    @GetMapping("/{id}")
    @CircuitBreaker(name = "productoCircuitBreaker", fallbackMethod = "fallbackProductoById")
    ResponseEntity<Producto> listById(@PathVariable Integer id);

    // Fallback method en el mismo Feign
    default ResponseEntity<Producto> fallbackProductoById(Integer id, Throwable e) {
        Producto producto = new Producto();
        producto.setId(id);
        producto.setNombre("Producto no disponible");
        producto.setCategoria(null); // Puedes ajustar si quieres un objeto categoría vacío
        return ResponseEntity.ok(producto);
    }
}
