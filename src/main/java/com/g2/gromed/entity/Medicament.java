package com.g2.gromed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import oracle.sql.TIMESTAMP;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Medicament")
public class Medicament {
	
	@Id
	private String codeCIS;
	private String formePharmaceutique;
	private String voiesAdministratifAMM;
	private String typeProcedureAMM;
	private TIMESTAMP dateAMM;
	private String etatCommercialisation;
	private String statusBDM;
	private String numeroAutorisationEuropeenne;
	private String titulaire;
	private String surveillanceRenforcee;
	private String denomination;
	@ManyToMany(mappedBy = "medicaments")
	private List<Composition> compositions;
	@OneToMany(mappedBy = "medicament")
	private List<Presentation> presentations;
}
