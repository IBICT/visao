import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { TypePresentationComponentsPage, TypePresentationUpdatePage } from './type-presentation.page-object';

describe('TypePresentation e2e test', () => {
    let navBarPage: NavBarPage;
    let typePresentationUpdatePage: TypePresentationUpdatePage;
    let typePresentationComponentsPage: TypePresentationComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load TypePresentations', () => {
        navBarPage.goToEntity('type-presentation');
        typePresentationComponentsPage = new TypePresentationComponentsPage();
        expect(typePresentationComponentsPage.getTitle()).toMatch(/visaoApp.typePresentation.home.title/);
    });

    it('should load create TypePresentation page', () => {
        typePresentationComponentsPage.clickOnCreateButton();
        typePresentationUpdatePage = new TypePresentationUpdatePage();
        expect(typePresentationUpdatePage.getPageTitle()).toMatch(/visaoApp.typePresentation.home.createOrEditLabel/);
        typePresentationUpdatePage.cancel();
    });

    it('should create and save TypePresentations', () => {
        typePresentationComponentsPage.clickOnCreateButton();
        typePresentationUpdatePage.setNameInput('name');
        expect(typePresentationUpdatePage.getNameInput()).toMatch('name');
        typePresentationUpdatePage.setDisplayInput('display');
        expect(typePresentationUpdatePage.getDisplayInput()).toMatch('display');
        typePresentationUpdatePage.setDescriptionInput('description');
        expect(typePresentationUpdatePage.getDescriptionInput()).toMatch('description');
        typePresentationUpdatePage.save();
        expect(typePresentationUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
