package com.g2.gromed.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Presentation")
public class Presentation {

	@Id
	private String codeCIP7;

	@Column(length = 1024)
	private String denomination;

	private String statusAdministratif;

	private String etatCommercialisation;

	private Double tauxRemboursement;

	@Column(length = 2048)
	private String indicationRemboursement;

	private Double prix;

	private int stock;
	
	@ManyToOne
	@JoinColumn(name = "codeCIS" , referencedColumnName = "codeCIS")
	private Medicament medicament;
}
