import { element, by, promise, ElementFinder } from 'protractor';

export class GeographicFilterComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-geographic-filter div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getText();
    }
}

export class GeographicFilterUpdatePage {
    pageTitle = element(by.id('jhi-geographic-filter-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nameInput = element(by.id('field_name'));
    activeInput = element(by.id('field_active'));
    descriptionInput = element(by.id('field_description'));
    keyWordInput = element(by.id('field_keyWord'));
    dateInput = element(by.id('field_date'));
    sourceInput = element(by.id('field_source'));
    dateChangeInput = element(by.id('field_dateChange'));
    noteInput = element(by.id('field_note'));
    cidadePoloSelect = element(by.id('field_cidadePolo'));
    ownerSelect = element(by.id('field_owner'));
    regionSelect = element(by.id('field_region'));
    categorySelect = element(by.id('field_category'));

    getPageTitle() {
        return this.pageTitle.getText();
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

    cidadePoloSelectLastOption(): promise.Promise<void> {
        return this.cidadePoloSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    cidadePoloSelectOption(option): promise.Promise<void> {
        return this.cidadePoloSelect.sendKeys(option);
    }

    getCidadePoloSelect(): ElementFinder {
        return this.cidadePoloSelect;
    }

    getCidadePoloSelectedOption() {
        return this.cidadePoloSelect.element(by.css('option:checked')).getText();
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
