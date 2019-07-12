import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { ITypePresentation } from 'app/shared/model/type-presentation.model';
import { Principal } from 'app/core';
import { TypePresentationService } from './type-presentation.service';

@Component({
    selector: 'jhi-type-presentation',
    templateUrl: './type-presentation.component.html'
})
export class TypePresentationComponent implements OnInit, OnDestroy {
    typePresentations: ITypePresentation[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private typePresentationService: TypePresentationService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.typePresentationService.query().subscribe(
            (res: HttpResponse<ITypePresentation[]>) => {
                this.typePresentations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTypePresentations();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITypePresentation) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInTypePresentations() {
        this.eventSubscriber = this.eventManager.subscribe('typePresentationListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
