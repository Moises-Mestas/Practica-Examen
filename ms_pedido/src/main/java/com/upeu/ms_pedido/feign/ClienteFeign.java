package com.upeu.ms_pedido.feign;

import com.upeu.ms_pedido.dto.Cliente;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-cliente-service", path = "/clientes")
public interface ClienteFeign {

    @GetMapping("/{id}")
    @CircuitBreaker(name = "clienteCircuitBreaker", fallbackMethod = "fallbackClienteById")
    ResponseEntity<Cliente> listById(@PathVariable Integer id);

    // Fallback method en el mismo Feign
    default ResponseEntity<Cliente> fallbackClienteById(Integer id, Throwable e) {
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNombre("Cliente no disponible");
        cliente.setDni("DNI no encontrado");
        cliente.setDireccion("Dirección no disponible");
        cliente.setTelefono(0);
        return ResponseEntity.ok(cliente);
    }
}
