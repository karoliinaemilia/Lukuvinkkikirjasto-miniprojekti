Feature: As a user I want to be able to add tags to reading tips

Scenario: A tag can be added
Given The user navigates to the tags page
When The user adds tag "Science"
Then The tag "Science" is shown

Scenario: An existing tag can be added to an existing book
Given The database has an existing book "9789522344786", "Book of the ages" that hasn't been read
When The user adds tag "Science" to book "Book of the ages"
When The user is directed to "Book of the ages" page
Then The tag "Science" is shown

Scenario: An existing tag can be added to an existing article
Given The database has an existing article named "Dream big" that hasn't been read
When The user adds tag "Science" to article "Dream big"
When The user is directed to "Dream big" page
Then The tag "Science" is shown