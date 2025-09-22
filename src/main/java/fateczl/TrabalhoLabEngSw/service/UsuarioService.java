package fateczl.TrabalhoLabEngSw.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
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
		if (novo.getNickname().contains(" ")) {
			throw new Exception("O nickname não pode conter espacos.");
		}

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
		/*Na pasta "chaves" no diretorio do projeto*/
		Path currentDirectoryPath = FileSystems.getDefault().getPath("");
		String currentDirectoryName = currentDirectoryPath.toAbsolutePath().toString();
		System.out.println(currentDirectoryName);
		new File(currentDirectoryName+"/chaves").mkdirs();
		File file = new File(currentDirectoryName+"/chaves/"+nomeArquivo);
		try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
			fileOutputStream.write(chaveByteArray);
			fileOutputStream.close();
		}
	}
	public Usuario buscarUsuarioByEmail(String email) {
		return rep.findByEmail(email);
	}
}
