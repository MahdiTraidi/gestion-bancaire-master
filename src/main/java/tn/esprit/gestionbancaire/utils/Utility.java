package tn.esprit.gestionbancaire.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import tn.esprit.gestionbancaire.model.MyUserDetails;

public class Utility {

	public static MyUserDetails getCurrenUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			return (MyUserDetails) principal;
		}
		return null;
	}
}
