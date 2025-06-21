package fateczl.TrabalhoLabEngSw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import fateczl.TrabalhoLabEngSw.model.Commite;
import fateczl.TrabalhoLabEngSw.model.Diretorio;
import fateczl.TrabalhoLabEngSw.persistence.DiretorioRepository;

@Controller
public class DiretorioController {
	@Autowired
	private DiretorioRepository dirRep;
	
	public List<Diretorio> findAllByCommit(Commite commit) {
		return dirRep.findByCommit(commit);
	}
	public void excluir(Diretorio diretorio) {
		dirRep.delete(diretorio);
	}
}
