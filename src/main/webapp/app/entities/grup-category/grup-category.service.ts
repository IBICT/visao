import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGrupCategory } from 'app/shared/model/grup-category.model';

type EntityResponseType = HttpResponse<IGrupCategory>;
type EntityArrayResponseType = HttpResponse<IGrupCategory[]>;

@Injectable({ providedIn: 'root' })
export class GrupCategoryService {
    private resourceUrl = SERVER_API_URL + 'api/grup-categories';

    constructor(private http: HttpClient) {}

    create(grupCategory: IGrupCategory): Observable<EntityResponseType> {
        return this.http.post<IGrupCategory>(this.resourceUrl, grupCategory, { observe: 'response' });
    }

    update(grupCategory: IGrupCategory): Observable<EntityResponseType> {
        return this.http.put<IGrupCategory>(this.resourceUrl, grupCategory, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IGrupCategory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IGrupCategory[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
