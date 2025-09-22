package fateczl.TrabalhoLabEngSw.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fateczl.TrabalhoLabEngSw.model.Repositorio;
import fateczl.TrabalhoLabEngSw.model.Usuario;
import fateczl.TrabalhoLabEngSw.model.UsuarioRepositorio;

public interface UsuarioRepositorioRepository extends JpaRepository<UsuarioRepositorio, Long> {

    public List<UsuarioRepositorio> findByRepositorioId(Long repositorioId);

    public List<UsuarioRepositorio> findByUsuario(Usuario usuario);

    public UsuarioRepositorio findByUsuarioIdAndRepositorioId(Long usuarioId, Long repositorioId);
    
}
