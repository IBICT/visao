import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IYear } from 'app/shared/model/year.model';

type EntityResponseType = HttpResponse<IYear>;
type EntityArrayResponseType = HttpResponse<IYear[]>;

@Injectable({ providedIn: 'root' })
export class YearService {
    private resourceUrl = SERVER_API_URL + 'api/years';

    constructor(private http: HttpClient) {}

    create(year: IYear): Observable<EntityResponseType> {
        return this.http.post<IYear>(this.resourceUrl, year, { observe: 'response' });
    }

    update(year: IYear): Observable<EntityResponseType> {
        return this.http.put<IYear>(this.resourceUrl, year, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IYear>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IYear[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
