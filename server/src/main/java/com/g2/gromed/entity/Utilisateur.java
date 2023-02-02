package com.g2.gromed.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "UTILISATEUR")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Utilisateur {

	@Id
	@Column(unique = true)
	private String email;

	private String nom;

	private String prenom;

	private String motDePasse;

	@Column(name = "date_naissance", columnDefinition = "DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Instant dateNaissance;

	private String adresse;

	private String codePostal;

	private String ville;

	private String telephone;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumns(value = {@JoinColumn(name = "finess", referencedColumnName = "finess"), @JoinColumn(name = "etalab", referencedColumnName = "etalab")})
	private Etablissement etablissement;
	
	@OneToMany(mappedBy = "utilisateur")
	private List<Commande> commandes;

	@OneToMany(mappedBy = "utilisateur")
	private List<CommandeType> commandeTypes;
}
