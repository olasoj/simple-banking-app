![Logo](https://i.postimg.cc/Prh7tGGn/simple-banking-app-logo.jpg)

# Banking Web Application

A Banking Web Application that simulates the day-to-day operations of a bank.

## Authors

- [@olasoj](https://www.github.com/olasoj)

## 🚀 About Me

An analytical and solution-oriented computer scientist with significant practical and professional experience. Capable of motivating oneself and others. Passionate about programming and educating the next generation of technology consumers and innovators.

## Features

**The following functionalities are available in this application:**

- Users can request their account balance by entering their account number and account password. Provided the account exists.

- Users can deposit any amount less than $1,000,000.00 to any account number, and the funds will be credited to the account in real-time. This is subject to the following constraints: The amount is between $1000,000.00 and $1.00, and the account exists.

- Users can request transaction history.

- Users can withdraw if the account balance after deducting the withdrawn amount from the current balance is at least $500, the withdrawal amount is larger than $1.00, and the account exists.

- Users can create an account and receive a unique 10-digit account number after signing up. When registering, the user must provide an account name that does not already exist on the platform, as well as a minimum deposit of $500.00.

- The account number and password are used to authenticate the user, which results in the user receiving an access token if successful.

- Except for login and registration, every request must have a valid access token.

- This system was built using the spring-boot framework with spring security, without a database, and following the best principles in software engineering.

## Environment Variables

This project made no use of environmental variables.

## Run Locally

Clone the project

```bash
   git clone -b main https://github.com/olasoj/simple-banking-app.git
```

Go to the project directory

```bash
  cd simple-banking-app
```

Start the simple-banking-app

```bash
    mvn spring-boot:run
```

## Documentation

[Documentation](https://banking-api-doc.surge.sh/#account-create-account-post)

[API](https://simple-banking-application.herokuapp.com/)

## Tech Stack

**Client:** Postman

**Server:** Java, Spring boot
