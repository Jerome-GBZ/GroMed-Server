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
	@Column(name = "dateamm", columnDefinition = "DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private TIMESTAMP dateAMM;
	private String etatCommercialisation;
	private String statusBDM;
	private String numeroAutorisationEuropeenne;
	private String titulaire;
	private String surveillanceRenforcee;
	private String denomination;
	@OneToMany(mappedBy = "medicament")
	private List<Composition> compositions;
	@OneToMany(mappedBy = "medicament")
	private List<Presentation> presentations;
	@OneToMany(mappedBy = "medicament")
	private List<ConditionDelivrance> conditionDelivrances;
	@OneToMany(mappedBy = "medicament")
	private List<InfoImportante> infoImportantes;

}
