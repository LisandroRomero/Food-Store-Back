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
                            .imagen("https://depositphotos.com/es/illustrations/men%C3%BA-comida-rapida.html")
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
                            .imagen("https://www.istockphoto.com/es/fotos/botellas-de-bebidas-gaseosas")
                            .categoriaPadre(bebidas)  // ← RELACIÓN CON PADRE
                            .activo(true)
                            .build());
                });

        // PRODUCTOS EN BEBIDAS (directamente)
        crearProductoSiNoExiste("Agua Mineral", "Agua purificada sin gas", 80.0, 100, "https://www.supermaxi.com/producto/agua-mineral-natural-sin-gas-tesalia-1200ml-2/", bebidas);
        crearProductoSiNoExiste("Jugo de Naranja", "Jugo natural de naranja", 120.0, 50, "https://www.istockphoto.com/es/fotos/zumo-de-naranja", bebidas);

        // PRODUCTOS EN GASEOSAS (subcategoría)
        crearProductoSiNoExiste("Coca Cola", "Refresco de cola", 150.0, 80, "https://www.istockphoto.com/es/fotos/lata-de-coca", gaseosas);
        crearProductoSiNoExiste("Sprite", "Refresco de lima-limón", 140.0, 60, "https://www.istockphoto.com/es/foto/sprite-lata-de-aluminio-gm458304573-22587611", gaseosas);
        crearProductoSiNoExiste("Fanta", "Refresco de naranja", 140.0, 70, "https://www.istockphoto.com/es/fotos/fanta-fotos", gaseosas);

        // PRODUCTOS EN SNACKS
        crearProductoSiNoExiste("Papas Fritas", "Papas fritas crujientes", 200.0, 40, "https://www.istockphoto.com/es/fotos/papas-fritas-en-paquete-sin-marcar", platos);
        crearProductoSiNoExiste("Nachos", "Tortilla chips para dipping", 180.0, 30, "https://www.istockphoto.com/es/fotos/nachos", platos);
        crearProductoSiNoExiste("Hamburguesa", "Hamburguesa completa con aderezos", 250.0, 100, "https://www.istockphoto.com/es/fotos/hamburguesa", platos);
        crearProductoSiNoExiste("Lomo pan arabe", "Sandwitch de Lomo", 120.0, 60, "https://www.infobae.com/tendencias/2024/03/10/un-clasico-sandwich-argentino-esta-entre-los-mejores-10-del-mundo-segun-taste-atlas/", platos);
        crearProductoSiNoExiste("Pancho", "Pancho completo con aderezos", 110.0, 80, "https://www.istockphoto.com/es/fotos/panchos", platos);

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