package br.com.ibict.visao.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Indicator entity. This class is used in IndicatorResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /indicators?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class IndicatorCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private DoubleFilter value;

    private LongFilter regionId;

    private LongFilter nameId;

    private LongFilter anoId;

    private LongFilter filtersId;

    public IndicatorCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public DoubleFilter getValue() {
        return value;
    }

    public void setValue(DoubleFilter value) {
        this.value = value;
    }

    public LongFilter getRegionId() {
        return regionId;
    }

    public void setRegionId(LongFilter regionId) {
        this.regionId = regionId;
    }

    public LongFilter getNameId() {
        return nameId;
    }

    public void setNameId(LongFilter nameId) {
        this.nameId = nameId;
    }

    public LongFilter getAnoId() {
        return anoId;
    }

    public void setAnoId(LongFilter anoId) {
        this.anoId = anoId;
    }

    public LongFilter getFiltersId() {
        return filtersId;
    }

    public void setFiltersId(LongFilter filtersId) {
        this.filtersId = filtersId;
    }

    @Override
    public String toString() {
        return "IndicatorCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (value != null ? "value=" + value + ", " : "") +
                (regionId != null ? "regionId=" + regionId + ", " : "") +
                (nameId != null ? "nameId=" + nameId + ", " : "") +
                (anoId != null ? "anoId=" + anoId + ", " : "") +
            "}";
    }

}
