@all
Feature: [wikipedia] Example Feature

  @boris
  Scenario: [WIK-01 [wikipedia]
    Given the start page is loaded
    Then the searchbar is visible
    When the Selenium page is opened
    Then the header should be "Selenium"