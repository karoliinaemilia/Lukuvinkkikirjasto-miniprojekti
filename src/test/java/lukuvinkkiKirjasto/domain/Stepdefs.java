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

    @Given("^user navigates to the listing page for articles$")
    public void user_navigates_to_the_listing_page_for_articles() throws Throwable {
        clickLink("Artikkelien listaukseen");
    }

    @When("^the form fields for an article are filled with \"([^\"]*)\", \"([^\"]*)\",\"([^\"]*)\" ,\"([^\"]*)\", \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\" and submitted$")
    public void the_form_fields_for_an_article_are_filled_with_and_submitted(String nimi, String pituus, String linkki, String tekija, String lehti, String vuosi, String numero, String sivut) throws Throwable {
        fillArticleForm(nimi, pituus, linkki, tekija, lehti, vuosi, numero, sivut);
    }

    @Then("^Article with the information \"([^\"]*)\", \"([^\"]*)\",\"([^\"]*)\" ,\"([^\"]*)\", \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\" has been added$")
    public void article_with_the_information_has_been_added(String nimi, String pituus, String linkki, String tekija, String lehti, String vuosi, String numero, String sivut) throws Throwable {
        pageHasContent(nimi);
        pageHasContent(tekija);
    }

    @Given("^the database has an existing article with name \"([^\"]*)\" by \"([^\"]*)\" and the user navigates to the listing page for articles$")
    public void the_database_has_an_existing_article_with_name_by_and_the_user_navigates_to_the_listing_page_for_articles(String nimi, String tekija) throws Throwable {
        clickLink("Artikkelien listaukseen");
        fillArticleForm(nimi, "456", "linkkilinkki.com", tekija, "Journal for excellence", "2321", "342", "23-234");

    }

    @When("^delete button is pressed at \"([^\"]*)\"$")
    public void delete_button_is_pressed_at(String index) throws Throwable {
        List<WebElement> elements = driver.findElements(By.name("poistoNappi"));
        if (index.isEmpty()) {
            elements.get(elements.size() - 1).submit();
        } else {
            elements.get(Integer.parseInt(index)).submit();
        }
    }

    @Then("^article \"([^\"]*)\" by \"([^\"]*)\" has been deleted$")
    public void article_by_has_been_deleted(String nimi, String tekija) throws Throwable {
        pageDoesNotHaveContent(nimi);
        pageDoesNotHaveContent(tekija);
    }

    @When("^kentat taytetaan tiedoilla \"([^\"]*)\",\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\",\"([^\"]*)\" ,\"([^\"]*)\", \"([^\"]*)\"  ja painetaan lisaa$")
    public void kentat_taytetaan_tiedoilla_ja_painetaan_lisaa(String ISBN, String nimi,
             String genre, String pituus,
             String linkki, String tekija,
             String julkaisuVuosi) throws Throwable {
        fillBookForm(ISBN, nimi, genre, pituus, linkki, tekija, julkaisuVuosi);
    }

    @Given("^user navigates to the listing page for books$")
    public void user_navigates_to_the_listing_page_for_books() throws Throwable {
        clickLink("Kirjojen listaukseen");
    }

    @When("^the form fields for a book are filled with \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" and submitted$")
    public void the_form_fields_for_a_book_are_filled_with_and_submitted(String ISBN, String nimi,
             String genre, String pituus,
             String linkki, String tekija,
             String julkaisuVuosi) throws Throwable {
        fillBookForm(ISBN, nimi, genre, pituus, linkki, tekija, julkaisuVuosi);
    }

    @Then("^the book \"([^\"]*)\",\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\",\"([^\"]*)\" ,\"([^\"]*)\", \"([^\"]*)\" is not added$")
    public void the_book_is_not_added(String ISBN, String nimi,
             String genre, String pituus,
             String linkki, String tekija,
             String julkaisuVuosi) throws Throwable {
        pageDoesNotHaveContent(nimi);
        pageDoesNotHaveContent(tekija);
    }

    @Then("^Book with the information \"([^\"]*)\",\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\",\"([^\"]*)\" ,\"([^\"]*)\", \"([^\"]*)\" has been added$")
    public void book_with_the_information_has_been_added(String ISBN, String nimi,
             String genre, String pituus,
             String linkki, String tekija,
             String julkaisuVuosi) throws Throwable {
        pageHasContent(nimi);
        pageHasContent(tekija);
    }

    @Given("^the database has an existing book with name \"([^\"]*)\" by \"([^\"]*)\" and the user navigates to the listing page for books$")
    public void the_database_has_an_existing_book_with_name_by_and_the_user_navigates_to_the_listing_page_for_books(String nimi, String tekija) throws Throwable {
        clickLink("Kirjojen listaukseen");
        fillBookForm(nimi, "2312", "genrenen", "2930", "linked", tekija, "2312");
    }

    @Then("^book \"([^\"]*)\" by \"([^\"]*)\" has been deleted$")
    public void book_by_has_been_deleted(String nimi, String tekija) throws Throwable {
        pageDoesNotHaveContent(nimi);
        pageDoesNotHaveContent(tekija);
    }

    @Given("^the database has an existing book$")
    public void the_database_has_an_existing_book() throws Throwable {
        clickLink("Kirjojen listaukseen");
        fillBookForm("9780997316025", "There once was a book named Barry", "edjumacational", "long", "linklinkalinklinklinkisaidalinklinklinklinkedylink", "Barry the First", "932");
        System.out.println(driver.getPageSource());
    }

    @Given("^the database has an existing article$")
    public void the_database_has_an_existing_article() throws Throwable {
        clickLink("Artikkelien listaukseen");
        fillArticleForm("The Software Engineer", "3231", "lii.com", "Sandro MacMuffin", "Scientific Canadian", "48485", "2303", "223-22");

    }

    @Then("^Only some of the information for book is shown$")
    public void only_some_of_the_information_for_book_is_shown() throws Throwable {
        pageDoesNotHaveContent("genre: edjumacational");
        pageDoesNotHaveContent("932");
        pageDoesNotHaveContent("linklinkalinklinklinkisaidalinklinklinklinkedylink");
    }

    @Then("^Only some of the information for article is shown$")
    public void only_some_of_the_information_for_article_is_shown() throws Throwable {
        pageDoesNotHaveContent("lii.com");
        pageDoesNotHaveContent("Scientific Canadian");
    }

    private void clickLink(String name) {
        driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText(name));
        element.click();
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
