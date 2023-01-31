package com.g2.gromed.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "PRESENTATION",indexes = {
		@Index(name="stockIndex", columnList = "stock")
})
public class Presentation {

	@Id
	@Column(unique = true)
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
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "codeCIS" , referencedColumnName = "codeCIS")
	private Medicament medicament;
}
