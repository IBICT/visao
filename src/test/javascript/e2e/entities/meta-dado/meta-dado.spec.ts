import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { MetaDadoComponentsPage, MetaDadoUpdatePage } from './meta-dado.page-object';

describe('MetaDado e2e test', () => {
    let navBarPage: NavBarPage;
    let metaDadoUpdatePage: MetaDadoUpdatePage;
    let metaDadoComponentsPage: MetaDadoComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load MetaDados', () => {
        navBarPage.goToEntity('meta-dado');
        metaDadoComponentsPage = new MetaDadoComponentsPage();
        expect(metaDadoComponentsPage.getTitle()).toMatch(/visaoApp.metaDado.home.title/);
    });

    it('should load create MetaDado page', () => {
        metaDadoComponentsPage.clickOnCreateButton();
        metaDadoUpdatePage = new MetaDadoUpdatePage();
        expect(metaDadoUpdatePage.getPageTitle()).toMatch(/visaoApp.metaDado.home.createOrEditLabel/);
        metaDadoUpdatePage.cancel();
    });

    it('should create and save MetaDados', () => {
        metaDadoComponentsPage.clickOnCreateButton();
        metaDadoUpdatePage.setNameInput('name');
        expect(metaDadoUpdatePage.getNameInput()).toMatch('name');
        metaDadoUpdatePage.setValueInput('value');
        expect(metaDadoUpdatePage.getValueInput()).toMatch('value');
        // metaDadoUpdatePage.nomeSelectLastOption();
        metaDadoUpdatePage.save();
        expect(metaDadoUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
