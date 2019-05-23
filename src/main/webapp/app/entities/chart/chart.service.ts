import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IChart } from 'app/shared/model/chart.model';

type EntityResponseType = HttpResponse<IChart>;
type EntityArrayResponseType = HttpResponse<IChart[]>;

@Injectable({ providedIn: 'root' })
export class ChartService {
    private resourceUrl = SERVER_API_URL + 'api/charts';

    constructor(private http: HttpClient) {}

    create(chart: IChart): Observable<EntityResponseType> {
        return this.http.post<IChart>(this.resourceUrl, chart, { observe: 'response' });
    }

    update(chart: IChart): Observable<EntityResponseType> {
        return this.http.put<IChart>(this.resourceUrl, chart, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IChart>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IChart[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
