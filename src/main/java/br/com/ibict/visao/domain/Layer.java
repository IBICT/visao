package br.com.ibict.visao.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

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
    private byte[] geoJson;

    @Column(name = "geo_json_content_type", nullable = false)
    private String geoJsonContentType;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "description")
    private String description;

    @Column(name = "key_word")
    private String keyWord;

    @Column(name = "jhi_date")
    private Instant date;

    @Column(name = "producer")
    private String producer;

    @Column(name = "source")
    private String source;

    @Column(name = "date_change")
    private Instant dateChange;

    @Column(name = "note")
    private String note;

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

    public byte[] getGeoJson() {
        return geoJson;
    }

    public Layer geoJson(byte[] geoJson) {
        this.geoJson = geoJson;
        return this;
    }

    public void setGeoJson(byte[] geoJson) {
        this.geoJson = geoJson;
    }

    public String getGeoJsonContentType() {
        return geoJsonContentType;
    }

    public Layer geoJsonContentType(String geoJsonContentType) {
        this.geoJsonContentType = geoJsonContentType;
        return this;
    }

    public void setGeoJsonContentType(String geoJsonContentType) {
        this.geoJsonContentType = geoJsonContentType;
    }

    public Boolean isActive() {
        return active;
    }

    public Layer active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
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

    public String getKeyWord() {
        return keyWord;
    }

    public Layer keyWord(String keyWord) {
        this.keyWord = keyWord;
        return this;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
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

    public String getProducer() {
        return producer;
    }

    public Layer producer(String producer) {
        this.producer = producer;
        return this;
    }

    public void setProducer(String producer) {
        this.producer = producer;
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
            ", geoJsonContentType='" + getGeoJsonContentType() + "'" +
            ", active='" + isActive() + "'" +
            ", description='" + getDescription() + "'" +
            ", keyWord='" + getKeyWord() + "'" +
            ", date='" + getDate() + "'" +
            ", producer='" + getProducer() + "'" +
            ", source='" + getSource() + "'" +
            ", dateChange='" + getDateChange() + "'" +
            ", note='" + getNote() + "'" +
            "}";
    }
}
