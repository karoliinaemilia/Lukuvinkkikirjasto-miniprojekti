Feature: As a user I ant to be able to add a book

Scenario: Book cannot be added with invalid ISBN
Given user navigates to the listing page for books
When the form fields for a book are filled with "54892", "Introduction to Algoholism", "John Favreu", "323", "linkMcLink.com", "self help", "2323" and submitted
Then the book "54892","Introfuction to Algoholism", "John Favreu", "323","linkMcLink.com" ,"self help", "2323" is not added

Scenario: Adding a book
Given user navigates to the listing page for books
When the form fields for a book are filled with "9789519854892", "Introduction to Algorithms", "School", "2000", "info.com", "Terrence", "2039" and submitted
Then Book with the information "9789519854892","Introduction to Algorithms", "School", "2000","info.com" ,"Terrence", "2039" has been added

Scenario: Book cannot be added with same ISBN
Given user navigates to the listing page for books
When the form fields for a book are filled with "9789519854892", "Introduction to Algoholism", "John Favreu", "323", "linkMcLink.com", "self help", "2323" and submitted
Then the book "97895198954892","Introfuction to Algoholism", "John Favreu", "323","linkMcLink.com" ,"self help", "2323" is not added
