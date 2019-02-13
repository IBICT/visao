import { browser, protractor } from 'protractor';
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
        nameUpdatePage
            .getActiveInput()
            .isSelected()
            .then(selected => {
                if (selected) {
                    nameUpdatePage.getActiveInput().click();
                    expect(nameUpdatePage.getActiveInput().isSelected()).toBeFalsy();
                } else {
                    nameUpdatePage.getActiveInput().click();
                    expect(nameUpdatePage.getActiveInput().isSelected()).toBeTruthy();
                }
            });
        nameUpdatePage.setDescriptionInput('description');
        expect(nameUpdatePage.getDescriptionInput()).toMatch('description');
        nameUpdatePage.setKeyWordInput('keyWord');
        expect(nameUpdatePage.getKeyWordInput()).toMatch('keyWord');
        nameUpdatePage.setDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(nameUpdatePage.getDateInput()).toContain('2001-01-01T02:30');
        nameUpdatePage.setSourceInput('source');
        expect(nameUpdatePage.getSourceInput()).toMatch('source');
        nameUpdatePage.setDateChangeInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(nameUpdatePage.getDateChangeInput()).toContain('2001-01-01T02:30');
        nameUpdatePage.setNoteInput('note');
        expect(nameUpdatePage.getNoteInput()).toMatch('note');
        nameUpdatePage.categorySelectLastOption();
        nameUpdatePage.userSelectLastOption();
        nameUpdatePage.save();
        expect(nameUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
