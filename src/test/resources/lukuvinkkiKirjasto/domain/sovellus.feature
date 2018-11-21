Feature: Kayttaja voi lisata kirjan

  Scenario: kirjan lisays
    Given mennaan alkusivulle
    When kentat taytetaan tiedoilla ja painetaan lisaa
    Then Sovellus lisaa yhden kirjan
