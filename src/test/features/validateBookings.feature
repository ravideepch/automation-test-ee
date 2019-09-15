Feature: Validate bookings

  Scenario Outline: Bookings should not be saved if first name field has invalid value
    Given Invalid value entered in "first name" field
      | <firstName> |
    Then Booking should not be saved
    Examples:
      | firstName    |
      | 121212       |
      | £@$%         |
      |              |

  Scenario Outline: Bookings should not be saved if last name field  has invalid value
    Given Invalid value entered in "last name" field
      | <lastName> |
    Then Booking should not be saved
    Examples:
      | lastName  |
      | 121212       |
      | £@$%         |
      |              |

  Scenario Outline: Bookings should not be saved if price field has invalid value
    Given Invalid value entered in "price" field
      | <price> |
    Then Booking should not be saved
    Examples:
      | price         |
      | random string |
      | £@$%         |

  Scenario Outline: Bookings should not be saved if check in field has invalid value
    Given Invalid value entered in "check in date" field
      | <checkInDate> |
    Then Booking should not be saved
    Examples:
      | checkInDate    |
      | 121212121      |
      | random string  |
      | £@$%           |

  Scenario Outline: Bookings should not be saved if check out field has invalid value
    Given Invalid value entered in "check out date" field
      | <checkOutDate> |
    Then Booking should not be saved
    Examples:
      | checkOutDate  |
      | 121212121     |
      | £@$%          |