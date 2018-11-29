Feature: Kayttaja voi lisata artikkelin

  Scenario: artikkelin lisays
    Given mennaan artikkelien alkusivulle
    When kentat taytetaan tiedoilla "The Software Craftsman", "561","linkki.com" ,"Sandro Mancuso", "Scientific American","1998","71","23"  ja painetaan lisaa
    Then Sovellus on lisannyt artikkelin tiedoilla "The Software Craftsman", "561","linkki.com" ,"Sandro Mancuso", "Scientific American","1998","71","23"