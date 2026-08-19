Feature: PayDocker User Authentication

  Scenario: Successful login with valid credentials
    Given the user navigates to the PayDocker login page
    When the user enters email "arsh@bakuun.com" and password "password123"
    And clicks the Continue button
    Then the user should be redirected to the dashboard

    
    