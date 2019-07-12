/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { VisaoTestModule } from '../../../test.module';
import { GeographicFilterUpdateComponent } from 'app/entities/geographic-filter/geographic-filter-update.component';
import { GeographicFilterService } from 'app/entities/geographic-filter/geographic-filter.service';
import { GeographicFilter } from 'app/shared/model/geographic-filter.model';

describe('Component Tests', () => {
    describe('GeographicFilter Management Update Component', () => {
        let comp: GeographicFilterUpdateComponent;
        let fixture: ComponentFixture<GeographicFilterUpdateComponent>;
        let service: GeographicFilterService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisaoTestModule],
                declarations: [GeographicFilterUpdateComponent]
            })
                .overrideTemplate(GeographicFilterUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GeographicFilterUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GeographicFilterService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new GeographicFilter(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.geographicFilter = entity;
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
                    const entity = new GeographicFilter();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.geographicFilter = entity;
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
