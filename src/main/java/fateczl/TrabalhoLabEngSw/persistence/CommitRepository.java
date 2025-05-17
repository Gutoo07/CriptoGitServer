package fateczl.TrabalhoLabEngSw.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fateczl.TrabalhoLabEngSw.model.Commite;

public interface CommitRepository extends JpaRepository<Commite, Long> {
	
	@Query(nativeQuery = true, value  = "SELECT Top 1 * FROM commite c ORDER BY id DESC")
	public Commite findLastCommit();
	@Query(nativeQuery = true, value = "SELECT Top 1 * FROM commite c WHERE c.repositorio_origem_id = :repId ORDER BY id DESC")
	public Commite findLastCommitByRepositorio(Long repId);
	@Query(nativeQuery = true, value = "SELECT * FROM commite c WHERE c.repositorio_origem_id =:repId ORDER BY id ASC")
	public List<Commite> getAllByRepId(Long repId);
	
}
