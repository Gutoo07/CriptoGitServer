package fateczl.TrabalhoLabEngSw.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fateczl.TrabalhoLabEngSw.model.Usuario;
import fateczl.TrabalhoLabEngSw.persistence.UsuarioRepository;

@Service
public class UsuarioService {
	@Autowired
	private UsuarioRepository rep;
	
	public void salvarUsuario(Usuario novo) throws Exception {
		if(rep.findByEmail(novo.getEmail()) != null) {
			throw new Exception("Este email ja esta cadastrado: "+novo.getEmail());
		}
		KeyPair keyPair = gerarRsaKeyPair();
		PublicKey chavePublica = keyPair.getPublic();
		byte[] chavePublicaArray = chavePublica.getEncoded();
		novo.setChavePublica(chavePublicaArray);
		
		PrivateKey chavePrivada = keyPair.getPrivate();
		byte[] chavePrivadaArray = chavePrivada.getEncoded();
		
		salvarChaveEmArquivo("chavePrivada", chavePrivadaArray);

		rep.save(novo);
	}
	
	public Usuario loginUser(String email, String senha) throws Exception {
		return rep.buscarLogin(email, senha);
	}
	
	public KeyPair gerarRsaKeyPair() throws Exception {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		/* Tamanho das chaves: */
		kpg.initialize(2048);
		return kpg.generateKeyPair();
	}
	
	public void salvarChaveEmArquivo(String nomeArquivo, byte[] chaveByteArray) throws FileNotFoundException, IOException {
		File file = new File(nomeArquivo);
		try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
			fileOutputStream.write(chaveByteArray);
		}
	}
}
