import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMetaDado } from 'app/shared/model/meta-dado.model';

type EntityResponseType = HttpResponse<IMetaDado>;
type EntityArrayResponseType = HttpResponse<IMetaDado[]>;

@Injectable({ providedIn: 'root' })
export class MetaDadoService {
    private resourceUrl = SERVER_API_URL + 'api/meta-dados';

    constructor(private http: HttpClient) {}

    create(metaDado: IMetaDado): Observable<EntityResponseType> {
        return this.http.post<IMetaDado>(this.resourceUrl, metaDado, { observe: 'response' });
    }

    update(metaDado: IMetaDado): Observable<EntityResponseType> {
        return this.http.put<IMetaDado>(this.resourceUrl, metaDado, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMetaDado>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMetaDado[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
