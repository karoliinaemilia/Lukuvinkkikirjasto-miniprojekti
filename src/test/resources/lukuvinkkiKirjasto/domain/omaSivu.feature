Feature: As a user I want all the reading tips to have their own individual pages that you can get to clicking the name of the tip

  Scenario: Books have their own pages
    Given The database has an existing book "9789510434215", "Book about Coding" that hasn't been read
    Then The user is directed to "Book about Coding" page

  Scenario: Articles have their own pages
    Given The database has an existing article named "Article about coding" that hasn't been read
    Then The user is directed to "Article about coding" page
