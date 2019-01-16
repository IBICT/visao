import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { NameComponentsPage, NameUpdatePage } from './name.page-object';

describe('Name e2e test', () => {
    let navBarPage: NavBarPage;
    let nameUpdatePage: NameUpdatePage;
    let nameComponentsPage: NameComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Names', () => {
        navBarPage.goToEntity('name');
        nameComponentsPage = new NameComponentsPage();
        expect(nameComponentsPage.getTitle()).toMatch(/visaoApp.name.home.title/);
    });

    it('should load create Name page', () => {
        nameComponentsPage.clickOnCreateButton();
        nameUpdatePage = new NameUpdatePage();
        expect(nameUpdatePage.getPageTitle()).toMatch(/visaoApp.name.home.createOrEditLabel/);
        nameUpdatePage.cancel();
    });

    it('should create and save Names', () => {
        nameComponentsPage.clickOnCreateButton();
        nameUpdatePage.setValueInput('value');
        expect(nameUpdatePage.getValueInput()).toMatch('value');
        nameUpdatePage.categorySelectLastOption();
        nameUpdatePage.save();
        expect(nameUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
