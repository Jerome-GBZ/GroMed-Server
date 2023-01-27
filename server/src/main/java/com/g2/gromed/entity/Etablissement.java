package com.g2.gromed.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@IdClass(EtablissementId.class)
@Table(name = "ETABLISSEMENT")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Etablissement {

	@Id
	private Long finess;

	@Id
	private String etalab;

	private String nom;

	private String telephone;

	private String adresse;

	private String codePostal;

	private String ville;

	private String departement;

	private String region;

	private String categorie;
	
}
