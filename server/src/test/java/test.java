import com.g2.gromed.GromedApplication;
import com.g2.gromed.entity.Etablissement;
import com.g2.gromed.entity.Utilisateur;
import com.g2.gromed.repository.IEtablissementRepository;
import com.g2.gromed.repository.IUtilisateurRepository;
import lombok.extern.java.Log;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@Log
@SpringBootTest(classes = GromedApplication.class)
public class test {
	
	@Autowired
	private IUtilisateurRepository utilisateurRepository;
	
	@Autowired
	private IEtablissementRepository etablissementRepository;
	
	@BeforeEach
	public void setUp(){
		log.info("startup - creating DB connection");
		Etablissement e1 = new Etablissement(1L,"1","Hopital 1","0505050505","Coin coin","20165","pouet","20","region","superette",null);
		Etablissement e2 = new Etablissement(2L,"2","Hopital 2","0485251693","cuicui","98542","pouet","98","idk","hopital",null);
		e1 = etablissementRepository.save(e1);
		e2 = etablissementRepository.save(e2);
		utilisateurRepository.save(new Utilisateur(1L,"test1@gmail.com","test1","john","salut",new Date(),"coin","20152","turlut","0606060606",e1,null,null));
		utilisateurRepository.save(new Utilisateur(2L,"test2@gmail.com","test2","doe","chouet",new Date(),"Pas","29172","coinpied","0607485120",e2,null,null));
		utilisateurRepository.save(new Utilisateur(3L,"test3@gmail.com","test3","fort","youpi",new Date(),"Ici","36192","compote","0607512345",e2,null,null));
	}
	@Test
	void getUtilisateurSuccess() {
		Utilisateur u = utilisateurRepository.findFirstByEmailAndMotDePasse("test1@gmail.com","salut");
		Assertions.assertEquals("Hopital 1",u.getEtablissement().getNom());
		Assertions.assertEquals("john",u.getPrenom());
		Assertions.assertEquals("test1",u.getNom());
	}
	@Test
	void getUtilisateurWrongPassword() {
		Utilisateur u = utilisateurRepository.findFirstByEmailAndMotDePasse("test2@gmail.com","salut");
		Assertions.assertNull(u);
	}
	@Test
	void getUtilisateurWrongEmail() {
		Utilisateur u = utilisateurRepository.findFirstByEmailAndMotDePasse("test1@gmail.com","pouet");
		Assertions.assertNull(u);
	
	}
}