package com.app.productos;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductoTest {

    @Autowired
    private ProductoRepository productoRepository;

    @Test
    @Rollback(value = false)
    @Order(1)
    public void testGuardarProducto(){
        Producto producto = new Producto("Laptop HP", 5000);
        Producto productoGuardado= productoRepository.save(producto);

        assertNotNull(productoGuardado);

    }

    @Test
    @Order(2)
    public void testBuscarProductoPorNombre(){
        String nombre="Xiaomi";
        Producto producto = productoRepository.findByNombre(nombre);

        //assertThat(producto.getNombre()).isEqualTo(nombre) ;
        assertNull(producto);
    }

    @Test
    @Rollback(value = false)
    @Order(4)
    public void testActualizarProducto(){
        String nombreProducto="Televisor HD";
        Producto  producto = new Producto(nombreProducto,2000);
        producto.setId(1);

        productoRepository.save(producto);
        Producto productoactualizado = productoRepository.findByNombre(nombreProducto);
        assertThat(productoactualizado.getNombre()).isEqualTo(nombreProducto);
    }

    @Test
    @Order(5)
    public void listarProductos(){
        List<Producto> productos = (List<Producto>) productoRepository.findAll();

        for(Producto producto : productos){
            System.out.println(producto);
        }

        assertThat(productos.size()).isGreaterThan(0);
    }

    @Test
    @Rollback(value = false)
    @Order(6)
    public void testEliminarProducto(){
        Integer id= 1;

        boolean isExitenteAntesDeEliminar= productoRepository.findById(id).isPresent();
        productoRepository.deleteById(id);

        boolean noExitenteDespuesDeEliminar= productoRepository.findById(id).isPresent();

        assertTrue(isExitenteAntesDeEliminar);
        assertFalse(noExitenteDespuesDeEliminar);
    }

}
