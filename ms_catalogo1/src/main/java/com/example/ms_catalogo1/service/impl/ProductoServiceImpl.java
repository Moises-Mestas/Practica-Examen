package com.example.ms_catalogo1.service.impl;

import com.example.ms_catalogo1.entity.Producto;
import com.example.ms_catalogo1.repository.ProductoRepository;
import com.example.ms_catalogo1.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Optional<Producto> buscarPorId(Long id) {
        return productoRepository.findById(id);
    }

    @Override
    public Producto actualizar(Long id, Producto producto) {
        Producto productoDB = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));

        productoDB.setNombre(producto.getNombre());
        productoDB.setCategoria(producto.getCategoria());

        return productoRepository.save(productoDB);
    }

    @Override
    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }
}

