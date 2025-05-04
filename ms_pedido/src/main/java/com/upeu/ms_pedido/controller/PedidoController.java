package com.upeu.ms_pedido.controller;

import com.upeu.ms_pedido.entity.Pedido;
import com.upeu.ms_pedido.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // Listar todos los pedidos
    @GetMapping
    public ResponseEntity<List<Pedido>> list() {
        return ResponseEntity.ok().body(pedidoService.listar());
    }

    // Guardar un nuevo pedido
    @PostMapping
    public ResponseEntity<Pedido> save(@RequestBody Pedido pedido) {
        return ResponseEntity.ok(pedidoService.guardar(pedido));
    }

    // Actualizar un pedido existente
    @PutMapping
    public ResponseEntity<Pedido> update(@RequestBody Pedido pedido) {
        return ResponseEntity.ok(pedidoService.actualizar(pedido));
    }

    // Buscar pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> listById(@PathVariable(required = true) Integer id) {
        return ResponseEntity.ok().body(pedidoService.listarPorId(id).get());
    }

    // Eliminar un pedido por ID
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        pedidoService.eliminar(id);
    }
}