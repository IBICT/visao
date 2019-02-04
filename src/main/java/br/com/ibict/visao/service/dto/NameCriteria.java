package br.com.ibict.visao.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import io.github.jhipster.service.filter.InstantFilter;




/**
 * Criteria class for the Name entity. This class is used in NameResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /names?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NameCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter value;

    private BooleanFilter active;

    private StringFilter description;

    private StringFilter keyWord;

    private InstantFilter date;

    private StringFilter producer;

    private StringFilter source;

    private InstantFilter dateChange;

    private StringFilter note;

    private LongFilter categoryId;

    private LongFilter userId;

    public NameCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getValue() {
        return value;
    }

    public void setValue(StringFilter value) {
        this.value = value;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(StringFilter keyWord) {
        this.keyWord = keyWord;
    }

    public InstantFilter getDate() {
        return date;
    }

    public void setDate(InstantFilter date) {
        this.date = date;
    }

    public StringFilter getProducer() {
        return producer;
    }

    public void setProducer(StringFilter producer) {
        this.producer = producer;
    }

    public StringFilter getSource() {
        return source;
    }

    public void setSource(StringFilter source) {
        this.source = source;
    }

    public InstantFilter getDateChange() {
        return dateChange;
    }

    public void setDateChange(InstantFilter dateChange) {
        this.dateChange = dateChange;
    }

    public StringFilter getNote() {
        return note;
    }

    public void setNote(StringFilter note) {
        this.note = note;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "NameCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (value != null ? "value=" + value + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (keyWord != null ? "keyWord=" + keyWord + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (producer != null ? "producer=" + producer + ", " : "") +
                (source != null ? "source=" + source + ", " : "") +
                (dateChange != null ? "dateChange=" + dateChange + ", " : "") +
                (note != null ? "note=" + note + ", " : "") +
                (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
