package com.g2.gromed.model.dto.utilisateur;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Donnée utilisateur renvoyé a la connection" ,name = "UtilisateurModel")
public class UtilisateurDTO {

	@Schema(description = "Nom de l'utilisateur" ,example = "Doe")
	private String nom;
	@Schema(description = "Prénom de l'utilisateur" ,example = "John")
	private String prenom;
	@Schema(description = "Nom de l'établissement de l'utilisateur" ,example = "Hopital de la Croix Rouge")
	private String nomEtablissement;
}
