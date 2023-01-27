package com.g2.gromed.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "COMMANDE_MEDICAMENT")
public class CommandeMedicament {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long commandeMedicamentId;

	private int quantite;

	@ManyToOne
	@JoinColumn(name = "numero_commande")
	private Commande commande;
	
	@OneToOne
	
	@JoinColumn(name = "codeCIP7", referencedColumnName = "codeCIP7")
	private Presentation presentation;
}
