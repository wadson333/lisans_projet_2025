package com.ciatech.uniconnect.web.rest;

import com.ciatech.uniconnect.repository.TbLoginAttemptsRepository;
import com.ciatech.uniconnect.service.TbLoginAttemptsService;
import com.ciatech.uniconnect.service.dto.TbLoginAttemptsDTO;
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
 * REST controller for managing {@link com.ciatech.uniconnect.domain.TbLoginAttempts}.
 */
@RestController
@RequestMapping("/api/tb-login-attempts")
public class TbLoginAttemptsResource {

    private static final Logger LOG = LoggerFactory.getLogger(TbLoginAttemptsResource.class);

    private static final String ENTITY_NAME = "tbLoginAttempts";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TbLoginAttemptsService tbLoginAttemptsService;

    private final TbLoginAttemptsRepository tbLoginAttemptsRepository;

    public TbLoginAttemptsResource(TbLoginAttemptsService tbLoginAttemptsService, TbLoginAttemptsRepository tbLoginAttemptsRepository) {
        this.tbLoginAttemptsService = tbLoginAttemptsService;
        this.tbLoginAttemptsRepository = tbLoginAttemptsRepository;
    }

    /**
     * {@code POST  /tb-login-attempts} : Create a new tbLoginAttempts.
     *
     * @param tbLoginAttemptsDTO the tbLoginAttemptsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tbLoginAttemptsDTO, or with status {@code 400 (Bad Request)} if the tbLoginAttempts has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TbLoginAttemptsDTO> createTbLoginAttempts(@Valid @RequestBody TbLoginAttemptsDTO tbLoginAttemptsDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save TbLoginAttempts : {}", tbLoginAttemptsDTO);
        if (tbLoginAttemptsDTO.getId() != null) {
            throw new BadRequestAlertException("A new tbLoginAttempts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tbLoginAttemptsDTO = tbLoginAttemptsService.save(tbLoginAttemptsDTO);
        return ResponseEntity.created(new URI("/api/tb-login-attempts/" + tbLoginAttemptsDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, tbLoginAttemptsDTO.getId().toString()))
            .body(tbLoginAttemptsDTO);
    }

    /**
     * {@code PUT  /tb-login-attempts/:id} : Updates an existing tbLoginAttempts.
     *
     * @param id the id of the tbLoginAttemptsDTO to save.
     * @param tbLoginAttemptsDTO the tbLoginAttemptsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tbLoginAttemptsDTO,
     * or with status {@code 400 (Bad Request)} if the tbLoginAttemptsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tbLoginAttemptsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TbLoginAttemptsDTO> updateTbLoginAttempts(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TbLoginAttemptsDTO tbLoginAttemptsDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update TbLoginAttempts : {}, {}", id, tbLoginAttemptsDTO);
        if (tbLoginAttemptsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tbLoginAttemptsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tbLoginAttemptsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tbLoginAttemptsDTO = tbLoginAttemptsService.update(tbLoginAttemptsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tbLoginAttemptsDTO.getId().toString()))
            .body(tbLoginAttemptsDTO);
    }

    /**
     * {@code PATCH  /tb-login-attempts/:id} : Partial updates given fields of an existing tbLoginAttempts, field will ignore if it is null
     *
     * @param id the id of the tbLoginAttemptsDTO to save.
     * @param tbLoginAttemptsDTO the tbLoginAttemptsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tbLoginAttemptsDTO,
     * or with status {@code 400 (Bad Request)} if the tbLoginAttemptsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tbLoginAttemptsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tbLoginAttemptsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TbLoginAttemptsDTO> partialUpdateTbLoginAttempts(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TbLoginAttemptsDTO tbLoginAttemptsDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update TbLoginAttempts partially : {}, {}", id, tbLoginAttemptsDTO);
        if (tbLoginAttemptsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tbLoginAttemptsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tbLoginAttemptsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TbLoginAttemptsDTO> result = tbLoginAttemptsService.partialUpdate(tbLoginAttemptsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tbLoginAttemptsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tb-login-attempts} : get all the tbLoginAttempts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tbLoginAttempts in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TbLoginAttemptsDTO>> getAllTbLoginAttempts(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of TbLoginAttempts");
        Page<TbLoginAttemptsDTO> page = tbLoginAttemptsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tb-login-attempts/:id} : get the "id" tbLoginAttempts.
     *
     * @param id the id of the tbLoginAttemptsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tbLoginAttemptsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TbLoginAttemptsDTO> getTbLoginAttempts(@PathVariable("id") Long id) {
        LOG.debug("REST request to get TbLoginAttempts : {}", id);
        Optional<TbLoginAttemptsDTO> tbLoginAttemptsDTO = tbLoginAttemptsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tbLoginAttemptsDTO);
    }

    /**
     * {@code DELETE  /tb-login-attempts/:id} : delete the "id" tbLoginAttempts.
     *
     * @param id the id of the tbLoginAttemptsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTbLoginAttempts(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete TbLoginAttempts : {}", id);
        tbLoginAttemptsService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
