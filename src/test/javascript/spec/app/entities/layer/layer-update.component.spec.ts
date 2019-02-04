/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { VisaoTestModule } from '../../../test.module';
import { LayerUpdateComponent } from 'app/entities/layer/layer-update.component';
import { LayerService } from 'app/entities/layer/layer.service';
import { Layer } from 'app/shared/model/layer.model';

describe('Component Tests', () => {
    describe('Layer Management Update Component', () => {
        let comp: LayerUpdateComponent;
        let fixture: ComponentFixture<LayerUpdateComponent>;
        let service: LayerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisaoTestModule],
                declarations: [LayerUpdateComponent]
            })
                .overrideTemplate(LayerUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LayerUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LayerService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Layer(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.layer = entity;
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
                    const entity = new Layer();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.layer = entity;
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
