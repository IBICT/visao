import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGroupLayer } from 'app/shared/model/group-layer.model';

type EntityResponseType = HttpResponse<IGroupLayer>;
type EntityArrayResponseType = HttpResponse<IGroupLayer[]>;

@Injectable({ providedIn: 'root' })
export class GroupLayerService {
    private resourceUrl = SERVER_API_URL + 'api/group-layers';

    constructor(private http: HttpClient) {}

    create(groupLayer: IGroupLayer): Observable<EntityResponseType> {
        return this.http.post<IGroupLayer>(this.resourceUrl, groupLayer, { observe: 'response' });
    }

    update(groupLayer: IGroupLayer): Observable<EntityResponseType> {
        return this.http.put<IGroupLayer>(this.resourceUrl, groupLayer, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IGroupLayer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IGroupLayer[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
