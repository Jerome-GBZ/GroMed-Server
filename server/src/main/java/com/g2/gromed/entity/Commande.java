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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long numeroCommande;
	@Column(name = "date_commande", columnDefinition = "DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private TIMESTAMP dateCommande;

	private double total;
	private String facture;
	private StatusCommande status;
	
	@ManyToOne
	@JoinColumn(name = "id")
	private Utilisateur utilisateur;
	@OneToOne
	@JoinColumn(name = "commandeTypeId", referencedColumnName = "commandeTypeId")
	private CommandeType commandeType;
	@OneToMany(mappedBy = "commande")
	private List<CommandeMedicament> commandeMedicaments;
}
