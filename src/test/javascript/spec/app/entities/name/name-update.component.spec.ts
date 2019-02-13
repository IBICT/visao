/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { VisaoTestModule } from '../../../test.module';
import { NameUpdateComponent } from 'app/entities/name/name-update.component';
import { NameService } from 'app/entities/name/name.service';
import { Name } from 'app/shared/model/name.model';

describe('Component Tests', () => {
    describe('Name Management Update Component', () => {
        let comp: NameUpdateComponent;
        let fixture: ComponentFixture<NameUpdateComponent>;
        let service: NameService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisaoTestModule],
                declarations: [NameUpdateComponent]
            })
                .overrideTemplate(NameUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(NameUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NameService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Name(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.name = entity;
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
                    const entity = new Name();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.name = entity;
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
