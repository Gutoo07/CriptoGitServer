package fateczl.TrabalhoLabEngSw.persistence;

import org.springframework.data.jpa.repository.JpaRepository; 

import fateczl.TrabalhoLabEngSw.model.Commite;

public interface CommitRepository extends JpaRepository<Commite, Long> {

}
