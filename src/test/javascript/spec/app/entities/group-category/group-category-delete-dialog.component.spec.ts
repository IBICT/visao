/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { VisaoTestModule } from '../../../test.module';
import { GroupCategoryDeleteDialogComponent } from 'app/entities/group-category/group-category-delete-dialog.component';
import { GroupCategoryService } from 'app/entities/group-category/group-category.service';

describe('Component Tests', () => {
    describe('GroupCategory Management Delete Component', () => {
        let comp: GroupCategoryDeleteDialogComponent;
        let fixture: ComponentFixture<GroupCategoryDeleteDialogComponent>;
        let service: GroupCategoryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisaoTestModule],
                declarations: [GroupCategoryDeleteDialogComponent]
            })
                .overrideTemplate(GroupCategoryDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GroupCategoryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GroupCategoryService);
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
