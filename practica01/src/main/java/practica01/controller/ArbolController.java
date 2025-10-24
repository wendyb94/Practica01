/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica01.controller;

import practica01.domain.Arbol;
import practica01.service.ArbolService;
import jakarta.validation.Valid;
import java.util.Locale;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/arbol")
public class ArbolController {

    @Autowired
    private ArbolService arbolService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/listado")
    public String listado(Model model) {
        var arboles = arbolService.getArboles();
        model.addAttribute("arboles", arboles);
        model.addAttribute("totalArboles", arboles.size());
        return "/arbol/listado";
    }

    //Mostrar arboles
    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        Optional<Arbol> arbolOpt = arbolService.getArbol(id);
        if (arbolOpt.isEmpty()) {
            ra.addFlashAttribute("error", messageSource.getMessage("arbol.error01", null, "El árbol no existe.", Locale.getDefault()));
            return "redirect:/arbol/listado";
        }
        model.addAttribute("arbol", arbolOpt.get());
        return "/arbol/detalle";
    }

    //Añadir nuevos arboles
    @GetMapping("/agregar")
    public String agregar(Model model) {
        model.addAttribute("arbol", new Arbol());
        return "/arbol/modifica"; 
    }

    //Guardar o actualizar
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("arbol") Arbol arbol, BindingResult br, RedirectAttributes ra, Model model) {

        if (br.hasErrors()) {
            return "/arbol/modifica";
        }

        arbolService.saveOrUpdate(arbol);
        ra.addFlashAttribute("todoOk",
                messageSource.getMessage("mensaje.actualizado", null, "Se realizó la actualización.", Locale.getDefault()));
        return "redirect:/arbol/listado";
    }

    //Modificar
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        Optional<Arbol> arbolOpt = arbolService.getArbol(id);
        if (arbolOpt.isEmpty()) {
            ra.addFlashAttribute("error", messageSource.getMessage("arbol.error01", null, "El árbol no existe.", Locale.getDefault()));
            return "redirect:/arbol/listado";
        }
        model.addAttribute("arbol", arbolOpt.get());
        return "/arbol/modifica";
    }

    //Eliminar
    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer id, RedirectAttributes ra) {
        String titulo = "todoOk";
        String detalle = "mensaje.eliminado";
        try {
            arbolService.delete(id);
        } catch (IllegalArgumentException e) {
            titulo = "error";
            detalle = "arbol.error01";
        } catch (IllegalStateException e) {
            titulo = "error";
            detalle = "arbol.error02";
        } catch (Exception e) {
            titulo = "error";
            detalle = "arbol.error03";
        }
        ra.addFlashAttribute(titulo, messageSource.getMessage(detalle, null, "Operación finalizada.", Locale.getDefault()));
        return "redirect:/arbol/listado";
    }
}

