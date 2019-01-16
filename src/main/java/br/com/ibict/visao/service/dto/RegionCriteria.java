package br.com.ibict.visao.service.dto;

import java.io.Serializable;
import br.com.ibict.visao.domain.enumeration.TypeRegion;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Region entity. This class is used in RegionResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /regions?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RegionCriteria implements Serializable {
    /**
     * Class for filtering TypeRegion
     */
    public static class TypeRegionFilter extends Filter<TypeRegion> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter name;

    private StringFilter uf;

    private IntegerFilter geoCode;

    private TypeRegionFilter typeRegion;

    private LongFilter relacaoId;

    public RegionCriteria() {
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

    public StringFilter getUf() {
        return uf;
    }

    public void setUf(StringFilter uf) {
        this.uf = uf;
    }

    public IntegerFilter getGeoCode() {
        return geoCode;
    }

    public void setGeoCode(IntegerFilter geoCode) {
        this.geoCode = geoCode;
    }

    public TypeRegionFilter getTypeRegion() {
        return typeRegion;
    }

    public void setTypeRegion(TypeRegionFilter typeRegion) {
        this.typeRegion = typeRegion;
    }

    public LongFilter getRelacaoId() {
        return relacaoId;
    }

    public void setRelacaoId(LongFilter relacaoId) {
        this.relacaoId = relacaoId;
    }

    @Override
    public String toString() {
        return "RegionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (uf != null ? "uf=" + uf + ", " : "") +
                (geoCode != null ? "geoCode=" + geoCode + ", " : "") +
                (typeRegion != null ? "typeRegion=" + typeRegion + ", " : "") +
                (relacaoId != null ? "relacaoId=" + relacaoId + ", " : "") +
            "}";
    }

}
