import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { GroupLayerComponentsPage, GroupLayerUpdatePage } from './group-layer.page-object';

describe('GroupLayer e2e test', () => {
    let navBarPage: NavBarPage;
    let groupLayerUpdatePage: GroupLayerUpdatePage;
    let groupLayerComponentsPage: GroupLayerComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load GroupLayers', () => {
        navBarPage.goToEntity('group-layer');
        groupLayerComponentsPage = new GroupLayerComponentsPage();
        expect(groupLayerComponentsPage.getTitle()).toMatch(/Group Layers/);
    });

    it('should load create GroupLayer page', () => {
        groupLayerComponentsPage.clickOnCreateButton();
        groupLayerUpdatePage = new GroupLayerUpdatePage();
        expect(groupLayerUpdatePage.getPageTitle()).toMatch(/Create or edit a Group Layer/);
        groupLayerUpdatePage.cancel();
    });

    it('should create and save GroupLayers', () => {
        groupLayerComponentsPage.clickOnCreateButton();
        groupLayerUpdatePage.setNameInput('name');
        expect(groupLayerUpdatePage.getNameInput()).toMatch('name');
        groupLayerUpdatePage
            .getActiveInput()
            .isSelected()
            .then(selected => {
                if (selected) {
                    groupLayerUpdatePage.getActiveInput().click();
                    expect(groupLayerUpdatePage.getActiveInput().isSelected()).toBeFalsy();
                } else {
                    groupLayerUpdatePage.getActiveInput().click();
                    expect(groupLayerUpdatePage.getActiveInput().isSelected()).toBeTruthy();
                }
            });
        groupLayerUpdatePage.setKeyWordInput('keyWord');
        expect(groupLayerUpdatePage.getKeyWordInput()).toMatch('keyWord');
        groupLayerUpdatePage.ownerSelectLastOption();
        // groupLayerUpdatePage.categorySelectLastOption();
        groupLayerUpdatePage.save();
        expect(groupLayerUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
