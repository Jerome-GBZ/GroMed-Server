package com.g2.gromed.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "MEDICAMENT",indexes = {
		@Index(name="denominationMedicamentIndex", columnList = "denomination"),
		@Index(name="titulaireMedicamentIndex", columnList = "titulaire")
})
public class Medicament {
	
	@Id
	private String codeCIS;

	private String formePharmaceutique;

	private String voiesAdministratifAMM;

	private String typeProcedureAMM;

	@Column(name = "dateamm", columnDefinition = "DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Instant dateAMM;

	private String etatCommercialisation;

	private String statusBDM;

	private String numeroAutorisationEuropeenne;

	private String titulaire;

	private String surveillanceRenforcee;

	private String denomination;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "codeCIS")
	private List<Composition> compositions;

	@JoinColumn(name = "codeCIS")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<ConditionDelivrance> conditionDelivrances;

	@JoinColumn(name = "codeCIS")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<InfoImportante> infoImportantes;

	@JoinColumn(name = "codeCIS")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<GroupeGenerique> groupeGeneriques;

}
