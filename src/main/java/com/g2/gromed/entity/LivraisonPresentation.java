package com.g2.gromed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "LivraisonPresentation")
public class LivraisonPresentation {
	@Id
	private Long livraisonPresentationId;
	private int quantite;
	
	@ManyToOne
	@JoinColumn(name = "livraisonId")
	private Livraison livraison;
	
	@OneToOne
	@JoinColumn(name = "codeCIS", referencedColumnName = "codeCIS")
	private Medicament medicament;
}
