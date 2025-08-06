#Author : Manasa T S

Feature: PATCH API Tests

  Background:
    Given User has valid auth token

  Scenario: Partial update - single field (firstname)
    Given User creates a new booking with data "validBooking"
    When User partially updates booking with data:
      | firstname | Kobe |
    Then Booking response status should be 200
    And Booking field "firstname" should be "Kobe"
    And Booking field "lastname" should remain unchanged as "Jordan"

  Scenario: Partial update - multiple fields
    Given User creates a new booking with data "validBooking"
    When User partially updates booking with data "partialUpdateBooking"
    Then Booking response status should be 200
    And Booking field "firstname" should be "Scottie"
    And Booking field "additionalneeds" should be "Lunch"

  Scenario: Partial update - nested object (bookingdates)
    Given User creates a new booking with data "validBooking"
    When User partially updates booking with data:
      | bookingdates | {"checkin":"2025-01-01","checkout":"2025-01-10"} |
    Then Booking response status should be 200
    And Booking date "checkin" should be "2025-01-01"

  Scenario: Invalid booking ID error handling
    When User partially updates booking id "9999999" with data "partialUpdateBooking"
    Then Booking response status should be 405

  Scenario: Invalid data type error handling
    Given User creates a new booking with data "validBooking"
    When User partially updates booking with invalid data:
      | totalprice | "invalid-price" |
    Then Booking response status should be 400 or 500

  Scenario: Missing authentication
    Given User creates a new booking with data "validBooking"
    When User partially updates booking without authentication with data "partialUpdateBooking"
    Then Booking response status should be 403

  Scenario: Verify idempotency of updates
    Given User creates a new booking with data "validBooking"
    When User partially updates booking with data "partialUpdateBooking" twice
    Then Both responses should be identical


