package com.ciatech.uniconnect.repository;

import com.ciatech.uniconnect.domain.TbUsers;
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
public class TbUsersRepositoryWithBagRelationshipsImpl implements TbUsersRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String TBUSERS_PARAMETER = "tbUsers";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<TbUsers> fetchBagRelationships(Optional<TbUsers> tbUsers) {
        return tbUsers.map(this::fetchRoles);
    }

    @Override
    public Page<TbUsers> fetchBagRelationships(Page<TbUsers> tbUsers) {
        return new PageImpl<>(fetchBagRelationships(tbUsers.getContent()), tbUsers.getPageable(), tbUsers.getTotalElements());
    }

    @Override
    public List<TbUsers> fetchBagRelationships(List<TbUsers> tbUsers) {
        return Optional.of(tbUsers).map(this::fetchRoles).orElse(Collections.emptyList());
    }

    TbUsers fetchRoles(TbUsers result) {
        return entityManager
            .createQuery("select tbUsers from TbUsers tbUsers left join fetch tbUsers.roles where tbUsers.id = :id", TbUsers.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<TbUsers> fetchRoles(List<TbUsers> tbUsers) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, tbUsers.size()).forEach(index -> order.put(tbUsers.get(index).getId(), index));
        List<TbUsers> result = entityManager
            .createQuery("select tbUsers from TbUsers tbUsers left join fetch tbUsers.roles where tbUsers in :tbUsers", TbUsers.class)
            .setParameter(TBUSERS_PARAMETER, tbUsers)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
