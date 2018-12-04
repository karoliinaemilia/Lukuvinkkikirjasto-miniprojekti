Feature: As a user I want to be able to delete an article

Scenario: Pressing the delete button deletes the article
Given the database has an existing article with name "unClean Code" by "Giovanni Tribisi" and the user navigates to the listing page for articles
When delete button is pressed
Then article "unClean Code" by "Giovanni Tribisi" has been deleted
