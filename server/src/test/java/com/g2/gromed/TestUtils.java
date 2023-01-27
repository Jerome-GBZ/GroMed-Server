package com.g2.gromed;

import com.g2.gromed.entity.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class TestUtils {

    public static Presentation getPresentationMedicamentSimple(int n) {
        return Presentation.builder()
                .codeCIP7(String.format("CIP7-%d", n))
                .denomination(String.format("Présentation médicament %d", n))
                .statusAdministratif(String.format("Statut admin médicament %d", n))
                .etatCommercialisation(String.format("Etat comm médicament %d", n))
                .tauxRemboursement((double) n)
                .indicationRemboursement(String.format("Indication remboursement médicament %d", n))
                .prix((double) 10 * n)
                .stock(100)
                .medicament(getMedicamentSimple(1))
                .build();
    }

    public static Presentation getPresentationWithGivenMedicament(int n, Medicament medicament) {
        return Presentation.builder()
                .codeCIP7(String.format("CIP7-%d", n))
                .denomination(String.format("Présentation médicament %d", n))
                .statusAdministratif(String.format("Statut admin médicament %d", n))
                .etatCommercialisation(String.format("Etat comm médicament %d", n))
                .tauxRemboursement((double) n)
                .indicationRemboursement(String.format("Indication remboursement médicament %d", n))
                .prix((double) 10 * n)
                .stock(100)
                .medicament(medicament)
                .build();
    }

    public static Presentation getPresentationMedicamentFull(int n, int nbGenerique, int nbInfo, int nbCompo, int nbCondition) {
        return Presentation.builder()
                .codeCIP7(String.format("CIP7-%d", n))
                .denomination(String.format("Présentation médicament %d", n))
                .statusAdministratif(String.format("Statut admin médicament %d", n))
                .etatCommercialisation(String.format("Etat comm médicament %d", n))
                .tauxRemboursement((double) n)
                .indicationRemboursement(String.format("Indication remboursement médicament %d", n))
                .prix((double) 10 * n)
                .stock(100)
                .medicament(getMedicamentFull(1, nbGenerique, nbInfo, nbCompo, nbCondition))
                .build();
    }

    public static Medicament getMedicamentFull(int n, int nbGroupeGenerique, int nbInfoImportante, int nbCompositions, int nbConditionDelivrance) {
        List<InfoImportante> infoImportanteList = getInfoImportanteList(nbInfoImportante);
        List<GroupeGenerique> groupeGeneriqueList = getGroupeGeneriqueList(nbGroupeGenerique);
        List<Composition> compositionList = getCompositionList(nbCompositions);
        List<ConditionDelivrance> conditionDelivranceList = getConditionDelivranceList(nbConditionDelivrance);
        return Medicament.builder()
                .codeCIS(String.format("CIS-%d", 1))
                .formePharmaceutique(String.format("Forme pharma médicament %d", n))
                .voiesAdministratifAMM(String.format("Forme pharma médicament %d", n))
                .typeProcedureAMM(String.format("Type procédure médicament %d", n))
                .dateAMM(new Date())
                .etatCommercialisation(String.format("Etat commercialisation %d", n))
                .statusBDM(String.format("Status BDM médicament %d", n))
                .numeroAutorisationEuropeenne(String.format("Numéro auto %d", n))
                .titulaire(String.format("Titulaire médicament %d", n))
                .surveillanceRenforcee("Non")
                .denomination(String.format("Dénomination médicament %d", n))
                .groupeGeneriques(groupeGeneriqueList)
                .infoImportantes(infoImportanteList)
                .compositions(compositionList)
                .conditionDelivrances(conditionDelivranceList)
                .build();
    }

    public static Medicament getMedicamentSimple(int n) {
        return Medicament.builder()
                .codeCIS(String.format("CIS-%d", n))
                .formePharmaceutique(String.format("Forme pharma médicament %d", n))
                .voiesAdministratifAMM(String.format("Forme pharma médicament %d", n))
                .typeProcedureAMM(String.format("Type procédure médicament %d", n))
                .dateAMM(new Date())
                .etatCommercialisation(String.format("Etat commercialisation %d", n))
                .statusBDM(String.format("Status BDM médicament %d", n))
                .numeroAutorisationEuropeenne(String.format("Numéro auto %d", n))
                .titulaire(String.format("Titulaire médicament %d", n))
                .surveillanceRenforcee("Non")
                .denomination(String.format("Dénomination médicament %d", n))
                .groupeGeneriques(new ArrayList<>())
                .infoImportantes(new ArrayList<>())
                .compositions(new ArrayList<>())
                .conditionDelivrances(new ArrayList<>())
                .build();
    }

    public static List<InfoImportante> getInfoImportanteList(int nb) {
        List<InfoImportante> infoImportanteList = new ArrayList<>();
        for (int i = 0; i < nb; i++) {
            infoImportanteList.add(InfoImportante.builder()
                    .idInfoImportante((long) i)
                    .message(String.format("message %d", i))
                    .dateDebut(new Date())
                    .dateFin(new Date())
                    .lien(String.format("lien %d", i))
                    .build());

        }
        return infoImportanteList;
    }

    public static List<Composition> getCompositionList(int nb) {
        List<Composition> compositions = new ArrayList<>();
        for (int i = 0; i < nb; i++) {
            compositions.add(Composition.builder()
                    .codeSubstance(String.format("code substance %d", i))
                    .dosage(String.format("dosage %d", i))
                    .denominationSubstance(String.format("denomination %d", i))
                    .referenceDosage(String.format("reference dosage %d", i))
                    .natureComposant(String.format("nature composant %d", i))
                    .designationElementPharmaceutique(String.format("designation element pharmaceutique %d", i))
                    .build());
        }
        return compositions;
    }

    public static List<GroupeGenerique> getGroupeGeneriqueList(int nb) {
        List<GroupeGenerique> groupeGeneriques = new ArrayList<>();
        for (int i = 0; i < nb; i++) {
            groupeGeneriques.add(GroupeGenerique.builder()
                    .identifiantGroupeGenerique(String.format("identifiant %d", i))
                    .libelle(String.format("libelle %d", i))
                    .typeGenerique(String.format("type %d", i))
                    .medicament(getMedicamentSimple(0))
                    .numeroTri(String.format("numero tri %d", i))
                    .build());
        }
        return groupeGeneriques;
    }

    public static List<ConditionDelivrance> getConditionDelivranceList(int nb) {
        List<ConditionDelivrance> conditionDelivrances = new ArrayList<>();
        for (int i = 0; i < nb; i++) {
            conditionDelivrances.add(ConditionDelivrance.builder()
                    .idConditionDelivrance((long) i)
                    .condition(String.format("condition %d", i))
                    .build());
        }
        return conditionDelivrances;
    }

    public static List<Etablissement> getEtablissementList(int nb) {
        List<Etablissement> etablissements = new ArrayList<>();
        for (int i = 0; i < nb; i++) {
            etablissements.add(Etablissement.builder()
                    .etalab(String.format("etalab%d", i))
                    .finess((long) i)
                    .codePostal(String.format("code postal %d", i))
                    .region(String.format("code region %d", i))
                    .adresse(String.format("adresse %d", i))
                    .categorie(String.format("categorie %d", i))
                    .departement(String.format("departement %d", i))
                    .nom(String.format("nom %d", i))
                    .telephone(String.format("telephone %d", i))
                    .ville(String.format("ville %d", i))
                    .build());
        }
        return etablissements;
    }

    public static List<Utilisateur> getUtilisateurList(int nb) throws ParseException {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
        String stringDate = DateFor.format(date);
        for (int i = 0; i < nb; i++) {
            utilisateurs.add(Utilisateur.builder()
                    .nom(String.format("nom %d", i))
                    .prenom(String.format("prenom %d", i))
                    .email(String.format("email %d", i))
                    .adresse(String.format("adresse %d", i))
                    .motDePasse(String.format("mot de passe %d", i))
                    .dateNaissance(DateFor.parse(DateFor.format(new Date())))
                    .ville(String.format("ville %d", i))
                    .telephone(String.format("telephone %d", i))
                    .codePostal(String.format("code postal %d", i))
                    .etablissement(getEtablissementList(1).get(0))
                    .build());
        }
        return utilisateurs;
    }

    public static Utilisateur getSimpleUtilisateurWithEmail(String email) {
        return Utilisateur.builder()
                .email(email)
                .build();
    }

    public static Commande getCommandeWith3Products(int n, List<Presentation> presentations) {
        Commande commande = Commande.builder()
                .numeroCommande((long) n)
                .total(10)
                .commandeType(null)
                .dateCommande(new Date())
                .utilisateur(null)
                .build();

        List<CommandeMedicament> commandeMedicaments = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            commandeMedicaments.add(CommandeMedicament.builder()
                    .commande(commande)
                    .quantite((i+1) * 50)
                    .presentation(presentations.get(i))
                    .build());
        }

        commande.setCommandeMedicaments(commandeMedicaments);
        return commande;
    }

    public static Commande getCommandeSimple(int n){
        return Commande.builder()
                .numeroCommande((long) n)
                .commandeMedicaments(new ArrayList<>())
                .total(10)
                .build();
    }

}
