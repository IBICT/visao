import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';

import { GroupCategory } from '../shared/model/group-category.model';

@Injectable({ providedIn: 'root' })
export class HomeService {
    constructor(private http: HttpClient) {}

    get(userId: String): Observable<HttpResponse<GroupCategory[]>> {
        return this.http.get<GroupCategory[]>(SERVER_API_URL + 'api/group-categories-by-user/' + userId, { observe: 'response' });
    }
}
