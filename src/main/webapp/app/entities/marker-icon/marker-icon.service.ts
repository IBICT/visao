import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMarkerIcon } from 'app/shared/model/marker-icon.model';

type EntityResponseType = HttpResponse<IMarkerIcon>;
type EntityArrayResponseType = HttpResponse<IMarkerIcon[]>;

@Injectable({ providedIn: 'root' })
export class MarkerIconService {
    private resourceUrl = SERVER_API_URL + 'api/marker-icons';

    constructor(private http: HttpClient) {}

    create(markerIcon: IMarkerIcon): Observable<EntityResponseType> {
        return this.http.post<IMarkerIcon>(this.resourceUrl, markerIcon, { observe: 'response' });
    }

    update(markerIcon: IMarkerIcon): Observable<EntityResponseType> {
        return this.http.put<IMarkerIcon>(this.resourceUrl, markerIcon, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMarkerIcon>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMarkerIcon[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
