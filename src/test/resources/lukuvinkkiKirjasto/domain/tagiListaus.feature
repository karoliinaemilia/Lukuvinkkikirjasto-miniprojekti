Feature: As a user I want to be able to list reading tips according to tags

Scenario: A tag's page shows the article's and book's that have been tagged with it
Given The tag "Educational" has been added to book "Really wish testing wasn't a thing", ISBN "9789510425855" and article "I want to go to sleep"
When the user navigates to tag "Educational" page
Then Book "Really wish testing wasn't a thing" and article "I want to go to sleep" are shown