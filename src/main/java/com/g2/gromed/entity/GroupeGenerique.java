package com.g2.gromed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "GroupeGenerique")
public class GroupeGenerique {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idGroupeGenerique;
	private String identifiantGroupeGenerique;
	@OneToOne
	@JoinColumn(name = "codeCIS",unique = true)
	private Medicament medicament;
	private String typeGenerique;
	private String numeroTri;
	@Column(length = 1024)
	private String libelle;
}
