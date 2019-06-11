package br.com.ibict.visao.service.dto;

import java.io.Serializable;
import br.com.ibict.visao.domain.enumeration.TypePermission;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the GroupCategory entity. This class is used in GroupCategoryResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /group-categories?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class GroupCategoryCriteria implements Serializable {
    /**
     * Class for filtering TypePermission
     */
    public static class TypePermissionFilter extends Filter<TypePermission> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter about;

    private TypePermissionFilter permission;

    private StringFilter name;

    private LongFilter ownerId;

    private LongFilter categoriesId;

    private LongFilter sharedsId;

    public GroupCategoryCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getAbout() {
        return about;
    }

    public void setAbout(StringFilter about) {
        this.about = about;
    }

    public TypePermissionFilter getPermission() {
        return permission;
    }

    public void setPermission(TypePermissionFilter permission) {
        this.permission = permission;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public LongFilter getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(LongFilter ownerId) {
        this.ownerId = ownerId;
    }

    public LongFilter getCategoriesId() {
        return categoriesId;
    }

    public void setCategoriesId(LongFilter categoriesId) {
        this.categoriesId = categoriesId;
    }

    public LongFilter getSharedsId() {
        return sharedsId;
    }

    public void setSharedsId(LongFilter sharedsId) {
        this.sharedsId = sharedsId;
    }

    @Override
    public String toString() {
        return "GroupCategoryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (about != null ? "about=" + about + ", " : "") +
                (permission != null ? "permission=" + permission + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (ownerId != null ? "ownerId=" + ownerId + ", " : "") +
                (categoriesId != null ? "categoriesId=" + categoriesId + ", " : "") +
                (sharedsId != null ? "sharedsId=" + sharedsId + ", " : "") +
            "}";
    }

}
