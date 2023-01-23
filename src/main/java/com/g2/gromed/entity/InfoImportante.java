package com.g2.gromed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import oracle.sql.TIMESTAMP;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "InfoImportante")
public class InfoImportante {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idInfoImportante;
	@Column(name = "date_debut", columnDefinition = "DATE")
	@Temporal(TemporalType.DATE)
	private Date dateDebut;
	@Column(name = "date_fin", columnDefinition = "DATE")
	@Temporal(TemporalType.DATE)
	private Date dateFin;
	
	@Column(length = 1024)
	private String lien;
	@Column(length = 1024)
	private String message;
}
