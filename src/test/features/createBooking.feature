Feature: Create a booking
  Background: Booking site is loaded
    Given I am on the booking site

    Scenario Outline: Create a booking when all fields filled with valid values
      Given Fill form a with given values
            | firstName   | lastName    | price   | booking paid | checkInDate   | checkOutDate   |
            |<firstName>  | <lastName>  | <price> | <booking>    | <checkInDate> | <checkOutDate> |
      And Deposit is not paid
      When I save the booking
      Then booking should be saved

      Examples:
      | firstName | lastName | price | booking  | checkInDate | checkOutDate  |
      | Anthony   | Maj      | 30    |  Yes     | 2019-01-01  | 2019-02-02    |
      | Nebr      | Mach     | 30    |  No      | 2017-01-01  | 2017-02-01    |
      | Neil      | Lyn      | 30    |  Yes     | 2017-01-01  | 2017-02-01    |

