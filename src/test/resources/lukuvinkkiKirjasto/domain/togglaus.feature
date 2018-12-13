Feature: As a user I want to be able to toggle whether I see read reading tips unread reading tips or all

  Scenario: Pressing button luetut shows just the read books
    Given database has one read and one unread book
    When luetut is pressed
    Then only the read book is shown

  Scenario: Pressing button lukemattomat shows just the unread books
    Given database has one read and one unread book
    When lukemattomat is pressed
    Then only the unread book is shown

  Scenario: Pressing button luetut shows just the read articles
    Given database has one read and one unread article
    When luetut is pressed
    Then only the read article is shown

  Scenario: Pressing button lukemattomat shows just the unread articles
    Given database has one read and one unread article
    When lukemattomat is pressed
    Then only the unread article is shown

  Scenario: Presseing button kaikki shows all books
    Given database has one read and one unread book
    When luetut is pressed
    When kaikki is pressed
    Then all books are shown

  Scenario: Presseing button kaikki shows all articles
    Given database has one read and one unread article
    When luetut is pressed
    When kaikki is pressed
    Then all articles are shown
