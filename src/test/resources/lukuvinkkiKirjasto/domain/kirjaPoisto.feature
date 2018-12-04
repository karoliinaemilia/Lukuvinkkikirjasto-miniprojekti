Feature: As a user I want to be able to delete a book

Scenario: Pressing the delete button deletes the book
Given the database has an existing book with name "Limits" by "Ellen Lowe" and the user navigates to the listing page for books
When delete button is pressed
Then book "Limits" by "Ellen Lowe" has been deleted
