package com.g2.gromed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Utilisateur")
public class Utilisateur {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String email;
	private String nom;
	private String prenom;
	private String motDePasse;
	@Column(name = "date_naissance", columnDefinition = "DATE")
	@Temporal(TemporalType.DATE)
	private Date dateNaissance;
	private String adresse;
	private String codePostal;
	private String ville;
	private String telephone;
	@ManyToOne
	@JoinColumns(value = {@JoinColumn(name = "finess", referencedColumnName = "finess"), @JoinColumn(name = "etalab", referencedColumnName = "etalab")})
	private Etablissement etablissement;
	
	@OneToMany(mappedBy = "utilisateur")
	private List<Commande> commandes;
	@OneToMany(mappedBy = "utilisateur")
	private List<CommandeType> commandeTypes;
}
