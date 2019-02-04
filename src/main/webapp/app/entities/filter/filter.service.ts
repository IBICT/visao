import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFilter } from 'app/shared/model/filter.model';

type EntityResponseType = HttpResponse<IFilter>;
type EntityArrayResponseType = HttpResponse<IFilter[]>;

@Injectable({ providedIn: 'root' })
export class FilterService {
    private resourceUrl = SERVER_API_URL + 'api/filters';

    constructor(private http: HttpClient) {}

    create(filter: IFilter): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(filter);
        return this.http
            .post<IFilter>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(filter: IFilter): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(filter);
        return this.http
            .put<IFilter>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IFilter>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IFilter[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(filter: IFilter): IFilter {
        const copy: IFilter = Object.assign({}, filter, {
            date: filter.date != null && filter.date.isValid() ? filter.date.toJSON() : null,
            dateChange: filter.dateChange != null && filter.dateChange.isValid() ? filter.dateChange.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.date = res.body.date != null ? moment(res.body.date) : null;
        res.body.dateChange = res.body.dateChange != null ? moment(res.body.dateChange) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((filter: IFilter) => {
            filter.date = filter.date != null ? moment(filter.date) : null;
            filter.dateChange = filter.dateChange != null ? moment(filter.dateChange) : null;
        });
        return res;
    }
}
