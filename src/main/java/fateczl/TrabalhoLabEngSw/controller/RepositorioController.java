package fateczl.TrabalhoLabEngSw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fateczl.TrabalhoLabEngSw.persistence.RepositorioRepository;

@Controller
@RequestMapping("/repositorio")
public class RepositorioController {
	@Autowired
	private RepositorioRepository repRep;
	
	@GetMapping("/repositorios")
	public String carregaRepositorios() {
		return "repositorio/listagem";
	}
}
