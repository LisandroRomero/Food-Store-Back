package com.example.foodstore.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Table(name = "categorias")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Categoria extends Base {

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @Column(length = 300)
    private String imagen;

    // RELACIÓN RECURSIVA: Categoría padre (opcional)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_padre_id")
    private Categoria categoriaPadre;

    // RELACIÓN RECURSIVA: Subcategorías hijas
    @Builder.Default
    @OneToMany(mappedBy = "categoriaPadre", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Categoria> subcategorias = new ArrayList<>();

    // Productos de esta categoría (se mantiene igual)
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Producto> productos;

    @Builder.Default
    @Column(nullable = false)
    private boolean activo = true;

    // Metodo helper para agregar subcategoría
    public void addSubcategoria(Categoria subcategoria) {
        if (subcategoria == null) return;
        if (!this.subcategorias.contains(subcategoria)) {
            this.subcategorias.add(subcategoria);
            subcategoria.setCategoriaPadre(this);
        }
    }

    // Metodo helper para remover subcategoría
    public void removeSubcategoria(Categoria subcategoria) {
        if (subcategoria == null) return;
        if (this.subcategorias.remove(subcategoria)) {
            subcategoria.setCategoriaPadre(null);
        }
    }
}
