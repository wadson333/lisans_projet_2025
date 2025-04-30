package com.ciatech.uniconnect.service.impl;

import com.ciatech.uniconnect.domain.TbRoles;
import com.ciatech.uniconnect.repository.TbRolesRepository;
import com.ciatech.uniconnect.service.TbRolesService;
import com.ciatech.uniconnect.service.dto.TbRolesDTO;
import com.ciatech.uniconnect.service.mapper.TbRolesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.ciatech.uniconnect.domain.TbRoles}.
 */
@Service
@Transactional
public class TbRolesServiceImpl implements TbRolesService {

    private static final Logger LOG = LoggerFactory.getLogger(TbRolesServiceImpl.class);

    private final TbRolesRepository tbRolesRepository;

    private final TbRolesMapper tbRolesMapper;

    public TbRolesServiceImpl(TbRolesRepository tbRolesRepository, TbRolesMapper tbRolesMapper) {
        this.tbRolesRepository = tbRolesRepository;
        this.tbRolesMapper = tbRolesMapper;
    }

    @Override
    public TbRolesDTO save(TbRolesDTO tbRolesDTO) {
        LOG.debug("Request to save TbRoles : {}", tbRolesDTO);
        TbRoles tbRoles = tbRolesMapper.toEntity(tbRolesDTO);
        tbRoles = tbRolesRepository.save(tbRoles);
        return tbRolesMapper.toDto(tbRoles);
    }

    @Override
    public TbRolesDTO update(TbRolesDTO tbRolesDTO) {
        LOG.debug("Request to update TbRoles : {}", tbRolesDTO);
        TbRoles tbRoles = tbRolesMapper.toEntity(tbRolesDTO);
        tbRoles = tbRolesRepository.save(tbRoles);
        return tbRolesMapper.toDto(tbRoles);
    }

    @Override
    public Optional<TbRolesDTO> partialUpdate(TbRolesDTO tbRolesDTO) {
        LOG.debug("Request to partially update TbRoles : {}", tbRolesDTO);

        return tbRolesRepository
            .findById(tbRolesDTO.getId())
            .map(existingTbRoles -> {
                tbRolesMapper.partialUpdate(existingTbRoles, tbRolesDTO);

                return existingTbRoles;
            })
            .map(tbRolesRepository::save)
            .map(tbRolesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TbRolesDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all TbRoles");
        return tbRolesRepository.findAll(pageable).map(tbRolesMapper::toDto);
    }

    public Page<TbRolesDTO> findAllWithEagerRelationships(Pageable pageable) {
        return tbRolesRepository.findAllWithEagerRelationships(pageable).map(tbRolesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TbRolesDTO> findOne(Long id) {
        LOG.debug("Request to get TbRoles : {}", id);
        return tbRolesRepository.findOneWithEagerRelationships(id).map(tbRolesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete TbRoles : {}", id);
        tbRolesRepository.deleteById(id);
    }
}
