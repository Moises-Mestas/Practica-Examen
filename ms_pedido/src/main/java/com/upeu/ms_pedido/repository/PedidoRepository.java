package com.upeu.ms_pedido.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<com.upeu.ms_pedido.entity.Pedido, Integer> {
}