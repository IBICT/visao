package br.com.ibict.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A MetaDado.
 */
@Entity
@Table(name = "meta_dado")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MetaDado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "jhi_value", nullable = false)
    private String value;

    @ManyToOne
    @JsonIgnoreProperties("metaDados")
    private Indicator indicator;

    @ManyToOne
    @JsonIgnoreProperties("metaDados")
    private GrupIndicator grupIndicator;

    @ManyToOne
    @JsonIgnoreProperties("metaDados")
    private GeographicFilter geographicFilter;

    @ManyToOne
    @JsonIgnoreProperties("metaDados")
    private Layer layer;

    @ManyToOne
    @JsonIgnoreProperties("metaDados")
    private GroupLayer groupLayer;

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

    public MetaDado name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public MetaDado value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Indicator getIndicator() {
        return indicator;
    }

    public MetaDado indicator(Indicator indicator) {
        this.indicator = indicator;
        return this;
    }

    public void setIndicator(Indicator indicator) {
        this.indicator = indicator;
    }

    public GrupIndicator getGrupIndicator() {
        return grupIndicator;
    }

    public MetaDado grupIndicator(GrupIndicator grupIndicator) {
        this.grupIndicator = grupIndicator;
        return this;
    }

    public void setGrupIndicator(GrupIndicator grupIndicator) {
        this.grupIndicator = grupIndicator;
    }

    public GeographicFilter getGeographicFilter() {
        return geographicFilter;
    }

    public MetaDado geographicFilter(GeographicFilter geographicFilter) {
        this.geographicFilter = geographicFilter;
        return this;
    }

    public void setGeographicFilter(GeographicFilter geographicFilter) {
        this.geographicFilter = geographicFilter;
    }

    public Layer getLayer() {
        return layer;
    }

    public MetaDado layer(Layer layer) {
        this.layer = layer;
        return this;
    }

    public void setLayer(Layer layer) {
        this.layer = layer;
    }

    public GroupLayer getGroupLayer() {
        return groupLayer;
    }

    public MetaDado groupLayer(GroupLayer groupLayer) {
        this.groupLayer = groupLayer;
        return this;
    }

    public void setGroupLayer(GroupLayer groupLayer) {
        this.groupLayer = groupLayer;
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
        MetaDado metaDado = (MetaDado) o;
        if (metaDado.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), metaDado.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MetaDado{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
