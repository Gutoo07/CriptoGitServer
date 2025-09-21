package fateczl.TrabalhoLabEngSw.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fateczl.TrabalhoLabEngSw.model.Repositorio;
import fateczl.TrabalhoLabEngSw.model.Usuario;
import fateczl.TrabalhoLabEngSw.model.UsuarioRepositorio;
import fateczl.TrabalhoLabEngSw.persistence.RepositorioRepository;
import fateczl.TrabalhoLabEngSw.persistence.UsuarioRepositorioRepository;
import fateczl.TrabalhoLabEngSw.persistence.UsuarioRepository;

@Service
public class UsuarioRepositorioService {
    @Autowired
    private UsuarioRepositorioRepository rep;
    @Autowired
    private UsuarioRepository usuarioRep;
    @Autowired
    private RepositorioRepository repositorioRep;

    /**
     * Adiciona um colaborador ao repositório
     * @param rep_id
     * @param user_id
     * @throws Exception
     */
    public void adicionarColaborador(Long rep_id, Long user_id) throws Exception {        
        UsuarioRepositorio usuarioRepositorio = new UsuarioRepositorio();
        // Verifica se o usuario e o repositorio existem
        Optional<Usuario> usuario = usuarioRep.findById(user_id);
        Optional<Repositorio> repositorio = repositorioRep.findById(rep_id);
        // Se não encontrar o usuário, retorna exceção
        if (!usuario.isPresent()) {
            throw new Exception("Usuario nao encontrado");
        }
        // Se não encontrar o repositório, retorna exceção
        if (!repositorio.isPresent()) {
            throw new Exception("Repositorio nao encontrado");
        }
        // Confere se o usuário já está no repositório
        if (rep.findByUsuarioIdAndRepositorioId(user_id, rep_id) != null) {
            throw new Exception("Usuario já está no repositório");
        }
        // Se encontrar o usuário e o repositório, adiciona o usuário ao repositório
        if (usuario.isPresent() && repositorio.isPresent()) {
            usuarioRepositorio.setUsuario(usuario.get());
            usuarioRepositorio.setRepositorio(repositorio.get());
        }
        // Salva o usuário no repositório
        rep.save(usuarioRepositorio);
    }
    /**
     * Exclui um colaborador do repositório
     * @param colaboradorId
     * @param rep_id
     * @param user_id
     * @throws Exception
     */
    public void deleteColaborador(Long colaboradorId) throws Exception {
        // Verifica se o colaborador existe
        Optional<UsuarioRepositorio> usuarioRepositorio = rep.findById(colaboradorId);
        // Se não encontrar o colaborador, retorna exceção
        if (!usuarioRepositorio.isPresent()) {
            throw new Exception("Colaborador não encontrado.");
        }
        // Se encontrar o colaborador, exclui o colaborador do repositório
        if (usuarioRepositorio.isPresent()) {
            rep.delete(usuarioRepositorio.get());
        }
    }    
}
