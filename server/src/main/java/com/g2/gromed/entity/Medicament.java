package com.g2.gromed.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Medicament")
public class Medicament {
	
	@Id
	private String codeCIS;

	private String formePharmaceutique;

	private String voiesAdministratifAMM;

	private String typeProcedureAMM;

	@Column(name = "dateamm", columnDefinition = "DATE")
	@Temporal(TemporalType.DATE)
	private Date dateAMM;

	private String etatCommercialisation;

	private String statusBDM;

	private String numeroAutorisationEuropeenne;

	private String titulaire;

	private String surveillanceRenforcee;

	private String denomination;

	@OneToMany()
	@JoinColumn(name = "codeCIS")
	private List<Composition> compositions;

	@JoinColumn(name = "codeCIS")
	@OneToMany()
	private List<ConditionDelivrance> conditionDelivrances;

	@JoinColumn(name = "codeCIS")
	@OneToMany()
	private List<InfoImportante> infoImportantes;
	@JoinColumn(name = "codeCIS")
	@OneToMany()
	private List<GroupeGenerique> groupeGeneriques;

}
