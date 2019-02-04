import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMetaDado } from 'app/shared/model/meta-dado.model';
import { MetaDadoService } from './meta-dado.service';

@Component({
    selector: 'jhi-meta-dado-delete-dialog',
    templateUrl: './meta-dado-delete-dialog.component.html'
})
export class MetaDadoDeleteDialogComponent {
    metaDado: IMetaDado;

    constructor(private metaDadoService: MetaDadoService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.metaDadoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'metaDadoListModification',
                content: 'Deleted an metaDado'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-meta-dado-delete-popup',
    template: ''
})
export class MetaDadoDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ metaDado }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MetaDadoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.metaDado = metaDado;
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
