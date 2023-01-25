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
@Table(name = "Commande")
public class Commande {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long numeroCommande;

	@Column(name = "date_commande", columnDefinition = "DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCommande;

	private double total;

	private String facture;

	private StatusCommande status;
	
	@ManyToOne
	@JoinColumn(name = "email")
	private Utilisateur utilisateur;

	@OneToOne
	@JoinColumn(name = "commande_type_id", referencedColumnName = "commande_type_id")
	private CommandeType commandeType;

	@OneToMany(mappedBy = "commande")
	private List<CommandeMedicament> commandeMedicaments;
}
