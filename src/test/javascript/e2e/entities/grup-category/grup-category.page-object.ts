import { element, by, promise, ElementFinder } from 'protractor';

export class GrupCategoryComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-grup-category div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class GrupCategoryUpdatePage {
    pageTitle = element(by.id('jhi-grup-category-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    logoInput = element(by.id('file_logo'));
    sobreInput = element(by.id('field_sobre'));
    permissionSelect = element(by.id('field_permission'));
    ownerSelect = element(by.id('field_owner'));
    categorySelect = element(by.id('field_category'));
    sharedSelect = element(by.id('field_shared'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setLogoInput(logo): promise.Promise<void> {
        return this.logoInput.sendKeys(logo);
    }

    getLogoInput() {
        return this.logoInput.getAttribute('value');
    }

    setSobreInput(sobre): promise.Promise<void> {
        return this.sobreInput.sendKeys(sobre);
    }

    getSobreInput() {
        return this.sobreInput.getAttribute('value');
    }

    setPermissionSelect(permission): promise.Promise<void> {
        return this.permissionSelect.sendKeys(permission);
    }

    getPermissionSelect() {
        return this.permissionSelect.element(by.css('option:checked')).getText();
    }

    permissionSelectLastOption(): promise.Promise<void> {
        return this.permissionSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }
    ownerSelectLastOption(): promise.Promise<void> {
        return this.ownerSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    ownerSelectOption(option): promise.Promise<void> {
        return this.ownerSelect.sendKeys(option);
    }

    getOwnerSelect(): ElementFinder {
        return this.ownerSelect;
    }

    getOwnerSelectedOption() {
        return this.ownerSelect.element(by.css('option:checked')).getText();
    }

    categorySelectLastOption(): promise.Promise<void> {
        return this.categorySelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    categorySelectOption(option): promise.Promise<void> {
        return this.categorySelect.sendKeys(option);
    }

    getCategorySelect(): ElementFinder {
        return this.categorySelect;
    }

    getCategorySelectedOption() {
        return this.categorySelect.element(by.css('option:checked')).getText();
    }

    sharedSelectLastOption(): promise.Promise<void> {
        return this.sharedSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    sharedSelectOption(option): promise.Promise<void> {
        return this.sharedSelect.sendKeys(option);
    }

    getSharedSelect(): ElementFinder {
        return this.sharedSelect;
    }

    getSharedSelectedOption() {
        return this.sharedSelect.element(by.css('option:checked')).getText();
    }

    save(): promise.Promise<void> {
        return this.saveButton.click();
    }

    cancel(): promise.Promise<void> {
        return this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}
