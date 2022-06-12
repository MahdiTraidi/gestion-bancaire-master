package tn.esprit.gestionbancaire.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tn.esprit.gestionbancaire.model.MyUserDetails;
import tn.esprit.gestionbancaire.model.User;
import tn.esprit.gestionbancaire.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<User> user = userRepository.findUserByUserName(username);
		user.orElseThrow(()->new UsernameNotFoundException("Not found: "+username));
		return user.map(MyUserDetails::new).get();

	}

}
