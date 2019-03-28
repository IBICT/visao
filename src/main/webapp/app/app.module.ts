import './vendor.ts';

import { NgModule, Injector } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { Ng2Webstorage } from 'ngx-webstorage';
import { JhiEventManager } from 'ng-jhipster';

import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import { VisaoSharedModule } from 'app/shared';
import { VisaoCoreModule } from 'app/core';
import { VisaoAppRoutingModule } from './app-routing.module';
import { VisaoHomeModule } from './home/home.module';
import { VisaoAccountModule } from './account/account.module';
import { VisaoEntityModule } from './entities/entity.module';
import { StateStorageService } from 'app/core/auth/state-storage.service';
import { VisaoAppMapModule } from './map/map.module';
import { VisaoPageSetsModule } from './pages/page-sets.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent, NavbarComponent, FooterComponent, PageRibbonComponent, ActiveMenuDirective, ErrorComponent } from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        VisaoAppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-' }),
        VisaoSharedModule,
        VisaoCoreModule,
        VisaoHomeModule,
        VisaoAccountModule,
        VisaoAppMapModule,
        VisaoPageSetsModule,
        VisaoEntityModule
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthExpiredInterceptor,
            multi: true,
            deps: [StateStorageService, Injector]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: ErrorHandlerInterceptor,
            multi: true,
            deps: [JhiEventManager]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: NotificationInterceptor,
            multi: true,
            deps: [Injector]
        }
    ],
    bootstrap: [JhiMainComponent]
})
export class VisaoAppModule {}
