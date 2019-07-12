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
 * A GroupLayer.
 */
@Entity
@Table(name = "group_layer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GroupLayer implements Serializable {

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

    @Column(name = "key_word")
    private String keyWord;

    @OneToMany(mappedBy = "groupLayer")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MetaDado> metaDados = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("")
    private User owner;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "group_layer_category",
               joinColumns = @JoinColumn(name = "group_layers_id", referencedColumnName = "id"),
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

    public GroupLayer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isActive() {
        return active;
    }

    public GroupLayer active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public GroupLayer keyWord(String keyWord) {
        this.keyWord = keyWord;
        return this;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Set<MetaDado> getMetaDados() {
        return metaDados;
    }

    public GroupLayer metaDados(Set<MetaDado> metaDados) {
        this.metaDados = metaDados;
        return this;
    }

    public GroupLayer addMetaDado(MetaDado metaDado) {
        this.metaDados.add(metaDado);
        metaDado.setGroupLayer(this);
        return this;
    }

    public GroupLayer removeMetaDado(MetaDado metaDado) {
        this.metaDados.remove(metaDado);
        metaDado.setGroupLayer(null);
        return this;
    }

    public void setMetaDados(Set<MetaDado> metaDados) {
        this.metaDados = metaDados;
    }

    public User getOwner() {
        return owner;
    }

    public GroupLayer owner(User user) {
        this.owner = user;
        return this;
    }

    public void setOwner(User user) {
        this.owner = user;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public GroupLayer categories(Set<Category> categories) {
        this.categories = categories;
        return this;
    }

    public GroupLayer addCategory(Category category) {
        this.categories.add(category);
        return this;
    }

    public GroupLayer removeCategory(Category category) {
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
        GroupLayer groupLayer = (GroupLayer) o;
        if (groupLayer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), groupLayer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GroupLayer{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", active='" + isActive() + "'" +
            ", keyWord='" + getKeyWord() + "'" +
            "}";
    }
}
