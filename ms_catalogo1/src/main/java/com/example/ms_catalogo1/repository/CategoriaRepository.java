package com.example.ms_catalogo1.repository;

import com.example.ms_catalogo1.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}
