package com.upeu.ms_pedido.feign;

import com.upeu.ms_pedido.dto.Producto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@FeignClient(name = "ms-catalogo1-service", path = "/productos")
public interface ProductoFeign {

    @CircuitBreaker(name = "productoCircuitBreaker", fallbackMethod = "fallbackProducto")
    @GetMapping("/{id}")
    public ResponseEntity<Producto> listById(@PathVariable(required = true) Integer id);

    // Método de fallback en caso de que el Circuit Breaker se active
    default ResponseEntity<Producto> fallbackProducto(Integer id, Throwable throwable) {
        System.out.println("Fallo la llamada a Producto con ID: " + id);
        // Aquí puedes devolver una respuesta con un producto vacío o alguna lógica de manejo de errores
        Producto producto = new Producto();
        return ResponseEntity.ok(producto);
    }
}
