import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypePresentation } from 'app/shared/model/type-presentation.model';
import { TypePresentationService } from './type-presentation.service';

@Component({
    selector: 'jhi-type-presentation-delete-dialog',
    templateUrl: './type-presentation-delete-dialog.component.html'
})
export class TypePresentationDeleteDialogComponent {
    typePresentation: ITypePresentation;

    constructor(
        private typePresentationService: TypePresentationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.typePresentationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'typePresentationListModification',
                content: 'Deleted an typePresentation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-type-presentation-delete-popup',
    template: ''
})
export class TypePresentationDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typePresentation }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TypePresentationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.typePresentation = typePresentation;
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
