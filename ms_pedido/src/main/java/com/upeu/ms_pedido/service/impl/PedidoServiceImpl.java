package com.upeu.ms_pedido.service.impl;

import com.upeu.ms_pedido.entity.Pedido;
import com.upeu.ms_pedido.dto.Cliente;
import com.upeu.ms_pedido.dto.Producto;
import com.upeu.ms_pedido.entity.PedidoDetalle;
import com.upeu.ms_pedido.feign.ClienteFeign;
import com.upeu.ms_pedido.feign.ProductoFeign;
import com.upeu.ms_pedido.repository.PedidoRepository;
import com.upeu.ms_pedido.service.PedidoService;
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

    @Override
    public List<Pedido> listar() {
        List<Pedido> pedidos = pedidoRepository.findAll();

        // Cargar las relaciones cliente y producto de manera similar a como se hace en listarPorId
        for (Pedido pedido : pedidos) {
            Cliente cliente = clienteFeign.listById(pedido.getClienteId()).getBody();
            pedido.setCliente(cliente);

            // Cargar los detalles del pedido y los productos
            List<PedidoDetalle> pedidoDetalles = pedido.getDetalle().stream().map(pedidoDetalle -> {
                Producto producto = productoFeign.listById(pedidoDetalle.getProductoId()).getBody();
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

            Cliente cliente = clienteFeign.listById(pedido.getClienteId()).getBody();
            System.out.println("Cliente obtenido: " + cliente);

            List<PedidoDetalle> pedidoDetalles = pedido.getDetalle().stream().map(pedidoDetalle -> {
                Producto producto = productoFeign.listById(pedidoDetalle.getProductoId()).getBody();
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
