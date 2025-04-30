package com.ciatech.uniconnect.repository;

import com.ciatech.uniconnect.domain.TbAddresses;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TbAddresses entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TbAddressesRepository extends JpaRepository<TbAddresses, Long> {}
