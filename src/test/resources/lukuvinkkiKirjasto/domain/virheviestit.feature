Feature: As a user I want to be able to see appropriate error messages

Scenario: Book cannot be added with invalid ISBN
    Given user navigates to the listing page for books
    When the form fields for a book are filled with "54892", "Introduction to Algoholism", "John Favreu", "323", "linkMcLink.com", "self help", "2323" and submitted
    Then the book "54892","Introfuction to Algoholism", "John Favreu", "323","linkMcLink.com" ,"self help", "2323" is not added
    Then the user is shown a error message "Ei oikea ISBN!"