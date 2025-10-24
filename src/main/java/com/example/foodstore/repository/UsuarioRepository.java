package com.example.foodstore.repository;

import com.example.foodstore.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findAllByEliminadoFalse();
    Optional<Usuario> findByMail(String mail);
    boolean existsByMail(String mail);
}
