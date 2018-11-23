package lukuvinkkiKirjasto.domain;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class Stepdefs {
    
    WebDriver driver = new HtmlUnitDriver();
    String baseUrl = "http://localhost:4567";
    
    @Given("^mennaan alkusivulle$")
    public void mennaan_alkusivulle() throws Throwable {
        driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText("Täältä kirjojen listaukseen"));
        element.click();
    }
    
    @When("^kentat taytetaan tiedoilla ja painetaan lisaa$")
    public void kentat_taytetaan_tiedoilla_ja_painetaan_lisaa() throws Throwable {
        WebElement element = driver.findElement(By.name("nimi"));
        element.sendKeys("Testi");
        element = driver.findElement(By.name("ISBN"));
        element.sendKeys("97801346");
        element = driver.findElement(By.name("genre"));
        element.sendKeys("testinen");
        element = driver.findElement(By.name("pituus"));
        element.sendKeys("200");
        element = driver.findElement(By.name("tekija"));
        element.sendKeys("Testi testinen");
        element = driver.findElement(By.name("julkaisuVuosi"));
        element.sendKeys("2018");
       
        element.submit();
    }
    
    @Then("^Sovellus lisaa yhden kirjan$")
    public void sovellus_lisaa_yhden_kirjan() throws Throwable {
        pageHasContent("Testi, ISBN: 97801346, Kirjailija: Testi testinen, julkaisuvuosi: 2018, pituus: 200, genre: testinen, LUETTU");
   
    }
    
    private void pageHasContent(String content) {
        assertTrue(driver.getPageSource().contains(content));
    }
    
    @After
    public void tearDown() {
        driver.quit();
    }
    
}
