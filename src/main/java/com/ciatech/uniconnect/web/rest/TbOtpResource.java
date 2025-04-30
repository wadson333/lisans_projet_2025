package com.ciatech.uniconnect.web.rest;

import com.ciatech.uniconnect.repository.TbOtpRepository;
import com.ciatech.uniconnect.service.TbOtpService;
import com.ciatech.uniconnect.service.dto.TbOtpDTO;
import com.ciatech.uniconnect.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.ciatech.uniconnect.domain.TbOtp}.
 */
@RestController
@RequestMapping("/api/tb-otps")
public class TbOtpResource {

    private static final Logger LOG = LoggerFactory.getLogger(TbOtpResource.class);

    private static final String ENTITY_NAME = "tbOtp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TbOtpService tbOtpService;

    private final TbOtpRepository tbOtpRepository;

    public TbOtpResource(TbOtpService tbOtpService, TbOtpRepository tbOtpRepository) {
        this.tbOtpService = tbOtpService;
        this.tbOtpRepository = tbOtpRepository;
    }

    /**
     * {@code POST  /tb-otps} : Create a new tbOtp.
     *
     * @param tbOtpDTO the tbOtpDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tbOtpDTO, or with status {@code 400 (Bad Request)} if the tbOtp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TbOtpDTO> createTbOtp(@Valid @RequestBody TbOtpDTO tbOtpDTO) throws URISyntaxException {
        LOG.debug("REST request to save TbOtp : {}", tbOtpDTO);
        if (tbOtpDTO.getId() != null) {
            throw new BadRequestAlertException("A new tbOtp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tbOtpDTO = tbOtpService.save(tbOtpDTO);
        return ResponseEntity.created(new URI("/api/tb-otps/" + tbOtpDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, tbOtpDTO.getId().toString()))
            .body(tbOtpDTO);
    }

    /**
     * {@code PUT  /tb-otps/:id} : Updates an existing tbOtp.
     *
     * @param id the id of the tbOtpDTO to save.
     * @param tbOtpDTO the tbOtpDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tbOtpDTO,
     * or with status {@code 400 (Bad Request)} if the tbOtpDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tbOtpDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TbOtpDTO> updateTbOtp(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TbOtpDTO tbOtpDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update TbOtp : {}, {}", id, tbOtpDTO);
        if (tbOtpDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tbOtpDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tbOtpRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tbOtpDTO = tbOtpService.update(tbOtpDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tbOtpDTO.getId().toString()))
            .body(tbOtpDTO);
    }

    /**
     * {@code PATCH  /tb-otps/:id} : Partial updates given fields of an existing tbOtp, field will ignore if it is null
     *
     * @param id the id of the tbOtpDTO to save.
     * @param tbOtpDTO the tbOtpDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tbOtpDTO,
     * or with status {@code 400 (Bad Request)} if the tbOtpDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tbOtpDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tbOtpDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TbOtpDTO> partialUpdateTbOtp(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TbOtpDTO tbOtpDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update TbOtp partially : {}, {}", id, tbOtpDTO);
        if (tbOtpDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tbOtpDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tbOtpRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TbOtpDTO> result = tbOtpService.partialUpdate(tbOtpDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tbOtpDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tb-otps} : get all the tbOtps.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tbOtps in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TbOtpDTO>> getAllTbOtps(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of TbOtps");
        Page<TbOtpDTO> page = tbOtpService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tb-otps/:id} : get the "id" tbOtp.
     *
     * @param id the id of the tbOtpDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tbOtpDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TbOtpDTO> getTbOtp(@PathVariable("id") Long id) {
        LOG.debug("REST request to get TbOtp : {}", id);
        Optional<TbOtpDTO> tbOtpDTO = tbOtpService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tbOtpDTO);
    }

    /**
     * {@code DELETE  /tb-otps/:id} : delete the "id" tbOtp.
     *
     * @param id the id of the tbOtpDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTbOtp(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete TbOtp : {}", id);
        tbOtpService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
