Feature: As a user I want to be able to delete an article

  Scenario: Pressing the delete button deletes the article
    Given user navigates to the listing page for articles
    When the form fields for an article are filled with "unClean Code", "500","linkki.com" ,"Giovanni Tribisi", "Info","1998","71","23" and submitted
    When delete button is pressed
    Then article "unClean Code" by "Giovanni Tribisi" has been deleted
