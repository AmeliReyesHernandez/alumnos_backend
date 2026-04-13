package com.israel.alumnos.controllers;

import com.israel.alumnos.model.Usuario;
import com.israel.alumnos.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registro")
    public ResponseEntity<Map<String, String>> registrar(@RequestBody Usuario usuario) {
        Map<String, String> response = new HashMap<>();
        if (usuarioService.existeUsuario(usuario.getUsuario())) {
            response.put("message", "El nombre de usuario ya está registrado");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        usuarioService.guardarUsuario(usuario);
        response.put("message", "Usuario registrado exitosamente");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Usuario usuario) {
        Map<String, String> response = new HashMap<>();
        boolean auth = usuarioService.verificarCredenciales(usuario.getUsuario(), usuario.getPassword());
        if (auth) {
            response.put("message", "Autenticación exitosa");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Usuario o contraseña incorrectos");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
