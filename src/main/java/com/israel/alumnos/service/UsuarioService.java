package com.israel.alumnos.service;

import com.israel.alumnos.model.Usuario;
import com.israel.alumnos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public boolean existeUsuario(String usuario) {
        return usuarioRepository.findByUsuario(usuario).isPresent();
    }

    public boolean verificarCredenciales(String usuario, String password) {
        Optional<Usuario> usuarioDb = usuarioRepository.findByUsuario(usuario);
        if (usuarioDb.isPresent()) {
            return usuarioDb.get().getPassword().equals(password);
        }
        return false;
    }

    public java.util.List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }
}
