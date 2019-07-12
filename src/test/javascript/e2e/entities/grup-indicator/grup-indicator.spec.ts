import { browser, protractor } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { GrupIndicatorComponentsPage, GrupIndicatorUpdatePage } from './grup-indicator.page-object';

describe('GrupIndicator e2e test', () => {
    let navBarPage: NavBarPage;
    let grupIndicatorUpdatePage: GrupIndicatorUpdatePage;
    let grupIndicatorComponentsPage: GrupIndicatorComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load GrupIndicators', () => {
        navBarPage.goToEntity('grup-indicator');
        grupIndicatorComponentsPage = new GrupIndicatorComponentsPage();
        expect(grupIndicatorComponentsPage.getTitle()).toMatch(/Grup Indicators/);
    });

    it('should load create GrupIndicator page', () => {
        grupIndicatorComponentsPage.clickOnCreateButton();
        grupIndicatorUpdatePage = new GrupIndicatorUpdatePage();
        expect(grupIndicatorUpdatePage.getPageTitle()).toMatch(/Create or edit a Grup Indicator/);
        grupIndicatorUpdatePage.cancel();
    });

    it('should create and save GrupIndicators', () => {
        grupIndicatorComponentsPage.clickOnCreateButton();
        grupIndicatorUpdatePage.setNameInput('name');
        expect(grupIndicatorUpdatePage.getNameInput()).toMatch('name');
        grupIndicatorUpdatePage
            .getActiveInput()
            .isSelected()
            .then(selected => {
                if (selected) {
                    grupIndicatorUpdatePage.getActiveInput().click();
                    expect(grupIndicatorUpdatePage.getActiveInput().isSelected()).toBeFalsy();
                } else {
                    grupIndicatorUpdatePage.getActiveInput().click();
                    expect(grupIndicatorUpdatePage.getActiveInput().isSelected()).toBeTruthy();
                }
            });
        grupIndicatorUpdatePage.setDescriptionInput('description');
        expect(grupIndicatorUpdatePage.getDescriptionInput()).toMatch('description');
        grupIndicatorUpdatePage.setKeyWordInput('keyWord');
        expect(grupIndicatorUpdatePage.getKeyWordInput()).toMatch('keyWord');
        grupIndicatorUpdatePage.setDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(grupIndicatorUpdatePage.getDateInput()).toContain('2001-01-01T02:30');
        grupIndicatorUpdatePage.setSourceInput('source');
        expect(grupIndicatorUpdatePage.getSourceInput()).toMatch('source');
        grupIndicatorUpdatePage.setDateChangeInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(grupIndicatorUpdatePage.getDateChangeInput()).toContain('2001-01-01T02:30');
        grupIndicatorUpdatePage.setNoteInput('note');
        expect(grupIndicatorUpdatePage.getNoteInput()).toMatch('note');
        grupIndicatorUpdatePage.ownerSelectLastOption();
        grupIndicatorUpdatePage.typePresentationSelectLastOption();
        // grupIndicatorUpdatePage.categorySelectLastOption();
        grupIndicatorUpdatePage.save();
        expect(grupIndicatorUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
