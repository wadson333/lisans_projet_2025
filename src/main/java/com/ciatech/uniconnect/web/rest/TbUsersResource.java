package com.ciatech.uniconnect.web.rest;

import com.ciatech.uniconnect.repository.TbUsersRepository;
import com.ciatech.uniconnect.service.TbUsersService;
import com.ciatech.uniconnect.service.dto.TbUsersDTO;
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
 * REST controller for managing {@link com.ciatech.uniconnect.domain.TbUsers}.
 */
@RestController
@RequestMapping("/api/tb-users")
public class TbUsersResource {

    private static final Logger LOG = LoggerFactory.getLogger(TbUsersResource.class);

    private static final String ENTITY_NAME = "tbUsers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TbUsersService tbUsersService;

    private final TbUsersRepository tbUsersRepository;

    public TbUsersResource(TbUsersService tbUsersService, TbUsersRepository tbUsersRepository) {
        this.tbUsersService = tbUsersService;
        this.tbUsersRepository = tbUsersRepository;
    }

    /**
     * {@code POST  /tb-users} : Create a new tbUsers.
     *
     * @param tbUsersDTO the tbUsersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tbUsersDTO, or with status {@code 400 (Bad Request)} if the tbUsers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TbUsersDTO> createTbUsers(@Valid @RequestBody TbUsersDTO tbUsersDTO) throws URISyntaxException {
        LOG.debug("REST request to save TbUsers : {}", tbUsersDTO);
        if (tbUsersDTO.getId() != null) {
            throw new BadRequestAlertException("A new tbUsers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tbUsersDTO = tbUsersService.save(tbUsersDTO);
        return ResponseEntity.created(new URI("/api/tb-users/" + tbUsersDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, tbUsersDTO.getId().toString()))
            .body(tbUsersDTO);
    }

    /**
     * {@code PUT  /tb-users/:id} : Updates an existing tbUsers.
     *
     * @param id the id of the tbUsersDTO to save.
     * @param tbUsersDTO the tbUsersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tbUsersDTO,
     * or with status {@code 400 (Bad Request)} if the tbUsersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tbUsersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TbUsersDTO> updateTbUsers(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TbUsersDTO tbUsersDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update TbUsers : {}, {}", id, tbUsersDTO);
        if (tbUsersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tbUsersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tbUsersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tbUsersDTO = tbUsersService.update(tbUsersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tbUsersDTO.getId().toString()))
            .body(tbUsersDTO);
    }

    /**
     * {@code PATCH  /tb-users/:id} : Partial updates given fields of an existing tbUsers, field will ignore if it is null
     *
     * @param id the id of the tbUsersDTO to save.
     * @param tbUsersDTO the tbUsersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tbUsersDTO,
     * or with status {@code 400 (Bad Request)} if the tbUsersDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tbUsersDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tbUsersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TbUsersDTO> partialUpdateTbUsers(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TbUsersDTO tbUsersDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update TbUsers partially : {}, {}", id, tbUsersDTO);
        if (tbUsersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tbUsersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tbUsersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TbUsersDTO> result = tbUsersService.partialUpdate(tbUsersDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tbUsersDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tb-users} : get all the tbUsers.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tbUsers in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TbUsersDTO>> getAllTbUsers(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of TbUsers");
        Page<TbUsersDTO> page;
        if (eagerload) {
            page = tbUsersService.findAllWithEagerRelationships(pageable);
        } else {
            page = tbUsersService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tb-users/:id} : get the "id" tbUsers.
     *
     * @param id the id of the tbUsersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tbUsersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TbUsersDTO> getTbUsers(@PathVariable("id") Long id) {
        LOG.debug("REST request to get TbUsers : {}", id);
        Optional<TbUsersDTO> tbUsersDTO = tbUsersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tbUsersDTO);
    }

    /**
     * {@code DELETE  /tb-users/:id} : delete the "id" tbUsers.
     *
     * @param id the id of the tbUsersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTbUsers(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete TbUsers : {}", id);
        tbUsersService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
