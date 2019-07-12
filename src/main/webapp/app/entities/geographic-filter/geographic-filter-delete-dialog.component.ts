import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGeographicFilter } from 'app/shared/model/geographic-filter.model';
import { GeographicFilterService } from './geographic-filter.service';

@Component({
    selector: 'jhi-geographic-filter-delete-dialog',
    templateUrl: './geographic-filter-delete-dialog.component.html'
})
export class GeographicFilterDeleteDialogComponent {
    geographicFilter: IGeographicFilter;

    constructor(
        private geographicFilterService: GeographicFilterService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.geographicFilterService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'geographicFilterListModification',
                content: 'Deleted an geographicFilter'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-geographic-filter-delete-popup',
    template: ''
})
export class GeographicFilterDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ geographicFilter }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(GeographicFilterDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.geographicFilter = geographicFilter;
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
