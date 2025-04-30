package com.ciatech.uniconnect.repository;

import com.ciatech.uniconnect.domain.TbUsers;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface TbUsersRepositoryWithBagRelationships {
    Optional<TbUsers> fetchBagRelationships(Optional<TbUsers> tbUsers);

    List<TbUsers> fetchBagRelationships(List<TbUsers> tbUsers);

    Page<TbUsers> fetchBagRelationships(Page<TbUsers> tbUsers);
}
