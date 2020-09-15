package org.example;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class ContactInfoPage {
    /**
     * конструктор класса, занимающийся инициализацией полей класса
     */
    public WebDriver driver;

    public ContactInfoPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(css = "td.middleCenter tr:nth-child(2) input")
    private WebElement firstName;

    @FindBy(css = "td.middleCenter tr:nth-child(3) input")
    private WebElement lastName;

    @FindBy(css = "tr.middle select")
    private WebElement category;

    @FindBy(css = "td.middleCenter tr:nth-child(5) input")
    private WebElement birthday;

    @FindBy(css = "td.middleCenter textarea")
    private WebElement address;

    @FindBy(css = "tr.middle button:nth-child(2)")
    private WebElement btnCreate;

    @FindBy(css = "table > tbody > tr:nth-child(1) > td:nth-child(1) > div.GNHGC04CJJ")
    private WebElement table;

    @FindBy(css = "table > tbody > tr:nth-child(1) > td:nth-child(1) > div.GNHGC04CJJ > div > div > div:nth-child(1) > div")
    private List<WebElement> tableElements;

    @FindBy(css = "table > tbody > tr:nth-child(1) > td:nth-child(1) > div.GNHGC04CJJ > div > div > div:nth-child(1) > div:last-child")
    private WebElement lastTableElement;

    @FindBy(css = "table div.gwt-HTML")
    private WebElement tableLegend;

    /**
     * Метод ввода имени
     * @param name
     */
    private void setFirstName(String name) {
        firstName.clear();
        firstName.sendKeys(name);
    }

    /**
     * Метод ввода фамилии
     * @param surname
     */
    private void setLastName(String surname) {
        lastName.clear();
        lastName.sendKeys(surname);
    }

    /**
     * Метод для выбора значения в комбобоксе
     * @param combobox
     */
    private void setCategory(String combobox) {
        category.click();
        Select select = new Select(category);
        select.selectByValue(combobox);
    }

    /**
     * Метод для ввода даты рождения
     * @param date Дата рождения в формате "December 2, 1993"
     */
    private void setBirthday(String date) {
        birthday.clear();
        birthday.sendKeys(date);
        birthday.sendKeys(Keys.ENTER);
    }

    /**
     * Метод ввода адреса
     * @param adress
     */
    private void setAddress(String adress) {
        address.clear();
        address.sendKeys(adress);
    }

    /**
     * Метод нажатия кнопки "Create Contact"
     */
    private void clickCreateButton() {
        btnCreate.click();
    }

    /**
     * Метод получения легенды таблицы
     */
    private String getTableLegend() {
        return tableLegend.getText();
    }

    private String getLastTableElement() {
        return lastTableElement.getText();
    }

    /**
     * метод для ввода контактной информации
     */
    public void createContact(String name, String surname, String combobox, String birthDate, String adress) {
        System.out.println("Create contact with params: " + name + " " + surname + " " + combobox + " " + birthDate + " " + adress);
        setFirstName(name);
        setLastName(surname);
        setCategory(combobox);
        setBirthday(birthDate);
        setAddress(adress);
        clickCreateButton();
    }

    /**
     * Метод получения количества записей в таблице
     * @return
     */
    public Integer getTableCount() {
        String[] count = getTableLegend().replace(" ", "").split(":");
        return Integer.parseInt(count[1]);
    }

    public void scrollTable(String position) {
        EventFiringWebDriver eventFiringWebDriver = new EventFiringWebDriver(driver);
        if (position.equals("down"))
        eventFiringWebDriver.executeScript("document.querySelector('table > tbody > tr:nth-child(1) > td:nth-child(1) > div.GNHGC04CJJ').scrollTop = 20000");
        else eventFiringWebDriver.executeScript("document.querySelector('table > tbody > tr:nth-child(1) > td:nth-child(1) > div.GNHGC04CJJ').scrollTop = -20000");
    }

    public Integer getTableSize() {
        return tableElements.size();
    }

    public Boolean checkContactInfoInTable(String name, String surname, String adress) {
        String tableInfo = getLastTableElement();
        System.out.println("Check contact info: Expected: \n" + tableInfo + "\nActual: \n" + name + " " + surname + "\n" + adress);
        if (tableInfo.equals(name + " " + surname + "\n" + adress))
            return true;
        else return false;
    }
}
