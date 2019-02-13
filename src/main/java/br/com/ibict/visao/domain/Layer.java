package br.com.ibict.visao.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import br.com.ibict.visao.domain.enumeration.TypeLayer;

/**
 * A Layer.
 */
@Entity
@Table(name = "layer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Layer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    
    @Lob
    @Column(name = "geo_json", nullable = false)
    private String geoJson;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private TypeLayer type;

    @Column(name = "description")
    private String description;

    @Column(name = "jhi_date")
    private Instant date;

    @Column(name = "source")
    private String source;

    @Column(name = "date_change")
    private Instant dateChange;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Category category;

    @ManyToOne
    @JsonIgnoreProperties("")
    private MarkerIcon icon;

    @ManyToOne
    @JsonIgnoreProperties("")
    private GroupLayer group;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Layer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGeoJson() {
        return geoJson;
    }

    public Layer geoJson(String geoJson) {
        this.geoJson = geoJson;
        return this;
    }

    public void setGeoJson(String geoJson) {
        this.geoJson = geoJson;
    }

    public TypeLayer getType() {
        return type;
    }

    public Layer type(TypeLayer type) {
        this.type = type;
        return this;
    }

    public void setType(TypeLayer type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public Layer description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getDate() {
        return date;
    }

    public Layer date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getSource() {
        return source;
    }

    public Layer source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Instant getDateChange() {
        return dateChange;
    }

    public Layer dateChange(Instant dateChange) {
        this.dateChange = dateChange;
        return this;
    }

    public void setDateChange(Instant dateChange) {
        this.dateChange = dateChange;
    }

    public String getNote() {
        return note;
    }

    public Layer note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Category getCategory() {
        return category;
    }

    public Layer category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public MarkerIcon getIcon() {
        return icon;
    }

    public Layer icon(MarkerIcon markerIcon) {
        this.icon = markerIcon;
        return this;
    }

    public void setIcon(MarkerIcon markerIcon) {
        this.icon = markerIcon;
    }

    public GroupLayer getGroup() {
        return group;
    }

    public Layer group(GroupLayer groupLayer) {
        this.group = groupLayer;
        return this;
    }

    public void setGroup(GroupLayer groupLayer) {
        this.group = groupLayer;
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
        Layer layer = (Layer) o;
        if (layer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), layer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Layer{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", geoJson='" + getGeoJson() + "'" +
            ", type='" + getType() + "'" +
            ", description='" + getDescription() + "'" +
            ", date='" + getDate() + "'" +
            ", source='" + getSource() + "'" +
            ", dateChange='" + getDateChange() + "'" +
            ", note='" + getNote() + "'" +
            "}";
    }
}
