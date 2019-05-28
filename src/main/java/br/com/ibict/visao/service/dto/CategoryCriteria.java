package br.com.ibict.visao.service.dto;

import br.com.ibict.visao.domain.enumeration.TypeCategory;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;






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

    @Override
    public String toString() {
        return "CategoryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
            "}";
    }

}
