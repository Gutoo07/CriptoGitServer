package fateczl.TrabalhoLabEngSw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import fateczl.TrabalhoLabEngSw.model.Commite;
import fateczl.TrabalhoLabEngSw.persistence.CommitRepository;

@Controller
public class CommitController {
	@Autowired
	private CommitRepository comRep;
	
	public List<Commite> getAllByRepId(Long repId) {
		return comRep.getAllByRepId(repId);
	}
}
