# E-Commerce Shopping System

A console-based E-Commerce Shopping System developed in Java using Object-Oriented Programming principles.

The project provides a complete shopping workflow including customer registration and login, product browsing, cart management, checkout, discounts, payment processing, invoice generation, and product persistence.

---

## Features

### Customer Authentication

- Customer registration
- Customer login
- Password handling
- Customer ID generation
- Logout functionality

### Product Browsing

- View all products
- Search products
- Browse products by category
- Display product details
- Product stock management

### Shopping Cart

- Add products to cart
- Add a specific quantity
- View cart
- Calculate cart total
- Update product quantity
- Remove products from cart
- Handle invalid quantities
- Handle products that are not available in the cart

### Checkout

- Create an order from the shopping cart
- Calculate order total
- Apply discount
- Process payment
- Generate invoice
- Update product stock
- Save updated stock to persistent storage
- Clear the cart after successful checkout

### Payment

- Card payment
- UPI payment
- Payment validation
- Successful and unsuccessful payment handling

### Discount

- Discount calculation
- Discount amount calculation
- Final amount calculation
- Validation of discount values

### Invoice

- Invoice generation after checkout
- Order details
- Product details
- Quantity and subtotal
- Discount
- Final amount
- Payment information

### Persistence

- Product data stored in a text file
- Product data loaded when the application starts
- Updated stock saved after successful checkout
- Product data remains available after restarting the application

### Testing

- Automated project test suite
- Product creation and validation tests
- Cart tests
- Order tests
- Payment tests
- Discount tests
- Invoice tests
- Product browsing tests
- Persistence tests
- Invalid input and exception tests

---

## Technologies Used

- Java
- Object-Oriented Programming
- Java Collections Framework
- File Handling
- Exception Handling
- Git
- GitHub
- Visual Studio Code

---

## Project Structure

```text
ecommerce-shopping-system/
│
├── src/
│   └── ecommerce/
│       │
│       ├── Main.java
│       │
│       ├── controller/
│       │   └── ConsoleMenu.java
│       │
│       ├── model/
│       │   ├── Admin.java
│       │   ├── CardPayment.java
│       │   ├── Cart.java
│       │   ├── CartItem.java
│       │   ├── Category.java
│       │   ├── Customer.java
│       │   ├── Order.java
│       │   ├── OrderItem.java
│       │   ├── Payment.java
│       │   ├── Product.java
│       │   ├── UPIPayment.java
│       │   └── User.java
│       │
│       ├── repository/
│       │   ├── CustomerRepository.java
│       │   ├── FileManager.java
│       │   ├── ProductDataSource.java
│       │   └── ProductRepository.java
│       │
│       ├── service/
│       │   ├── AuthenticationService.java
│       │   ├── Discount.java
│       │   ├── Invoice.java
│       │   ├── PasswordHasher.java
│       │   └── ProductBrowser.java
│       │
│       ├── exception/
│       │   ├── InvalidProductException.java
│       │   └── InvalidQuantityException.java
│       │
│       └── test/
│           └── ProjectTestSuite.java
│
├── data/
│   └── products.txt
│
└── README.md
```

# How to Run the Project

## Prerequisites

Before running the project, make sure you have:

- Java JDK installed
- Git installed
- Visual Studio Code (optional)

Check the installations:

```powershell
java -version
javac -version
git --version
```

---

## 1. Clone the Repository

Open PowerShell or Command Prompt and run:

```powershell
git clone https://github.com/kislaydev/ecommerce-shopping-system.git
```

---

## 2. Navigate to the Project

```powershell
cd ecommerce-shopping-system
```

You can verify that you are inside the project with:

```powershell
git status
```

---

## 3. Open the Project in Visual Studio Code

If you are using VS Code:

```powershell
code .
```

You can also open the project folder manually in VS Code.

---

## 4. Compile the Project

From the project root directory, run:

```powershell
javac -d out (Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName })
```

The compiled `.class` files will be placed inside the `out` directory.

If compilation is successful, no compilation errors should be displayed.

---

## 5. Run the Application

Start the application using:

```powershell
java -cp out ecommerce.Main
```
