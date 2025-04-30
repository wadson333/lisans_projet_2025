package com.ciatech.uniconnect.repository;

import com.ciatech.uniconnect.domain.TbRoles;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface TbRolesRepositoryWithBagRelationships {
    Optional<TbRoles> fetchBagRelationships(Optional<TbRoles> tbRoles);

    List<TbRoles> fetchBagRelationships(List<TbRoles> tbRoles);

    Page<TbRoles> fetchBagRelationships(Page<TbRoles> tbRoles);
}
