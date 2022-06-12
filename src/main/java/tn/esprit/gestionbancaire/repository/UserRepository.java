package tn.esprit.gestionbancaire.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.gestionbancaire.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>  {

	public Optional<User> findUserByUserName(String username);
}
