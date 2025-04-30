package fateczl.TrabalhoLabEngSw.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fateczl.TrabalhoLabEngSw.model.Repositorio;
import fateczl.TrabalhoLabEngSw.model.Usuario;

public interface RepositorioRepository extends JpaRepository<Repositorio, Long> {

	List<Repositorio> findByUsuario(Usuario usuario);

}
