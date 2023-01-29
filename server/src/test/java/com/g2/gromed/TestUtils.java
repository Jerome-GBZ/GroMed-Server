package com.g2.gromed;

import com.g2.gromed.entity.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.time.Instant;

public abstract class TestUtils {

    public static Presentation getPresentation(int n) {
        return Presentation.builder()
                .codeCIP7(String.format("CIP7-%d", n))
                .denomination(String.format("Présentation médicament %d", n))
                .statusAdministratif(String.format("Statut admin médicament %d", n))
                .etatCommercialisation(String.format("Etat comm médicament %d", n))
                .tauxRemboursement(10.0)
                .indicationRemboursement(String.format("Indication remboursement médicament %d", n))
                .prix((double) 100)
                .stock(100)
                .build();
    }

    public static Medicament getMedicament(int n) {
        return Medicament.builder()
                .codeCIS(String.format("CIS-%d", n))
                .formePharmaceutique(String.format("Forme pharma médicament %d", n))
                .voiesAdministratifAMM(String.format("Forme pharma médicament %d", n))
                .typeProcedureAMM(String.format("Type procédure médicament %d", n))
                .dateAMM(Instant.now())
                .etatCommercialisation(String.format("Etat commercialisation %d", n))
                .statusBDM(String.format("Status BDM médicament %d", n))
                .numeroAutorisationEuropeenne(String.format("Numéro auto %d", n))
                .titulaire(String.format("Titulaire médicament %d", n))
                .surveillanceRenforcee("Non")
                .denomination(String.format("Dénomination médicament %d", n))
                .compositions(new ArrayList<>())
                .conditionDelivrances(new ArrayList<>())
                .infoImportantes(new ArrayList<>())
                .groupeGeneriques(new ArrayList<>())
                .build();
    }

    public static Composition getComposition(int n) {
        return Composition.builder()
                .idComposition((long) n)
                .codeSubstance(String.format("code substance %d", n))
                .dosage(String.format("dosage %d", n))
                .denominationSubstance(String.format("denomination %d", n))
                .referenceDosage(String.format("reference dosage %d", n))
                .natureComposant(String.format("nature composant %d", n))
                .designationElementPharmaceutique(String.format("designation element pharmaceutique %d", n))
                .build();
    }

    public static ConditionDelivrance getConditionDelivrance(int n) {
        return ConditionDelivrance.builder()
                .idConditionDelivrance((long) n)
                .condition(String.format("condition %d", n))
                .build();
    }

    public static InfoImportante getInfoImportante(int n) {
        return InfoImportante.builder()
                .idInfoImportante((long) n)
                .dateDebut(Instant.now())
                .dateFin(Instant.now())
                .lien(String.format("lien %d", n))
                .message(String.format("message %d", n))
                .build();
    }

    public static GroupeGenerique getGroupeGenerique(int n){
        return GroupeGenerique.builder()
                .idGroupeGenerique((long) n)
                .identifiantGroupeGenerique(String.format("identifiant %d", n))
                .typeGenerique(String.format("type %d", n))
                .numeroTri(String.format("numero %d", n))
                .libelle(String.format("libelle %d", n))
                .build();
    }

    public static Utilisateur getUtilisateur(int n) {
        return Utilisateur.builder()
                .nom(String.format("nom %d", n))
                .prenom(String.format("prenom %d", n))
                .email(String.format("email %d", n))
                .adresse(String.format("adresse %d", n))
                .motDePasse(String.format("mot de passe %d", n))
                .dateNaissance(Instant.now())
                .ville(String.format("ville %d", n))
                .telephone(String.format("telephone %d", n))
                .codePostal(String.format("code postal %d", n))
                .build();
    }

    public static Commande getCommande(int n, StatusCommande statusCommande) {
        return Commande.builder()
                .numeroCommande((long) n)
                .dateCommande(Instant.now())
                .total(n * 10)
                .facture(String.format("facture %d", n))
                .status(statusCommande)
                .commandeMedicaments(null)
                .build();
    }

    public static CommandeMedicament getCommandeMedicament(int n){
            return CommandeMedicament.builder()
                    .commandeMedicamentId((long) n)
                    .quantite(n * 10)
                    .build();
    }
    public static Livraison getLivraison() {
        return Livraison.builder()
                .dateLivraison(Instant.now())
                .livraisonPresentations(null)
                .build();
    }
    public static CommandeType getCommandeType(int n,String name){
        return CommandeType.builder()
                .commandeTypeId((long) n)
                .name(name)
                .build();
    }
}
