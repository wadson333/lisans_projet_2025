package com.ciatech.uniconnect.repository;

import com.ciatech.uniconnect.domain.TbPermissions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TbPermissions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TbPermissionsRepository extends JpaRepository<TbPermissions, Long> {}
