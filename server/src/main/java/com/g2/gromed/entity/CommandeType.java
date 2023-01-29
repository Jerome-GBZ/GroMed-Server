package com.g2.gromed.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "COMMANDE_TYPE")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommandeType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "commande_type_id")
	private Long commandeTypeId;

	private String name;
	
	@ManyToOne
	@JoinColumn(name = "numero_commande")
	private Commande commande;
	
	@ManyToOne
	@JoinColumn(name = "email",referencedColumnName = "email")
	private Utilisateur utilisateur;
}
