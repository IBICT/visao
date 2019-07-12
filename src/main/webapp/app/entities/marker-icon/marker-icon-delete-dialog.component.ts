import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMarkerIcon } from 'app/shared/model/marker-icon.model';
import { MarkerIconService } from './marker-icon.service';

@Component({
    selector: 'jhi-marker-icon-delete-dialog',
    templateUrl: './marker-icon-delete-dialog.component.html'
})
export class MarkerIconDeleteDialogComponent {
    markerIcon: IMarkerIcon;

    constructor(private markerIconService: MarkerIconService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.markerIconService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'markerIconListModification',
                content: 'Deleted an markerIcon'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-marker-icon-delete-popup',
    template: ''
})
export class MarkerIconDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ markerIcon }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MarkerIconDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.markerIcon = markerIcon;
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
