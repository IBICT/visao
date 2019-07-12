import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGrupIndicator } from 'app/shared/model/grup-indicator.model';
import { GrupIndicatorService } from './grup-indicator.service';

@Component({
    selector: 'jhi-grup-indicator-delete-dialog',
    templateUrl: './grup-indicator-delete-dialog.component.html'
})
export class GrupIndicatorDeleteDialogComponent {
    grupIndicator: IGrupIndicator;

    constructor(
        private grupIndicatorService: GrupIndicatorService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.grupIndicatorService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'grupIndicatorListModification',
                content: 'Deleted an grupIndicator'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-grup-indicator-delete-popup',
    template: ''
})
export class GrupIndicatorDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ grupIndicator }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(GrupIndicatorDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.grupIndicator = grupIndicator;
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
