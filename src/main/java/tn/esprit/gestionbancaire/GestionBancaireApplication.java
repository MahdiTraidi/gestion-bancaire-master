package tn.esprit.gestionbancaire;

import java.time.Instant;
import java.util.Calendar;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.context.annotation.Bean;

import tn.esprit.gestionbancaire.enums.CivilState;
import tn.esprit.gestionbancaire.enums.Sexe;
import tn.esprit.gestionbancaire.model.Client;
import tn.esprit.gestionbancaire.model.User;
import tn.esprit.gestionbancaire.repository.UserRepository;
import tn.esprit.gestionbancaire.services.ClientService;
import tn.esprit.gestionbancaire.services.CreditService;
import tn.esprit.gestionbancaire.services.UserService;

@SpringBootApplication
@EnableScheduling
@EnableAspectJAutoProxy
public class GestionBancaireApplication {

    public static void main(String[] args) {

        SpringApplication.run(GestionBancaireApplication.class, args);


    }
    @Bean
    ApplicationRunner init(UserService service,ClientService clientService) {
        return args -> {
            	createUser(service,"admin",clientService);
            	createUser(service,"client",clientService);
            	createUser(service,"employe",clientService);
        };
    }
	private void createUser(UserService service,String role, ClientService clientService) {
		Optional<User> user =service.getUserByUsername(role);
		if(user.isEmpty())
		{
			Calendar cal = Calendar.getInstance();
			cal.set(1995, 01, 01);
			Client client=  new Client("1253",role,role,cal.getTime(),"docteur",true,Sexe.H,CivilState.Maried,role+"@gmail.com");
			clientService.save(client);
			User newUser = new User(role,role,true,client,role);
			newUser.setCreationDate(Instant.now());
			service.saveUser(newUser);
			System.out.println(role+" added");
		}
	}

}
