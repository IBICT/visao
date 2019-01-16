import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IYear } from 'app/shared/model/year.model';
import { YearService } from './year.service';

@Component({
    selector: 'jhi-year-delete-dialog',
    templateUrl: './year-delete-dialog.component.html'
})
export class YearDeleteDialogComponent {
    year: IYear;

    constructor(private yearService: YearService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.yearService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'yearListModification',
                content: 'Deleted an year'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-year-delete-popup',
    template: ''
})
export class YearDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ year }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(YearDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.year = year;
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
