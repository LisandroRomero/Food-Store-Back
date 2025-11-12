package com.example.foodstore.config;

import com.example.foodstore.entity.*;
import com.example.foodstore.repository.*;
import com.example.foodstore.util.Sha256Util;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Verificando y creando datos de prueba...");

        crearUsuarios();
        crearCategoriasYProductos();

        log.info("Carga de datos de prueba completada");
    }

    private void crearUsuarios() {
        // Crear ADMIN solo si no existe
        if (!usuarioRepository.existsByEmail("admin@food.com")) {
            log.info("Creando admin...");
            Usuario admin = Usuario.builder()
                    .nombre("Admin")
                    .apellido("Sistema")
                    .email("admin@food.com")
                    .password(Sha256Util.hash("admin123"))
                    .rol(Rol.ADMIN)
                    .build();
            usuarioRepository.save(admin);
            log.info("Admin creado: admin@food.com / admin123");
        } else {
            log.info("Admin ya existe en la base de datos");
        }

        // Crear CLIENTE solo si no existe
        if (!usuarioRepository.existsByEmail("juan@mail.com")) {
            log.info("Creando cliente...");
            Usuario cliente = Usuario.builder()
                    .nombre("Juan")
                    .apellido("Pérez")
                    .email("juan@mail.com")
                    .password(Sha256Util.hash("123456"))
                    .rol(Rol.USUARIO)
                    .build();
            usuarioRepository.save(cliente);
            log.info("Cliente creado: juan@mail.com / 123456");
        } else {
            log.info("Cliente ya existe en la base de datos");
        }
    }

    private void crearCategoriasYProductos() {
        // CATEGORÍA 1: Bebidas (raíz)
        Categoria bebidas = categoriaRepository.findByNombreIgnoreCase("Bebidas")
                .orElseGet(() -> {
                    log.info("Creando categoría Bebidas...");
                    return categoriaRepository.save(Categoria.builder()
                            .nombre("Bebidas")
                            .descripcion("Todo tipo de bebidas y refrescos")
                            .imagen("https://www.shutterstock.com/image-photo/refreshing-colorful-soda-beverages-glass-600nw-2657643985.jpg")
                            .activo(true)
                            .build());
                });

        // CATEGORÍA 2: Platos Principales (raíz)
        Categoria platos = categoriaRepository.findByNombreIgnoreCase("Platos Principales")
                .orElseGet(() -> {
                    log.info("Creando categoría Platos...");
                    return categoriaRepository.save(Categoria.builder()
                            .nombre("Platos Principales")
                            .descripcion("Platos principales para almuerzo o cena")
                            .imagen("https://www.marthadebayle.com/wp-content/uploads/2024/05/comida-chatarra-y-como-evitarla.jpg")
                            .activo(true)
                            .build());
                });

        // SUBCATEGORÍA: Gaseosas (hija de Bebidas)
        Categoria gaseosas = categoriaRepository.findByNombreIgnoreCase("Gaseosas")
                .orElseGet(() -> {
                    log.info("Creando subcategoría Gaseosas...");
                    return categoriaRepository.save(Categoria.builder()
                            .nombre("Gaseosas")
                            .descripcion("Bebidas carbonatadas y refrescos")
                            .imagen("https://i.pinimg.com/736x/17/53/07/17530782da06b17d1e6654526dbde2be.jpg")
                            .categoriaPadre(bebidas)  // ← RELACIÓN CON PADRE
                            .activo(true)
                            .build());
                });

        // PRODUCTOS EN BEBIDAS (directamente)
        crearProductoSiNoExiste("Agua Mineral", "Agua purificada sin gas", 80.0, 100, "https://www.supermaxi.com/wp-content/uploads/2024/12/759494997838-1-5.jpg", bebidas);
        crearProductoSiNoExiste("Jugo de Naranja", "Jugo natural de naranja", 120.0, 50, "https://libbys.es/wordpress/wp-content/uploads/2019/09/mitos-zumo-de-naranja.jpg", bebidas);

        // PRODUCTOS EN GASEOSAS (subcategoría)
        crearProductoSiNoExiste("Coca Cola", "Refresco de cola", 150.0, 80, "https://upload.wikimedia.org/wikipedia/commons/2/27/Coca_Cola_Flasche_-_Original_Taste.jpg", gaseosas);
        crearProductoSiNoExiste("Sprite", "Refresco de lima-limón", 140.0, 60, "https://chamberswineandliquor.com/wp-content/uploads/2021/12/4913207.jpg", gaseosas);
        crearProductoSiNoExiste("Fanta", "Refresco de naranja", 140.0, 70, "https://www.coca-cola.com/content/dam/onexp/es/es/brand/fanta-v2/es_fanta_naranja_750x750.jpg", gaseosas);

        // PRODUCTOS EN SNACKS
        crearProductoSiNoExiste("Papas Fritas", "Papas fritas crujientes", 200.0, 40, "https://micorazondearroz.com/wp-content/uploads/2020/09/DSC02914-scaled.jpg", platos);
        crearProductoSiNoExiste("Nachos", "Tortilla chips para dipping", 180.0, 30, "https://www.tasteofhome.com/wp-content/uploads/2018/04/Zucchini-Pico-de-Gallo-Salsa_EXPS_THSUM18_199216_D02_01_9b.jpg", platos);
        crearProductoSiNoExiste("Hamburguesa", "Hamburguesa", 250.0, 100, "https://imag.bonviveur.com/hamburguesa-clasica.jpg", platos);
        crearProductoSiNoExiste("Lomo pan arabe", "Sandwitch de Lomo", 120.0, 60, "https://www.clarin.com/images/2021/07/26/u-aUfp64d_1200x630__1.jpg", platos);
        crearProductoSiNoExiste("Pancho", "Pancho completo", 110.0, 80, "https://cloudfront-us-east-1.images.arcpublishing.com/grupoclarin/ELL3XQJDNBFNJI7YN5ZLXQQL6U.jpg", platos);

        log.info("Categorías y productos de prueba creados/verificados");
    }

    private void crearProductoSiNoExiste(String nombre, String descripcion, double precio, int stock, String imagen, Categoria categoria) {
        if (!productoRepository.existsByNombreIgnoreCase(nombre)) {
            Producto producto = Producto.builder()
                    .nombre(nombre)
                    .descripcion(descripcion)
                    .precio(precio)
                    .stock(stock)
                    .imagen(imagen)
                    .categoria(categoria)
                    .disponible(true)
                    .activo(true)
                    .build();
            productoRepository.save(producto);
            log.info("Producto creado: {} - ${}", nombre, precio);
        }
    }
}