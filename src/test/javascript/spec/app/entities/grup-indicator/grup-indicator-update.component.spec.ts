/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { VisaoTestModule } from '../../../test.module';
import { GrupIndicatorUpdateComponent } from 'app/entities/grup-indicator/grup-indicator-update.component';
import { GrupIndicatorService } from 'app/entities/grup-indicator/grup-indicator.service';
import { GrupIndicator } from 'app/shared/model/grup-indicator.model';

describe('Component Tests', () => {
    describe('GrupIndicator Management Update Component', () => {
        let comp: GrupIndicatorUpdateComponent;
        let fixture: ComponentFixture<GrupIndicatorUpdateComponent>;
        let service: GrupIndicatorService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisaoTestModule],
                declarations: [GrupIndicatorUpdateComponent]
            })
                .overrideTemplate(GrupIndicatorUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GrupIndicatorUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GrupIndicatorService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new GrupIndicator(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.grupIndicator = entity;
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
                    const entity = new GrupIndicator();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.grupIndicator = entity;
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
