import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { HomeService } from './home.service';
import { GroupCategory } from '../shared/model/group-category.model';

import { LoginModalService, Principal, Account } from 'app/core';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;

    instancias: GroupCategory[];

    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        public homeService: HomeService
    ) {}

    ngOnInit() {
        this.principal.identity().then(account => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
        this.carregaInstancias();
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', message => {
            this.principal.identity().then(account => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    carregaInstancias(): void {
        console.log(this.principal);
        console.log(this.principal.userIdentity);
        console.log(this.account);

        this.instancias = [
            {
                id: 1,
                iconPresentation: null,
                iconContentType: null,
                about: null,
                permission: null,
                name: 'IBICT',
                owner: null,
                categories: null,
                shareds: null
            },
            {
                id: 1,
                iconPresentation: null,
                iconContentType: null,
                about: null,
                permission: null,
                name: 'MDICT',
                owner: null,
                categories: null,
                shareds: null
            },
            {
                id: 1,
                iconPresentation: null,
                iconContentType: null,
                about: null,
                permission: null,
                name: 'SUS',
                owner: null,
                categories: null,
                shareds: null
            }
        ];
    }
}
