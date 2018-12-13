Feature: As a user I want to mark a reading tip as read

  Scenario: Marking a book as read
    Given user navigates to the listing page for books
    When the form fields for a book are filled with "9789511321224", "Obama", "Michelle", "323", "linkMcLink.com", "self help", "2323" and submitted
    When book is marked as read
    Then the site shows the book as read with the time of reading

  Scenario: Marking an article as read
    Given user navigates to the listing page for articles
    When the form fields for an article are filled with "mark as read", "561","linkki.com" ,"reader", "Scientific readern","1998","71","23" and submitted
    When article is marked as read
    Then the site shows the article as read with the time of reading
