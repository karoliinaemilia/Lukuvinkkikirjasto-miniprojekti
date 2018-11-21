Feature: Kayttaja voi avata sovelluksen

   Scenario: Sovellus aukeaa
     Given Tietokanta yhteys avataan
     When Sovellus avataan
     Then Sovellus aukeaa

