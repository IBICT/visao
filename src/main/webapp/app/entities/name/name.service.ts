import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IName } from 'app/shared/model/name.model';

type EntityResponseType = HttpResponse<IName>;
type EntityArrayResponseType = HttpResponse<IName[]>;

@Injectable({ providedIn: 'root' })
export class NameService {
    private resourceUrl = SERVER_API_URL + 'api/names';

    constructor(private http: HttpClient) {}

    create(name: IName): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(name);
        return this.http
            .post<IName>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(name: IName): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(name);
        return this.http
            .put<IName>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IName>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IName[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(name: IName): IName {
        const copy: IName = Object.assign({}, name, {
            date: name.date != null && name.date.isValid() ? name.date.toJSON() : null,
            dateChange: name.dateChange != null && name.dateChange.isValid() ? name.dateChange.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.date = res.body.date != null ? moment(res.body.date) : null;
        res.body.dateChange = res.body.dateChange != null ? moment(res.body.dateChange) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((name: IName) => {
            name.date = name.date != null ? moment(name.date) : null;
            name.dateChange = name.dateChange != null ? moment(name.dateChange) : null;
        });
        return res;
    }
}
