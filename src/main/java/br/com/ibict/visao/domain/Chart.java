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
 * A Chart.
 */
@Entity
@Table(name = "chart")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Chart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "html")
    private String html;

    @Enumerated(EnumType.STRING)
    @Column(name = "permission")
    private Permission permission;

    @ManyToOne
    @JsonIgnoreProperties("")
    private User owner;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "chart_shared",
               joinColumns = @JoinColumn(name = "charts_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "shareds_id", referencedColumnName = "id"))
    private Set<User> shareds = new HashSet<>();

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

    public Chart name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHtml() {
        return html;
    }

    public Chart html(String html) {
        this.html = html;
        return this;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public Permission getPermission() {
        return permission;
    }

    public Chart permission(Permission permission) {
        this.permission = permission;
        return this;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public User getOwner() {
        return owner;
    }

    public Chart owner(User user) {
        this.owner = user;
        return this;
    }

    public void setOwner(User user) {
        this.owner = user;
    }

    public Set<User> getShareds() {
        return shareds;
    }

    public Chart shareds(Set<User> users) {
        this.shareds = users;
        return this;
    }

    public Chart addShared(User user) {
        this.shareds.add(user);
        return this;
    }

    public Chart removeShared(User user) {
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
        Chart chart = (Chart) o;
        if (chart.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), chart.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Chart{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", html='" + getHtml() + "'" +
            ", permission='" + getPermission() + "'" +
            "}";
    }
}
