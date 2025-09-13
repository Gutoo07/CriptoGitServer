package fateczl.TrabalhoLabEngSw.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@Column(name = "msg", length = 100, nullable = true, unique = false)
	private String msg;
	
	@JsonIgnore
	@ManyToOne(targetEntity = Usuario.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_Autor_Id", nullable = false, unique = false)
	private Usuario autor;
	
	@JsonIgnore
	@ManyToOne(targetEntity = Commite.class, fetch = FetchType.LAZY)
	@JoinColumn(name="commite_anterior_id", nullable = true, unique = false)
	private Commite anterior;
	
	@JsonIgnore
	@ManyToOne(targetEntity = Repositorio.class, fetch = FetchType.LAZY)
	@JoinColumn(name="repositorio_origem_id", nullable = false, unique = false)
	private Repositorio origem;
}
