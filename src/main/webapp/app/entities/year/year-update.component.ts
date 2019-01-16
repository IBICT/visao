import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IYear } from 'app/shared/model/year.model';
import { YearService } from './year.service';

@Component({
    selector: 'jhi-year-update',
    templateUrl: './year-update.component.html'
})
export class YearUpdateComponent implements OnInit {
    private _year: IYear;
    isSaving: boolean;

    constructor(private yearService: YearService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ year }) => {
            this.year = year;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.year.id !== undefined) {
            this.subscribeToSaveResponse(this.yearService.update(this.year));
        } else {
            this.subscribeToSaveResponse(this.yearService.create(this.year));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IYear>>) {
        result.subscribe((res: HttpResponse<IYear>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get year() {
        return this._year;
    }

    set year(year: IYear) {
        this._year = year;
    }
}
