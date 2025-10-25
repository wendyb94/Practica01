/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package practica01.service;

import practica01.domain.Arbol;
import practica01.repository.ArbolRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArbolService {

    @Autowired
    private ArbolRepository arbolRepository;

    @Transactional(readOnly = true)
    public List<Arbol> getArboles() {
        return arbolRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Arbol> getArbol(Integer id) {
        return arbolRepository.findById(id);
    }

    @Transactional
    public Arbol saveOrUpdate(Arbol src) {
        //Si viene con id update; si no create
        if (src.getId() != null) {
            Arbol dst = arbolRepository.findById(src.getId())
                    .orElseThrow(() -> new IllegalArgumentException("El árbol no existe"));

            dst.setNombreComun(src.getNombreComun());
            dst.setTipoFlor(src.getTipoFlor());
            dst.setDurezaMadera(src.getDurezaMadera());
            dst.setAlturaPromedio(src.getAlturaPromedio());
            dst.setImagenRuta(src.getImagenRuta());

            return arbolRepository.save(dst);
        }
        return arbolRepository.save(src);
    }

   @Transactional
    public void delete(Integer id) {
        if (!arbolRepository.existsById(id)) {
            throw new IllegalArgumentException("El árbol con ID " + id + " no existe.");
        }
        try {
            arbolRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("No se puede eliminar el árbol. Tiene datos asociados.", e);
        }
    }
}

