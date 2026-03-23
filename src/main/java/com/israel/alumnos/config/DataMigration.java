package com.israel.alumnos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.israel.alumnos.model.Alumno;
import com.israel.alumnos.repository.AlumnoRepository;
import java.util.List;

@Component
public class DataMigration implements CommandLineRunner {

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Override
    public void run(String... args) throws Exception {
        // Migrar datos existentes que no tengan apellidoPaterno, apellidoMaterno o lada
        List<Alumno> alumnos = alumnoRepository.findAll();
        
        boolean needsUpdate = false;
        for (Alumno alumno : alumnos) {
            if (alumno.getApellidoPaterno() == null || alumno.getApellidoPaterno().isEmpty()) {
                alumno.setApellidoPaterno("Por actualizar");
                needsUpdate = true;
            }
            if (alumno.getApellidoMaterno() == null || alumno.getApellidoMaterno().isEmpty()) {
                alumno.setApellidoMaterno("Por actualizar");
                needsUpdate = true;
            }
            if (alumno.getLada() == null || alumno.getLada().isEmpty()) {
                alumno.setLada("+52");
                needsUpdate = true;
            }
        }
        
        if (needsUpdate) {
            alumnoRepository.saveAll(alumnos);
            System.out.println("✓ Datos migrados correctamente");
        }
    }
}
