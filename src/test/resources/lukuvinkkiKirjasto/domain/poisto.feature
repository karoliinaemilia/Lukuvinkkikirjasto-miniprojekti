Feature: Kayttaja voi poistaa kirjan

  Scenario: painamalla poista nappia kirja poistetaan
    Given mennaan alkusivulle
    When kentat taytetaan tiedoilla ja linkilla "9789524952934","Testi", "testinen", "200","testi linkki" ,"Testi testinen", "2018"  ja painetaan lisaa
    When painetaan kirjan poista nappia
    Then kirja "9789524952934","Testi", "testinen", "200","testi linkki" ,"Testi testinen", "2018" on poistunut