package br.com.ibict.visao.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import br.com.ibict.visao.domain.enumeration.Permission;

/**
 * A GrupCategory.
 */
@Entity
@Table(name = "grup_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GrupCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "logo")
    private byte[] logo;

    @Column(name = "logo_content_type")
    private String logoContentType;

    @Lob
    @Column(name = "sobre")
    private String sobre;

    @Enumerated(EnumType.STRING)
    @Column(name = "permission")
    private Permission permission;

    @ManyToOne
    @JsonIgnoreProperties("")
    private User owner;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "grup_category_category",
               joinColumns = @JoinColumn(name = "grup_categories_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "categories_id", referencedColumnName = "id"))
    private Set<Category> categories = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "grup_category_shared",
               joinColumns = @JoinColumn(name = "grup_categories_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "shareds_id", referencedColumnName = "id"))
    private Set<User> shareds = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getLogo() {
        return logo;
    }

    public GrupCategory logo(byte[] logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return logoContentType;
    }

    public GrupCategory logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public String getSobre() {
        return sobre;
    }

    public GrupCategory sobre(String sobre) {
        this.sobre = sobre;
        return this;
    }

    public void setSobre(String sobre) {
        this.sobre = sobre;
    }

    public Permission getPermission() {
        return permission;
    }

    public GrupCategory permission(Permission permission) {
        this.permission = permission;
        return this;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public User getOwner() {
        return owner;
    }

    public GrupCategory owner(User user) {
        this.owner = user;
        return this;
    }

    public void setOwner(User user) {
        this.owner = user;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public GrupCategory categories(Set<Category> categories) {
        this.categories = categories;
        return this;
    }

    public GrupCategory addCategory(Category category) {
        this.categories.add(category);
        return this;
    }

    public GrupCategory removeCategory(Category category) {
        this.categories.remove(category);
        return this;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<User> getShareds() {
        return shareds;
    }

    public GrupCategory shareds(Set<User> users) {
        this.shareds = users;
        return this;
    }

    public GrupCategory addShared(User user) {
        this.shareds.add(user);
        return this;
    }

    public GrupCategory removeShared(User user) {
        this.shareds.remove(user);
        return this;
    }

    public void setShareds(Set<User> users) {
        this.shareds = users;
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
        GrupCategory grupCategory = (GrupCategory) o;
        if (grupCategory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), grupCategory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GrupCategory{" +
            "id=" + getId() +
            ", logo='" + getLogo() + "'" +
            ", logoContentType='" + getLogoContentType() + "'" +
            ", sobre='" + getSobre() + "'" +
            ", permission='" + getPermission() + "'" +
            "}";
    }
}
