@Controller
@RequestMapping("/arboles")
public class ArbolController {
  @Autowired private ArbolRepository repo;

  @GetMapping
  public String listar(Model model) {
    model.addAttribute("arboles", repo.findAll());
    return "arbol/lista";
  }

  @GetMapping("/nuevo")
  public String nuevo(Model model) {
    model.addAttribute("arbol", new Arbol());
    return "arbol/formulario";
  }

  @PostMapping("/guardar")
  public String guardar(@ModelAttribute Arbol arbol) {
    repo.save(arbol);
    return "redirect:/arboles";
  }

  @GetMapping("/editar/{id}")
  public String editar(@PathVariable Integer id, Model model) {
    model.addAttribute("arbol", repo.findById(id).orElse(null));
    return "arbol/formulario";
  }

  @GetMapping("/eliminar/{id}")
  public String eliminar(@PathVariable Integer id) {
    repo.deleteById(id);
    return "redirect:/arboles";
  }
}
