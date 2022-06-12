package tn.esprit.gestionbancaire.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import tn.esprit.gestionbancaire.model.User;
import tn.esprit.gestionbancaire.repository.UserRepository;

@Service
public class UserService implements IUserService{

	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public User getUserById(Long id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id).get() ;
	}

	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public void deleteUser(Long id) {
		// TODO Auto-generated method stub
		userRepository.deleteById(id);
	}

	@Override
	public User updateUser(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user) ;
	}

	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public Optional<User> getUserByUsername(String username) {
		// TODO Auto-generated method stub
		return userRepository.findUserByUserName(username);
	}

	@Override
	public Map<String, List<User>> getUsersByProfession() {
		// TODO Auto-generated method stub
		Map<String, List<User>> map = new HashMap<>();
		for (User user : userRepository.findAll()) {
			if(map.get(user.getClient().getJob())==null)
				map.put(user.getClient().getJob(), new ArrayList<User>());
			map.get(user.getClient().getJob()).add(user);

		}
		return map;
	}

	@Override
	public Map<Integer, List<User>> getUsersByAge() {
		Map<Integer, List<User>> map = new HashMap<>();
		for (User user : userRepository.findAll()) {
			if(map.get(user.getAge())==null)
				map.put(user.getAge(), new ArrayList<User>());
			map.get(user.getAge()).add(user);

		}
		return map;
	}

	@Override
	public Map<String, List<User>> getUsersBySexe() {
		Map<String, List<User>> map = new HashMap<>();
		for (User user : userRepository.findAll()) {
			if(map.get(user.getClient().getSexe().toString())==null)
				map.put(user.getClient().getSexe().toString(), new ArrayList<User>());
			map.get(user.getClient().getSexe().toString()).add(user);

		}
		return map;
	}

	@Override
	public Map<String, List<User>> getUsersByCivilStatus() {
		Map<String, List<User>> map = new HashMap<>();
		for (User user : userRepository.findAll()) {
			if(map.get(user.getClient().getCivilState().toString())==null)
				map.put(user.getClient().getCivilState().toString(), new ArrayList<User>());
			map.get(user.getClient().getCivilState().toString()).add(user);

		}
		return map;
	}
}
