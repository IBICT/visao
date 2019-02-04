import { element, by, promise, ElementFinder } from 'protractor';

export class FilterComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-filter div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class FilterUpdatePage {
    pageTitle = element(by.id('jhi-filter-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nameInput = element(by.id('field_name'));
    activeInput = element(by.id('field_active'));
    descriptionInput = element(by.id('field_description'));
    keyWordInput = element(by.id('field_keyWord'));
    dateInput = element(by.id('field_date'));
    producerInput = element(by.id('field_producer'));
    sourceInput = element(by.id('field_source'));
    dateChangeInput = element(by.id('field_dateChange'));
    noteInput = element(by.id('field_note'));
    userSelect = element(by.id('field_user'));
    regionSelect = element(by.id('field_region'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setNameInput(name): promise.Promise<void> {
        return this.nameInput.sendKeys(name);
    }

    getNameInput() {
        return this.nameInput.getAttribute('value');
    }

    getActiveInput() {
        return this.activeInput;
    }
    setDescriptionInput(description): promise.Promise<void> {
        return this.descriptionInput.sendKeys(description);
    }

    getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
    }

    setKeyWordInput(keyWord): promise.Promise<void> {
        return this.keyWordInput.sendKeys(keyWord);
    }

    getKeyWordInput() {
        return this.keyWordInput.getAttribute('value');
    }

    setDateInput(date): promise.Promise<void> {
        return this.dateInput.sendKeys(date);
    }

    getDateInput() {
        return this.dateInput.getAttribute('value');
    }

    setProducerInput(producer): promise.Promise<void> {
        return this.producerInput.sendKeys(producer);
    }

    getProducerInput() {
        return this.producerInput.getAttribute('value');
    }

    setSourceInput(source): promise.Promise<void> {
        return this.sourceInput.sendKeys(source);
    }

    getSourceInput() {
        return this.sourceInput.getAttribute('value');
    }

    setDateChangeInput(dateChange): promise.Promise<void> {
        return this.dateChangeInput.sendKeys(dateChange);
    }

    getDateChangeInput() {
        return this.dateChangeInput.getAttribute('value');
    }

    setNoteInput(note): promise.Promise<void> {
        return this.noteInput.sendKeys(note);
    }

    getNoteInput() {
        return this.noteInput.getAttribute('value');
    }

    userSelectLastOption(): promise.Promise<void> {
        return this.userSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    userSelectOption(option): promise.Promise<void> {
        return this.userSelect.sendKeys(option);
    }

    getUserSelect(): ElementFinder {
        return this.userSelect;
    }

    getUserSelectedOption() {
        return this.userSelect.element(by.css('option:checked')).getText();
    }

    regionSelectLastOption(): promise.Promise<void> {
        return this.regionSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    regionSelectOption(option): promise.Promise<void> {
        return this.regionSelect.sendKeys(option);
    }

    getRegionSelect(): ElementFinder {
        return this.regionSelect;
    }

    getRegionSelectedOption() {
        return this.regionSelect.element(by.css('option:checked')).getText();
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
