import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILayer } from 'app/shared/model/layer.model';

type EntityResponseType = HttpResponse<ILayer>;
type EntityArrayResponseType = HttpResponse<ILayer[]>;

@Injectable({ providedIn: 'root' })
export class LayerService {
    private resourceUrl = SERVER_API_URL + 'api/layers';

    constructor(private http: HttpClient) {}

    create(layer: ILayer): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(layer);
        return this.http
            .post<ILayer>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(layer: ILayer): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(layer);
        return this.http
            .put<ILayer>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ILayer>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ILayer[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(layer: ILayer): ILayer {
        const copy: ILayer = Object.assign({}, layer, {
            date: layer.date != null && layer.date.isValid() ? layer.date.toJSON() : null,
            dateChange: layer.dateChange != null && layer.dateChange.isValid() ? layer.dateChange.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.date = res.body.date != null ? moment(res.body.date) : null;
        res.body.dateChange = res.body.dateChange != null ? moment(res.body.dateChange) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((layer: ILayer) => {
            layer.date = layer.date != null ? moment(layer.date) : null;
            layer.dateChange = layer.dateChange != null ? moment(layer.dateChange) : null;
        });
        return res;
    }
}
