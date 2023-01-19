package com.g2.gromed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CommandeMedicament")
public class CommandeMedicament {
	@Id
	private Long commandeMedicamentId;
	private int quantite;
	@ManyToOne
	@JoinColumn(name = "numeroCommande")
	private Commande commande;
	
	@OneToOne
	@JoinColumn(name = "codeCIS", referencedColumnName = "codeCIS")
	private Medicament medicament;
}
