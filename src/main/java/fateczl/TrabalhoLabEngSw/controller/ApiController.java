package fateczl.TrabalhoLabEngSw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fateczl.TrabalhoLabEngSw.model.Repositorio;
import fateczl.TrabalhoLabEngSw.persistence.RepositorioRepository;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private RepositorioRepository repRep;

    @GetMapping("/get_commit")
    public List<Repositorio> getCommit() {
        return repRep.findAll();
    }
}
