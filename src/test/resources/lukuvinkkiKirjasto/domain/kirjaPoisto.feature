Feature: As a user I want to be able to delete a book

  Scenario: Pressing the delete button deletes the book
    Given user navigates to the listing page for books
    When the form fields for a book are filled with "9789510435854", "Limits", "Ellen Lowe", "2000", "info.com", "school", "2039" and submitted
    When delete button is pressed
    Then book "Limits" by "Ellen Lowe" has been deleted
