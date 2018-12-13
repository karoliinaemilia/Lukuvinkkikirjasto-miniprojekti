package lukuvinkkiKirjasto.domain;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import lukuvinkkiKirjasto.ui.Ui;
import static org.junit.Assert.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class Stepdefs {

    WebDriver driver = new HtmlUnitDriver();
    String baseUrl = "http://localhost:4567";
    Boolean kirjatLuotu = false;
    Boolean artikkelitLuotu = false;

    //mennään artikkelit sivulle
    @Given("^user navigates to the listing page for articles$")
    public void user_navigates_to_the_listing_page_for_articles() throws Throwable {
        clickLink("Artikkelien listaukseen");
    }

    //lisätään uusi artikkeli
    @When("^the form fields for an article are filled with \"([^\"]*)\", \"([^\"]*)\",\"([^\"]*)\" ,\"([^\"]*)\", \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\" and submitted$")
    public void the_form_fields_for_an_article_are_filled_with_and_submitted(String nimi, String pituus, String linkki, String tekija, String lehti, String vuosi, String numero, String sivut) throws Throwable {
        fillArticleForm(nimi, pituus, linkki, tekija, lehti, vuosi, numero, sivut);
    }

    //uusi artikkeli on lisätty
    @Then("^Article with the information \"([^\"]*)\", \"([^\"]*)\",\"([^\"]*)\" ,\"([^\"]*)\", \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\" has been added$")
    public void article_with_the_information_has_been_added(String nimi, String pituus, String linkki, String tekija, String lehti, String vuosi, String numero, String sivut) throws Throwable {
        pageHasContent(nimi);
        pageHasContent(tekija);
    }

//    @Given("^the database has an existing article with name \"([^\"]*)\" by \"([^\"]*)\" and the user navigates to the listing page for articles$")
//    public void the_database_has_an_existing_article_with_name_by_and_the_user_navigates_to_the_listing_page_for_articles(String nimi, String tekija) throws Throwable {
//        clickLink("Artikkelien listaukseen");
//        fillArticleForm(nimi, "456", "linkkilinkki.com", tekija, "Journal for excellence", "2321", "342", "23-234");
//    }
    //poisto nappia painetaan
    @When("^delete button is pressed$")
    public void delete_button_is_pressed() throws Throwable {
        List<WebElement> elements = driver.findElements(By.id("poistoNappi"));
        elements.get(elements.size() - 1).submit();
    }

    //artikkeli on poistunut
    @Then("^article \"([^\"]*)\" by \"([^\"]*)\" has been deleted$")
    public void article_by_has_been_deleted(String nimi, String tekija) throws Throwable {
        pageDoesNotHaveContent(nimi);
        pageDoesNotHaveContent(tekija);
    }

    /*    @When("^kentat taytetaan tiedoilla \"([^\"]*)\",\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\",\"([^\"]*)\" ,\"([^\"]*)\", \"([^\"]*)\"  ja painetaan lisaa$")
    public void kentat_taytetaan_tiedoilla_ja_painetaan_lisaa(String ISBN, String nimi,
            String genre, String pituus,
            String linkki, String tekija,
            String julkaisuVuosi) throws Throwable {
        fillBookForm(ISBN, nimi, genre, pituus, linkki, tekija, julkaisuVuosi);
    }
     */
    //mennään kirjat sivulle
    @Given("^user navigates to the listing page for books$")
    public void user_navigates_to_the_listing_page_for_books() throws Throwable {
        clickLink("Kirjojen listaukseen");
    }

    //kun uusi kirja lisätään
    @When("^the form fields for a book are filled with \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" and submitted$")
    public void the_form_fields_for_a_book_are_filled_with_and_submitted(String ISBN, String nimi,
            String genre, String pituus,
            String linkki, String tekija,
            String julkaisuVuosi) throws Throwable {
        fillBookForm(ISBN, nimi, genre, pituus, linkki, tekija, julkaisuVuosi);
    }

    //kirjaa ei ole lisätty
    @Then("^the book \"([^\"]*)\",\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\",\"([^\"]*)\" ,\"([^\"]*)\", \"([^\"]*)\" is not added$")
    public void the_book_is_not_added(String ISBN, String nimi,
            String genre, String pituus,
            String linkki, String tekija,
            String julkaisuVuosi) throws Throwable {
        pageDoesNotHaveContent(nimi);
        pageDoesNotHaveContent(tekija);
    }

    //kirja on lisätty
    @Then("^Book with the information \"([^\"]*)\",\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\",\"([^\"]*)\" ,\"([^\"]*)\", \"([^\"]*)\" has been added$")
    public void book_with_the_information_has_been_added(String ISBN, String nimi,
            String genre, String pituus,
            String linkki, String tekija,
            String julkaisuVuosi) throws Throwable {
        pageHasContent(nimi);
        pageHasContent(tekija);
    }

    //kirja on poistettu
    @Then("^book \"([^\"]*)\" by \"([^\"]*)\" has been deleted$")
    public void book_by_has_been_deleted(String nimi, String tekija) throws Throwable {
        pageDoesNotHaveContent(nimi);
        pageDoesNotHaveContent(tekija);
    }

    /*  @Given("^the database has an existing article$")
    public void the_database_has_an_existing_article() throws Throwable {
        clickLink("Artikkelien listaukseen");
        fillArticleForm("The Software Engineer", "3231", "lii.com", "Sandro MacMuffin", "Scientific Canadian", "48485", "2303", "223-22");

    }*/
    //vain osa kirjan tiedoista näytetään
    @Then("^information such as \"([^\"]*)\" and \"([^\"]*)\" are not shown$")
    public void information_such_as_and_are_not_shown(String content1, String content2) throws Throwable {
        pageDoesNotHaveContent(content1);
        pageDoesNotHaveContent(content2);
    }

    // tietokannassa on lukematon kirja
    @Given("^The database has an existing book \"([^\"]*)\", \"([^\"]*)\" that hasn't been read$")
    public void the_database_has_an_existing_book_that_hasn_t_been_read(String ISBN, String name) throws Throwable {
        clickLink("Kirjojen listaukseen");
        fillBookForm(ISBN, name, "Swift", "202", "amazon", "Rob Kerr", "1223");
    }

    @When("^The user is directed to \"([^\"]*)\" page$")
    public void the_user_is_directed_to_page(String name) throws Throwable {
        WebElement element = driver.findElement(By.linkText(name));
        element.click();
    }

    //kirja merkitään luetuksi
    @When("^book is marked as read$")
    public void book_is_marked_as_read() throws Throwable {
        List<WebElement> elements = driver.findElements(By.id("luettuNappi"));
        elements.get(elements.size() - 1).submit();
    }

    //kirja näkyy luettuna luku ajan kera.
    @Then("^the site shows the book as read with the time of reading$")
    public void the_site_shows_the_book_as_read() throws Throwable {
        pageHasContent("Luettu " + LocalDate.now().toString() + " " + LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
    }

    //artikkeli on merkattu luetuksi
    @When("^article is marked as read$")
    public void article_is_marked_as_read() throws Throwable {
        List<WebElement> elements = driver.findElements(By.id("luettuNappi"));
        elements.get(elements.size() - 1).submit();
    }

    //tietokannassa on lukematon artikkeli
    @Given("^The database has an existing article named \"([^\"]*)\" that hasn't been read$")
    public void the_database_has_an_existing_article_named_that_hasn_t_been_read(String name) throws Throwable {
        clickLink("Artikkelien listaukseen");
        fillArticleForm(name, "23412", "linkedy.com", "Martin Strohm", "Academic", "324", "213456", "345-12");
    }

    //sivulla näkyy että artikkeli on luettu lukuajan kera
    @Then("^the site shows the article as read with the time of reading$")
    public void the_site_shows_the_article_as_read() throws Throwable {
        pageHasContent("Luettu " + LocalDate.now().toString() + " " + LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
    }

    @When("^some of the information \"([^\"]*)\" \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" is changed$")
    public void some_of_the_information_and_is_changed(String muuttuja, String arvo, String muuttuja2, String arvo2) throws Throwable {
        WebElement element = driver.findElement(By.name(muuttuja));
        element.sendKeys(arvo);
        element = driver.findElement(By.name(muuttuja2));
        element.sendKeys(arvo2);
        element.submit();
    }

    @Then("^the new information \"([^\"]*)\" and \"([^\"]*)\" is shown$")
    public void the_new_information_and_is_shown(String content1, String content2) throws Throwable {
        pageHasContent(content1);
        pageHasContent(content2);
    }

    @When("^the ISBN is changed to a nonvalid ISBN$")
    public void the_ISBN_is_changed_to_a_nonvalid_ISBN() throws Throwable {
        WebElement element = driver.findElement(By.name("ISBN"));
        element.sendKeys("1");
        element.submit();
    }

    @Then("^the user is shown a error message \"([^\"]*)\"$")
    public void the_user_is_shown_a_error_message(String arg1) throws Throwable {
        pageHasContent("Ei oikea ISBN!");
    }

    @Given("^database has one read and one unread book$")
    public void database_has_one_read_and_one_unread_book() throws Throwable {
        clickLink("Kirjojen listaukseen");
        if (kirjatLuotu == false) {
            luoKaksiKirjaa();
        }
    }

    @When("^luetut is pressed$")
    public void luetut_is_pressed() throws Throwable {
        WebElement element = driver.findElement(By.id("luetut"));
        element.submit();
    }

    @Then("^only the read book is shown$")
    public void only_the_read_book_is_shown() throws Throwable {
        pageHasContent("luettuKirja");
        pageDoesNotHaveContent("kirjainen");
    }

    //EI TOIMI
    @Then("^only the unread book is shown$")
    public void only_the_unread_book_is_shown() throws Throwable {
        pageDoesNotHaveContent("luettuKirja");

    }

    @Given("^database has one read and one unread article$")
    public void database_has_one_read_and_one_unread_article() throws Throwable {
        clickLink("Artikkelien listaukseen");
        if (artikkelitLuotu == false) {
            luoKaksiArtikkelia();
        }
    }

    @Then("^only the read article is shown$")
    public void only_the_read_article_is_shown() throws Throwable {
        pageDoesNotHaveContent("artikkeliiniJokaEiLuettu");
        pageHasContent("luettuArtikkeli");
    }

    //EI TOIMI
    @Then("^only the unread article is shown$")
    public void only_the_unread_article_is_shown() throws Throwable {
        pageDoesNotHaveContent("luettuArtikkeli");

    }

    @When("^kaikki is pressed$")
    public void kaikki_is_pressed() throws Throwable {
        WebElement element = driver.findElement(By.id("kaikki"));
        element.submit();
    }

    @Then("^all books are shown$")
    public void all_books_are_shown() throws Throwable {
        pageHasContent("kirjainen");
        pageHasContent("luettuKirja");
    }

    @Then("^all articles are shown$")
    public void all_articles_are_shown() throws Throwable {
        pageHasContent("artikkeliiniJokaEiLuettu");
        pageHasContent("luettuArtikkeli");
    }

    @When("^lukemattomat is pressed$")
    public void lukemattomat_is_pressed() throws Throwable {
        WebElement element = driver.findElement(By.id("lukemattomat"));
        element.submit();
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

    public void luoKaksiKirjaa() throws Throwable {
        System.out.println("taoa");
        fillBookForm("9789523003927", "luettuKirja", "luettuGenre", "4", "luettuLinkki", "luettuTekijä", "2019");
        book_is_marked_as_read();
        fillBookForm("9789510422410", "kirjainen", "genreinen", "3", "linkkelinen", "tekijäinen", "2018");
        kirjatLuotu = true;
    }

    public void luoKaksiArtikkelia() throws Throwable {

        fillArticleForm("luettuArtikkeli", "3", "luettuLinkki", "tekijä", "lehti", "2000", "2", "2");
        article_is_marked_as_read();
        fillArticleForm("artikkeliiniJokaEiLuettu", "4", "linkki", "luettuTekijä", "luettuLehti", "2000", "2", "2");
        artikkelitLuotu = true;
    }

    @After
    public void tearDown() {
        driver.quit();
    }

}
