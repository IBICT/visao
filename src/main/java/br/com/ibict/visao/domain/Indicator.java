package br.com.ibict.visao.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
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

    @ManyToOne
    @JsonIgnoreProperties("")
    private Region region;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Name name;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Year ano;

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

    public Name getName() {
        return name;
    }

    public Indicator name(Name name) {
        this.name = name;
        return this;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Year getAno() {
        return ano;
    }

    public Indicator ano(Year year) {
        this.ano = year;
        return this;
    }

    public void setAno(Year year) {
        this.ano = year;
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
