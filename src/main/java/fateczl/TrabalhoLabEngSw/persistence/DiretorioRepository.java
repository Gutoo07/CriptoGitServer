package fateczl.TrabalhoLabEngSw.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fateczl.TrabalhoLabEngSw.model.Commite;
import fateczl.TrabalhoLabEngSw.model.Diretorio;

public interface DiretorioRepository extends JpaRepository<Diretorio, Long> {
	
	List<Diretorio> findByCommit(Commite commit);

}
