package com.ciatech.uniconnect.repository;

import com.ciatech.uniconnect.domain.TbLoginAttempts;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TbLoginAttempts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TbLoginAttemptsRepository extends JpaRepository<TbLoginAttempts, Long> {}
