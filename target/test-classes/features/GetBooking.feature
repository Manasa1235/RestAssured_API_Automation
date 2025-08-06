#Author : Manasa T S

Feature: GET API Tests

  Background:
    Given User has valid auth token

  Scenario: Retrieve all booking IDs without filters
    When User requests all booking IDs without filters
    Then Booking response status should be 200
    And Response should contain list of booking IDs

  Scenario Outline: Retrieve bookings with individual filter "<filter>"="<value>"
    When User requests bookings with filter "<filter>"="<value>"
    Then Booking response status should be 200
    And Response should contain list of booking IDs
    Examples:
      | filter    | value      |
      | firstname | Sally      |
      | lastname  | Brown      |
      | checkin   | 2024-01-01 |
      | checkout  | 2024-12-31 |

  Scenario: Retrieve bookings with combined filters
    When User requests bookings with filters:
      | firstname | Jim   |
      | lastname  | Brown |
    Then Booking response status should be 200
    And Response should contain list of booking IDs

  Scenario: Validate error handling for invalid date format
    When User requests bookings with filter "checkin"="invalid-date"
    Then Booking response status should be 400 or 500

  Scenario: Validate error handling for invalid parameter value
    When User requests bookings with filter "totalprice"="invalid-value"
    Then Booking response status should be 400 or 500

  Scenario: Verify response time is less than 2 seconds
    When User requests all booking IDs without filters
    Then Booking response time should be less than 2000 milliseconds
