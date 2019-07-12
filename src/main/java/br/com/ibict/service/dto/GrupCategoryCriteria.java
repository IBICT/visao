package br.com.ibict.service.dto;

import java.io.Serializable;
import br.com.ibict.domain.enumeration.Permission;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the GrupCategory entity. This class is used in GrupCategoryResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /grup-categories?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class GrupCategoryCriteria implements Serializable {
    /**
     * Class for filtering Permission
     */
    public static class PermissionFilter extends Filter<Permission> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private PermissionFilter permission;

    private LongFilter ownerId;

    private LongFilter categoryId;

    private LongFilter sharedId;

    public GrupCategoryCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public PermissionFilter getPermission() {
        return permission;
    }

    public void setPermission(PermissionFilter permission) {
        this.permission = permission;
    }

    public LongFilter getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(LongFilter ownerId) {
        this.ownerId = ownerId;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
    }

    public LongFilter getSharedId() {
        return sharedId;
    }

    public void setSharedId(LongFilter sharedId) {
        this.sharedId = sharedId;
    }

    @Override
    public String toString() {
        return "GrupCategoryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (permission != null ? "permission=" + permission + ", " : "") +
                (ownerId != null ? "ownerId=" + ownerId + ", " : "") +
                (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
                (sharedId != null ? "sharedId=" + sharedId + ", " : "") +
            "}";
    }

}
