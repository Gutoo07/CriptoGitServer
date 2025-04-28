package fateczl.TrabalhoLabEngSw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fateczl.TrabalhoLabEngSw.persistence.RepositorioRepository;

@Service
public class RepositorioService {
	@Autowired
	private RepositorioRepository rep;
	
}
