package com.upeu.ms_pedido.service.impl;

import com.upeu.ms_pedido.entity.Pedido;
import com.upeu.ms_pedido.dto.Cliente;
import com.upeu.ms_pedido.dto.Producto;
import com.upeu.ms_pedido.entity.PedidoDetalle;
import com.upeu.ms_pedido.feign.ClienteFeign;
import com.upeu.ms_pedido.feign.ProductoFeign;
import com.upeu.ms_pedido.repository.PedidoRepository;
import com.upeu.ms_pedido.service.PedidoService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteFeign clienteFeign;

    @Autowired
    private ProductoFeign productoFeign;

    // Método con Circuit Breaker para Cliente
    @CircuitBreaker(name = "clienteCircuitBreaker", fallbackMethod = "clienteFallback")
    private Cliente getCliente(Integer clienteId) {
        try {
            // Obtener el cliente desde el servicio Feign
            ResponseEntity<Cliente> response = clienteFeign.listById(clienteId);
            if (response.getBody() != null) {
                return response.getBody();
            } else {
                // Si el cliente no se encuentra, retornar el fallback
                return clienteFallback(clienteId, new RuntimeException("Cliente no encontrado"));
            }
        } catch (Exception e) {
            // En caso de cualquier error en la llamada, retornamos el fallback
            return clienteFallback(clienteId, e);
        }
    }

    // Método con Circuit Breaker para Producto
    @CircuitBreaker(name = "productoCircuitBreaker", fallbackMethod = "productoFallback")
    private Producto getProducto(Integer productoId) {
        try {
            // Obtener el producto desde el servicio Feign
            ResponseEntity<Producto> response = productoFeign.listById(productoId);
            if (response.getBody() != null) {
                return response.getBody();
            } else {
                // Si el producto no se encuentra, retornar el fallback
                return productoFallback(productoId, new RuntimeException("Producto no encontrado"));
            }
        } catch (Exception e) {
            // En caso de cualquier error en la llamada, retornamos el fallback
            return productoFallback(productoId, e);
        }
    }

    // Método de fallback para Cliente en caso de error
    public Cliente clienteFallback(Integer clienteId, Throwable t) {
        System.out.println("Fallo el servicio de cliente: " + t.getMessage());
        return new Cliente(clienteId, "Cliente no disponible", "N/A", "N/A", 0);
    }

    // Método de fallback para Producto en caso de error
    public Producto productoFallback(Integer productoId, Throwable t) {
        System.out.println("Fallo el servicio de producto: " + t.getMessage());
        return new Producto(productoId, "Producto no disponible", null);  // Asumí que la categoría puede ser nula en caso de fallo
    }

    @Override
    public List<Pedido> listar() {
        List<Pedido> pedidos = pedidoRepository.findAll();

        // Cargar las relaciones cliente y producto de manera similar a como se hace en listarPorId
        for (Pedido pedido : pedidos) {
            Cliente cliente = getCliente(pedido.getClienteId());  // Usando el método con Circuit Breaker
            pedido.setCliente(cliente);

            // Cargar los detalles del pedido y los productos
            List<PedidoDetalle> pedidoDetalles = pedido.getDetalle().stream().map(pedidoDetalle -> {
                Producto producto = getProducto(pedidoDetalle.getProductoId());  // Usando el método con Circuit Breaker
                pedidoDetalle.setProducto(producto);
                return pedidoDetalle;
            }).collect(Collectors.toList());

            pedido.setDetalle(pedidoDetalles);
        }

        return pedidos;
    }

    @Override
    public Optional<Pedido> listarPorId(Integer id) {
        try {
            Pedido pedido = pedidoRepository.findById(id).get();
            System.out.println("Pedido obtenido: " + pedido);

            Cliente cliente = getCliente(pedido.getClienteId());  // Usando el método con Circuit Breaker
            System.out.println("Cliente obtenido: " + cliente);

            List<PedidoDetalle> pedidoDetalles = pedido.getDetalle().stream().map(pedidoDetalle -> {
                Producto producto = getProducto(pedidoDetalle.getProductoId());  // Usando el método con Circuit Breaker
                pedidoDetalle.setProducto(producto);
                System.out.println("Detalle de producto: " + pedidoDetalle);
                return pedidoDetalle;
            }).collect(Collectors.toList());

            pedido.setDetalle(pedidoDetalles);
            pedido.setCliente(cliente);
            System.out.println("Pedido final: " + pedido);

            return Optional.of(pedido);
        } catch (Exception e) {
            // Capturar y registrar cualquier error
            System.err.println("Error al procesar el pedido: " + e.getMessage());
            e.printStackTrace();  // Imprimir la traza completa del error
            return Optional.empty();
        }
    }

    // Guardar un nuevo pedido
    @Override
    public Pedido guardar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    // Actualizar un pedido existente
    @Override
    public Pedido actualizar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    // Eliminar un pedido por ID
    @Override
    public void eliminar(Integer id) {
        pedidoRepository.deleteById(id);
    }
}
