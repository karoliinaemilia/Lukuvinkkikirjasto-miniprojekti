package lukuvinkkiKirjasto.domain;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.util.*;
import static org.junit.Assert.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class Stepdefs {

    WebDriver driver = new HtmlUnitDriver();
    String baseUrl = "http://localhost:4567";

    @Given("^mennaan kirjojen alkusivulle$")
    public void mennaan_kirjojen_alkusivulle() throws Throwable {
        driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText("Kirjojen listaukseen"));
        element.click();
    }

    @When("^kentat taytetaan tiedoilla \"([^\"]*)\",\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\",\"([^\"]*)\" ,\"([^\"]*)\", \"([^\"]*)\"  ja painetaan lisaa$")
    public void kentat_taytetaan_tiedoilla_ja_painetaan_lisaa(String ISBN, String nimi, String genre, String pituus, String linkki, String tekija, String julkaisuVuosi) throws Throwable {
        fillBookForm(ISBN, nimi, genre, pituus, linkki, tekija, julkaisuVuosi);
    }

    @Then("^Sovellus on lisannyt kirjan tiedoilla ja linkilla \"([^\"]*)\",\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\",\"([^\"]*)\" ,\"([^\"]*)\", \"([^\"]*)\"$")
    public void sovellus_on_lisannyt_kirjan_tiedoilla_ja_linkilla(String ISBN, String nimi, String genre, String pituus, String linkki, String tekija, String julkaisuVuosi) throws Throwable {
        pageHasContent(nimi + ", ISBN: " + ISBN + ", Kirjailija: " + tekija + ", julkaisuvuosi: " + julkaisuVuosi + ", pituus: " + pituus + ", genre: " + genre + ", linkki: " + linkki);

    }

    @Then("^kirjaa linkilla \"([^\"]*)\",\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\",\"([^\"]*)\" ,\"([^\"]*)\", \"([^\"]*)\" ei lisata$")
    public void kirjaa_linkilla_ei_lisata(String ISBN, String nimi, String genre, String pituus, String linkki, String tekija, String julkaisuVuosi) throws Throwable {
        pageDoesNotHaveContent(nimi + ", ISBN: " + ISBN + ", Kirjailija: " + tekija + ", julkaisuvuosi: " + julkaisuVuosi + ", pituus: " + pituus + ", genre: " + genre + ", linkki: " + linkki);

    }

    @Then("^Sovellus on lisannyt kirjan tiedoilla ilman linkkia \"([^\"]*)\",\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$")
    public void sovellus_on_lisannyt_kirjan_tiedoilla_ilman_linkkia(String ISBN, String nimi, String genre, String pituus, String tekija, String julkaisuVuosi) throws Throwable {
        pageHasContent(nimi + ", ISBN: " + ISBN + ", Kirjailija: " + tekija + ", julkaisuvuosi: " + julkaisuVuosi + ", pituus: " + pituus + ", genre: " + genre);

    }

    @When("^painetaan kirjan poista nappia$")
    public void painetaan_kirjan_poista_nappia() throws Throwable {
        List<WebElement> elements = driver.findElements(By.id("nappi"));
        elements.get(elements.size() - 1).submit();
    }

    @Then("^kirja \"([^\"]*)\",\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\",\"([^\"]*)\" ,\"([^\"]*)\", \"([^\"]*)\" on poistunut$")
    public void kirja_on_poistunut(String ISBN, String nimi, String genre, String pituus, String linkki, String tekija, String julkaisuVuosi) throws Throwable {
        pageDoesNotHaveContent(nimi + ", ISBN: " + ISBN + ", Kirjailija: " + tekija + ", julkaisuvuosi: " + julkaisuVuosi + ", pituus: " + pituus + ", genre: " + genre + ", linkki: " + linkki);
    }
//artikkeli 

    @Given("^mennaan artikkelien alkusivulle$")
    public void mennaan_artikkelien_alkusivulle() throws Throwable {
        driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText("Artikkelien listaukseen"));
        element.click();
    }

    @When("^kentat taytetaan tiedoilla \"([^\"]*)\", \"([^\"]*)\",\"([^\"]*)\" ,\"([^\"]*)\", \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"  ja painetaan lisaa$")
    public void kentat_taytetaan_tiedoilla_ja_painetaan_lisaa(String nimi, String pituus, String linkki, String tekija, String lehti, String vuosi, String numero, String sivut) throws Throwable {
        fillArticleForm(nimi, pituus, linkki, tekija, lehti, vuosi, numero, sivut);
    }

    @Then("^Sovellus on lisannyt artikkelin tiedoilla ja linkilla \"([^\"]*)\", \"([^\"]*)\",\"([^\"]*)\" ,\"([^\"]*)\", \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$")
    public void sovellus_on_lisannyt_artikkelin_tiedoilla_ja_linkilla(String nimi, String pituus, String linkki, String tekija, String lehti, String vuosi, String numero, String sivut) throws Throwable {
        pageHasContent(nimi + ", Lehti: " + lehti + ", nro: " + numero + ", sivuja: " + sivut + ", tekijä: " + tekija + ", julkaistu: " + vuosi + ", linkki: " + linkki);
    }

    @Then("^Sovellus on lisannyt artikkelin tiedoilla ilman linkkia \"([^\"]*)\", \"([^\"]*)\",\"([^\"]*)\" ,\"([^\"]*)\", \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$")
    public void sovellus_on_lisannyt_artikkelin_tiedoilla_ilman_linkkia(String nimi, String pituus, String linkki, String tekija, String lehti, String vuosi, String numero, String sivut) throws Throwable {
        pageHasContent(nimi + ", Lehti: " + lehti + ", nro: " + numero + ", sivuja: " + sivut + ", tekijä: " + tekija + ", julkaistu: " + vuosi);
    }

    @When("^painetaan artikkelin poista nappia$")
    public void painetaan_artikkelin_poista_nappia() throws Throwable {
        List<WebElement> elements = driver.findElements(By.id("nappi"));
        elements.get(elements.size() - 1).submit();
    }

    @Then("^artikkeli \"([^\"]*)\", \"([^\"]*)\",\"([^\"]*)\" ,\"([^\"]*)\", \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\" on poistunut$")
    public void artikkeli_on_poistunut(String nimi, String pituus, String linkki, String tekija, String lehti, String vuosi, String numero, String sivut) throws Throwable {
        pageDoesNotHaveContent(nimi + ", Lehti: " + lehti + ", nro: " + numero + ", sivuja: " + sivut + ", tekijä: " + tekija + ", julkaistu: " + vuosi);
    }

    private void pageHasContent(String content) {
        assertTrue(driver.getPageSource().contains(content));
    }

    private void pageDoesNotHaveContent(String content) {
        assertFalse(driver.getPageSource().contains(content));
    }

    private void fillBookForm(String ISBN, String nimi, String genre, String pituus, String linkki, String tekija, String julkaisuVuosi) {
        WebElement element = driver.findElement(By.name("ISBN"));
        element.sendKeys(ISBN);
        element = driver.findElement(By.name("nimi"));
        element.sendKeys(nimi);
        element = driver.findElement(By.name("genre"));
        element.sendKeys(genre);
        element = driver.findElement(By.name("pituus"));
        element.sendKeys(pituus);
        element = driver.findElement(By.name("linkki"));
        element.sendKeys(linkki);
        element = driver.findElement(By.name("tekija"));
        element.sendKeys(tekija);
        element = driver.findElement(By.name("julkaisuVuosi"));
        element.sendKeys(julkaisuVuosi);

        element.submit();
    }

    private void fillArticleForm(String nimi, String pituus, String linkki, String tekija, String lehti, String vuosi, String numero, String sivut) {

        WebElement element = driver.findElement(By.name("nimi"));
        element.sendKeys(nimi);
        element = driver.findElement(By.name("pituus"));
        element.sendKeys(pituus);
        element = driver.findElement(By.name("linkki"));
        element.sendKeys(linkki);
        element = driver.findElement(By.name("tekija"));
        element.sendKeys(tekija);
        element = driver.findElement(By.name("julkaisuLehti"));
        element.sendKeys(lehti);
        element = driver.findElement(By.name("julkaisuVuosi"));
        element.sendKeys(vuosi);
        element = driver.findElement(By.name("numero"));
        element.sendKeys(numero);
        element = driver.findElement(By.name("sivut"));
        element.sendKeys(sivut);

        element.submit();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

}
