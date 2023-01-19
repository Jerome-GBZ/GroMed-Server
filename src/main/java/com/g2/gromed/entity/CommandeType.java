package com.g2.gromed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CommandeType")
public class CommandeType {
	@Id
	private Long commandeTypeId;
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "numeroCommande")
	private Commande commande;
	
	@ManyToOne
	@JoinColumn(name = "email")
	private Utilisateur utilisateur;
}
