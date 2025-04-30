package com.ciatech.uniconnect.web.rest;

import com.ciatech.uniconnect.repository.TbPasswordHistoryRepository;
import com.ciatech.uniconnect.service.TbPasswordHistoryService;
import com.ciatech.uniconnect.service.dto.TbPasswordHistoryDTO;
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
 * REST controller for managing {@link com.ciatech.uniconnect.domain.TbPasswordHistory}.
 */
@RestController
@RequestMapping("/api/tb-password-histories")
public class TbPasswordHistoryResource {

    private static final Logger LOG = LoggerFactory.getLogger(TbPasswordHistoryResource.class);

    private static final String ENTITY_NAME = "tbPasswordHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TbPasswordHistoryService tbPasswordHistoryService;

    private final TbPasswordHistoryRepository tbPasswordHistoryRepository;

    public TbPasswordHistoryResource(
        TbPasswordHistoryService tbPasswordHistoryService,
        TbPasswordHistoryRepository tbPasswordHistoryRepository
    ) {
        this.tbPasswordHistoryService = tbPasswordHistoryService;
        this.tbPasswordHistoryRepository = tbPasswordHistoryRepository;
    }

    /**
     * {@code POST  /tb-password-histories} : Create a new tbPasswordHistory.
     *
     * @param tbPasswordHistoryDTO the tbPasswordHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tbPasswordHistoryDTO, or with status {@code 400 (Bad Request)} if the tbPasswordHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TbPasswordHistoryDTO> createTbPasswordHistory(@Valid @RequestBody TbPasswordHistoryDTO tbPasswordHistoryDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save TbPasswordHistory : {}", tbPasswordHistoryDTO);
        if (tbPasswordHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new tbPasswordHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tbPasswordHistoryDTO = tbPasswordHistoryService.save(tbPasswordHistoryDTO);
        return ResponseEntity.created(new URI("/api/tb-password-histories/" + tbPasswordHistoryDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, tbPasswordHistoryDTO.getId().toString()))
            .body(tbPasswordHistoryDTO);
    }

    /**
     * {@code PUT  /tb-password-histories/:id} : Updates an existing tbPasswordHistory.
     *
     * @param id the id of the tbPasswordHistoryDTO to save.
     * @param tbPasswordHistoryDTO the tbPasswordHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tbPasswordHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the tbPasswordHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tbPasswordHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TbPasswordHistoryDTO> updateTbPasswordHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TbPasswordHistoryDTO tbPasswordHistoryDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update TbPasswordHistory : {}, {}", id, tbPasswordHistoryDTO);
        if (tbPasswordHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tbPasswordHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tbPasswordHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tbPasswordHistoryDTO = tbPasswordHistoryService.update(tbPasswordHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tbPasswordHistoryDTO.getId().toString()))
            .body(tbPasswordHistoryDTO);
    }

    /**
     * {@code PATCH  /tb-password-histories/:id} : Partial updates given fields of an existing tbPasswordHistory, field will ignore if it is null
     *
     * @param id the id of the tbPasswordHistoryDTO to save.
     * @param tbPasswordHistoryDTO the tbPasswordHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tbPasswordHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the tbPasswordHistoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tbPasswordHistoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tbPasswordHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TbPasswordHistoryDTO> partialUpdateTbPasswordHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TbPasswordHistoryDTO tbPasswordHistoryDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update TbPasswordHistory partially : {}, {}", id, tbPasswordHistoryDTO);
        if (tbPasswordHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tbPasswordHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tbPasswordHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TbPasswordHistoryDTO> result = tbPasswordHistoryService.partialUpdate(tbPasswordHistoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tbPasswordHistoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tb-password-histories} : get all the tbPasswordHistories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tbPasswordHistories in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TbPasswordHistoryDTO>> getAllTbPasswordHistories(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of TbPasswordHistories");
        Page<TbPasswordHistoryDTO> page = tbPasswordHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tb-password-histories/:id} : get the "id" tbPasswordHistory.
     *
     * @param id the id of the tbPasswordHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tbPasswordHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TbPasswordHistoryDTO> getTbPasswordHistory(@PathVariable("id") Long id) {
        LOG.debug("REST request to get TbPasswordHistory : {}", id);
        Optional<TbPasswordHistoryDTO> tbPasswordHistoryDTO = tbPasswordHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tbPasswordHistoryDTO);
    }

    /**
     * {@code DELETE  /tb-password-histories/:id} : delete the "id" tbPasswordHistory.
     *
     * @param id the id of the tbPasswordHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTbPasswordHistory(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete TbPasswordHistory : {}", id);
        tbPasswordHistoryService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
