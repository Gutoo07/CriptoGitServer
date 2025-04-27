package fateczl.TrabalhoLabEngSw.service;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import fateczl.TrabalhoLabEngSw.model.Arquivo;
import fateczl.TrabalhoLabEngSw.model.Blob;
import fateczl.TrabalhoLabEngSw.persistence.ArquivoRepository;

@Service
public class ArquivoService {
	@Autowired
	private ArquivoRepository rep;
	
	public void uploadArquivo(MultipartFile arquivo) throws IOException {
		Arquivo novo = new Arquivo();
		novo.setNome(arquivo.getOriginalFilename());
		Blob blob = new Blob();
		blob.setConteudo(arquivo.getBytes());
		novo.setBlob(blob);
		rep.save(novo);
	}
}
