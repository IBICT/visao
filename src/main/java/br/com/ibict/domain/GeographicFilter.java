package br.com.ibict.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A GeographicFilter.
 */
@Entity
@Table(name = "geographic_filter")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GeographicFilter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "key_word")
    private String keyWord;

    @Column(name = "jhi_date")
    private Instant date;

    @Column(name = "source")
    private String source;

    @Column(name = "date_change")
    private Instant dateChange;

    @Lob
    @Column(name = "note")
    private String note;

    @OneToOne
    @JoinColumn(unique = true)
    private Region cidadePolo;

    @OneToMany(mappedBy = "geographicFilter")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MetaDado> metaDados = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("")
    private User owner;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "geographic_filter_region",
               joinColumns = @JoinColumn(name = "geographic_filters_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "regions_id", referencedColumnName = "id"))
    private Set<Region> regions = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "geographic_filter_category",
               joinColumns = @JoinColumn(name = "geographic_filters_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "categories_id", referencedColumnName = "id"))
    private Set<Category> categories = new HashSet<>();

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

    public GeographicFilter name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isActive() {
        return active;
    }

    public GeographicFilter active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public GeographicFilter description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public GeographicFilter keyWord(String keyWord) {
        this.keyWord = keyWord;
        return this;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Instant getDate() {
        return date;
    }

    public GeographicFilter date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getSource() {
        return source;
    }

    public GeographicFilter source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Instant getDateChange() {
        return dateChange;
    }

    public GeographicFilter dateChange(Instant dateChange) {
        this.dateChange = dateChange;
        return this;
    }

    public void setDateChange(Instant dateChange) {
        this.dateChange = dateChange;
    }

    public String getNote() {
        return note;
    }

    public GeographicFilter note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Region getCidadePolo() {
        return cidadePolo;
    }

    public GeographicFilter cidadePolo(Region region) {
        this.cidadePolo = region;
        return this;
    }

    public void setCidadePolo(Region region) {
        this.cidadePolo = region;
    }

    public Set<MetaDado> getMetaDados() {
        return metaDados;
    }

    public GeographicFilter metaDados(Set<MetaDado> metaDados) {
        this.metaDados = metaDados;
        return this;
    }

    public GeographicFilter addMetaDado(MetaDado metaDado) {
        this.metaDados.add(metaDado);
        metaDado.setGeographicFilter(this);
        return this;
    }

    public GeographicFilter removeMetaDado(MetaDado metaDado) {
        this.metaDados.remove(metaDado);
        metaDado.setGeographicFilter(null);
        return this;
    }

    public void setMetaDados(Set<MetaDado> metaDados) {
        this.metaDados = metaDados;
    }

    public User getOwner() {
        return owner;
    }

    public GeographicFilter owner(User user) {
        this.owner = user;
        return this;
    }

    public void setOwner(User user) {
        this.owner = user;
    }

    public Set<Region> getRegions() {
        return regions;
    }

    public GeographicFilter regions(Set<Region> regions) {
        this.regions = regions;
        return this;
    }

    public GeographicFilter addRegion(Region region) {
        this.regions.add(region);
        return this;
    }

    public GeographicFilter removeRegion(Region region) {
        this.regions.remove(region);
        return this;
    }

    public void setRegions(Set<Region> regions) {
        this.regions = regions;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public GeographicFilter categories(Set<Category> categories) {
        this.categories = categories;
        return this;
    }

    public GeographicFilter addCategory(Category category) {
        this.categories.add(category);
        return this;
    }

    public GeographicFilter removeCategory(Category category) {
        this.categories.remove(category);
        return this;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
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
        GeographicFilter geographicFilter = (GeographicFilter) o;
        if (geographicFilter.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), geographicFilter.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GeographicFilter{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", active='" + isActive() + "'" +
            ", description='" + getDescription() + "'" +
            ", keyWord='" + getKeyWord() + "'" +
            ", date='" + getDate() + "'" +
            ", source='" + getSource() + "'" +
            ", dateChange='" + getDateChange() + "'" +
            ", note='" + getNote() + "'" +
            "}";
    }
}
