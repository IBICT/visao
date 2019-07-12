package br.com.ibict.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A MarkerIcon.
 */
@Entity
@Table(name = "marker_icon")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MarkerIcon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Lob
    @Column(name = "icon", nullable = false)
    private byte[] icon;

    @Column(name = "icon_content_type", nullable = false)
    private String iconContentType;

    @Lob
    @Column(name = "shadow")
    private byte[] shadow;

    @Column(name = "shadow_content_type")
    private String shadowContentType;

    @Column(name = "icon_size")
    private String iconSize;

    @Column(name = "shadow_size")
    private String shadowSize;

    @Column(name = "icon_anchor")
    private String iconAnchor;

    @Column(name = "shadow_anchor")
    private String shadowAnchor;

    @Column(name = "popup_anchor")
    private String popupAnchor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getIcon() {
        return icon;
    }

    public MarkerIcon icon(byte[] icon) {
        this.icon = icon;
        return this;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }

    public String getIconContentType() {
        return iconContentType;
    }

    public MarkerIcon iconContentType(String iconContentType) {
        this.iconContentType = iconContentType;
        return this;
    }

    public void setIconContentType(String iconContentType) {
        this.iconContentType = iconContentType;
    }

    public byte[] getShadow() {
        return shadow;
    }

    public MarkerIcon shadow(byte[] shadow) {
        this.shadow = shadow;
        return this;
    }

    public void setShadow(byte[] shadow) {
        this.shadow = shadow;
    }

    public String getShadowContentType() {
        return shadowContentType;
    }

    public MarkerIcon shadowContentType(String shadowContentType) {
        this.shadowContentType = shadowContentType;
        return this;
    }

    public void setShadowContentType(String shadowContentType) {
        this.shadowContentType = shadowContentType;
    }

    public String getIconSize() {
        return iconSize;
    }

    public MarkerIcon iconSize(String iconSize) {
        this.iconSize = iconSize;
        return this;
    }

    public void setIconSize(String iconSize) {
        this.iconSize = iconSize;
    }

    public String getShadowSize() {
        return shadowSize;
    }

    public MarkerIcon shadowSize(String shadowSize) {
        this.shadowSize = shadowSize;
        return this;
    }

    public void setShadowSize(String shadowSize) {
        this.shadowSize = shadowSize;
    }

    public String getIconAnchor() {
        return iconAnchor;
    }

    public MarkerIcon iconAnchor(String iconAnchor) {
        this.iconAnchor = iconAnchor;
        return this;
    }

    public void setIconAnchor(String iconAnchor) {
        this.iconAnchor = iconAnchor;
    }

    public String getShadowAnchor() {
        return shadowAnchor;
    }

    public MarkerIcon shadowAnchor(String shadowAnchor) {
        this.shadowAnchor = shadowAnchor;
        return this;
    }

    public void setShadowAnchor(String shadowAnchor) {
        this.shadowAnchor = shadowAnchor;
    }

    public String getPopupAnchor() {
        return popupAnchor;
    }

    public MarkerIcon popupAnchor(String popupAnchor) {
        this.popupAnchor = popupAnchor;
        return this;
    }

    public void setPopupAnchor(String popupAnchor) {
        this.popupAnchor = popupAnchor;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MarkerIcon markerIcon = (MarkerIcon) o;
        if (markerIcon.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), markerIcon.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MarkerIcon{" +
            "id=" + getId() +
            ", icon='" + getIcon() + "'" +
            ", iconContentType='" + getIconContentType() + "'" +
            ", shadow='" + getShadow() + "'" +
            ", shadowContentType='" + getShadowContentType() + "'" +
            ", iconSize='" + getIconSize() + "'" +
            ", shadowSize='" + getShadowSize() + "'" +
            ", iconAnchor='" + getIconAnchor() + "'" +
            ", shadowAnchor='" + getShadowAnchor() + "'" +
            ", popupAnchor='" + getPopupAnchor() + "'" +
            "}";
    }
}
