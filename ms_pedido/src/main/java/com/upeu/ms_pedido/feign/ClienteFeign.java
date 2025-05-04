package com.upeu.ms_pedido.feign;

import com.upeu.ms_pedido.dto.Cliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@FeignClient(name = "ms-cliente-service", path = "/clientes")
public interface ClienteFeign {

    @CircuitBreaker(name = "clienteCircuitBreaker", fallbackMethod = "fallbackCliente")
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> listById(@PathVariable(required = true) Integer id);

    // Método de fallback en caso de que el Circuit Breaker se active
    default ResponseEntity<Cliente> fallbackCliente(Integer id, Throwable throwable) {
        System.out.println("Fallo la llamada a Cliente con ID: " + id);
        // Aquí puedes devolver una respuesta con un cliente vacío o alguna lógica de manejo de errores
        Cliente cliente = new Cliente();
        return ResponseEntity.ok(cliente);
    }
}
