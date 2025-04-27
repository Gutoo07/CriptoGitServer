package fateczl.TrabalhoLabEngSw.model;

import org.hibernate.engine.jdbc.env.internal.LobTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Blob")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="sha1")
public class Blob {
	@Id
	@Column(name = "sha1", length = 40, nullable = false)
	private String sha1;
	
	@Lob
	private byte[] conteudo;
}
