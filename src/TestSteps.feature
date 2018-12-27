Feature: Registration
  Scenario: User should be able to register successfully
    Given User on the "demo.nopcommerce.com" website
    When user click on register button
    And user asked to enter personal details
    And click on Gender Male button
    And enter the First Name
    And enter the Last Name
    And choose the Day, Month and Year for Date of Birth
    And enter the email address
    And enter the Company name
    And select the Newsletter check box
    And enter the password
    And re enter the password
    When click on the register button
    Then user should be able to register successfully

Feature : send email with product
  Scenario: registered user should be able to send email with product successfully
    Given user is registered and logged in
    Then click on computers
    Then click on laptops
    Then click on email a friend button
    Then enter friend's email address
    And enter the message in the personal message field
    When click on send email button
    Then Your message has been sent message should be displayed

Feature: send email with product
  Scenario: unregistered user should not be able to send email with product successfully
    Given user on the home page of the url
    Then click on computers
    Then click on laptops
    Then click on email a friend button
    Then enter friend's email address
    And enter the message in the personal message field
    When click on send email button
    Then error message should be displayed

Feature: mandatory T&C option
  Scenario: user need to accept terms and condition before checkout
    Given user on the home page of the url
    Then click on computers
    And click on laptops
    When select any product from the list
    Then product should be added to the cart
    Then user click on shopping cart
    When user click on checkout button
    Then user should get the pop message to accept T&C

Feature: product purchase
  Scenario: registered user should be able to buy single product successfully
    Given user is registered and logged in
    Then click on computers
    And click on laptops
    When select any product from the list
    Then product should be added to the cart
    Then user click on shopping cart
    Then click the T&C check box
    When user click on checkout button
    Then user should be directed to checkout page
    Then user enter the billing address details on billing address tab
    When user click on continue button
    Then user should be moved to shipping address tab and enter the details
    When user click on continue button
    Then user should be moved to shipping method tab and accept the default option
    When user click on continue button
    Then user should be moved to payment method tap and select Credit card option
    When user click on continue button
    Then user should be moved to payment information tab and enter the details
    When user click on continue button
    Then user should be moved to confirm order tab
    When user click on confirm button
    Then successful order processed message should be displayed

Feature: sort by functionality
  Scenario: user should be able to sort the price by high to low
    Given user on the home page of the url
    Then click on computers
    Then click on laptops
    When user select the value Price:High to Low from Sort by field
    Then user should be able to see the product arranged in price from high to low







