package br.com.ibict.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Indicator.
 */
@Entity
@Table(name = "indicator")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Indicator implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_value", nullable = false)
    private Double value;

    @OneToMany(mappedBy = "indicator")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MetaDado> metaDados = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("")
    private Region region;

    @ManyToOne
    @JsonIgnoreProperties("")
    private GrupIndicator group;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Year year;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public Indicator value(Double value) {
        this.value = value;
        return this;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Set<MetaDado> getMetaDados() {
        return metaDados;
    }

    public Indicator metaDados(Set<MetaDado> metaDados) {
        this.metaDados = metaDados;
        return this;
    }

    public Indicator addMetaDado(MetaDado metaDado) {
        this.metaDados.add(metaDado);
        metaDado.setIndicator(this);
        return this;
    }

    public Indicator removeMetaDado(MetaDado metaDado) {
        this.metaDados.remove(metaDado);
        metaDado.setIndicator(null);
        return this;
    }

    public void setMetaDados(Set<MetaDado> metaDados) {
        this.metaDados = metaDados;
    }

    public Region getRegion() {
        return region;
    }

    public Indicator region(Region region) {
        this.region = region;
        return this;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public GrupIndicator getGroup() {
        return group;
    }

    public Indicator group(GrupIndicator grupIndicator) {
        this.group = grupIndicator;
        return this;
    }

    public void setGroup(GrupIndicator grupIndicator) {
        this.group = grupIndicator;
    }

    public Year getYear() {
        return year;
    }

    public Indicator year(Year year) {
        this.year = year;
        return this;
    }

    public void setYear(Year year) {
        this.year = year;
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
        Indicator indicator = (Indicator) o;
        if (indicator.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), indicator.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Indicator{" +
            "id=" + getId() +
            ", value=" + getValue() +
            "}";
    }
}
