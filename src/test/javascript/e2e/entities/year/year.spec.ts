import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { YearComponentsPage, YearUpdatePage } from './year.page-object';

describe('Year e2e test', () => {
    let navBarPage: NavBarPage;
    let yearUpdatePage: YearUpdatePage;
    let yearComponentsPage: YearComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Years', () => {
        navBarPage.goToEntity('year');
        yearComponentsPage = new YearComponentsPage();
        expect(yearComponentsPage.getTitle()).toMatch(/Years/);
    });

    it('should load create Year page', () => {
        yearComponentsPage.clickOnCreateButton();
        yearUpdatePage = new YearUpdatePage();
        expect(yearUpdatePage.getPageTitle()).toMatch(/Create or edit a Year/);
        yearUpdatePage.cancel();
    });

    it('should create and save Years', () => {
        yearComponentsPage.clickOnCreateButton();
        yearUpdatePage.setDateInput('date');
        expect(yearUpdatePage.getDateInput()).toMatch('date');
        yearUpdatePage.save();
        expect(yearUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
