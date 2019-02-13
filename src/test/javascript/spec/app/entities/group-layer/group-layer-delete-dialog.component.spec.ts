/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { VisaoTestModule } from '../../../test.module';
import { GroupLayerDeleteDialogComponent } from 'app/entities/group-layer/group-layer-delete-dialog.component';
import { GroupLayerService } from 'app/entities/group-layer/group-layer.service';

describe('Component Tests', () => {
    describe('GroupLayer Management Delete Component', () => {
        let comp: GroupLayerDeleteDialogComponent;
        let fixture: ComponentFixture<GroupLayerDeleteDialogComponent>;
        let service: GroupLayerService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisaoTestModule],
                declarations: [GroupLayerDeleteDialogComponent]
            })
                .overrideTemplate(GroupLayerDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GroupLayerDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GroupLayerService);
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
