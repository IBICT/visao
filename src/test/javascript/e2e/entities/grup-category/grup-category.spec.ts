import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { GrupCategoryComponentsPage, GrupCategoryUpdatePage } from './grup-category.page-object';
import * as path from 'path';

describe('GrupCategory e2e test', () => {
    let navBarPage: NavBarPage;
    let grupCategoryUpdatePage: GrupCategoryUpdatePage;
    let grupCategoryComponentsPage: GrupCategoryComponentsPage;
    const fileToUpload = '../../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load GrupCategories', () => {
        navBarPage.goToEntity('grup-category');
        grupCategoryComponentsPage = new GrupCategoryComponentsPage();
        expect(grupCategoryComponentsPage.getTitle()).toMatch(/visaoApp.grupCategory.home.title/);
    });

    it('should load create GrupCategory page', () => {
        grupCategoryComponentsPage.clickOnCreateButton();
        grupCategoryUpdatePage = new GrupCategoryUpdatePage();
        expect(grupCategoryUpdatePage.getPageTitle()).toMatch(/visaoApp.grupCategory.home.createOrEditLabel/);
        grupCategoryUpdatePage.cancel();
    });

    it('should create and save GrupCategories', () => {
        grupCategoryComponentsPage.clickOnCreateButton();
        grupCategoryUpdatePage.setLogoInput(absolutePath);
        grupCategoryUpdatePage.setSobreInput('sobre');
        expect(grupCategoryUpdatePage.getSobreInput()).toMatch('sobre');
        grupCategoryUpdatePage.permissionSelectLastOption();
        grupCategoryUpdatePage.ownerSelectLastOption();
        // grupCategoryUpdatePage.categorySelectLastOption();
        // grupCategoryUpdatePage.sharedSelectLastOption();
        grupCategoryUpdatePage.save();
        expect(grupCategoryUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
