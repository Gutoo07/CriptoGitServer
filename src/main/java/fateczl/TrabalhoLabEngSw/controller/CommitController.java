package fateczl.TrabalhoLabEngSw.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import fateczl.TrabalhoLabEngSw.model.Commite;
import fateczl.TrabalhoLabEngSw.model.Repositorio;
import fateczl.TrabalhoLabEngSw.persistence.CommitRepository;

@Controller
public class CommitController {
	@Autowired
	private CommitRepository comRep;
	
	public List<Commite> getAllByRepId(Long repId) {
		return comRep.getAllByRepId(repId);
	}
	public List<Commite> getAllByRepIdOrderByIdDesc(Repositorio repositorio) {
		return comRep.findByOrigemOrderByIdDesc(repositorio);
	}
	public Commite getLast(Long repId) {
		return comRep.findLastCommitByRepositorio(repId);
	}	
	public void excluir(Commite commit) {
		comRep.delete(commit);
	}
}

