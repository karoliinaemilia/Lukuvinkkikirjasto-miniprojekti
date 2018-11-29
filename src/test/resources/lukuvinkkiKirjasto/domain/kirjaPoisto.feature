Feature: Kayttaja voi poistaa kirjan

  Scenario: painamalla poista nappia kirja poistetaan
    Given mennaan kirjojen alkusivulle
    When kentat taytetaan tiedoilla "9789524952934","Clean Code", "Informational", "431","alinktonowhere.com" ,"Robert C. Martin", "2008"  ja painetaan lisaa
    When painetaan kirjan poista nappia
    Then kirja "9789524952934","Clean Code", "Informational", "431","alinktonowhere.com" ,"Robert C. Martin", "2008" on poistunut
