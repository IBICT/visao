/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { VisaoTestModule } from '../../../test.module';
import { GrupCategoryDeleteDialogComponent } from 'app/entities/grup-category/grup-category-delete-dialog.component';
import { GrupCategoryService } from 'app/entities/grup-category/grup-category.service';

describe('Component Tests', () => {
    describe('GrupCategory Management Delete Component', () => {
        let comp: GrupCategoryDeleteDialogComponent;
        let fixture: ComponentFixture<GrupCategoryDeleteDialogComponent>;
        let service: GrupCategoryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisaoTestModule],
                declarations: [GrupCategoryDeleteDialogComponent]
            })
                .overrideTemplate(GrupCategoryDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GrupCategoryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GrupCategoryService);
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
