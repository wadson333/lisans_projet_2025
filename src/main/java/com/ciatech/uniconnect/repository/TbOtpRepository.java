package com.ciatech.uniconnect.repository;

import com.ciatech.uniconnect.domain.TbOtp;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TbOtp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TbOtpRepository extends JpaRepository<TbOtp, Long> {}
