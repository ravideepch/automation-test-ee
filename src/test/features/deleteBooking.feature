@BookingTests
Feature: Delete bookings

   Scenario: Delete bookings for which deposit is paid
      Given I have a booking
        And Deposit is paid
      When I delete booked
      Then Booking should be deleted

    Scenario: Delete bookings for which deposit is not paid
      Given I have a booking
        And Deposit is not paid
      When I delete booked
      Then Booking should be deleted
