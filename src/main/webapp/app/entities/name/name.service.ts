import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

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
        return this.http.post<IName>(this.resourceUrl, name, { observe: 'response' });
    }

    update(name: IName): Observable<EntityResponseType> {
        return this.http.put<IName>(this.resourceUrl, name, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IName>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IName[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
