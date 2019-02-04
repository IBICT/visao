import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IIndicator } from 'app/shared/model/indicator.model';

type EntityResponseType = HttpResponse<IIndicator>;
type EntityArrayResponseType = HttpResponse<IIndicator[]>;

@Injectable({ providedIn: 'root' })
export class IndicatorService {
    private resourceUrl = SERVER_API_URL + 'api/indicators';

    constructor(private http: HttpClient) {}

    create(indicator: IIndicator): Observable<EntityResponseType> {
        return this.http.post<IIndicator>(this.resourceUrl, indicator, { observe: 'response' });
    }

    update(indicator: IIndicator): Observable<EntityResponseType> {
        return this.http.put<IIndicator>(this.resourceUrl, indicator, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IIndicator>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IIndicator[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
