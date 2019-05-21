package br.com.ibict.visao.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A GroupCategory.
 */
@Entity
@Table(name = "group_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GroupCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "icon_presentation")
    private String iconPresentation;

    @Column(name = "icon_content_type")
    private String iconContentType;

    @Column(name = "about")
    private String about;

    @ManyToOne
    @JsonIgnoreProperties("")
    private User owner;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "group_category_categories",
               joinColumns = @JoinColumn(name = "group_categories_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "categories_id", referencedColumnName = "id"))
    private Set<Category> categories = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "group_category_shareds",
               joinColumns = @JoinColumn(name = "group_categories_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "shareds_id", referencedColumnName = "id"))
    private Set<User> shareds = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIconPresentation() {
        return iconPresentation;
    }

    public GroupCategory iconPresentation(String iconPresentation) {
        this.iconPresentation = iconPresentation;
        return this;
    }

    public void setIconPresentation(String iconPresentation) {
        this.iconPresentation = iconPresentation;
    }

    public String getIconContentType() {
        return iconContentType;
    }

    public GroupCategory iconContentType(String iconContentType) {
        this.iconContentType = iconContentType;
        return this;
    }

    public void setIconContentType(String iconContentType) {
        this.iconContentType = iconContentType;
    }

    public String getAbout() {
        return about;
    }

    public GroupCategory about(String about) {
        this.about = about;
        return this;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public User getOwner() {
        return owner;
    }

    public GroupCategory owner(User user) {
        this.owner = user;
        return this;
    }

    public void setOwner(User user) {
        this.owner = user;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public GroupCategory categories(Set<Category> categories) {
        this.categories = categories;
        return this;
    }

    public GroupCategory addCategories(Category category) {
        this.categories.add(category);
        category.getGroupCategories().add(this);
        return this;
    }

    public GroupCategory removeCategories(Category category) {
        this.categories.remove(category);
        category.getGroupCategories().remove(this);
        return this;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<User> getShareds() {
        return shareds;
    }

    public GroupCategory shareds(Set<User> users) {
        this.shareds = users;
        return this;
    }

    public GroupCategory addShareds(User user) {
        this.shareds.add(user);
        user.getGroupCategories().add(this);
        return this;
    }

    public GroupCategory removeShareds(User user) {
        this.shareds.remove(user);
        user.getGroupCategories().remove(this);
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
        GroupCategory groupCategory = (GroupCategory) o;
        if (groupCategory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), groupCategory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GroupCategory{" +
            "id=" + getId() +
            ", iconPresentation='" + getIconPresentation() + "'" +
            ", iconContentType='" + getIconContentType() + "'" +
            ", about='" + getAbout() + "'" +
            "}";
    }
}
