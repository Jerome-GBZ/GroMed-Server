package com.g2.gromed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "COMMANDE_TYPE")
public class CommandeType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long commandeTypeId;

	private String name;
	
	@ManyToOne
	@JoinColumn(name = "numero_commande")
	private Commande commande;
	
	@ManyToOne
	@JoinColumn(name = "email")
	private Utilisateur utilisateur;
}
