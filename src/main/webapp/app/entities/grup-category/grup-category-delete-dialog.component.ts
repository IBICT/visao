import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGrupCategory } from 'app/shared/model/grup-category.model';
import { GrupCategoryService } from './grup-category.service';

@Component({
    selector: 'jhi-grup-category-delete-dialog',
    templateUrl: './grup-category-delete-dialog.component.html'
})
export class GrupCategoryDeleteDialogComponent {
    grupCategory: IGrupCategory;

    constructor(
        private grupCategoryService: GrupCategoryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.grupCategoryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'grupCategoryListModification',
                content: 'Deleted an grupCategory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-grup-category-delete-popup',
    template: ''
})
export class GrupCategoryDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ grupCategory }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(GrupCategoryDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.grupCategory = grupCategory;
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
