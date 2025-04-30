package com.ciatech.uniconnect.service.impl;

import com.ciatech.uniconnect.domain.TbOtp;
import com.ciatech.uniconnect.repository.TbOtpRepository;
import com.ciatech.uniconnect.service.TbOtpService;
import com.ciatech.uniconnect.service.dto.TbOtpDTO;
import com.ciatech.uniconnect.service.mapper.TbOtpMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.ciatech.uniconnect.domain.TbOtp}.
 */
@Service
@Transactional
public class TbOtpServiceImpl implements TbOtpService {

    private static final Logger LOG = LoggerFactory.getLogger(TbOtpServiceImpl.class);

    private final TbOtpRepository tbOtpRepository;

    private final TbOtpMapper tbOtpMapper;

    public TbOtpServiceImpl(TbOtpRepository tbOtpRepository, TbOtpMapper tbOtpMapper) {
        this.tbOtpRepository = tbOtpRepository;
        this.tbOtpMapper = tbOtpMapper;
    }

    @Override
    public TbOtpDTO save(TbOtpDTO tbOtpDTO) {
        LOG.debug("Request to save TbOtp : {}", tbOtpDTO);
        TbOtp tbOtp = tbOtpMapper.toEntity(tbOtpDTO);
        tbOtp = tbOtpRepository.save(tbOtp);
        return tbOtpMapper.toDto(tbOtp);
    }

    @Override
    public TbOtpDTO update(TbOtpDTO tbOtpDTO) {
        LOG.debug("Request to update TbOtp : {}", tbOtpDTO);
        TbOtp tbOtp = tbOtpMapper.toEntity(tbOtpDTO);
        tbOtp = tbOtpRepository.save(tbOtp);
        return tbOtpMapper.toDto(tbOtp);
    }

    @Override
    public Optional<TbOtpDTO> partialUpdate(TbOtpDTO tbOtpDTO) {
        LOG.debug("Request to partially update TbOtp : {}", tbOtpDTO);

        return tbOtpRepository
            .findById(tbOtpDTO.getId())
            .map(existingTbOtp -> {
                tbOtpMapper.partialUpdate(existingTbOtp, tbOtpDTO);

                return existingTbOtp;
            })
            .map(tbOtpRepository::save)
            .map(tbOtpMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TbOtpDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all TbOtps");
        return tbOtpRepository.findAll(pageable).map(tbOtpMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TbOtpDTO> findOne(Long id) {
        LOG.debug("Request to get TbOtp : {}", id);
        return tbOtpRepository.findById(id).map(tbOtpMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete TbOtp : {}", id);
        tbOtpRepository.deleteById(id);
    }
}
