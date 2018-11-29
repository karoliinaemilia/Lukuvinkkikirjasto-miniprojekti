Feature: Kayttaja voi poistaa artikkelin

  Scenario: painamalla poista nappia artikkeli poistetaan
    Given mennaan artikkelien alkusivulle
    When kentat taytetaan tiedoilla "nimi4", "200","testi linkki" ,"tekijä", "julkaisulehti","2019","25","31"  ja painetaan lisaa
    When painetaan artikkelin poista nappia
    Then artikkeli "nimi4", "200","testi linkki" ,"tekijä", "julkaisulehti","2019","25","31" on poistunut
