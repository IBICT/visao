/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { VisaoTestModule } from '../../../test.module';
import { FilterUpdateComponent } from 'app/entities/filter/filter-update.component';
import { FilterService } from 'app/entities/filter/filter.service';
import { Filter } from 'app/shared/model/filter.model';

describe('Component Tests', () => {
    describe('Filter Management Update Component', () => {
        let comp: FilterUpdateComponent;
        let fixture: ComponentFixture<FilterUpdateComponent>;
        let service: FilterService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisaoTestModule],
                declarations: [FilterUpdateComponent]
            })
                .overrideTemplate(FilterUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FilterUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FilterService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Filter(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.filter = entity;
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
                    const entity = new Filter();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.filter = entity;
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
