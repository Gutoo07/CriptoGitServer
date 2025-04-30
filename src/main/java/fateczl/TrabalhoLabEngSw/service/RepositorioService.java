package fateczl.TrabalhoLabEngSw.service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fateczl.TrabalhoLabEngSw.model.Repositorio;
import fateczl.TrabalhoLabEngSw.persistence.RepositorioRepository;

@Service
public class RepositorioService {
	@Autowired
	private RepositorioRepository rep;
	
	
	public SecretKey gerarChaveSimetrica() throws Exception {
	    KeyGenerator keyGen = KeyGenerator.getInstance("AES");
	    keyGen.init(256); // 128 192 256
	    return keyGen.generateKey();
	}
	
	public void criarRepositorio(Repositorio repositorio) {
		rep.save(repositorio);
	}
	
}
