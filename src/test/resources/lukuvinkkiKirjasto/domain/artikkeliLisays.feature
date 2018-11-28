Feature: Kayttaja voi lisata artikkelin

  Scenario: artikkelin lisays
    Given mennaan artikkelien alkusivulle
    When kentat taytetaan tiedoilla "nimi", "200","testi linkki" ,"tekijä", "julkaisulehti","2019","2","3"  ja painetaan lisaa
    Then Sovellus on lisannyt artikkelin tiedoilla ja linkilla "nimi", "200","testi linkki" ,"tekijä", "julkaisulehti","2019","2","3"

  Scenario: artikkelin voi lisata ilman linkkia
    Given mennaan artikkelien alkusivulle
    When kentat taytetaan tiedoilla "nimi2", "200","" ,"tekijä", "julkaisulehti","2019","2","3"  ja painetaan lisaa
    Then Sovellus on lisannyt artikkelin tiedoilla ilman linkkia "nimi2", "200","" ,"tekijä", "julkaisulehti","2019","2","3"
