package com.g2.gromed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "LIVRAISON_PRESENTATION")
public class LivraisonPresentation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long livraisonPresentationId;

	private int quantite;
	
	@ManyToOne
	@JoinColumn(name = "livraison_id")
	private Livraison livraison;
	
	@OneToOne
	@JoinColumn(name = "codeCIP7", referencedColumnName = "codeCIP7")
	private Presentation presentation;
}
