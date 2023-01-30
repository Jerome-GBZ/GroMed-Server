package com.g2.gromed.entity;



import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Commande")
public class Commande {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long numeroCommande;

	@Column(name = "date_commande", columnDefinition = "DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Instant dateCommande;

	private double total;

	private String facture;

	private StatusCommande status;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "email",referencedColumnName = "email")
	private Utilisateur utilisateur;

	@OneToOne
	@JoinColumn(name = "commande_type_id", referencedColumnName = "commande_type_id")
	private CommandeType commandeType;

	@OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
	private List<CommandeMedicament> commandeMedicaments;
	
	@OneToMany(mappedBy = "commande",cascade = CascadeType.ALL)
	private List<Livraison> livraisons;
}
