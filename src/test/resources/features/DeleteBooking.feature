#Author : Manasa T S

Feature: DELETE API Tests

  Background:
    Given User has valid auth token

  Scenario: Successfully delete existing booking
    Given User creates a new booking with data "validBooking"
    When User deletes the booking
    Then Booking delete response status should be 201
    And Booking should no longer exist

  Scenario: Delete non-existent booking
    When User deletes booking id "9999999"
    Then Booking delete response status should be 405

  Scenario: Delete booking without authentication
    Given User creates a new booking with data "validBooking"
    When User deletes booking without authentication
    Then Booking delete response status should be 403