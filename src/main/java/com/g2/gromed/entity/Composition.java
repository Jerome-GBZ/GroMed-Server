package com.g2.gromed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@IdClass(CompositionId.class)
@Table(name = "Composition")
public class Composition {
	
	@Id
	private String codeSubstance;
	private String denominationSubstance;
	private String designationElementPharmaceutique;
	@Id
	private String dosage;
	private String referenceDosage;
	private String natureComposant;
	@ManyToMany
	@JoinTable(name = "MedicamentComposition", joinColumns = {@JoinColumn(name = "codeSubstance"),@JoinColumn(name = "dosage")}, inverseJoinColumns = @JoinColumn(name = "codeCIS"))
	private List<Medicament> medicaments;
}
