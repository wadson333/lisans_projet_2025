package com.ciatech.uniconnect.service.impl;

import com.ciatech.uniconnect.domain.TbUsers;
import com.ciatech.uniconnect.repository.TbUsersRepository;
import com.ciatech.uniconnect.service.TbUsersService;
import com.ciatech.uniconnect.service.dto.TbUsersDTO;
import com.ciatech.uniconnect.service.mapper.TbUsersMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.ciatech.uniconnect.domain.TbUsers}.
 */
@Service
@Transactional
public class TbUsersServiceImpl implements TbUsersService {

    private static final Logger LOG = LoggerFactory.getLogger(TbUsersServiceImpl.class);

    private final TbUsersRepository tbUsersRepository;

    private final TbUsersMapper tbUsersMapper;

    public TbUsersServiceImpl(TbUsersRepository tbUsersRepository, TbUsersMapper tbUsersMapper) {
        this.tbUsersRepository = tbUsersRepository;
        this.tbUsersMapper = tbUsersMapper;
    }

    @Override
    public TbUsersDTO save(TbUsersDTO tbUsersDTO) {
        LOG.debug("Request to save TbUsers : {}", tbUsersDTO);
        TbUsers tbUsers = tbUsersMapper.toEntity(tbUsersDTO);
        tbUsers = tbUsersRepository.save(tbUsers);
        return tbUsersMapper.toDto(tbUsers);
    }

    @Override
    public TbUsersDTO update(TbUsersDTO tbUsersDTO) {
        LOG.debug("Request to update TbUsers : {}", tbUsersDTO);
        TbUsers tbUsers = tbUsersMapper.toEntity(tbUsersDTO);
        tbUsers = tbUsersRepository.save(tbUsers);
        return tbUsersMapper.toDto(tbUsers);
    }

    @Override
    public Optional<TbUsersDTO> partialUpdate(TbUsersDTO tbUsersDTO) {
        LOG.debug("Request to partially update TbUsers : {}", tbUsersDTO);

        return tbUsersRepository
            .findById(tbUsersDTO.getId())
            .map(existingTbUsers -> {
                tbUsersMapper.partialUpdate(existingTbUsers, tbUsersDTO);

                return existingTbUsers;
            })
            .map(tbUsersRepository::save)
            .map(tbUsersMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TbUsersDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all TbUsers");
        return tbUsersRepository.findAll(pageable).map(tbUsersMapper::toDto);
    }

    public Page<TbUsersDTO> findAllWithEagerRelationships(Pageable pageable) {
        return tbUsersRepository.findAllWithEagerRelationships(pageable).map(tbUsersMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TbUsersDTO> findOne(Long id) {
        LOG.debug("Request to get TbUsers : {}", id);
        return tbUsersRepository.findOneWithEagerRelationships(id).map(tbUsersMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete TbUsers : {}", id);
        tbUsersRepository.deleteById(id);
    }
}
