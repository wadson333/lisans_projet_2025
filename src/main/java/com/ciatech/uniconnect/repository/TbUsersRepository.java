package com.ciatech.uniconnect.repository;

import com.ciatech.uniconnect.domain.TbUsers;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TbUsers entity.
 *
 * When extending this class, extend TbUsersRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface TbUsersRepository extends TbUsersRepositoryWithBagRelationships, JpaRepository<TbUsers, Long> {
    default Optional<TbUsers> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<TbUsers> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<TbUsers> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }

    // Méthode pour trouver un utilisateur par email (ignorant la casse)
    // et charger ses rôles immédiatement grâce à @EntityGraph.
    @EntityGraph(attributePaths = "roles") // Charge la collection 'roles'
    Optional<TbUsers> findOneWithRolesByEmailIgnoreCase(String email);

    // JHipster a peut-être aussi généré findOneByEmailIgnoreCase,
    // = "roles") // Fetch the 'roles' collection eagerly
    Optional<TbUsers> findOneWithRolesByEmail(String email);

    Optional<TbUsers> findByEmail(String email);

    Optional<TbUsers> findByCode(String code); // If 'code' is also a unique identifier
    // Méthode pour trouver un utilisateur par code (ignorant la casse)
    // et charger ses rôles immédiatement grâce à @EntityGraph.
    Optional<TbUsers> findOneWithRolesByCodeIgnoreCase(String code); // If 'code' is also a unique identifier

    /**
     * Trouve un utilisateur par son email (sans tenir compte de la casse)
     * et charge immédiatement sa collection de rôles ET les permissions associées à ces rôles.
     *
     * @param email l'email à rechercher.
     * @return un Optional contenant TbUsers avec ses rôles et permissions, ou vide si non trouvé.
     */
    // MODIFICATION ICI : Ajout de "roles.permissions" à attributePaths
    @EntityGraph(attributePaths = { "roles", "roles.permissions" })
    Optional<TbUsers> findOneWithRolesAndPermissionsByEmailIgnoreCase(String email);

    @EntityGraph(attributePaths = { "roles", "roles.permissions" })
    Optional<TbUsers> findOneWithRolesAndPermissionsByCodeIgnoreCase(String code);
}
