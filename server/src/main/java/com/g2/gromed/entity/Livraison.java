package com.g2.gromed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import oracle.sql.TIMESTAMP;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "LIVRAISON")
public class Livraison {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long livraisonId;

	@Column(name = "date_livraison", columnDefinition = "DATE")
	@Temporal(TemporalType.DATE)
	private Date dateLivraison;

	@ManyToOne
	private Commande commande;

	@OneToMany(mappedBy = "livraison",cascade = CascadeType.ALL)
	private List<LivraisonPresentation> livraisonPresentations;
}
