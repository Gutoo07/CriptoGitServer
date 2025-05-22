package fateczl.TrabalhoLabEngSw.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Arquivo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of ="id")
public class Arquivo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome", length = 1000, nullable = false)
	private String nome;
	
	@ManyToOne(targetEntity = Blob.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "blobId", nullable = false)
	private Blob blob;
	
	
	@ManyToOne(targetEntity = Diretorio.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "diretorioPaiId", nullable = false)
	private Diretorio diretorioPai;
	/*
	@OneToOne(targetEntity = Blob.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "commit_id", nullable = false)
	private Commite commite;*/
}
