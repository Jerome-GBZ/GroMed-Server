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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long commandeMedicamentId;
	private int quantite;
	@ManyToOne
	@JoinColumn(name = "numeroCommande")
	private Commande commande;
	
	@OneToOne
	@JoinColumn(name = "codeCIP7", referencedColumnName = "codeCIP7")
	private Presentation presentation;
}
