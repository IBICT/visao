import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGroupCategory } from 'app/shared/model/group-category.model';
import { GroupCategoryService } from './group-category.service';

@Component({
    selector: 'jhi-group-category-delete-dialog',
    templateUrl: './group-category-delete-dialog.component.html'
})
export class GroupCategoryDeleteDialogComponent {
    groupCategory: IGroupCategory;

    constructor(
        private groupCategoryService: GroupCategoryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.groupCategoryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'groupCategoryListModification',
                content: 'Deleted an groupCategory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-group-category-delete-popup',
    template: ''
})
export class GroupCategoryDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ groupCategory }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(GroupCategoryDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.groupCategory = groupCategory;
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
