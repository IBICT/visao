/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { VisaoTestModule } from '../../../test.module';
import { FilterDeleteDialogComponent } from 'app/entities/filter/filter-delete-dialog.component';
import { FilterService } from 'app/entities/filter/filter.service';

describe('Component Tests', () => {
    describe('Filter Management Delete Component', () => {
        let comp: FilterDeleteDialogComponent;
        let fixture: ComponentFixture<FilterDeleteDialogComponent>;
        let service: FilterService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisaoTestModule],
                declarations: [FilterDeleteDialogComponent]
            })
                .overrideTemplate(FilterDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FilterDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FilterService);
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
