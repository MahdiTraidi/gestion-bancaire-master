package tn.esprit.gestionbancaire.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.gestionbancaire.model.Client;
import tn.esprit.gestionbancaire.model.User;
import tn.esprit.gestionbancaire.repository.ClientRepository;
import tn.esprit.gestionbancaire.services.IUserService;
import tn.esprit.gestionbancaire.utils.Constants;

@RestController()
@RequestMapping(Constants.APP_ROOT + "/user")

public class UserController {

	@Autowired
	IUserService userService;
	@Autowired
	ClientRepository clientRepository;

	@GetMapping("/getAll")
	private ResponseEntity<List<User>> getAll() {
		return new ResponseEntity<List<User>>(userService.getAll(), HttpStatus.OK);
	}

	@GetMapping("/getUsersByProfession")
	private ResponseEntity<Map<String, List<User>>> getUsersByProfession() {
		return new ResponseEntity<Map<String, List<User>>>(userService.getUsersByProfession(), HttpStatus.OK);
	}

	@GetMapping("/userById/{id}")
	private ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
		return new ResponseEntity<User>(userService.getUserById(id), HttpStatus.OK);
	}

	@DeleteMapping("/deleteUser/{id}")
	private void deleteBook(@PathVariable("id") Long id) {
		userService.deleteUser(id);
	}

	// creating post mapping that post the book detail in the database
	@PostMapping("/userRequest/{cin}")
	private ResponseEntity<User> saveBook(@RequestBody User user, @PathVariable("cin") String cin) {
		Client client = clientRepository.findBynID(cin);
		if (client != null) {
			user.setClient(client);
			return new ResponseEntity<User>(userService.saveUser(user), HttpStatus.OK);
		} else
			return new ResponseEntity("Cin not related to client", HttpStatus.FORBIDDEN);
	}

	// creating put mapping that updates the book detail
	@PutMapping("/updateUser")
	private ResponseEntity<User> update(@RequestBody User user) {
		return new ResponseEntity<User>(userService.updateUser(user), HttpStatus.OK);
	}
}
