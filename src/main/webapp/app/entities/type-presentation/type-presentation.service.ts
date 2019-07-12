import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITypePresentation } from 'app/shared/model/type-presentation.model';

type EntityResponseType = HttpResponse<ITypePresentation>;
type EntityArrayResponseType = HttpResponse<ITypePresentation[]>;

@Injectable({ providedIn: 'root' })
export class TypePresentationService {
    private resourceUrl = SERVER_API_URL + 'api/type-presentations';

    constructor(private http: HttpClient) {}

    create(typePresentation: ITypePresentation): Observable<EntityResponseType> {
        return this.http.post<ITypePresentation>(this.resourceUrl, typePresentation, { observe: 'response' });
    }

    update(typePresentation: ITypePresentation): Observable<EntityResponseType> {
        return this.http.put<ITypePresentation>(this.resourceUrl, typePresentation, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITypePresentation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITypePresentation[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
