package com.g2.gromed.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
}
