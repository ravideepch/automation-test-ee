Feature: Create a booking
  Background: Booking site is loaded
    Given I am on the booking site

    Scenario Outline: Create a booking when all fields filled with valid values
      Given Fill form a with given values
            | firstName   | lastName    | price   | booking paid | checkInDate   | checkOutDate   |
            |<firstName>  | <lastName>  | <price> | <booking>    | <checkInDate> | <checkOutDate> |
      When I save the booking
      Then booking should be saved
      Examples:
      | firstName | lastName | price| booking | checkInDate | checkOutDate  |
      | Anthony   | Maj      | 30   | Yes     | 2019-09-15  | 2019-09-16    |
      | Nebr      | Mash     | 30000| No      | 2019-01-01  | 20019-02-01   |
      | Neil      | Lyn      | 30.5 | Yes     | 2017-10-01  | 2017-11-01    |
      | Neil      | Lyn      | 0.01 | Yes     | 2017-10-01  | 2017-11-01    |