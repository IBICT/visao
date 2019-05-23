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
 * Criteria class for the Filter entity. This class is used in FilterResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /filters?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FilterCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter name;

    private BooleanFilter active;

    private StringFilter keyWord;

    private InstantFilter date;

    private StringFilter source;

    private InstantFilter dateChange;

    private LongFilter cidadePoloId;

    private LongFilter userId;

    private LongFilter regionId;

    private LongFilter categoryId;

    public FilterCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
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

    public LongFilter getCidadePoloId() {
        return cidadePoloId;
    }

    public void setCidadePoloId(LongFilter cidadePoloId) {
        this.cidadePoloId = cidadePoloId;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getRegionId() {
        return regionId;
    }

    public void setRegionId(LongFilter regionId) {
        this.regionId = regionId;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "FilterCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (keyWord != null ? "keyWord=" + keyWord + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (source != null ? "source=" + source + ", " : "") +
                (dateChange != null ? "dateChange=" + dateChange + ", " : "") +
                (cidadePoloId != null ? "cidadePoloId=" + cidadePoloId + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (regionId != null ? "regionId=" + regionId + ", " : "") +
                (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
            "}";
    }

}
