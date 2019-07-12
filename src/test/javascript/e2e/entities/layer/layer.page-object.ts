import { element, by, promise, ElementFinder } from 'protractor';

export class LayerComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-layer div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getText();
    }
}

export class LayerUpdatePage {
    pageTitle = element(by.id('jhi-layer-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nameInput = element(by.id('field_name'));
    geoJsonInput = element(by.id('field_geoJson'));
    typeSelect = element(by.id('field_type'));
    descriptionInput = element(by.id('field_description'));
    dateInput = element(by.id('field_date'));
    sourceInput = element(by.id('field_source'));
    dateChangeInput = element(by.id('field_dateChange'));
    noteInput = element(by.id('field_note'));
    iconSelect = element(by.id('field_icon'));
    groupSelect = element(by.id('field_group'));

    getPageTitle() {
        return this.pageTitle.getText();
    }

    setNameInput(name): promise.Promise<void> {
        return this.nameInput.sendKeys(name);
    }

    getNameInput() {
        return this.nameInput.getAttribute('value');
    }

    setGeoJsonInput(geoJson): promise.Promise<void> {
        return this.geoJsonInput.sendKeys(geoJson);
    }

    getGeoJsonInput() {
        return this.geoJsonInput.getAttribute('value');
    }

    setTypeSelect(type): promise.Promise<void> {
        return this.typeSelect.sendKeys(type);
    }

    getTypeSelect() {
        return this.typeSelect.element(by.css('option:checked')).getText();
    }

    typeSelectLastOption(): promise.Promise<void> {
        return this.typeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }
    setDescriptionInput(description): promise.Promise<void> {
        return this.descriptionInput.sendKeys(description);
    }

    getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
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

    iconSelectLastOption(): promise.Promise<void> {
        return this.iconSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    iconSelectOption(option): promise.Promise<void> {
        return this.iconSelect.sendKeys(option);
    }

    getIconSelect(): ElementFinder {
        return this.iconSelect;
    }

    getIconSelectedOption() {
        return this.iconSelect.element(by.css('option:checked')).getText();
    }

    groupSelectLastOption(): promise.Promise<void> {
        return this.groupSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    groupSelectOption(option): promise.Promise<void> {
        return this.groupSelect.sendKeys(option);
    }

    getGroupSelect(): ElementFinder {
        return this.groupSelect;
    }

    getGroupSelectedOption() {
        return this.groupSelect.element(by.css('option:checked')).getText();
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
