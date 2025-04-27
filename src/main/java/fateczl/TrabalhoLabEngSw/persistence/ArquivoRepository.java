package fateczl.TrabalhoLabEngSw.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import fateczl.TrabalhoLabEngSw.model.Arquivo;

public interface ArquivoRepository extends JpaRepository<Arquivo, Long> {

}
