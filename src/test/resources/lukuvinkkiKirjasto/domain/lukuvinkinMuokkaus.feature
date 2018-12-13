Feature: As a user I want to be able to change the information for a reading tip on the reading tips page

  Scenario: The information for a book can be changed
    Given The database has an existing book "9789513196455", "lazarus" that hasn't been read
    When The user is directed to "lazarus" page
    When some of the information "genre" "Science fiction" and "pituus" "2384" is changed
    Then the new information "Science fiction" and "2384" is shown

  Scenario: The information for an article can be changed
    Given The database has an existing article named "The R Book" that hasn't been read
    When The user is directed to "The R Book" page
    When some of the information "tekija" "Jonathan Smith" and "julkaisuVuosi" "1345" is changed
    Then the new information "Jonathan Smith" and "1345" is shown

  Scenario: If the ISBN of a book is changed it must be valid
    Given The database has an existing book "9780997316025", "A totally legit book" that hasn't been read
    When The user is directed to "A totally legit book" page
    When the ISBN is changed to a nonvalid ISBN
    Then the user is shown a error message "Ei oikea ISBN!"
