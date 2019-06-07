import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { HomeService } from './home.service';
import { GroupCategory } from '../shared/model/group-category.model';

import { LoginModalService, Principal, Account } from 'app/core';
import { HttpResponse } from '@angular/common/http';

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
            this.carregaInstancias();
        });
        this.registerAuthenticationSuccess();
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
        let userLogin;
        if (this.account == null) {
            userLogin = null;
        } else {
            userLogin = this.account.login;
        }

        this.homeService.get(userLogin).subscribe((res: HttpResponse<GroupCategory[]>) => (this.instancias = res.body));
    }
}
