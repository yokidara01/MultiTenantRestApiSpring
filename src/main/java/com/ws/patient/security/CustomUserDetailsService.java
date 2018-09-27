package com.ws.patient.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ws.patient.model.Account;
import com.ws.patient.model.Privilege;
import com.ws.patient.model.Role;
import com.ws.patient.repository.AccountDao;

/**
 * Service pour extraire les infs des accounts
 * 
 * @author Asus
 *
 */

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

	private final AccountDao accountRepository;

	public CustomUserDetailsService(final AccountDao accountRepository) {
		this.accountRepository = accountRepository;
	}

	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		final Optional<Account> accountByUsername = accountRepository.findByUsername(username);
		if (!accountByUsername.isPresent()) {
			throw new UsernameNotFoundException("User " + username + " not found.");
		}
		final Account account = accountByUsername.get();
		if (account.getRoles() == null || account.getRoles().isEmpty()) {
			throw new UsernameNotFoundException("User not authorized.");
		}
		final User userDetails = new User(account.getUsername(), account.getPassword(), account.isEnabled(), true, true,
				true, getGrantedAuthorities(getRolesNames(account.getRoles())));
		// getAuthorities(account.getRoles())
		return userDetails;
	}

	private Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles) {
		return getGrantedAuthorities(getPrivileges(roles));
	}

	private List<String> getPrivileges(final Collection<Role> roles) {
		final List<String> privileges = new ArrayList<String>();
		final List<Privilege> collection = new ArrayList<Privilege>();
		for (final Role role : roles) {
			collection.addAll(role.getPrivileges());
		}
		for (final Privilege item : collection) {
			privileges.add(item.getName());
		}
		return privileges;
	}

	private List<String> getRolesNames(final Collection<Role> roles) {
		final List<String> privileges = new ArrayList<String>();
		final List<Role> collection = new ArrayList<Role>();
		for (final Role role : roles) {
			collection.add(role);
		}
		for (final Role item : collection) {
			privileges.add(item.getName());
		}
		return privileges;
	}

	private List<GrantedAuthority> getGrantedAuthorities(final List<String> privileges) {
		final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (final String privilege : privileges) {
			authorities.add(new SimpleGrantedAuthority(privilege));
		}
		return authorities;
	}

}