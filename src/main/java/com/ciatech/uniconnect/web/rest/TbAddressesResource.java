package com.ciatech.uniconnect.web.rest;

import com.ciatech.uniconnect.repository.TbAddressesRepository;
import com.ciatech.uniconnect.service.TbAddressesService;
import com.ciatech.uniconnect.service.dto.TbAddressesDTO;
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
 * REST controller for managing {@link com.ciatech.uniconnect.domain.TbAddresses}.
 */
@RestController
@RequestMapping("/api/tb-addresses")
public class TbAddressesResource {

    private static final Logger LOG = LoggerFactory.getLogger(TbAddressesResource.class);

    private static final String ENTITY_NAME = "tbAddresses";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TbAddressesService tbAddressesService;

    private final TbAddressesRepository tbAddressesRepository;

    public TbAddressesResource(TbAddressesService tbAddressesService, TbAddressesRepository tbAddressesRepository) {
        this.tbAddressesService = tbAddressesService;
        this.tbAddressesRepository = tbAddressesRepository;
    }

    /**
     * {@code POST  /tb-addresses} : Create a new tbAddresses.
     *
     * @param tbAddressesDTO the tbAddressesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tbAddressesDTO, or with status {@code 400 (Bad Request)} if the tbAddresses has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TbAddressesDTO> createTbAddresses(@Valid @RequestBody TbAddressesDTO tbAddressesDTO) throws URISyntaxException {
        LOG.debug("REST request to save TbAddresses : {}", tbAddressesDTO);
        if (tbAddressesDTO.getId() != null) {
            throw new BadRequestAlertException("A new tbAddresses cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tbAddressesDTO = tbAddressesService.save(tbAddressesDTO);
        return ResponseEntity.created(new URI("/api/tb-addresses/" + tbAddressesDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, tbAddressesDTO.getId().toString()))
            .body(tbAddressesDTO);
    }

    /**
     * {@code PUT  /tb-addresses/:id} : Updates an existing tbAddresses.
     *
     * @param id the id of the tbAddressesDTO to save.
     * @param tbAddressesDTO the tbAddressesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tbAddressesDTO,
     * or with status {@code 400 (Bad Request)} if the tbAddressesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tbAddressesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TbAddressesDTO> updateTbAddresses(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TbAddressesDTO tbAddressesDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update TbAddresses : {}, {}", id, tbAddressesDTO);
        if (tbAddressesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tbAddressesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tbAddressesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tbAddressesDTO = tbAddressesService.update(tbAddressesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tbAddressesDTO.getId().toString()))
            .body(tbAddressesDTO);
    }

    /**
     * {@code PATCH  /tb-addresses/:id} : Partial updates given fields of an existing tbAddresses, field will ignore if it is null
     *
     * @param id the id of the tbAddressesDTO to save.
     * @param tbAddressesDTO the tbAddressesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tbAddressesDTO,
     * or with status {@code 400 (Bad Request)} if the tbAddressesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tbAddressesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tbAddressesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TbAddressesDTO> partialUpdateTbAddresses(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TbAddressesDTO tbAddressesDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update TbAddresses partially : {}, {}", id, tbAddressesDTO);
        if (tbAddressesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tbAddressesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tbAddressesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TbAddressesDTO> result = tbAddressesService.partialUpdate(tbAddressesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tbAddressesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tb-addresses} : get all the tbAddresses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tbAddresses in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TbAddressesDTO>> getAllTbAddresses(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of TbAddresses");
        Page<TbAddressesDTO> page = tbAddressesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tb-addresses/:id} : get the "id" tbAddresses.
     *
     * @param id the id of the tbAddressesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tbAddressesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TbAddressesDTO> getTbAddresses(@PathVariable("id") Long id) {
        LOG.debug("REST request to get TbAddresses : {}", id);
        Optional<TbAddressesDTO> tbAddressesDTO = tbAddressesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tbAddressesDTO);
    }

    /**
     * {@code DELETE  /tb-addresses/:id} : delete the "id" tbAddresses.
     *
     * @param id the id of the tbAddressesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTbAddresses(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete TbAddresses : {}", id);
        tbAddressesService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
