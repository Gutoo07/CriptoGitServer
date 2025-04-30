package fateczl.TrabalhoLabEngSw.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fateczl.TrabalhoLabEngSw.model.Blob;

public interface BlobRepository extends JpaRepository<Blob, String> {
	@Query("SELECT b FROM Blob b WHERE b.sha1 = :sha1")
	public Blob findBySha1(String sha1);
}
