# OUTLINE FOR "PAY FOR RENTAL BIKE" USECASE OF ECOBIKE PROBLEM

## Descriptions

Customers pay money after returing the rental bike

## Actor

- Customers
- Internetbank

## Step for paying for rental bike

- Users confirm method of paying and the rental money
- Money is deducted from credit card

## Basic flow for paying for rental bike

- Users confirm paying method again to make transaction
- Users have to confirm card information and transaction content again
- System calculates the money based on the rental period
- Users confirm transaction
- The system sends a request
- Inter-bank deduct money from user's credit card
- System saves and sends an email of transaction to the customers

## Alternative flow

- Exceptions: not enough money in credit card, invalid input(Ex: card information)
