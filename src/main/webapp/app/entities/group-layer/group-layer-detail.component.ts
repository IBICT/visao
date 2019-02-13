import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGroupLayer } from 'app/shared/model/group-layer.model';

@Component({
    selector: 'jhi-group-layer-detail',
    templateUrl: './group-layer-detail.component.html'
})
export class GroupLayerDetailComponent implements OnInit {
    groupLayer: IGroupLayer;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ groupLayer }) => {
            this.groupLayer = groupLayer;
        });
    }

    previousState() {
        window.history.back();
    }
}
