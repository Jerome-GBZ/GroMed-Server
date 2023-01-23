package com.g2.gromed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import oracle.sql.TIMESTAMP;

@Getter
@Setter
@Entity
@Table(name = "InfoImportante")
public class InfoImportante {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idInfoImportante;
	@Column(name = "date_debut", columnDefinition = "DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private TIMESTAMP dateDebut;
	@Column(name = "date_fin", columnDefinition = "DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private TIMESTAMP dateFin;
	
	@Column(length = 1024)
	private String lien;
	@Column(length = 1024)
	private String message;
	@ManyToOne
	@JoinColumn(name = "codeCIS" , referencedColumnName = "codeCIS")
	private Medicament medicament;
}
