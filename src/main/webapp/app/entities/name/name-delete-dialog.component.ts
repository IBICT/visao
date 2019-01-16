import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IName } from 'app/shared/model/name.model';
import { NameService } from './name.service';

@Component({
    selector: 'jhi-name-delete-dialog',
    templateUrl: './name-delete-dialog.component.html'
})
export class NameDeleteDialogComponent {
    name: IName;

    constructor(private nameService: NameService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.nameService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'nameListModification',
                content: 'Deleted an name'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-name-delete-popup',
    template: ''
})
export class NameDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ name }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(NameDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.name = name;
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
