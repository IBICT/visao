package br.com.ibict.visao.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "meta_dado_nome",
               joinColumns = @JoinColumn(name = "meta_dados_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "nomes_id", referencedColumnName = "id"))
    private Set<Name> nomes = new HashSet<>();

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

    public Set<Name> getNomes() {
        return nomes;
    }

    public MetaDado nomes(Set<Name> names) {
        this.nomes = names;
        return this;
    }

    public MetaDado addNome(Name name) {
        this.nomes.add(name);
        return this;
    }

    public MetaDado removeNome(Name name) {
        this.nomes.remove(name);
        return this;
    }

    public void setNomes(Set<Name> names) {
        this.nomes = names;
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
