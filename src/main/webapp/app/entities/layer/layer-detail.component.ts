import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ILayer } from 'app/shared/model/layer.model';

@Component({
    selector: 'jhi-layer-detail',
    templateUrl: './layer-detail.component.html'
})
export class LayerDetailComponent implements OnInit {
    layer: ILayer;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ layer }) => {
            this.layer = layer;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
