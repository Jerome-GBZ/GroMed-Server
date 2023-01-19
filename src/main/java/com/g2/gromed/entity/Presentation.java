package com.g2.gromed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Presentation")
public class Presentation {
	@Id
	private String codeCIP7;
	private String denomination;
	private String statusAdministratif;
	private String etatCommercialisation;
	private double tauxRemboursement;
	private String indicationRemboursement;
	private double prix;
	private int stock;
	
	@ManyToOne
	@JoinColumn(name = "codeCIS" , referencedColumnName = "codeCIS")
	private Medicament medicament;
}
