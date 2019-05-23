import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IChart } from 'app/shared/model/chart.model';
import { ChartService } from './chart.service';

@Component({
    selector: 'jhi-chart-delete-dialog',
    templateUrl: './chart-delete-dialog.component.html'
})
export class ChartDeleteDialogComponent {
    chart: IChart;

    constructor(private chartService: ChartService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.chartService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'chartListModification',
                content: 'Deleted an chart'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-chart-delete-popup',
    template: ''
})
export class ChartDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ chart }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ChartDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.chart = chart;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
