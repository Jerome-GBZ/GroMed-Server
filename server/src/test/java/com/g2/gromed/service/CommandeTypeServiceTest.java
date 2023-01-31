package com.g2.gromed.service;

import com.g2.gromed.GromedApplication;
import com.g2.gromed.TestUtils;
import com.g2.gromed.composant.CommandeComposant;
import com.g2.gromed.composant.CommandeTypeComposant;
import com.g2.gromed.composant.UtilisateurComposant;
import com.g2.gromed.entity.*;
import com.g2.gromed.mapper.ICommandeMapper;
import com.g2.gromed.mapper.ICommandeTypeMapper;
import com.g2.gromed.model.dto.commande.PresentationRecapCommandeDTO;
import com.g2.gromed.model.dto.commandetype.CommandeTypeInfo;
import com.g2.gromed.model.dto.utilisateur.UtilisateurDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = GromedApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CommandeTypeServiceTest {
	@Autowired
	CommandeTypeService commandeTypeService;
	@MockBean
	CommandeTypeComposant commandeTypeComposant;
	@MockBean
	ICommandeTypeMapper commandeTypeMapper;
	@MockBean
	ICommandeMapper commandeMapper;
	
	@MockBean
	UtilisateurComposant utilisateurComposant;
	@MockBean
	CommandeService commandeService;
	
	@MockBean
	CommandeComposant commandeComposant;
	
	@Test
	void getCommandeTypes() {
		CommandeType ct1 = TestUtils.getCommandeType(1, "coucou");
		CommandeType ct2 = TestUtils.getCommandeType(2, "test");
		List<CommandeType> commandeTypes = List.of(ct1,ct2);
		CommandeTypeInfo cti1 = new CommandeTypeInfo();
		cti1.setNom("coucou");
		cti1.setNbProduit(0);
		cti1.setPrixTotal(0.0);
		CommandeTypeInfo cti2 = new CommandeTypeInfo();
		cti2.setNom("test");
		cti2.setNbProduit(0);
		cti2.setPrixTotal(0.0);
		when(commandeTypeComposant.getCommandeTypes(anyString(),anyString())).thenReturn(commandeTypes);
		when(commandeTypeMapper.toCommandeTypeInfo(ct1)).thenReturn(cti1);
		when(commandeTypeMapper.toCommandeTypeInfo(ct2)).thenReturn(cti2);
		List<CommandeTypeInfo> expected = List.of(cti1,cti2);
		List<CommandeTypeInfo> res = commandeTypeService.getCommandeTypes("mail","search");
		assertThat(res).usingRecursiveComparison().isEqualTo(expected);
	}
	
	@Test
	void getCommandeTypeDetail() {
		CommandeType ct1 = TestUtils.getCommandeType(1, "coucou");
		Commande commande = TestUtils.getCommande(1, StatusCommande.PANIER);
		CommandeMedicament cm1 = TestUtils.getCommandeMedicament(1);
		commande.setCommandeMedicaments(List.of(cm1));
		ct1.setCommande(commande);
		PresentationRecapCommandeDTO prcd = new PresentationRecapCommandeDTO();
		prcd.setQuantite(10);
		List<PresentationRecapCommandeDTO> expected = List.of(prcd);
		when(commandeTypeComposant.getCommandeType(anyString(),anyString())).thenReturn(ct1);
		when(commandeMapper.commandeMedicamentToPresentationRecapCommandeDTO(any())).thenReturn(prcd);
		List<PresentationRecapCommandeDTO> res = commandeTypeService.getCommandeTypeDetail("mail","coucou");
		assertThat(res).usingRecursiveComparison().isEqualTo(expected);
	}
	
	@Test
	void addCommandeTypeToUserCart() {
		Utilisateur u = TestUtils.getUtilisateur(1);
		CommandeType ct1 = TestUtils.getCommandeType(1, "coucou");
		Commande commande = TestUtils.getCommande(1, StatusCommande.PANIER);
		CommandeMedicament cm1 = TestUtils.getCommandeMedicament(1);
		Presentation p = TestUtils.getPresentation(1);
		cm1.setPresentation(p);
		commande.setCommandeMedicaments(List.of(cm1));
		ct1.setCommande(commande);
		
		when(utilisateurComposant.getUserByEmail(anyString())).thenReturn(u);
		when(commandeTypeComposant.getCommandeType(anyString(),anyString())).thenReturn(ct1);
		when(commandeComposant.getCart(anyString())).thenReturn(commande);
		UtilisateurDTO expected = new UtilisateurDTO();
		expected.setEmail(u.getEmail());
		expected.setNom(u.getNom());
		expected.setPrenom(u.getPrenom());
		expected.setNbMedicamentsPanier(1);
		UtilisateurDTO res = commandeTypeService.addCommandeTypeToUserCart("mail","coucou");
		assertThat(res).usingRecursiveComparison().isEqualTo(expected);
	}
	
}
