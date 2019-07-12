import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMetaDado } from 'app/shared/model/meta-dado.model';

@Component({
    selector: 'jhi-meta-dado-detail',
    templateUrl: './meta-dado-detail.component.html'
})
export class MetaDadoDetailComponent implements OnInit {
    metaDado: IMetaDado;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ metaDado }) => {
            this.metaDado = metaDado;
        });
    }

    previousState() {
        window.history.back();
    }
}
