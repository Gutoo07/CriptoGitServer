package fateczl.TrabalhoLabEngSw.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Diretorio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Diretorio {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome", length = 50, nullable = false)
	private String nome;
	
	@ManyToOne(targetEntity = Commite.class, fetch = FetchType.LAZY)
	@JoinColumn(name="commiteId", nullable = false)
	private Commite commit;
	
	@ManyToOne(targetEntity = Diretorio.class, fetch = FetchType.LAZY)
	@JoinColumn(name="diretorioPaiId", nullable = true)
	private Diretorio diretorioPai;
	
	/*
	@ManyToOne(targetEntity = Repositorio.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "repositorioId", nullable = false)
	private Repositorio repositorio; AGORA QUEM TEM REF AO REPOSITORIO SAO OS COMMITES*/
}
