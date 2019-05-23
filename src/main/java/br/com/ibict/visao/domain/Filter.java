package br.com.ibict.visao.domain;

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
 * A Filter.
 */
@Entity
@Table(name = "filter")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Filter implements Serializable {

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

    @ManyToOne
    @JsonIgnoreProperties("")
    private User user;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "filter_region",
               joinColumns = @JoinColumn(name = "filters_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "regions_id", referencedColumnName = "id"))
    private Set<Region> regions = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "filter_category",
               joinColumns = @JoinColumn(name = "filters_id", referencedColumnName = "id"),
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

    public Filter name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isActive() {
        return active;
    }

    public Filter active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public Filter description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public Filter keyWord(String keyWord) {
        this.keyWord = keyWord;
        return this;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Instant getDate() {
        return date;
    }

    public Filter date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getSource() {
        return source;
    }

    public Filter source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Instant getDateChange() {
        return dateChange;
    }

    public Filter dateChange(Instant dateChange) {
        this.dateChange = dateChange;
        return this;
    }

    public void setDateChange(Instant dateChange) {
        this.dateChange = dateChange;
    }

    public String getNote() {
        return note;
    }

    public Filter note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Region getCidadePolo() {
        return cidadePolo;
    }

    public Filter cidadePolo(Region region) {
        this.cidadePolo = region;
        return this;
    }

    public void setCidadePolo(Region region) {
        this.cidadePolo = region;
    }

    public User getUser() {
        return user;
    }

    public Filter user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Region> getRegions() {
        return regions;
    }

    public Filter regions(Set<Region> regions) {
        this.regions = regions;
        return this;
    }

    public Filter addRegion(Region region) {
        this.regions.add(region);
        return this;
    }

    public Filter removeRegion(Region region) {
        this.regions.remove(region);
        return this;
    }

    public void setRegions(Set<Region> regions) {
        this.regions = regions;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Filter categories(Set<Category> categories) {
        this.categories = categories;
        return this;
    }

    public Filter addCategory(Category category) {
        this.categories.add(category);
        return this;
    }

    public Filter removeCategory(Category category) {
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
        Filter filter = (Filter) o;
        if (filter.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), filter.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Filter{" +
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
