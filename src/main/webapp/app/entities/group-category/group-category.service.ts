import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGroupCategory } from 'app/shared/model/group-category.model';

type EntityResponseType = HttpResponse<IGroupCategory>;
type EntityArrayResponseType = HttpResponse<IGroupCategory[]>;

@Injectable({ providedIn: 'root' })
export class GroupCategoryService {
    private resourceUrl = SERVER_API_URL + 'api/group-categories';

    constructor(private http: HttpClient) {}

    create(groupCategory: IGroupCategory): Observable<EntityResponseType> {
        return this.http.post<IGroupCategory>(this.resourceUrl, groupCategory, { observe: 'response' });
    }

    update(groupCategory: IGroupCategory): Observable<EntityResponseType> {
        return this.http.put<IGroupCategory>(this.resourceUrl, groupCategory, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IGroupCategory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IGroupCategory[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
