import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGrupIndicator } from 'app/shared/model/grup-indicator.model';

type EntityResponseType = HttpResponse<IGrupIndicator>;
type EntityArrayResponseType = HttpResponse<IGrupIndicator[]>;

@Injectable({ providedIn: 'root' })
export class GrupIndicatorService {
    private resourceUrl = SERVER_API_URL + 'api/grup-indicators';

    constructor(private http: HttpClient) {}

    create(grupIndicator: IGrupIndicator): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(grupIndicator);
        return this.http
            .post<IGrupIndicator>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(grupIndicator: IGrupIndicator): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(grupIndicator);
        return this.http
            .put<IGrupIndicator>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IGrupIndicator>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IGrupIndicator[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(grupIndicator: IGrupIndicator): IGrupIndicator {
        const copy: IGrupIndicator = Object.assign({}, grupIndicator, {
            date: grupIndicator.date != null && grupIndicator.date.isValid() ? grupIndicator.date.toJSON() : null,
            dateChange: grupIndicator.dateChange != null && grupIndicator.dateChange.isValid() ? grupIndicator.dateChange.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.date = res.body.date != null ? moment(res.body.date) : null;
        res.body.dateChange = res.body.dateChange != null ? moment(res.body.dateChange) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((grupIndicator: IGrupIndicator) => {
            grupIndicator.date = grupIndicator.date != null ? moment(grupIndicator.date) : null;
            grupIndicator.dateChange = grupIndicator.dateChange != null ? moment(grupIndicator.dateChange) : null;
        });
        return res;
    }
}
