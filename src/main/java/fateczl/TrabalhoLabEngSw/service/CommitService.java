package fateczl.TrabalhoLabEngSw.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fateczl.TrabalhoLabEngSw.model.Commite;
import fateczl.TrabalhoLabEngSw.persistence.CommitRepository;

@Service
public class CommitService {
    @Autowired
    private CommitRepository comRep;

    public Optional<Commite> findById(Long commitId) {
		  return comRep.findById(commitId);
	}
}
