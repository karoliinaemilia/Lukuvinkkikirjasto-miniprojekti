Feature: Kayttaja voi lisata kirjan

  Scenario: kirjan lisays
    Given mennaan kirjojen alkusivulle
    When kentat taytetaan tiedoilla "9789519854892","Introduction to Algorithms", "School", "2000","info.com" ,"Terrence", "2039"  ja painetaan lisaa
    Then Sovellus on lisannyt kirjan tiedoilla "9789519854892","Introduction to Algorithms", "School", "2000","info.com" ,"Terrence", "2039"

  Scenario: kirjaa ei voi lisata samalla ISBN:alla
    Given mennaan kirjojen alkusivulle
    When kentat taytetaan tiedoilla "9780136019701","Testi", "testinen", "200","testi linkki" ,"Testi testinen", "2018"  ja painetaan lisaa
    When kentat taytetaan tiedoilla "9780136019701","toinenTesti", "testinen2", "456","testi linkki2" ,"Testi testinennen", "2019"  ja painetaan lisaa
    Then kirjaa "9780136019701","toinenTesti", "testinen2", "456","testi linkki2" ,"Testi testinennen", "2019" ei lisata
