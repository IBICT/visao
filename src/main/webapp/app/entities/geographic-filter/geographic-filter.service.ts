import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGeographicFilter } from 'app/shared/model/geographic-filter.model';

type EntityResponseType = HttpResponse<IGeographicFilter>;
type EntityArrayResponseType = HttpResponse<IGeographicFilter[]>;

@Injectable({ providedIn: 'root' })
export class GeographicFilterService {
    private resourceUrl = SERVER_API_URL + 'api/geographic-filters';

    constructor(private http: HttpClient) {}

    create(geographicFilter: IGeographicFilter): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(geographicFilter);
        return this.http
            .post<IGeographicFilter>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(geographicFilter: IGeographicFilter): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(geographicFilter);
        return this.http
            .put<IGeographicFilter>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IGeographicFilter>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IGeographicFilter[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(geographicFilter: IGeographicFilter): IGeographicFilter {
        const copy: IGeographicFilter = Object.assign({}, geographicFilter, {
            date: geographicFilter.date != null && geographicFilter.date.isValid() ? geographicFilter.date.toJSON() : null,
            dateChange:
                geographicFilter.dateChange != null && geographicFilter.dateChange.isValid() ? geographicFilter.dateChange.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.date = res.body.date != null ? moment(res.body.date) : null;
        res.body.dateChange = res.body.dateChange != null ? moment(res.body.dateChange) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((geographicFilter: IGeographicFilter) => {
            geographicFilter.date = geographicFilter.date != null ? moment(geographicFilter.date) : null;
            geographicFilter.dateChange = geographicFilter.dateChange != null ? moment(geographicFilter.dateChange) : null;
        });
        return res;
    }
}
