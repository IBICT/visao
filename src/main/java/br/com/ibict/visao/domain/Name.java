package br.com.ibict.visao.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Name.
 */
@Entity
@Table(name = "name")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Name implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_value", nullable = false)
    private String value;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

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

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Category category;

    @ManyToOne
    @JsonIgnoreProperties("")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public Name value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean isActive() {
        return active;
    }

    public Name active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public Name description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public Name keyWord(String keyWord) {
        this.keyWord = keyWord;
        return this;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Instant getDate() {
        return date;
    }

    public Name date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getSource() {
        return source;
    }

    public Name source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Instant getDateChange() {
        return dateChange;
    }

    public Name dateChange(Instant dateChange) {
        this.dateChange = dateChange;
        return this;
    }

    public void setDateChange(Instant dateChange) {
        this.dateChange = dateChange;
    }

    public String getNote() {
        return note;
    }

    public Name note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Category getCategory() {
        return category;
    }

    public Name category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public Name user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        Name name = (Name) o;
        if (name.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), name.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Name{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
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
