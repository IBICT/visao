package br.com.ibict.visao.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import br.com.ibict.visao.domain.enumeration.TypeRegion;

/**
 * A Region.
 */
@Entity
@Table(name = "region")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Region implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "uf")
    private String uf;

    @NotNull
    @Column(name = "geo_code", nullable = false)
    private Integer geoCode;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_region", nullable = false)
    private TypeRegion typeRegion;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "region_relacao",
               joinColumns = @JoinColumn(name = "regions_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "relacaos_id", referencedColumnName = "id"))
    private Set<Region> relacaos = new HashSet<>();

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

    public Region name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUf() {
        return uf;
    }

    public Region uf(String uf) {
        this.uf = uf;
        return this;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Integer getGeoCode() {
        return geoCode;
    }

    public Region geoCode(Integer geoCode) {
        this.geoCode = geoCode;
        return this;
    }

    public void setGeoCode(Integer geoCode) {
        this.geoCode = geoCode;
    }

    public TypeRegion getTypeRegion() {
        return typeRegion;
    }

    public Region typeRegion(TypeRegion typeRegion) {
        this.typeRegion = typeRegion;
        return this;
    }

    public void setTypeRegion(TypeRegion typeRegion) {
        this.typeRegion = typeRegion;
    }

    public Set<Region> getRelacaos() {
        return relacaos;
    }

    public Region relacaos(Set<Region> regions) {
        this.relacaos = regions;
        return this;
    }

    public Region addRelacao(Region region) {
        this.relacaos.add(region);
        return this;
    }

    public Region removeRelacao(Region region) {
        this.relacaos.remove(region);
        return this;
    }

    public void setRelacaos(Set<Region> regions) {
        this.relacaos = regions;
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
        Region region = (Region) o;
        if (region.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), region.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Region{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", uf='" + getUf() + "'" +
            ", geoCode=" + getGeoCode() +
            ", typeRegion='" + getTypeRegion() + "'" +
            "}";
    }
}
