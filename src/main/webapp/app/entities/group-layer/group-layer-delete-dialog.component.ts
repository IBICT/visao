import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGroupLayer } from 'app/shared/model/group-layer.model';
import { GroupLayerService } from './group-layer.service';

@Component({
    selector: 'jhi-group-layer-delete-dialog',
    templateUrl: './group-layer-delete-dialog.component.html'
})
export class GroupLayerDeleteDialogComponent {
    groupLayer: IGroupLayer;

    constructor(private groupLayerService: GroupLayerService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.groupLayerService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'groupLayerListModification',
                content: 'Deleted an groupLayer'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-group-layer-delete-popup',
    template: ''
})
export class GroupLayerDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ groupLayer }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(GroupLayerDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.groupLayer = groupLayer;
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
