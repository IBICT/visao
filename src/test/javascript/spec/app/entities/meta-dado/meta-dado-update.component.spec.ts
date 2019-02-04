/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { VisaoTestModule } from '../../../test.module';
import { MetaDadoUpdateComponent } from 'app/entities/meta-dado/meta-dado-update.component';
import { MetaDadoService } from 'app/entities/meta-dado/meta-dado.service';
import { MetaDado } from 'app/shared/model/meta-dado.model';

describe('Component Tests', () => {
    describe('MetaDado Management Update Component', () => {
        let comp: MetaDadoUpdateComponent;
        let fixture: ComponentFixture<MetaDadoUpdateComponent>;
        let service: MetaDadoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisaoTestModule],
                declarations: [MetaDadoUpdateComponent]
            })
                .overrideTemplate(MetaDadoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MetaDadoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MetaDadoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new MetaDado(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.metaDado = entity;
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
                    const entity = new MetaDado();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.metaDado = entity;
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
