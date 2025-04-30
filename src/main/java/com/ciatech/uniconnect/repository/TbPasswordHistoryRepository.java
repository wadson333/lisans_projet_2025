package com.ciatech.uniconnect.repository;

import com.ciatech.uniconnect.domain.TbPasswordHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TbPasswordHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TbPasswordHistoryRepository extends JpaRepository<TbPasswordHistory, Long> {}
