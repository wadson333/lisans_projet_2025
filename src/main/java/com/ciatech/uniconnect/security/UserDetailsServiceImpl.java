package com.ciatech.uniconnect.security;

import com.ciatech.uniconnect.domain.TbUsers;
import com.ciatech.uniconnect.domain.enumeration.AccountStatus;
import com.ciatech.uniconnect.repository.TbUsersRepository;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Authenticate a user from the database.
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final TbUsersRepository tbUsersRepository;

    // Inject the repository via constructor
    public UserDetailsServiceImpl(TbUsersRepository tbUsersRepository) {
        this.tbUsersRepository = tbUsersRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating user '{}' with roles and permissions", login);

        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);

        // MODIFICATION ICI : Utiliser la nouvelle méthode du repository
        Optional<TbUsers> userFromDatabase = tbUsersRepository.findOneWithRolesAndPermissionsByEmailIgnoreCase(lowercaseLogin);

        return userFromDatabase
            .map(tbUser -> {
                if (tbUser.getIsActive() != AccountStatus.ACTIVE) {
                    throw new UserNotActivatedException("User " + lowercaseLogin + " was not active");
                }

                // MODIFICATION ICI : Logique pour collecter Rôles ET Permissions
                // Utilisation d'un Set pour éviter les doublons d'autorités
                Set<GrantedAuthority> authorities = tbUser
                    .getRoles()
                    .stream()
                    .flatMap(role -> { // Pour chaque rôle...
                        // Créer un Stream contenant :
                        // 1. Le rôle lui-même (préfixé)
                        Stream<GrantedAuthority> roleAuthority = Stream.of(new SimpleGrantedAuthority(role.getName()));
                        // 2. Toutes les permissions associées à ce rôle
                        Stream<GrantedAuthority> permissionAuthorities = role
                            .getPermissions() // Accéder aux permissions du rôle
                            .stream()
                            .map(TbPermissions::getAction) // Obtenir le nom de l'action (ex: "CREATE_USER")
                            .filter(Objects::nonNull)
                            .map(SimpleGrantedAuthority::new); // Créer l'autorité pour la permission
                        // Combiner les deux streams (rôle + permissions du rôle)
                        return Stream.concat(roleAuthority, permissionAuthorities);
                    })
                    .collect(Collectors.toSet()); // Collecter dans un Set

                // Convertir le Set en List si nécessaire pour le constructeur User
                List<GrantedAuthority> grantedAuthorities = new ArrayList<>(authorities);

                log.debug("User '{}' authorities: {}", lowercaseLogin, grantedAuthorities);

                return new User(tbUser.getEmail(), tbUser.getPassword(), grantedAuthorities);
            })
            .orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database"));
    }
}
