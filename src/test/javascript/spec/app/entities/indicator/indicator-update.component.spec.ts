/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { VisaoTestModule } from '../../../test.module';
import { IndicatorUpdateComponent } from 'app/entities/indicator/indicator-update.component';
import { IndicatorService } from 'app/entities/indicator/indicator.service';
import { Indicator } from 'app/shared/model/indicator.model';

describe('Component Tests', () => {
    describe('Indicator Management Update Component', () => {
        let comp: IndicatorUpdateComponent;
        let fixture: ComponentFixture<IndicatorUpdateComponent>;
        let service: IndicatorService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisaoTestModule],
                declarations: [IndicatorUpdateComponent]
            })
                .overrideTemplate(IndicatorUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(IndicatorUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IndicatorService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Indicator(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.indicator = entity;
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
                    const entity = new Indicator();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.indicator = entity;
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
