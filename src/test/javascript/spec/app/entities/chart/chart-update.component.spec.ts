/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { VisaoTestModule } from '../../../test.module';
import { ChartUpdateComponent } from 'app/entities/chart/chart-update.component';
import { ChartService } from 'app/entities/chart/chart.service';
import { Chart } from 'app/shared/model/chart.model';

describe('Component Tests', () => {
    describe('Chart Management Update Component', () => {
        let comp: ChartUpdateComponent;
        let fixture: ComponentFixture<ChartUpdateComponent>;
        let service: ChartService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisaoTestModule],
                declarations: [ChartUpdateComponent]
            })
                .overrideTemplate(ChartUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ChartUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChartService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Chart(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.chart = entity;
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
                    const entity = new Chart();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.chart = entity;
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
