/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { VisaoTestModule } from '../../../test.module';
import { MarkerIconUpdateComponent } from 'app/entities/marker-icon/marker-icon-update.component';
import { MarkerIconService } from 'app/entities/marker-icon/marker-icon.service';
import { MarkerIcon } from 'app/shared/model/marker-icon.model';

describe('Component Tests', () => {
    describe('MarkerIcon Management Update Component', () => {
        let comp: MarkerIconUpdateComponent;
        let fixture: ComponentFixture<MarkerIconUpdateComponent>;
        let service: MarkerIconService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisaoTestModule],
                declarations: [MarkerIconUpdateComponent]
            })
                .overrideTemplate(MarkerIconUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MarkerIconUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MarkerIconService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new MarkerIcon(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.markerIcon = entity;
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
                    const entity = new MarkerIcon();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.markerIcon = entity;
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
