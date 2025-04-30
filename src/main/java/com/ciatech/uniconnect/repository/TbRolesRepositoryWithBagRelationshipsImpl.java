package com.ciatech.uniconnect.repository;

import com.ciatech.uniconnect.domain.TbRoles;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class TbRolesRepositoryWithBagRelationshipsImpl implements TbRolesRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String TBROLES_PARAMETER = "tbRoles";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<TbRoles> fetchBagRelationships(Optional<TbRoles> tbRoles) {
        return tbRoles.map(this::fetchPermissions);
    }

    @Override
    public Page<TbRoles> fetchBagRelationships(Page<TbRoles> tbRoles) {
        return new PageImpl<>(fetchBagRelationships(tbRoles.getContent()), tbRoles.getPageable(), tbRoles.getTotalElements());
    }

    @Override
    public List<TbRoles> fetchBagRelationships(List<TbRoles> tbRoles) {
        return Optional.of(tbRoles).map(this::fetchPermissions).orElse(Collections.emptyList());
    }

    TbRoles fetchPermissions(TbRoles result) {
        return entityManager
            .createQuery("select tbRoles from TbRoles tbRoles left join fetch tbRoles.permissions where tbRoles.id = :id", TbRoles.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<TbRoles> fetchPermissions(List<TbRoles> tbRoles) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, tbRoles.size()).forEach(index -> order.put(tbRoles.get(index).getId(), index));
        List<TbRoles> result = entityManager
            .createQuery("select tbRoles from TbRoles tbRoles left join fetch tbRoles.permissions where tbRoles in :tbRoles", TbRoles.class)
            .setParameter(TBROLES_PARAMETER, tbRoles)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
