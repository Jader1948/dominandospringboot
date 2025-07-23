package academy.devdojo.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = {"v1/customers", "v1/customers/"})
public class CustomerController {


    public static final List<String> NAMES = List.of("Jader", "Debora", "Francisco", "Sula", "Adriano");

    @GetMapping("filter")
    public List<String> filter(@RequestParam(defaultValue = "") String name) {
        return NAMES.stream().filter(n -> n.equalsIgnoreCase(name)).toList();
    }

    @GetMapping("filterFalse")
    public List<String> filterFalse(@RequestParam(required = false) String name) {
        return NAMES.stream().filter(n -> n.equalsIgnoreCase(name)).toList();
    }

    @GetMapping("filterOptional")
    public List<String> filter(@RequestParam Optional<String> name) {
        return NAMES.stream().filter(n -> n.equalsIgnoreCase(name.orElse(""))).toList();
    }

    @GetMapping("filterListName")
    public List<String> filterListName(@RequestParam(name = "name") List<String> names) {
        return NAMES.stream().filter(names::contains).toList();
    }

    @GetMapping("filterList")
    public List<String> filterList(@RequestParam List<String> names) {
        return NAMES.stream().filter(names::contains).toList();
    }

    @GetMapping("{name}")
    public String findByName(@PathVariable String name) {
        return NAMES.stream().filter(n -> n.equalsIgnoreCase(name))
                .findFirst()
                .orElseGet(() -> "");
    }
}
