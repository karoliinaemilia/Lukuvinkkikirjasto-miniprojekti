Feature: As a user I want to be able to change the information for a reading tip on the reading tips page

Scenario: The information for a book can be changed
Given The database has an existing book and the user navigates to its page
When some of the infomration the genre "Science fiction" and the length "2384" is changed for the book
Then the new information "Science fiction" and "2384" is shown

Scenario: The information for an article can be changed
Given The database has an existing article and the user navigates to its page
When some of the information the maker "Jonathan Smith" and year of release "1345" is changed
Then the new information "Jonathan Smith" and "1345" is shown