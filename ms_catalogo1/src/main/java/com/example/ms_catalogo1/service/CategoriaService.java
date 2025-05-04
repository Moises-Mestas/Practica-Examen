package com.example.ms_catalogo1.service;

import com.example.ms_catalogo1.entity.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {
    List<Categoria> listar();

    Optional<Categoria> buscar(Integer id);

    Categoria guardar(Categoria categoria);

    Categoria actualizar(Integer id, Categoria categoria);

    void eliminar(Integer id);
}