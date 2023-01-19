package com.g2.gromed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import oracle.sql.TIMESTAMP;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Livraison")
public class Livraison {
	@Id
	private Long livraisonId;
	private TIMESTAMP dateLivraison;
	@OneToOne
	private Commande commande;
	@OneToMany(mappedBy = "livraison")
	private List<LivraisonPresentation> livraisonPresentations;
}
