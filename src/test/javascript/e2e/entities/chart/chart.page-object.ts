import { element, by, promise, ElementFinder } from 'protractor';

export class ChartComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-chart div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getText();
    }
}

export class ChartUpdatePage {
    pageTitle = element(by.id('jhi-chart-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nameInput = element(by.id('field_name'));
    externalInput = element(by.id('field_external'));
    htmlInput = element(by.id('field_html'));
    permissionSelect = element(by.id('field_permission'));
    ownerSelect = element(by.id('field_owner'));
    sharedSelect = element(by.id('field_shared'));

    getPageTitle() {
        return this.pageTitle.getText();
    }

    setNameInput(name): promise.Promise<void> {
        return this.nameInput.sendKeys(name);
    }

    getNameInput() {
        return this.nameInput.getAttribute('value');
    }

    getExternalInput() {
        return this.externalInput;
    }
    setHtmlInput(html): promise.Promise<void> {
        return this.htmlInput.sendKeys(html);
    }

    getHtmlInput() {
        return this.htmlInput.getAttribute('value');
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
