package fateczl.TrabalhoLabEngSw.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import fateczl.TrabalhoLabEngSw.model.Commit;

public interface CommitRepository extends JpaRepository<Commit, Long> {

}
