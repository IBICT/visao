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
 * Criteria class for the MetaDado entity. This class is used in MetaDadoResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /meta-dados?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MetaDadoCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter name;

    private StringFilter value;

    private LongFilter indicatorId;

    private LongFilter grupIndicatorId;

    private LongFilter geographicFilterId;

    private LongFilter layerId;

    private LongFilter groupLayerId;

    public MetaDadoCriteria() {
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

    public StringFilter getValue() {
        return value;
    }

    public void setValue(StringFilter value) {
        this.value = value;
    }

    public LongFilter getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(LongFilter indicatorId) {
        this.indicatorId = indicatorId;
    }

    public LongFilter getGrupIndicatorId() {
        return grupIndicatorId;
    }

    public void setGrupIndicatorId(LongFilter grupIndicatorId) {
        this.grupIndicatorId = grupIndicatorId;
    }

    public LongFilter getGeographicFilterId() {
        return geographicFilterId;
    }

    public void setGeographicFilterId(LongFilter geographicFilterId) {
        this.geographicFilterId = geographicFilterId;
    }

    public LongFilter getLayerId() {
        return layerId;
    }

    public void setLayerId(LongFilter layerId) {
        this.layerId = layerId;
    }

    public LongFilter getGroupLayerId() {
        return groupLayerId;
    }

    public void setGroupLayerId(LongFilter groupLayerId) {
        this.groupLayerId = groupLayerId;
    }

    @Override
    public String toString() {
        return "MetaDadoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (value != null ? "value=" + value + ", " : "") +
                (indicatorId != null ? "indicatorId=" + indicatorId + ", " : "") +
                (grupIndicatorId != null ? "grupIndicatorId=" + grupIndicatorId + ", " : "") +
                (geographicFilterId != null ? "geographicFilterId=" + geographicFilterId + ", " : "") +
                (layerId != null ? "layerId=" + layerId + ", " : "") +
                (groupLayerId != null ? "groupLayerId=" + groupLayerId + ", " : "") +
            "}";
    }

}
