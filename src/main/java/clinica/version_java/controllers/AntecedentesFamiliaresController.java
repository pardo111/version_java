package clinica.version_java.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import clinica.version_java.DTOs.DTOAntecedentesFamiliares;
import clinica.version_java.services.interfaces.AntecedentesFamiliaresService;;

@RestController
@RequestMapping("/api/v1/antecedentesFamiliares")
public class AntecedentesFamiliaresController {

    @Autowired
    AntecedentesFamiliaresService antecedentesFamiliaresService;

    @GetMapping("/{id}/antecedentes")
    public List<DTOAntecedentesFamiliares> obtenerAntecedentes(@PathVariable("id") int id) {
        return antecedentesFamiliaresService.obtenerAntecedentes(id);
    }

    @DeleteMapping("/eliminarAntecedenteFamiliar")
    public ResponseEntity<?> eliminarAntecedenteFamiliar(@RequestBody Map<String, Integer> body) {
        if (antecedentesFamiliaresService.eliminarAntecedenteFamiliar((Integer) body.get("idPaciente"),
                (Integer) body.get("idAntecedente")))
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

    @PostMapping("/agregarAntecedente")
    public ResponseEntity<?> agregarAntecedente(@RequestBody DTOAntecedentesFamiliares antecedente)
            throws RuntimeException {
        try {
            antecedentesFamiliaresService.agregarAntecedente(antecedente);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }

}
