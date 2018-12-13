Feature: As a user I want the listing of a reading tip to only show partial information

  Scenario: Partial information for a book
    Given user navigates to the listing page for books
    When the form fields for a book are filled with "9789522345387", "Räikkönen", "Kimi", "2000", "info.com", "school", "2039" and submitted
    Then information such as "kimi" and "info.com" are not shown

  Scenario: Partial information for an article
    Given user navigates to the listing page for articles
    When the form fields for an article are filled with "enter article here", "561","linkki.com" ,"Someone", "Scientific Article","1998","71","23" and submitted
    Then information such as "linkki.com" and "Scientific Article" are not shown
