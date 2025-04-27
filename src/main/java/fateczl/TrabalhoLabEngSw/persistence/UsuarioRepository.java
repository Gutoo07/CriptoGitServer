package fateczl.TrabalhoLabEngSw.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import fateczl.TrabalhoLabEngSw.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
