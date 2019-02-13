/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { VisaoTestModule } from '../../../test.module';
import { GroupLayerUpdateComponent } from 'app/entities/group-layer/group-layer-update.component';
import { GroupLayerService } from 'app/entities/group-layer/group-layer.service';
import { GroupLayer } from 'app/shared/model/group-layer.model';

describe('Component Tests', () => {
    describe('GroupLayer Management Update Component', () => {
        let comp: GroupLayerUpdateComponent;
        let fixture: ComponentFixture<GroupLayerUpdateComponent>;
        let service: GroupLayerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisaoTestModule],
                declarations: [GroupLayerUpdateComponent]
            })
                .overrideTemplate(GroupLayerUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GroupLayerUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GroupLayerService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new GroupLayer(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.groupLayer = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new GroupLayer();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.groupLayer = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
