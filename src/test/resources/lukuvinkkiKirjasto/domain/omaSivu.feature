Feature: As a user I want all the reading tips to have their own individual pages that you can get to clicking the name of the tip

  Scenario: Books have their own pages
    Given The database has an existing book "9789510434215", "the game" that hasn't been read
    When The user is directed to "the game" page

  Scenario: Articles have their own pages
    Given The database has an existing article named "testi2" that hasn't been read
    Then The user is directed to "testi2" page
