/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { VisaoTestModule } from '../../../test.module';
import { YearUpdateComponent } from 'app/entities/year/year-update.component';
import { YearService } from 'app/entities/year/year.service';
import { Year } from 'app/shared/model/year.model';

describe('Component Tests', () => {
    describe('Year Management Update Component', () => {
        let comp: YearUpdateComponent;
        let fixture: ComponentFixture<YearUpdateComponent>;
        let service: YearService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisaoTestModule],
                declarations: [YearUpdateComponent]
            })
                .overrideTemplate(YearUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(YearUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(YearService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Year(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.year = entity;
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
                    const entity = new Year();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.year = entity;
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
