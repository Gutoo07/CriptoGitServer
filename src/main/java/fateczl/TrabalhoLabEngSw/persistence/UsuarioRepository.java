package fateczl.TrabalhoLabEngSw.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fateczl.TrabalhoLabEngSw.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    @Query("select e from Usuario e where e.email = :email")
    public Usuario findByEmail(String email);

    @Query("select l from Usuario l where l.email = :email and l.senha = :senha")
    public Usuario buscarLogin(String email, String senha);

}
