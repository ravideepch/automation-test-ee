Feature: Delete bookings
    Scenario: Delete bookings
      Given I have a booking
      When I delete booked
      Then Booking should be deleted
