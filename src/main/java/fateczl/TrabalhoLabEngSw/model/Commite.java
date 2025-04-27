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
@Table(name="Commite") //"commit" eh palavra reservada no SQL Server; adicionei um 'e' no final
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Commite {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "msg", length = 100, nullable = true)
	private String msg;
	
	@ManyToOne(targetEntity = Usuario.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "usuarioAutorId", nullable = false)
	private Usuario autor;
	
	@OneToOne(targetEntity = Commite.class, fetch = FetchType.LAZY)
	@JoinColumn(name="commite_id", nullable = true)
	private Commite anterior;
}
