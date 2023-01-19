package com.g2.gromed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import oracle.sql.TIMESTAMP;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Commande")
public class Commande {
	@Id
	private Long numeroCommande;
	private TIMESTAMP dateCommande;
	private double total;
	private String facture;
	private StatusCommande status;
	
	@ManyToOne
	@JoinColumn(name = "email")
	private Utilisateur utilisateur;
	@OneToOne
	@JoinColumn(name = "commandeTypeId", referencedColumnName = "commandeTypeId")
	private CommandeType commandeType;
	@OneToMany(mappedBy = "commande")
	private List<CommandeMedicament> commandeMedicaments;
}
