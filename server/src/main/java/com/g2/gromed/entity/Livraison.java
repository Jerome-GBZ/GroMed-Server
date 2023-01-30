package com.g2.gromed.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "LIVRAISON")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Livraison {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long livraisonId;

	@Column(name = "date_livraison", columnDefinition = "DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Instant dateLivraison;

	@ManyToOne
	private Commande commande;

	@OneToMany(mappedBy = "livraison",cascade = CascadeType.ALL)
	private List<LivraisonPresentation> livraisonPresentations;
}
