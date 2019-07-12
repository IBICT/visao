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
 * Criteria class for the Chart entity. This class is used in ChartResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /charts?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ChartCriteria implements Serializable {
    /**
     * Class for filtering Permission
     */
    public static class PermissionFilter extends Filter<Permission> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter name;

    private BooleanFilter external;

    private PermissionFilter permission;

    private LongFilter ownerId;

    private LongFilter sharedId;

    public ChartCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public BooleanFilter getExternal() {
        return external;
    }

    public void setExternal(BooleanFilter external) {
        this.external = external;
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

    public LongFilter getSharedId() {
        return sharedId;
    }

    public void setSharedId(LongFilter sharedId) {
        this.sharedId = sharedId;
    }

    @Override
    public String toString() {
        return "ChartCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (external != null ? "external=" + external + ", " : "") +
                (permission != null ? "permission=" + permission + ", " : "") +
                (ownerId != null ? "ownerId=" + ownerId + ", " : "") +
                (sharedId != null ? "sharedId=" + sharedId + ", " : "") +
            "}";
    }

}
