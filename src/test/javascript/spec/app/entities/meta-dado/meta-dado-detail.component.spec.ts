/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VisaoTestModule } from '../../../test.module';
import { MetaDadoDetailComponent } from 'app/entities/meta-dado/meta-dado-detail.component';
import { MetaDado } from 'app/shared/model/meta-dado.model';

describe('Component Tests', () => {
    describe('MetaDado Management Detail Component', () => {
        let comp: MetaDadoDetailComponent;
        let fixture: ComponentFixture<MetaDadoDetailComponent>;
        const route = ({ data: of({ metaDado: new MetaDado(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisaoTestModule],
                declarations: [MetaDadoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MetaDadoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MetaDadoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.metaDado).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
