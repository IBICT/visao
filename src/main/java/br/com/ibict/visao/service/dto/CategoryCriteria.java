package br.com.ibict.visao.service.dto;

import java.io.Serializable;
import br.com.ibict.visao.domain.enumeration.TypeCategory;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Category entity. This class is used in CategoryResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /categories?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CategoryCriteria implements Serializable {
    /**
     * Class for filtering TypeCategory
     */
    public static class TypeCategoryFilter extends Filter<TypeCategory> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter name;

    private TypeCategoryFilter type;

    private IntegerFilter level;

    private LongFilter ownerId;

    public CategoryCriteria() {
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

    public TypeCategoryFilter getType() {
        return type;
    }

    public void setType(TypeCategoryFilter type) {
        this.type = type;
    }

    public IntegerFilter getLevel() {
        return level;
    }

    public void setLevel(IntegerFilter level) {
        this.level = level;
    }

    public LongFilter getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(LongFilter ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "CategoryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (level != null ? "level=" + level + ", " : "") +
                (ownerId != null ? "ownerId=" + ownerId + ", " : "") +
            "}";
    }

}
