package fateczl.TrabalhoLabEngSw.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fateczl.TrabalhoLabEngSw.model.Arquivo;
import fateczl.TrabalhoLabEngSw.model.Blob;
import fateczl.TrabalhoLabEngSw.model.Diretorio;

public interface ArquivoRepository extends JpaRepository<Arquivo, Long> {

	@Query(nativeQuery = true, value = "select a.id, a.nome, a.blob_id, a.diretorio_pai_id from arquivo a\r\n"
			+ "\r\n"
			+ "inner join diretorio d\r\n"
			+ "on a.diretorio_pai_id = d.id\r\n"
			+ "inner join commite c\r\n"
			+ "on d.commite_id = c.id\r\n"
			+ "inner join repositorio r\r\n"
			+ "on c.repositorio_origem_id = r.id\r\n"
			+ "where c.id = :commiteId and r.id=:repositorioId")
	public List<Arquivo> findByCommiteAndRepositorio(Long commiteId, Long repositorioId);
	
	public Arquivo findByBlob(Blob blob);
	
	public List<Arquivo> findByDiretorioPai(Diretorio diretorioPai);
}
