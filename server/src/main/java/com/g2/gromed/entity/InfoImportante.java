package com.g2.gromed.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "INFO_IMPORTANTE")
public class InfoImportante {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idInfoImportante;

	@Column(name = "date_debut", columnDefinition = "DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Instant dateDebut;

	@Column(name = "date_fin", columnDefinition = "DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Instant dateFin;
	
	@Column(length = 1024)
	private String lien;

	@Column(length = 1024)
	private String message;
}
