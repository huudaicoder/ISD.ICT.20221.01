# OUTLINE FOR "RETURN A BIKE" USECASE OF ECOBIKE PROBLEM

## Descriptions

Customers return the bike they rent

## Actor

- Customers

## Steps for returning bikes

- Users push the bike into an empty docking point of a dock
- Users close the dock
- System return deposit and deduct the amount of money

## Basic flow for returning bike

- Users choose a dock to return
- Users return the bike to the dock chosen
- System calls use case "return deposit"
- System calls use case "pay for rental bike"

## Alternative flow

- Exceptions: users haven't rent a bike yet, the dock chosen is full, not enough money in credit card
