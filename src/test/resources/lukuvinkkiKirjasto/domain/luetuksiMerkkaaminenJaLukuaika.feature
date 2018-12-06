Feature: As a user I want to mark a reading tip as read

Scenario: Marking a book as read
Given The database has an existing book that hasn't been read
When book is marked as read
Then the site shows the book as read with the time of reading

Scenario: Marking an article as read
Given The database has an existing article that hasn't been read
When article is marked as read
Then the site shows the article as read with the time of reading