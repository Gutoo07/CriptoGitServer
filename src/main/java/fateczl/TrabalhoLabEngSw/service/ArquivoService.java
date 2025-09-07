package fateczl.TrabalhoLabEngSw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import fateczl.TrabalhoLabEngSw.model.Arquivo;
import fateczl.TrabalhoLabEngSw.persistence.ArquivoRepository;
@Service
public class ArquivoService {
    @Autowired
    private ArquivoRepository arqRep;

    public List<Arquivo> findByCommit(Long commitId, Long repId) {
        return arqRep.findByCommiteAndRepositorio(commitId, repId);
    }
}
