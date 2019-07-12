package br.com.ibict.service.dto;

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

    private LongFilter metaDadoId;

    private LongFilter regionId;

    private LongFilter groupId;

    private LongFilter yearId;

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

    public LongFilter getMetaDadoId() {
        return metaDadoId;
    }

    public void setMetaDadoId(LongFilter metaDadoId) {
        this.metaDadoId = metaDadoId;
    }

    public LongFilter getRegionId() {
        return regionId;
    }

    public void setRegionId(LongFilter regionId) {
        this.regionId = regionId;
    }

    public LongFilter getGroupId() {
        return groupId;
    }

    public void setGroupId(LongFilter groupId) {
        this.groupId = groupId;
    }

    public LongFilter getYearId() {
        return yearId;
    }

    public void setYearId(LongFilter yearId) {
        this.yearId = yearId;
    }

    @Override
    public String toString() {
        return "IndicatorCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (value != null ? "value=" + value + ", " : "") +
                (metaDadoId != null ? "metaDadoId=" + metaDadoId + ", " : "") +
                (regionId != null ? "regionId=" + regionId + ", " : "") +
                (groupId != null ? "groupId=" + groupId + ", " : "") +
                (yearId != null ? "yearId=" + yearId + ", " : "") +
            "}";
    }

}
