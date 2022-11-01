package com.app.productos;

import org.springframework.data.repository.CrudRepository;

public interface ProductoRepository extends CrudRepository <Producto, Integer> {

    public Producto findByNombre(String nombre);
}
