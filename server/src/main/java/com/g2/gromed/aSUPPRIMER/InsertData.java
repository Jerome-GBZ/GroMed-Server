package com.g2.gromed.aSUPPRIMER;

import com.g2.gromed.entity.*;
import com.g2.gromed.repository.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.util.*;

@Log
@Service
public class InsertData {
	@Autowired
	private IMedicamentRepository medicamentRepository;
	@Autowired
	private IPresentationRepository presentationRepository;
	@Autowired
	private IEtablissementRepository etablissementRepository;
	
	@Autowired
	private IUtilisateurRepository utilisateurRepository;
	List<Medicament> medicamentsEntityList = new ArrayList<>();
	public void transformData() throws ParseException {
		// File paths for the input filesserver/src/main/resources/csv/CIS_bdpm.txt
		String[] filePaths = {"server/src/main/resources/csv/CIS_bdpm.txt", "server/src/main/resources/csv/CIS_CIP_bdpm.txt", "server/src/main/resources/csv/CIS_COMPO_bdpm.txt", "server/src/main/resources/csv/CIS_GENER_bdpm.txt", "server/src/main/resources/csv/CIS_CPD_bdpm.txt", "server/src/main/resources/csv/CIS_INFO_bdpm.txt"};
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
		// Delimiter used in TSV files
		String delimiter = "\t";
		int[] keyIndexes = {0, 0, 0, 2, 0, 0};
		// Array to hold the extracted data
		ArrayList<String[][]> allData = new ArrayList<>();
		
		ReadFile(filePaths, delimiter, allData);
		
		//Set of keys in the first array
		HashSet<String> keys = new HashSet<>();
		for (String[] fields : allData.get(0)) {
			keys.add(fields[0]);
		}
		
		// Iterate over the last 3 files
		for (int i = 1; i < allData.size(); i++) {
			String[][] data = allData.get(i);
			List<String[]> newData = new ArrayList<>();
			for (String[] fields : data) {
				if (keys.contains(fields[keyIndexes[i]])) {
					newData.add(fields);
				}
			}
			//Convert List<String[]> to String[][]
			String[][] newDataArray = newData.toArray(new String[newData.size()][]);
			allData.set(i, newDataArray);
			
			//Write the new data array to file
			try (FileWriter fw = new FileWriter(filePaths[i], StandardCharsets.UTF_8)) {
				for (String[] fields : newDataArray) {
					for (int j = 0; j < fields.length; j++) {
						fw.write(fields[j]);
						if (j < fields.length - 1) {
							fw.write(delimiter);
						}
					}
					fw.write("\n");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		ArrayList<String[][]> allData2 = new ArrayList<>();
		ReadFile(filePaths, delimiter, allData2);
		
		String[][] medicaments = allData2.get(0);
		String[][] presentations = allData2.get(1);
		String[][] compositions = allData.get(2);
		String[][] groupeGeneriques = allData2.get(3);
		String[][] conditionDelivrances = allData.get(4);
		String[][] infoImportantes = allData.get(5);
		
		Map<String, List<String[]>> compositionsByCodeCIS = new HashMap<>();
		for (String[] fields : compositions) {
			//populate medicament fields from fields array
			//...
			if (!compositionsByCodeCIS.containsKey(fields[0].trim())) {
				compositionsByCodeCIS.put(fields[0].trim(), new ArrayList<>());
			}
			compositionsByCodeCIS.get(fields[0].trim()).add(fields);
		}
		Map<String, List<String[]>> groupeGeneriquesByCodeCIS = new HashMap<>();
		for (String[] fields : groupeGeneriques) {
			//populate medicament fields from fields array
			//...
			if (!groupeGeneriquesByCodeCIS.containsKey(fields[2].trim())) {
				groupeGeneriquesByCodeCIS.put(fields[2].trim(), new ArrayList<>());
			}
			groupeGeneriquesByCodeCIS.get(fields[2].trim()).add(fields);
		}
		Map<String, List<String[]>> conditionDelivrancesByCodeCIS = new HashMap<>();
		for (String[] fields : conditionDelivrances) {
			//populate medicament fields from fields array
			//...
			if (!conditionDelivrancesByCodeCIS.containsKey(fields[0].trim())) {
				conditionDelivrancesByCodeCIS.put(fields[0].trim(), new ArrayList<>());
			}
			conditionDelivrancesByCodeCIS.get(fields[0].trim()).add(fields);
		}
		Map<String, List<String[]>> infoImportantesByCodeCIS = new HashMap<>();
		for (String[] fields : infoImportantes) {
			//populate medicament fields from fields array
			//...
			if (!infoImportantesByCodeCIS.containsKey(fields[0].trim())) {
				infoImportantesByCodeCIS.put(fields[0].trim(), new ArrayList<>());
			}
			infoImportantesByCodeCIS.get(fields[0].trim()).add(fields);
		}
		log.info("tri termin??");
		int j = 0;
		int batchSize = 100;
		for (String[] fields : medicaments) {
			Medicament medicament = new Medicament();
			medicament.setCodeCIS(fields[0].replace("\uFEFF", "").trim());
			medicament.setDenomination(fields[1].trim());
			medicament.setFormePharmaceutique(fields[2].trim());
			medicament.setVoiesAdministratifAMM(fields[3].trim());
			medicament.setStatusBDM(fields[8].trim());
			medicament.setEtatCommercialisation(fields[6].trim());
			medicament.setDateAMM(formatter.parse(fields[7].replace("/", "-").trim()).toInstant());
			
			medicament.setNumeroAutorisationEuropeenne(fields[9].trim());
			medicament.setTitulaire(fields[10].trim());
			medicament.setSurveillanceRenforcee(fields[11].trim());
			medicament.setTypeProcedureAMM(fields[4].trim());
			
			List<String[]> groupeGeneriquesMedicament = groupeGeneriquesByCodeCIS.get(fields[0]);
			List<GroupeGenerique> groupeGeneriquesEntity = new ArrayList<>();
			if (groupeGeneriquesMedicament != null) {
				for (String[] groupeGenerique : groupeGeneriquesMedicament) {
					GroupeGenerique groupe = new GroupeGenerique();
					groupe.setIdentifiantGroupeGenerique(groupeGenerique[0].trim());
					groupe.setLibelle(groupeGenerique[1].trim());
					groupe.setNumeroTri(groupeGenerique[4].trim());
					groupe.setTypeGenerique(groupeGenerique[3].trim());
					groupeGeneriquesEntity.add(groupe);
				}
				medicament.setGroupeGeneriques(groupeGeneriquesEntity);
			}
			//Same for InfoImportante
			List<String[]> infoImportantesMedicament = infoImportantesByCodeCIS.get(fields[0].trim());
			List<InfoImportante> infoImportantesEntity = new ArrayList<>();
			if (infoImportantesMedicament != null) {
				for (String[] infoImportante : infoImportantesMedicament) {
					InfoImportante info = new InfoImportante();
					info.setDateDebut(formatter.parse(infoImportante[1].replace("/", "-").trim()).toInstant());
					info.setDateFin(formatter.parse(infoImportante[2].replace("/", "-").trim()).toInstant());
					info.setLien(infoImportante[4].trim());
					info.setMessage(infoImportante[5].trim());
					infoImportantesEntity.add(info);
				}
				medicament.setInfoImportantes(infoImportantesEntity);
			}
			//Same for ConditionDelivrance
			List<String[]> conditionDelivrancesMedicament = conditionDelivrancesByCodeCIS.get(fields[0].trim());
			List<ConditionDelivrance> conditionDelivrancesEntity = new ArrayList<>();
			if (conditionDelivrancesMedicament != null) {
				for (String[] conditionDelivrance : conditionDelivrancesMedicament) {
					ConditionDelivrance condition = new ConditionDelivrance();
					condition.setCondition(conditionDelivrance[1].trim());
					conditionDelivrancesEntity.add(condition);
				}
				medicament.setConditionDelivrances(conditionDelivrancesEntity);
			}
			//Same for composition
			List<String[]> compositionsMedicament = compositionsByCodeCIS.get(fields[0].trim());
			List<Composition> compositionsEntity = new ArrayList<>();
			if (compositionsMedicament != null) {
				for (String[] composition : compositionsMedicament) {
					Composition compo = new Composition();
					compo.setCodeSubstance(composition[2].trim());
					compo.setDenominationSubstance(composition[3].trim());
					compo.setDosage(composition[4].trim());
					compo.setDesignationElementPharmaceutique(composition[1].trim());
					compo.setNatureComposant(composition[6].trim());
					compo.setReferenceDosage(composition[5].trim());
					compositionsEntity.add(compo);
				}
				medicament.setCompositions(compositionsEntity);
			}
			medicamentsEntityList.add(medicament);
		}
	log.info("Medicaments created");
	medicamentsEntityList = medicamentRepository.saveAll(medicamentsEntityList);
	log.info("Medicaments saved");
	List<Presentation> presentationsEntityList = new ArrayList<>();
	int t = 0;
	for(String[] presentation:presentations) {
		String prix = "0.0";
		if (presentation.length > 10 && !Objects.equals(presentation[9].trim(), "")) {
			prix = presentation[9].replace(",", ".").replace("%", "").trim();
			if (prix.split("\\.").length > 2) {
				prix = prix.replaceFirst("\\.", "").trim();
			}
		}
		String indicationRemboursement = "";
		if (Double.parseDouble(prix) > 0.0) {
			if (presentation.length == 13) {
				indicationRemboursement = presentation[12].replace("'", "''").trim();
			}
			Presentation pres = new Presentation();
			pres.setCodeCIP7(presentation[1].trim());
			pres.setDenomination(presentation[2].trim());
			pres.setEtatCommercialisation(presentation[4].trim());
			pres.setIndicationRemboursement(indicationRemboursement);
			pres.setPrix(Double.parseDouble(prix));
			pres.setStatusAdministratif(presentation[3].trim());
			pres.setStock(100);
			String tauxRemboursement = "0.0";
			if (presentation.length > 9 && !Objects.equals(presentation[8].trim(), "")) {
				tauxRemboursement = presentation[8].replace(",", ".").replace("%", "").trim();
			}
			pres.setTauxRemboursement(Double.parseDouble(tauxRemboursement));
			Medicament res = medicamentsEntityList.stream().filter(medicament -> medicament.getCodeCIS().equals(presentation[0].trim())).findFirst().orElse(null);
			if(res!=null) {
				pres.setMedicament(res);
				presentationsEntityList.add(pres);
			}
		}
	}
	log.info("Presentations created");
	presentationRepository.saveAll(presentationsEntityList);
	log.info("Presentations saved");
	List<String[]> data = new ArrayList<>();
	try(BufferedReader br = new BufferedReader(new FileReader("server/src/main/resources/csv/etalab_formatted.csv"))) {
		String line;
		while ((line = br.readLine()) != null) {
			// Use the delimiter to split the line into an array of fields
			String[] fields = line.split(";", Integer.MAX_VALUE);
			
			// Add the array of fields to the data list
			data.add(fields);
		}
	} catch(Exception e) {
		e.printStackTrace();
	}
	data.remove(0);
	int i = 0;
	while(data.size()>i &&!data.get(i)[8].equals("") &&data.get(i)[8]!=null) {
		i++;
	}
	List<Etablissement> etablissementsEntityList = new ArrayList<>();
	data =data.subList(0,i);
	//Convert List<String[]> to String[][]
	String[][] etablissements = data.toArray(new String[data.size()][]);
	int k = 0;
	for(String[] etablissment:etablissements) {
		String codePostal = etablissment[8].substring(0, etablissment[8].indexOf(" "));
		String ville = etablissment[8].substring(etablissment[8].indexOf(" ") + 1);
		Etablissement etablissement = new Etablissement();
		etablissement.setEtalab(etablissment[0].trim());
		try {
			etablissement.setFiness(Long.decode(etablissment[1].trim()));
			etablissement.setAdresse(etablissment[3].trim() + " " + etablissment[4].trim() + " " + etablissment[5].trim());
			etablissement.setCategorie(etablissment[10].trim());
			etablissement.setCodePostal(codePostal.trim());
			etablissement.setDepartement(etablissment[6].trim());
			etablissement.setNom(etablissment[2].trim());
			etablissement.setRegion(etablissment[7].trim());
			etablissement.setTelephone(etablissment[9].trim());
			etablissement.setVille(ville.trim());
			etablissementsEntityList.add(etablissement);
			k++;
		}catch (Exception e) {
		}
	}
	log.info("Etablissements created : "+k);
	etablissementRepository.saveAll(etablissementsEntityList);
	log.info("Etablissements saved");
	List<Utilisateur> utilisateurEntityList = new ArrayList<>();
	for(int m=0;m<4;m++) {
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setNom("nom"+m);
		utilisateur.setPrenom("prenom"+m);
		utilisateur.setAdresse("adresse"+m);
		utilisateur.setTelephone("telephone"+m);
		utilisateur.setEmail("mail"+m);
		utilisateur.setMotDePasse("motDePasse"+m);
		utilisateur.setEtablissement(etablissementsEntityList.get(m*15000));
		utilisateurEntityList.add(utilisateur);
	}
	log.info("Utilisateurs created");
	utilisateurRepository.saveAll(utilisateurEntityList);
	log.info("Utilisateurs saved");
	
}
	
	private static void ReadFile(String[] filePaths, String delimiter, ArrayList<String[][]> allData) {
		for (String filePath : filePaths) {
			// List to hold the extracted data
			List<String[]> data = new ArrayList<>();
			try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
				String line;
				while ((line = br.readLine()) != null) {
					// Use the delimiter to split the line into an array of fields
					String[] fields = line.split(delimiter, Integer.MAX_VALUE);
					
					// Add the array of fields to the data list
					data.add(fields);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			//Convert List<String[]> to String[][]
			String[][] dataArray = data.toArray(new String[data.size()][]);
			allData.add(dataArray);
		}
	}
	
}
