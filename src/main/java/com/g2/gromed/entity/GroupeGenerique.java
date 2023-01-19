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
	private String idGroupeGenerique;
	@OneToOne
	@JoinColumn(name = "codeCIS",unique = true)
	private Medicament medicament;
	private String typeGenerique;
	private String numeroTri;
	private String libelle;
}
