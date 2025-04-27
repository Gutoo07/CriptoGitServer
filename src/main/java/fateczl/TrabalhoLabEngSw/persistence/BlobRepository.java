package fateczl.TrabalhoLabEngSw.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import fateczl.TrabalhoLabEngSw.model.Blob;

public interface BlobRepository extends JpaRepository<Blob, String> {

}
