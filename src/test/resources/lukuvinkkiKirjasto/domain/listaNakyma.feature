Feature: As a user I want the listing of a reading tip to only show partial information

Scenario: Partial information for a book
Given the database has an existing book
When user navigates to the listing page for books
Then Only some of the information for book is shown

Scenario: Partial information for an article
Given the database has an existing article
When user navigates to the listing page for articles
Then Only some of the information for article is shown