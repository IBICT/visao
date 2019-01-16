import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IName } from 'app/shared/model/name.model';

@Component({
    selector: 'jhi-name-detail',
    templateUrl: './name-detail.component.html'
})
export class NameDetailComponent implements OnInit {
    name: IName;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ name }) => {
            this.name = name;
        });
    }

    previousState() {
        window.history.back();
    }
}
