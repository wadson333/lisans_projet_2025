package com.ciatech.uniconnect.web.rest;

import com.ciatech.uniconnect.repository.TbPermissionsRepository;
import com.ciatech.uniconnect.service.TbPermissionsService;
import com.ciatech.uniconnect.service.dto.TbPermissionsDTO;
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
 * REST controller for managing {@link com.ciatech.uniconnect.domain.TbPermissions}.
 */
@RestController
@RequestMapping("/api/tb-permissions")
public class TbPermissionsResource {

    private static final Logger LOG = LoggerFactory.getLogger(TbPermissionsResource.class);

    private static final String ENTITY_NAME = "tbPermissions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TbPermissionsService tbPermissionsService;

    private final TbPermissionsRepository tbPermissionsRepository;

    public TbPermissionsResource(TbPermissionsService tbPermissionsService, TbPermissionsRepository tbPermissionsRepository) {
        this.tbPermissionsService = tbPermissionsService;
        this.tbPermissionsRepository = tbPermissionsRepository;
    }

    /**
     * {@code POST  /tb-permissions} : Create a new tbPermissions.
     *
     * @param tbPermissionsDTO the tbPermissionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tbPermissionsDTO, or with status {@code 400 (Bad Request)} if the tbPermissions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TbPermissionsDTO> createTbPermissions(@Valid @RequestBody TbPermissionsDTO tbPermissionsDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save TbPermissions : {}", tbPermissionsDTO);
        if (tbPermissionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new tbPermissions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tbPermissionsDTO = tbPermissionsService.save(tbPermissionsDTO);
        return ResponseEntity.created(new URI("/api/tb-permissions/" + tbPermissionsDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, tbPermissionsDTO.getId().toString()))
            .body(tbPermissionsDTO);
    }

    /**
     * {@code PUT  /tb-permissions/:id} : Updates an existing tbPermissions.
     *
     * @param id the id of the tbPermissionsDTO to save.
     * @param tbPermissionsDTO the tbPermissionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tbPermissionsDTO,
     * or with status {@code 400 (Bad Request)} if the tbPermissionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tbPermissionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TbPermissionsDTO> updateTbPermissions(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TbPermissionsDTO tbPermissionsDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update TbPermissions : {}, {}", id, tbPermissionsDTO);
        if (tbPermissionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tbPermissionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tbPermissionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tbPermissionsDTO = tbPermissionsService.update(tbPermissionsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tbPermissionsDTO.getId().toString()))
            .body(tbPermissionsDTO);
    }

    /**
     * {@code PATCH  /tb-permissions/:id} : Partial updates given fields of an existing tbPermissions, field will ignore if it is null
     *
     * @param id the id of the tbPermissionsDTO to save.
     * @param tbPermissionsDTO the tbPermissionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tbPermissionsDTO,
     * or with status {@code 400 (Bad Request)} if the tbPermissionsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tbPermissionsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tbPermissionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TbPermissionsDTO> partialUpdateTbPermissions(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TbPermissionsDTO tbPermissionsDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update TbPermissions partially : {}, {}", id, tbPermissionsDTO);
        if (tbPermissionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tbPermissionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tbPermissionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TbPermissionsDTO> result = tbPermissionsService.partialUpdate(tbPermissionsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tbPermissionsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tb-permissions} : get all the tbPermissions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tbPermissions in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TbPermissionsDTO>> getAllTbPermissions(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of TbPermissions");
        Page<TbPermissionsDTO> page = tbPermissionsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tb-permissions/:id} : get the "id" tbPermissions.
     *
     * @param id the id of the tbPermissionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tbPermissionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TbPermissionsDTO> getTbPermissions(@PathVariable("id") Long id) {
        LOG.debug("REST request to get TbPermissions : {}", id);
        Optional<TbPermissionsDTO> tbPermissionsDTO = tbPermissionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tbPermissionsDTO);
    }

    /**
     * {@code DELETE  /tb-permissions/:id} : delete the "id" tbPermissions.
     *
     * @param id the id of the tbPermissionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTbPermissions(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete TbPermissions : {}", id);
        tbPermissionsService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
