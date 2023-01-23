package com.g2.gromed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Composition")
public class Composition {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idComposition;
	private String codeSubstance;
	@Column(length = 1024)
	private String denominationSubstance;
	@Column(length = 1024)
	private String designationElementPharmaceutique;
	private String dosage;
	@Column(length = 1024)
	private String referenceDosage;
	@Column(length = 1024)
	private String natureComposant;
	@ManyToOne
	@JoinColumn(name = "codeCIS" , referencedColumnName = "codeCIS")
	private Medicament medicament;
}
