package com.ciatech.uniconnect.web.rest;

import com.ciatech.uniconnect.repository.TbRolesRepository;
import com.ciatech.uniconnect.service.TbRolesService;
import com.ciatech.uniconnect.service.dto.TbRolesDTO;
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
 * REST controller for managing {@link com.ciatech.uniconnect.domain.TbRoles}.
 */
@RestController
@RequestMapping("/api/tb-roles")
public class TbRolesResource {

    private static final Logger LOG = LoggerFactory.getLogger(TbRolesResource.class);

    private static final String ENTITY_NAME = "tbRoles";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TbRolesService tbRolesService;

    private final TbRolesRepository tbRolesRepository;

    public TbRolesResource(TbRolesService tbRolesService, TbRolesRepository tbRolesRepository) {
        this.tbRolesService = tbRolesService;
        this.tbRolesRepository = tbRolesRepository;
    }

    /**
     * {@code POST  /tb-roles} : Create a new tbRoles.
     *
     * @param tbRolesDTO the tbRolesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tbRolesDTO, or with status {@code 400 (Bad Request)} if the tbRoles has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TbRolesDTO> createTbRoles(@Valid @RequestBody TbRolesDTO tbRolesDTO) throws URISyntaxException {
        LOG.debug("REST request to save TbRoles : {}", tbRolesDTO);
        if (tbRolesDTO.getId() != null) {
            throw new BadRequestAlertException("A new tbRoles cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tbRolesDTO = tbRolesService.save(tbRolesDTO);
        return ResponseEntity.created(new URI("/api/tb-roles/" + tbRolesDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, tbRolesDTO.getId().toString()))
            .body(tbRolesDTO);
    }

    /**
     * {@code PUT  /tb-roles/:id} : Updates an existing tbRoles.
     *
     * @param id the id of the tbRolesDTO to save.
     * @param tbRolesDTO the tbRolesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tbRolesDTO,
     * or with status {@code 400 (Bad Request)} if the tbRolesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tbRolesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TbRolesDTO> updateTbRoles(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TbRolesDTO tbRolesDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update TbRoles : {}, {}", id, tbRolesDTO);
        if (tbRolesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tbRolesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tbRolesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tbRolesDTO = tbRolesService.update(tbRolesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tbRolesDTO.getId().toString()))
            .body(tbRolesDTO);
    }

    /**
     * {@code PATCH  /tb-roles/:id} : Partial updates given fields of an existing tbRoles, field will ignore if it is null
     *
     * @param id the id of the tbRolesDTO to save.
     * @param tbRolesDTO the tbRolesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tbRolesDTO,
     * or with status {@code 400 (Bad Request)} if the tbRolesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tbRolesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tbRolesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TbRolesDTO> partialUpdateTbRoles(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TbRolesDTO tbRolesDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update TbRoles partially : {}, {}", id, tbRolesDTO);
        if (tbRolesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tbRolesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tbRolesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TbRolesDTO> result = tbRolesService.partialUpdate(tbRolesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tbRolesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tb-roles} : get all the tbRoles.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tbRoles in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TbRolesDTO>> getAllTbRoles(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of TbRoles");
        Page<TbRolesDTO> page;
        if (eagerload) {
            page = tbRolesService.findAllWithEagerRelationships(pageable);
        } else {
            page = tbRolesService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tb-roles/:id} : get the "id" tbRoles.
     *
     * @param id the id of the tbRolesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tbRolesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TbRolesDTO> getTbRoles(@PathVariable("id") Long id) {
        LOG.debug("REST request to get TbRoles : {}", id);
        Optional<TbRolesDTO> tbRolesDTO = tbRolesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tbRolesDTO);
    }

    /**
     * {@code DELETE  /tb-roles/:id} : delete the "id" tbRoles.
     *
     * @param id the id of the tbRolesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTbRoles(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete TbRoles : {}", id);
        tbRolesService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
