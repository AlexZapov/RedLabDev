package org.example;

import org.apache.tools.ant.taskdefs.EchoXML;
import org.junit.After;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class CellListTest {

    public static ContactInfoPage contactInfoPage;
    public static WebDriver driver;
    public int tableCount, tableCount2;

    @BeforeClass
    public static void setUp() {
        //определение пути до драйвера и его настройка
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
        //создание экземпляра драйвера
        driver = new ChromeDriver();
        contactInfoPage = new ContactInfoPage(driver);
        //окно разворачивается на полный экран
        driver.manage().window().maximize();
        //задержка на выполнение теста = 10 сек.
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //получение ссылки на страницу входа из файла настроек
        driver.get(ConfProperties.getProperty("startPage"));
    }

    @DataProvider(name = "test1")
    public static Object[][] contactInformation() {
        return new Object[][] {
                {"Max", "Osipov", "Family", "December 2, 1993", "1244 Zapov Street"},
                {"Evgeny", "Onegin", "Friends", "September 21, 1999", "Papina street 56"},
                {"Alex", "Ovechkin", "Coworkers", "October 4, 1981", "Vashington dist. 55/11"},
                {"John", "Coffee", "Businesses", "January 7, 1990", "Voodoo city, 1a"},
                {"Will", "Smith", "Contacts", "March 29, 1979", "Moscow, Tverskaya ul. 99"}
        };
    }

    @Test(dataProvider = "test1")
    public void testCellList(String name, String surname, String category, String birthDate, String adress) throws Exception {
        tableCount = contactInfoPage.getTableCount();
        contactInfoPage.createContact(name, surname, category, birthDate, adress);
        tableCount2 = contactInfoPage.getTableCount();
        if (tableCount2 - tableCount == 1) {
            System.out.println("The number of elements in the table has increased by: " + (tableCount2 - tableCount));
            while(contactInfoPage.getTableSize() != tableCount2){
                contactInfoPage.scrollTable("down");
            }
            if (!contactInfoPage.checkContactInfoInTable(name, surname, adress))
                throw new Exception("Table information insn`t correct");
            contactInfoPage.scrollTable("up");
        }
        else throw new Exception("Contact don`t create.");
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }
}
