package br.com.ibict.visao.service;

import br.com.ibict.visao.domain.GroupCategory;
import br.com.ibict.visao.domain.User;
import br.com.ibict.visao.repository.GroupCategoryRepository;
import br.com.ibict.visao.repository.UserRepository;
import br.com.ibict.visao.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing GroupCategory.
 */
@Service
@Transactional
public class GroupCategoryService {

    private final Logger log = LoggerFactory.getLogger(GroupCategoryService.class);

    private final GroupCategoryRepository groupCategoryRepository;

    private final UserRepository userRepository;

    public GroupCategoryService(GroupCategoryRepository groupCategoryRepository, UserRepository userRepository) {
        this.groupCategoryRepository = groupCategoryRepository;
        this.userRepository = userRepository;
    }

    /**
     * Save a groupCategory.
     *
     * @param groupCategory the entity to save
     * @return the persisted entity
     */
    public GroupCategory save(GroupCategory groupCategory) {
        log.debug("Request to save GroupCategory : {}", groupCategory);        return groupCategoryRepository.save(groupCategory);
    }

    /**
     * Get all the groupCategories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<GroupCategory> findAll(Pageable pageable) {
        log.debug("Request to get all GroupCategories");
        return groupCategoryRepository.findAll(pageable);
    }

    /**
     * Get all the GroupCategory with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<GroupCategory> findAllWithEagerRelationships(Pageable pageable) {
        return groupCategoryRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one groupCategory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<GroupCategory> findOne(Long id) {
        log.debug("Request to get GroupCategory : {}", id);
        return groupCategoryRepository.findOneWithEagerRelationships(id);
    }

    @Transactional(readOnly = true)
    public boolean isGroupCategoryEnabledByCurrentUser(Long id) {
        Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();
        Long codUser = null;
        if(currentUserLogin.isPresent()){
            Optional<User> findedUser = userRepository.findOneByLogin(currentUserLogin.get());
            if(findedUser.isPresent()){
                codUser = findedUser.get().getId();
            }
        }

        Long retrievedGroupCatId = groupCategoryRepository.isGroupCategoryEnabledByCurrentUser(id, codUser);
        return retrievedGroupCatId != null && retrievedGroupCatId.equals(id);
    }

    /**
     * Delete the groupCategory by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete GroupCategory : {}", id);
        groupCategoryRepository.deleteById(id);
    }
}
