package br.com.ibict.visao.web.rest;

import br.com.ibict.visao.security.SecurityUtils;
import com.codahale.metrics.annotation.Timed;
import br.com.ibict.visao.domain.GroupCategory;
import br.com.ibict.visao.service.GroupCategoryService;
import br.com.ibict.visao.web.rest.errors.BadRequestAlertException;
import br.com.ibict.visao.web.rest.util.HeaderUtil;
import br.com.ibict.visao.web.rest.util.PaginationUtil;
import br.com.ibict.visao.service.dto.GroupCategoryCriteria;
import br.com.ibict.visao.service.GroupCategoryQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing GroupCategory.
 */
@RestController
@RequestMapping("/api")
public class GroupCategoryResource {

    private final Logger log = LoggerFactory.getLogger(GroupCategoryResource.class);

    private static final String ENTITY_NAME = "groupCategory";

    private final GroupCategoryService groupCategoryService;

    private final GroupCategoryQueryService groupCategoryQueryService;

    public GroupCategoryResource(GroupCategoryService groupCategoryService, GroupCategoryQueryService groupCategoryQueryService) {
        this.groupCategoryService = groupCategoryService;
        this.groupCategoryQueryService = groupCategoryQueryService;
    }

    /**
     * POST  /group-categories : Create a new groupCategory.
     *
     * @param groupCategory the groupCategory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new groupCategory, or with status 400 (Bad Request) if the groupCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/group-categories")
    @Timed
    public ResponseEntity<GroupCategory> createGroupCategory(@Valid @RequestBody GroupCategory groupCategory) throws URISyntaxException {
        log.debug("REST request to save GroupCategory : {}", groupCategory);
        if (groupCategory.getId() != null) {
            throw new BadRequestAlertException("A new groupCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GroupCategory result = groupCategoryService.save(groupCategory);
        return ResponseEntity.created(new URI("/api/group-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /group-categories : Updates an existing groupCategory.
     *
     * @param groupCategory the groupCategory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated groupCategory,
     * or with status 400 (Bad Request) if the groupCategory is not valid,
     * or with status 500 (Internal Server Error) if the groupCategory couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/group-categories")
    @Timed
    public ResponseEntity<GroupCategory> updateGroupCategory(@Valid @RequestBody GroupCategory groupCategory) throws URISyntaxException {
        log.debug("REST request to update GroupCategory : {}", groupCategory);
        if (groupCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GroupCategory result = groupCategoryService.save(groupCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, groupCategory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /group-categories : get all the groupCategories.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of groupCategories in body
     */
    @GetMapping("/group-categories")
    @Timed
    public ResponseEntity<List<GroupCategory>> getAllGroupCategories(GroupCategoryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get GroupCategories by criteria: {}", criteria);
        Page<GroupCategory> page = groupCategoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/group-categories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /group-categories : get all the groupCategories enabled for the current user.
     *
     * @param pageable the pagination information
     * @param userLogin the userLogin which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of groupCategories in body
     */
    @GetMapping("/group-categories-by-user/{userLogin}")
    @Timed
    public ResponseEntity<List<GroupCategory>> getGroupCategoriesByUser(@PathVariable String userLogin, Pageable pageable) {
        log.debug("REST request to get GroupCategories by user with login: {}", userLogin);
        Page<GroupCategory> page = groupCategoryService.listGroupsCategoryByCurrentUser(userLogin, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/group-categories-by-user");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /group-categories/:id : get the "id" groupCategory.
     *
     * @param id the id of the groupCategory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the groupCategory, or with status 404 (Not Found)
     */
    @GetMapping("/group-categories/{id}")
    @Timed
    public ResponseEntity<GroupCategory> getGroupCategory(@PathVariable Long id) {
        log.debug("REST request to get GroupCategory : {}", id);
        Optional<GroupCategory> groupCategory = groupCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(groupCategory);
    }

    /**
     * GET  /group-categories/:id : get the "id" groupCategory.
     *
     * @param id the id of the groupCategory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the groupCategory, or with status 404 (Not Found)
     */
    @GetMapping("/group-categories-enabled/{id}")
    @Timed
    public boolean isGroupCategoryEnabledByCurrentUser(@PathVariable Long id) {
        if(id == null){
            return false;
        }
        return groupCategoryService.isGroupCategoryEnabledByCurrentUser(id);
    }

    /**
     * DELETE  /group-categories/:id : delete the "id" groupCategory.
     *
     * @param id the id of the groupCategory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/group-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteGroupCategory(@PathVariable Long id) {
        log.debug("REST request to delete GroupCategory : {}", id);
        groupCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
