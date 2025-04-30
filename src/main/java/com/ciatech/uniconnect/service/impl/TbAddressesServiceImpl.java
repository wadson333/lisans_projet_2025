package com.ciatech.uniconnect.service.impl;

import com.ciatech.uniconnect.domain.TbAddresses;
import com.ciatech.uniconnect.repository.TbAddressesRepository;
import com.ciatech.uniconnect.service.TbAddressesService;
import com.ciatech.uniconnect.service.dto.TbAddressesDTO;
import com.ciatech.uniconnect.service.mapper.TbAddressesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.ciatech.uniconnect.domain.TbAddresses}.
 */
@Service
@Transactional
public class TbAddressesServiceImpl implements TbAddressesService {

    private static final Logger LOG = LoggerFactory.getLogger(TbAddressesServiceImpl.class);

    private final TbAddressesRepository tbAddressesRepository;

    private final TbAddressesMapper tbAddressesMapper;

    public TbAddressesServiceImpl(TbAddressesRepository tbAddressesRepository, TbAddressesMapper tbAddressesMapper) {
        this.tbAddressesRepository = tbAddressesRepository;
        this.tbAddressesMapper = tbAddressesMapper;
    }

    @Override
    public TbAddressesDTO save(TbAddressesDTO tbAddressesDTO) {
        LOG.debug("Request to save TbAddresses : {}", tbAddressesDTO);
        TbAddresses tbAddresses = tbAddressesMapper.toEntity(tbAddressesDTO);
        tbAddresses = tbAddressesRepository.save(tbAddresses);
        return tbAddressesMapper.toDto(tbAddresses);
    }

    @Override
    public TbAddressesDTO update(TbAddressesDTO tbAddressesDTO) {
        LOG.debug("Request to update TbAddresses : {}", tbAddressesDTO);
        TbAddresses tbAddresses = tbAddressesMapper.toEntity(tbAddressesDTO);
        tbAddresses = tbAddressesRepository.save(tbAddresses);
        return tbAddressesMapper.toDto(tbAddresses);
    }

    @Override
    public Optional<TbAddressesDTO> partialUpdate(TbAddressesDTO tbAddressesDTO) {
        LOG.debug("Request to partially update TbAddresses : {}", tbAddressesDTO);

        return tbAddressesRepository
            .findById(tbAddressesDTO.getId())
            .map(existingTbAddresses -> {
                tbAddressesMapper.partialUpdate(existingTbAddresses, tbAddressesDTO);

                return existingTbAddresses;
            })
            .map(tbAddressesRepository::save)
            .map(tbAddressesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TbAddressesDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all TbAddresses");
        return tbAddressesRepository.findAll(pageable).map(tbAddressesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TbAddressesDTO> findOne(Long id) {
        LOG.debug("Request to get TbAddresses : {}", id);
        return tbAddressesRepository.findById(id).map(tbAddressesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete TbAddresses : {}", id);
        tbAddressesRepository.deleteById(id);
    }
}
