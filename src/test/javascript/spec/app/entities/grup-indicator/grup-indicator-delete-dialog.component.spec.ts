/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { VisaoTestModule } from '../../../test.module';
import { GrupIndicatorDeleteDialogComponent } from 'app/entities/grup-indicator/grup-indicator-delete-dialog.component';
import { GrupIndicatorService } from 'app/entities/grup-indicator/grup-indicator.service';

describe('Component Tests', () => {
    describe('GrupIndicator Management Delete Component', () => {
        let comp: GrupIndicatorDeleteDialogComponent;
        let fixture: ComponentFixture<GrupIndicatorDeleteDialogComponent>;
        let service: GrupIndicatorService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisaoTestModule],
                declarations: [GrupIndicatorDeleteDialogComponent]
            })
                .overrideTemplate(GrupIndicatorDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GrupIndicatorDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GrupIndicatorService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
