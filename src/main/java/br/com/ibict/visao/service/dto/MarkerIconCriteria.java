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
 * Criteria class for the MarkerIcon entity. This class is used in MarkerIconResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /marker-icons?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MarkerIconCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter iconSize;

    private StringFilter shadowSize;

    private StringFilter iconAnchor;

    private StringFilter shadowAnchor;

    private StringFilter popupAnchor;

    public MarkerIconCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getIconSize() {
        return iconSize;
    }

    public void setIconSize(StringFilter iconSize) {
        this.iconSize = iconSize;
    }

    public StringFilter getShadowSize() {
        return shadowSize;
    }

    public void setShadowSize(StringFilter shadowSize) {
        this.shadowSize = shadowSize;
    }

    public StringFilter getIconAnchor() {
        return iconAnchor;
    }

    public void setIconAnchor(StringFilter iconAnchor) {
        this.iconAnchor = iconAnchor;
    }

    public StringFilter getShadowAnchor() {
        return shadowAnchor;
    }

    public void setShadowAnchor(StringFilter shadowAnchor) {
        this.shadowAnchor = shadowAnchor;
    }

    public StringFilter getPopupAnchor() {
        return popupAnchor;
    }

    public void setPopupAnchor(StringFilter popupAnchor) {
        this.popupAnchor = popupAnchor;
    }

    @Override
    public String toString() {
        return "MarkerIconCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (iconSize != null ? "iconSize=" + iconSize + ", " : "") +
                (shadowSize != null ? "shadowSize=" + shadowSize + ", " : "") +
                (iconAnchor != null ? "iconAnchor=" + iconAnchor + ", " : "") +
                (shadowAnchor != null ? "shadowAnchor=" + shadowAnchor + ", " : "") +
                (popupAnchor != null ? "popupAnchor=" + popupAnchor + ", " : "") +
            "}";
    }

}
