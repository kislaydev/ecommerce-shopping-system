# Task 18 - Testing Suite and Test Cases

## Project
E-Commerce Shopping System

## Purpose

This document records the testing performed for the E-Commerce Shopping System.

The tests cover:

- Product browsing
- Cart
- Orders
- Payment
- Discount
- Invoice
- Invalid inputs
- Exception handling
- Persistence

Normal, boundary and invalid test cases are included.

---

## Test Cases

| Test ID | Feature | Type | Input | Expected Output | Actual Output | Result |
|---|---|---|---|---|---|---|
| TC-01 | Product | Normal | Create Laptop with valid details | Product created successfully | Product created successfully | PASS |
| TC-02 | Product | Invalid | Negative price | InvalidProductException | InvalidProductException | PASS |
| TC-03 | Product | Invalid | Negative quantity | InvalidProductException | InvalidProductException | PASS |
| TC-04 | Cart | Normal | Add Laptop quantity 2 | Product added with quantity 2 | Quantity became 2 | PASS |
| TC-05 | Cart | Normal | Add same Laptop again with quantity 3 | Quantity becomes 5 | Quantity became 5 | PASS |
| TC-06 | Cart | Normal | Update Laptop quantity to 4 | Quantity becomes 4 | Quantity became 4 | PASS |
| TC-07 | Cart | Normal | Remove Laptop | Product removed | Product removed | PASS |
| TC-08 | Cart | Normal | Laptop ₹50000 × 2 | Total ₹100000 | Total ₹100000 | PASS |
| TC-09 | Cart | Invalid | Add quantity 0 | InvalidQuantityException | InvalidQuantityException | PASS |
| TC-10 | Order | Normal | Laptop quantity 2 | Order total ₹100000 | Order total ₹100000 | PASS |
| TC-11 | Payment | Normal | Valid 16-digit card | Card payment accepted | Card payment accepted | PASS |
| TC-12 | Payment | Normal | Valid UPI `sahil@upi` | UPI payment accepted | UPI payment accepted | PASS |
| TC-13 | Payment | Invalid | Card number with invalid length | IllegalArgumentException | IllegalArgumentException | PASS |
| TC-14 | Payment | Invalid | UPI without `@` | IllegalArgumentException | IllegalArgumentException | PASS |
| TC-15 | Discount | Normal | 10% on ₹1000 | Final amount ₹900 | Final amount ₹900 | PASS |
| TC-16 | Discount | Boundary | 0% on ₹1000 | Final amount ₹1000 | Final amount ₹1000 | PASS |
| TC-17 | Discount | Boundary | 100% on ₹1000 | Final amount ₹0 | Final amount ₹0 | PASS |
| TC-18 | Discount | Invalid | 101% discount | IllegalArgumentException | IllegalArgumentException | PASS |
| TC-19 | Invoice | Normal | Order + 10% discount | Invoice generated | Invoice generated | PASS |
| TC-20 | Browsing | Normal | Load products | Products loaded | Products loaded | PASS |
| TC-21 | Persistence | Normal | Save changed quantity and reload | Saved quantity remains | Saved quantity remained | PASS |

---

## Test Categories

### Normal Cases

Normal cases verify that valid operations work correctly.

Examples:

- Creating a valid product
- Adding a product to cart
- Creating an order
- Valid card payment
- Valid UPI payment
- Applying a 10% discount
- Generating an invoice
- Loading products from storage

### Boundary Cases

Boundary cases test values at the limits of the accepted range.

Examples:

- 0% discount
- 100% discount

### Invalid Cases

Invalid cases verify that incorrect input is rejected.

Examples:

- Negative product price
- Negative product quantity
- Cart quantity 0
- Invalid card number
- Invalid UPI ID
- Discount greater than 100%

---

## Persistence Verification

Persistence was tested by:

1. Loading products from `data/products.txt`.
2. Changing a product quantity.
3. Saving the products.
4. Loading the products again.
5. Verifying that the changed quantity was preserved.
6. Restoring the original product quantity.

This verifies that product data can be saved and loaded successfully.

---

## Test Suite Result

The Java test suite reports:

- Passed: 21
- Failed: 0
- Total: 21

Therefore, all implemented test cases passed successfully.

---

## Conclusion

The testing suite verifies the main functionality of the E-Commerce Shopping System, including product browsing, cart operations, order calculation, payment validation, discount calculation, invoice generation, exception handling and persistent file storage.