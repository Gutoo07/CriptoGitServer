package fateczl.TrabalhoLabEngSw.service;

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
		rep.save(novo);
	}
	
	public Usuario loginUser(String email, String senha) throws Exception {
		return rep.buscarLogin(email, senha);
	}
}
