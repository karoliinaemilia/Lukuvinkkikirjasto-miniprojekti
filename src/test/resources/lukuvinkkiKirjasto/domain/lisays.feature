Feature: Kayttaja voi lisata kirjan

  Scenario: kirjan lisays
    Given mennaan alkusivulle
    When kentat taytetaan tiedoilla ja linkilla "9789519854892","Testi", "testinen", "200","testi linkki" ,"Testi testinen", "2018"  ja painetaan lisaa
    Then Sovellus on lisannyt kirjan tiedoilla ja linkilla "9789519854892","Testi", "testinen", "200","testi linkki" ,"Testi testinen", "2018"

  Scenario: kirjaa ei voi lisata samalla ISBN:alla
    Given mennaan alkusivulle
    When kentat taytetaan tiedoilla ja linkilla "9780136019701","Testi", "testinen", "200","testi linkki" ,"Testi testinen", "2018"  ja painetaan lisaa
    When kentat taytetaan tiedoilla ja linkilla "9780136019701","toinenTesti", "testinen2", "201","testi linkki2" ,"Testi testinennen", "2019"  ja painetaan lisaa
    Then kirjaa linkilla "9780136019701","toinenTesti", "testinen2", "201","testi linkki2" ,"Testi testinennen", "2019" ei lisata

  Scenario: kirjan voi lisata ilman linkkia
    Given mennaan alkusivulle
    When kentat taytetaan tiedoilla ilman linkkia "9789511308799","Testi", "testinen", "200" ,"Testi testinen", "2018"  ja painetaan lisaa
    Then Sovellus on lisannyt kirjan tiedoilla ilman linkkia "9789511308799","Testi", "testinen", "200", "Testi testinen", "2018"
