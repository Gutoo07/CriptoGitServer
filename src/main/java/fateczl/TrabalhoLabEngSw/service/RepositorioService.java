package fateczl.TrabalhoLabEngSw.service;

import java.util.List;
import java.util.Optional;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fateczl.TrabalhoLabEngSw.model.Arquivo;
import fateczl.TrabalhoLabEngSw.model.Commite;
import fateczl.TrabalhoLabEngSw.model.Repositorio;
import fateczl.TrabalhoLabEngSw.persistence.RepositorioRepository;

@Service
public class RepositorioService {
	@Autowired
	private RepositorioRepository rep;
	@Autowired
	private ArquivoService arqService;	
	@Autowired
	private CommitService comService;
	
	public SecretKey gerarChaveSimetrica() throws Exception {
	    KeyGenerator keyGen = KeyGenerator.getInstance("AES");
	    keyGen.init(256); // 128 192 256
	    return keyGen.generateKey();
	}
	
	public void criarRepositorio(Repositorio repositorio) {
		rep.save(repositorio);
	}
	
	public Commite acessarCommit(Long repId, Long commitId) {  
		// Procura o repositorio
	    Optional<Repositorio> repositorioOpt = rep.findById(repId);
		// Se não encontrar, retorna null
	    if (!repositorioOpt.isPresent()) {
	        return null;
	    }
	        
	    List<Arquivo> arquivos = arqService.findByCommit(commitId, repId);
	    Optional<Commite> commit = comService.findById(commitId);

	    return null;
	}
	
}
