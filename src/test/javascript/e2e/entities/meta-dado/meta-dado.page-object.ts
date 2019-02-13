import { element, by, promise, ElementFinder } from 'protractor';

export class MetaDadoComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-meta-dado div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class MetaDadoUpdatePage {
    pageTitle = element(by.id('jhi-meta-dado-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nameInput = element(by.id('field_name'));
    valueInput = element(by.id('field_value'));
    nomeSelect = element(by.id('field_nome'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setNameInput(name): promise.Promise<void> {
        return this.nameInput.sendKeys(name);
    }

    getNameInput() {
        return this.nameInput.getAttribute('value');
    }

    setValueInput(value): promise.Promise<void> {
        return this.valueInput.sendKeys(value);
    }

    getValueInput() {
        return this.valueInput.getAttribute('value');
    }

    nomeSelectLastOption(): promise.Promise<void> {
        return this.nomeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    nomeSelectOption(option): promise.Promise<void> {
        return this.nomeSelect.sendKeys(option);
    }

    getNomeSelect(): ElementFinder {
        return this.nomeSelect;
    }

    getNomeSelectedOption() {
        return this.nomeSelect.element(by.css('option:checked')).getText();
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
