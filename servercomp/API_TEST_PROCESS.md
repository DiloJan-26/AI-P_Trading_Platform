# API Test Process - Complete Postman Workflow

This document provides a comprehensive guide for testing each API endpoint using Postman with step-by-step instructions, including request/response details.

---
## DB Cleanup
```
drop database ez_trad_db;
create database ez_trad_db;
```

---
## BASE URL
```
http://localhost:5454
```

---

## 1. HOME API - Public & Protected Endpoints

### 1.1 Health Check - Public Endpoint

**Purpose:** Verify the server is running without authentication

| Property | Value |
|---|---|
| **Method** | `GET` |
| **URL** | `http://localhost:5454/` |
| **Authentication** | None (Public) |
| **Headers** | None |
| **Body** | None |

**Expected Response:**
```
Status: 200 OK
Body: "welcome to trading platform"
```

**Test Steps in Postman:**
1. Create new request, set method to `GET`
2. Enter URL: `http://localhost:5454/`
3. Click Send
4. Verify response is plain text message

---

### 1.2 Protected API Endpoint

**Purpose:** Test JWT token validation on a protected route

| Property | Value |
|---|---|
| **Method** | `GET` |
| **URL** | `http://localhost:5454/api` |
| **Authentication** | JWT Token Required |
| **Headers** | `Authorization: Bearer {JWT_TOKEN}` |
| **Body** | None |

**Expected Response (with valid token):**
```
Status: 200 OK
Body: "welcome to trading platform secured"
```

**Expected Response (without token):**
```
Status: 403 Forbidden
Body: {"message": "Unauthorized"}
```

**Test Steps in Postman:**
1. Create new request, set method to `GET`
2. Enter URL: `http://localhost:5454/api`
3. Go to **Headers** tab
4. Add header: `Authorization` = `Bearer {your_jwt_token_from_signup}`
5. Click Send
6. Without token, you should get 403 error

---

## 2. AUTH API - User Registration & Login

### 2.1 User Registration (Signup)

**Purpose:** Create a new user account and receive JWT token

| Property | Value |
|---|---|
| **Method** | `POST` |
| **URL** | `http://localhost:5454/auth/signup` |
| **Authentication** | None (Public) |
| **Content-Type** | `application/json` |

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "password123",
  "fullName": "John Doe"
}
```

**Expected Response:**
```json
{
  "jwt": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "status": true,
  "message": "register success"
}
```

**Test Steps in Postman:**
1. Create new request, set method to `POST`
2. Enter URL: `http://localhost:5454/auth/signup`
3. Go to **Body** tab → select **raw** → **JSON**
4. Copy the request JSON
5. **Save the JWT token** returned - you'll need it for other protected APIs
6. Click Send

**Important Notes:**
- Use unique email for each test
- Password must be strong (recommended 8+ characters)
- JWT token is valid for the session duration

---

### 2.2 Send Email Verification OTP

**Purpose:** Request OTP to be sent to user's email

| Property | Value |
|---|---|
| **Method** | `POST` |
| **URL** | `http://localhost:5454/api/users/verification/EMAIL/send-otp` |
| **Authentication** | JWT Token Required |
| **Headers** | `Authorization: Bearer {JWT_TOKEN}` |
| **Path Variable** | `verificationType = EMAIL` |

**Expected Response:**
```
Status: 200 OK
Body: "verification otp send successfully"
```

**Test Steps in Postman:**
1. Create new request, set method to `POST`
2. Enter URL: `http://localhost:5454/api/users/verification/EMAIL/send-otp`
3. Go to **Headers** tab
4. Add header: `Authorization` = `Bearer {jwt_token_from_signup}`
5. Click Send
6. Check email for OTP code

**Note:** OTP will be sent to the email address used during registration

---

### 2.3 Enable Two-Factor Authentication

**Purpose:** Verify OTP and enable 2FA on account

| Property | Value |
|---|---|
| **Method** | `PATCH` |
| **URL** | `http://localhost:5454/api/users/enable-two-factor/verify-otp/{otp}` |
| **Authentication** | JWT Token Required |
| **Headers** | `Authorization: Bearer {JWT_TOKEN}` |
| **Path Variable** | `otp = {received_otp_code}` |
| **Body** | None |

**Example URL:**
```
http://localhost:5454/api/users/enable-two-factor/verify-otp/123456
```

**Expected Response:**
```json
{
  "id": 1,
  "email": "user@example.com",
  "fullName": "John Doe",
  "role": "ROLE_CUSTOMER",
  "twoFactorAuth": {
    "isEnabled": true,
    "sendTo": "EMAIL"
  }
}
```

**Test Steps in Postman:**
1. Create new request, set method to `PATCH`
2. Enter URL: `http://localhost:5454/api/users/enable-two-factor/verify-otp/{otp_from_email}`
3. Add header: `Authorization` = `Bearer {jwt_token}`
4. Click Send
5. Verify `twoFactorAuth.isEnabled` is now `true`

---

### 2.4 Forgot Password Request

**Purpose:** Request password reset token

| Property | Value |
|---|---|
| **Method** | `POST` |
| **URL** | `http://localhost:5454/api/users/password-reset-request` |
| **Authentication** | None (Public) |
| **Content-Type** | `application/json` |

**Request Body:**
```json
{
  "email": "user@example.com"
}
```

**Expected Response:**
```json
{
  "message": "Password reset link sent to your email"
}
```

**Test Steps in Postman:**
1. Create new request, set method to `POST`
2. Enter URL: `http://localhost:5454/api/users/password-reset-request`
3. Go to **Body** tab → raw → JSON
4. Enter email in request body
5. Click Send
6. Check email for reset token/link

---

### 2.5 Reset Password with Token

**Purpose:** Change password using reset token

| Property | Value |
|---|---|
| **Method** | `POST` |
| **URL** | `http://localhost:5454/api/users/reset-password` |
| **Authentication** | None (Public) |
| **Content-Type** | `application/json` |

**Request Body:**
```json
{
  "token": "reset_token_from_email",
  "newPassword": "newPassword123"
}
```

**Expected Response:**
```json
{
  "status": true,
  "message": "Password reset successfully"
}
```

**Test Steps in Postman:**
1. Create new request, set method to `POST`
2. Enter URL: `http://localhost:5454/api/users/reset-password`
3. Go to **Body** tab → raw → JSON
4. Enter token from email and new password
5. Click Send

---

## 3. USER API - User Profile Management

### 3.1 Get User Profile

**Purpose:** Retrieve authenticated user's profile information

| Property | Value |
|---|---|
| **Method** | `GET` |
| **URL** | `http://localhost:5454/api/users/profile` |
| **Authentication** | JWT Token Required |
| **Headers** | `Authorization: Bearer {JWT_TOKEN}` |

**Expected Response:**
```json
{
  "id": 1,
  "email": "user@example.com",
  "fullName": "John Doe",
  "password": null,
  "role": "ROLE_CUSTOMER",
  "twoFactorAuth": {
    "isEnabled": false,
    "sendTo": null
  }
}
```

**Test Steps in Postman:**
1. Create new request, set method to `GET`
2. Enter URL: `http://localhost:5454/api/users/profile`
3. Go to **Headers** tab
4. Add header: `Authorization` = `Bearer {jwt_token}`
5. Click Send

**Note:** Password field is never returned (WRITE_ONLY property)

---

## 4. COIN API - Cryptocurrency Data

### 4.1 Get All Coins (Paginated)

**Purpose:** Retrieve list of cryptocurrencies with pagination

| Property | Value |
|---|---|
| **Method** | `GET` |
| **URL** | `http://localhost:5454/coins?page=0` |
| **Authentication** | None (Public) |
| **Query Parameters** | `page=0` (0-indexed) |

**Expected Response:**
```json
[
  {
    "id": "bitcoin",
    "name": "Bitcoin",
    "symbol": "BTC",
    "image": "https://...",
    "currentPrice": 45000,
    "marketCap": 900000000000,
    "marketCapRank": 1,
    "priceChange24h": 2.5,
    "totalVolume": 30000000000
  },
  {
    "id": "ethereum",
    "name": "Ethereum",
    "symbol": "ETH",
    ...
  }
]
```

**Test Steps in Postman:**
1. Create new request, set method to `GET`
2. Enter URL: `http://localhost:5454/coins?page=0`
3. Click Send
4. You'll get a paginated list of coins

---

### 4.2 Search Coin by Keyword

**Purpose:** Search for a specific cryptocurrency

| Property | Value |
|---|---|
| **Method** | `GET` |
| **URL** | `http://localhost:5454/coins/search?q={keyword}` |
| **Authentication** | None (Public) |
| **Query Parameters** | `q=bitcoin` |

**Test Steps in Postman:**
1. Create new request, set method to `GET`
2. Enter URL: `http://localhost:5454/coins/search?q=bitcoin`
3. Click Send
4. Get matching coin results

---

### 4.3 Get Top 50 Coins by Market Cap

**Purpose:** Retrieve top 50 cryptocurrencies by market capitalization

| Property | Value |
|---|---|
| **Method** | `GET` |
| **URL** | `http://localhost:5454/coins/top50` |
| **Authentication** | None (Public) |

**Test Steps in Postman:**
1. Create new request, set method to `GET`
2. Enter URL: `http://localhost:5454/coins/top50`
3. Click Send

---

### 4.4 Get Trading Coins

**Purpose:** Get coins suitable for trading

| Property | Value |
|---|---|
| **Method** | `GET` |
| **URL** | `http://localhost:5454/coins/trading` |
| **Authentication** | None (Public) |

**Test Steps in Postman:**
1. Create new request, set method to `GET`
2. Enter URL: `http://localhost:5454/coins/trading`
3. Click Send

---

### 4.5 Get Coin Market Chart

**Purpose:** Get historical price chart data for a coin

| Property | Value |
|---|---|
| **Method** | `GET` |
| **URL** | `http://localhost:5454/coins/{coinId}/chart?days={days}` |
| **Authentication** | None (Public) |
| **Path Variable** | `coinId = bitcoin` |
| **Query Parameter** | `days = 30` |

**Example URL:**
```
http://localhost:5454/coins/bitcoin/chart?days=30
```

**Expected Response:**
```json
{
  "prices": [
    [1613000000000, 45000],
    [1613086400000, 45500],
    ...
  ],
  "market_caps": [...],
  "volumes": [...]
}
```

**Test Steps in Postman:**
1. Create new request, set method to `GET`
2. Enter URL: `http://localhost:5454/coins/bitcoin/chart?days=30`
3. Click Send

---

### 4.6 Get Coin Details

**Purpose:** Get detailed information about a specific coin

| Property | Value |
|---|---|
| **Method** | `GET` |
| **URL** | `http://localhost:5454/coins/details/{coinId}` |
| **Authentication** | None (Public) |
| **Path Variable** | `coinId = bitcoin` |

**Test Steps in Postman:**
1. Create new request, set method to `GET`
2. Enter URL: `http://localhost:5454/coins/details/bitcoin`
3. Click Send

---

## 5. WALLET API - User Fund Management

### 5.1 Get User Wallet Balance

**Purpose:** Retrieve user's current wallet balance

| Property | Value |
|---|---|
| **Method** | `GET` |
| **URL** | `http://localhost:5454/api/wallet` |
| **Authentication** | JWT Token Required |
| **Headers** | `Authorization: Bearer {JWT_TOKEN}` |

**Expected Response:**
```json
{
  "id": 1,
  "balance": 50000.00,
  "user": {
    "id": 1,
    "email": "user@example.com"
  },
  "transactions": []
}
```

**Test Steps in Postman:**
1. Create new request, set method to `GET`
2. Enter URL: `http://localhost:5454/api/wallet`
3. Add header: `Authorization` = `Bearer {jwt_token}`
4. Click Send

---

### 5.2 Wallet to Wallet Transfer

**Purpose:** Transfer funds between two wallets

| Property | Value |
|---|---|
| **Method** | `PUT` |
| **URL** | `http://localhost:5454/api/wallet/{walletId}/transfer` |
| **Authentication** | JWT Token Required |
| **Headers** | `Authorization: Bearer {JWT_TOKEN}` |
| **Content-Type** | `application/json` |
| **Path Variable** | `walletId = recipient_wallet_id` |

**Request Body:**
```json
{
  "amount": 1000.00
}
```

**Expected Response:**
```json
{
  "id": 1,
  "balance": 49000.00,
  "user": {...}
}
```

**Test Steps in Postman:**
1. Create new request, set method to `PUT`
2. Enter URL: `http://localhost:5454/api/wallet/{walletId}/transfer`
3. Add header: `Authorization` = `Bearer {jwt_token}`
4. Go to **Body** tab → raw → JSON
5. Enter amount to transfer
6. Click Send

---

### 5.3 Deposit Funds (via Payment)

**Purpose:** Add balance to wallet after successful payment

| Property | Value |
|---|---|
| **Method** | `PUT` |
| **URL** | `http://localhost:5454/api/wallet/deposit` |
| **Authentication** | JWT Token Required |
| **Headers** | `Authorization: Bearer {JWT_TOKEN}` |
| **Query Parameters** | `order_id={orderId}`, `payment_id={paymentId}` |

**Example URL:**
```
http://localhost:5454/api/wallet/deposit?order_id=123&payment_id=pay_123abc
```

**Expected Response:**
```json
{
  "id": 1,
  "balance": 51000.00,
  "user": {...}
}
```

**Test Steps in Postman:**
1. Create new request, set method to `PUT`
2. Enter URL: `http://localhost:5454/api/wallet/deposit?order_id={orderId}&payment_id={paymentId}`
3. Add header: `Authorization` = `Bearer {jwt_token}`
4. Click Send

---

## 6. ASSET API - User Cryptocurrency Holdings

### 6.1 Get User Assets

**Purpose:** Retrieve all cryptocurrencies user owns

| Property | Value |
|---|---|
| **Method** | `GET` |
| **URL** | `http://localhost:5454/api/asset` |
| **Authentication** | JWT Token Required |
| **Headers** | `Authorization: Bearer {JWT_TOKEN}` |

**Expected Response:**
```json
[
  {
    "id": 1,
    "quantity": 0.5,
    "coin": {
      "id": "bitcoin",
      "name": "Bitcoin",
      "symbol": "BTC"
    },
    "user": {
      "id": 1,
      "email": "user@example.com"
    },
    "buyPrice": 45000
  }
]
```

**Test Steps in Postman:**
1. Create new request, set method to `GET`
2. Enter URL: `http://localhost:5454/api/asset`
3. Add header: `Authorization` = `Bearer {jwt_token}`
4. Click Send

---

### 6.2 Get Asset by Coin

**Purpose:** Get specific cryptocurrency holding

| Property | Value |
|---|---|
| **Method** | `GET` |
| **URL** | `http://localhost:5454/api/asset/coin/{coinId}/user` |
| **Authentication** | JWT Token Required |
| **Headers** | `Authorization: Bearer {JWT_TOKEN}` |
| **Path Variable** | `coinId = bitcoin` |

**Test Steps in Postman:**
1. Create new request, set method to `GET`
2. Enter URL: `http://localhost:5454/api/asset/coin/bitcoin/user`
3. Add header: `Authorization` = `Bearer {jwt_token}`
4. Click Send

---

### 6.3 Get Asset by ID

**Purpose:** Get specific asset by database ID

| Property | Value |
|---|---|
| **Method** | `GET` |
| **URL** | `http://localhost:5454/api/asset/{assetId}` |
| **Authentication** | JWT Token Required |
| **Headers** | `Authorization: Bearer {JWT_TOKEN}` |
| **Path Variable** | `assetId = 1` |

**Test Steps in Postman:**
1. Create new request, set method to `GET`
2. Enter URL: `http://localhost:5454/api/asset/1`
3. Add header: `Authorization` = `Bearer {jwt_token}`
4. Click Send

---

## 7. ORDER API - Buy/Sell Trading Orders

### 7.1 Create Order (Buy/Sell)

**Purpose:** Place a new buy or sell order

| Property | Value |
|---|---|
| **Method** | `POST` |
| **URL** | `http://localhost:5454/api/orders/pay` |
| **Authentication** | JWT Token Required |
| **Headers** | `Authorization: Bearer {JWT_TOKEN}` |
| **Content-Type** | `application/json` |

**Request Body:**
```json
{
  "coinId": "bitcoin",
  "quantity": 0.5,
  "orderType": "BUY"
}
```

**Order Type Options:** `BUY` or `SELL`

**Expected Response:**
```json
{
  "id": 1,
  "user": {
    "id": 1,
    "email": "user@example.com"
  },
  "orderItem": {
    "coin": {
      "id": "bitcoin",
      "currentPrice": 45000
    },
    "quantity": 0.5,
    "price": 22500
  },
  "orderType": "BUY",
  "price": 22500,
  "status": "PENDING",
  "timestamp": "2026-02-22T10:30:00Z"
}
```

**Test Steps in Postman:**
1. Create new request, set method to `POST`
2. Enter URL: `http://localhost:5454/api/orders/pay`
3. Add header: `Authorization` = `Bearer {jwt_token}`
4. Go to **Body** tab → raw → JSON
5. Enter order details (coin ID, quantity, order type)
6. Click Send
7. **Note:** Ensure wallet has sufficient balance for BUY orders

---

### 7.2 Get Order by ID

**Purpose:** Retrieve specific order details

| Property | Value |
|---|---|
| **Method** | `GET` |
| **URL** | `http://localhost:5454/api/orders/{orderId}` |
| **Authentication** | JWT Token Required |
| **Headers** | `Authorization: Bearer {JWT_TOKEN}` |
| **Path Variable** | `orderId = 1` |

**Test Steps in Postman:**
1. Create new request, set method to `GET`
2. Enter URL: `http://localhost:5454/api/orders/1`
3. Add header: `Authorization` = `Bearer {jwt_token}`
4. Click Send

---

### 7.3 Get All User Orders

**Purpose:** Retrieve all orders with optional filters

| Property | Value |
|---|---|
| **Method** | `GET` |
| **URL** | `http://localhost:5454/api/orders` |
| **Authentication** | JWT Token Required |
| **Headers** | `Authorization: Bearer {JWT_TOKEN}` |
| **Query Parameters** | `order_type=BUY`, `asset_symbol=BTC` (optional) |

**Example URL:**
```
http://localhost:5454/api/orders?order_type=BUY&asset_symbol=BTC
```

**Expected Response:**
```json
[
  {
    "id": 1,
    "orderType": "BUY",
    "price": 22500,
    "status": "PENDING"
  },
  {
    "id": 2,
    "orderType": "SELL",
    "price": 23000,
    "status": "COMPLETED"
  }
]
```

**Test Steps in Postman:**
1. Create new request, set method to `GET`
2. Enter URL: `http://localhost:5454/api/orders` (with optional query params)
3. Add header: `Authorization` = `Bearer {jwt_token}`
4. Click Send

---

## 8. WATCHLIST API - Favorite Coins Tracking

### 8.1 Get User Watchlist

**Purpose:** Retrieve user's watchlist of favorite coins

| Property | Value |
|---|---|
| **Method** | `GET` |
| **URL** | `http://localhost:5454/api/watchlist/user` |
| **Authentication** | JWT Token Required |
| **Headers** | `Authorization: Bearer {JWT_TOKEN}` |

**Expected Response:**
```json
{
  "id": 1,
  "user": {
    "id": 1,
    "email": "user@example.com"
  },
  "coins": [
    {
      "id": "bitcoin",
      "name": "Bitcoin",
      "symbol": "BTC",
      "currentPrice": 45000
    },
    {
      "id": "ethereum",
      "name": "Ethereum",
      "symbol": "ETH",
      "currentPrice": 3000
    }
  ]
}
```

**Test Steps in Postman:**
1. Create new request, set method to `GET`
2. Enter URL: `http://localhost:5454/api/watchlist/user`
3. Add header: `Authorization` = `Bearer {jwt_token}`
4. Click Send

---

### 8.2 Get Watchlist by ID

**Purpose:** Retrieve a specific watchlist

| Property | Value |
|---|---|
| **Method** | `GET` |
| **URL** | `http://localhost:5454/api/watchlist/{watchlistId}` |
| **Authentication** | Not Required |
| **Path Variable** | `watchlistId = 1` |

**Test Steps in Postman:**
1. Create new request, set method to `GET`
2. Enter URL: `http://localhost:5454/api/watchlist/1`
3. Click Send

---

### 8.3 Add Coin to Watchlist

**Purpose:** Add a cryptocurrency to user's watchlist

| Property | Value |
|---|---|
| **Method** | `PATCH` |
| **URL** | `http://localhost:5454/api/watchlist/add/coin/{coinId}` |
| **Authentication** | JWT Token Required |
| **Headers** | `Authorization: Bearer {JWT_TOKEN}` |
| **Path Variable** | `coinId = bitcoin` |

**Expected Response:**
```json
{
  "id": "bitcoin",
  "name": "Bitcoin",
  "symbol": "BTC",
  "currentPrice": 45000,
  "isAddedToWatchlist": true
}
```

**Test Steps in Postman:**
1. Create new request, set method to `PATCH`
2. Enter URL: `http://localhost:5454/api/watchlist/add/coin/bitcoin`
3. Add header: `Authorization` = `Bearer {jwt_token}`
4. Click Send

---

## 9. PAYMENT API - Payment Gateway Integration

### 9.1 Initiate Payment (Razorpay or Stripe)

**Purpose:** Create payment link for fund deposit

| Property | Value |
|---|---|
| **Method** | `POST` |
| **URL** | `http://localhost:5454/api/payment/{paymentMethod}/amount/{amount}` |
| **Authentication** | JWT Token Required |
| **Headers** | `Authorization: Bearer {JWT_TOKEN}` |
| **Path Variables** | `paymentMethod = RAZORPAY` or `STRIPE`, `amount = 5000` |

**Example URL:**
```
http://localhost:5454/api/payment/RAZORPAY/amount/5000
```

**Expected Response (Razorpay):**
```json
{
  "paymentUrl": "https://rzp.io/i/abc123xyz",
  "paymentLinkId": "plink_abc123xyz",
  "amount": 5000,
  "currency": "INR"
}
```

**Expected Response (Stripe):**
```json
{
  "paymentUrl": "https://checkout.stripe.com/pay/abc123xyz",
  "sessionId": "cs_test_abc123xyz",
  "amount": 5000
}
```

**Test Steps in Postman:**
1. Create new request, set method to `POST`
2. Enter URL: `http://localhost:5454/api/payment/RAZORPAY/amount/5000`
3. Add header: `Authorization` = `Bearer {jwt_token}`
4. Click Send
5. Copy payment URL and open in browser to complete payment
6. Save payment ID returned

---

## 10. PAYMENT DETAILS API - Save Payment Methods

### 10.1 Add Payment Details (Bank Account)

**Purpose:** Save bank account for withdrawal

| Property | Value |
|---|---|
| **Method** | `POST` |
| **URL** | `http://localhost:5454/api/payment-details` |
| **Authentication** | JWT Token Required |
| **Headers** | `Authorization: Bearer {JWT_TOKEN}` |
| **Content-Type** | `application/json` |

**Request Body:**
```json
{
  "accountNumber": "1234567890",
  "accountHolderName": "John Doe",
  "ifsc": "SBIN0001234",
  "bankName": "State Bank of India"
}
```

**Expected Response:**
```json
{
  "id": 1,
  "accountNumber": "****7890",
  "accountHolderName": "John Doe",
  "ifsc": "SBIN0001234",
  "bankName": "State Bank of India",
  "user": {
    "id": 1,
    "email": "user@example.com"
  }
}
```

**Test Steps in Postman:**
1. Create new request, set method to `POST`
2. Enter URL: `http://localhost:5454/api/payment-details`
3. Add header: `Authorization` = `Bearer {jwt_token}`
4. Go to **Body** tab → raw → JSON
5. Enter bank details (account number, IFSC, etc.)
6. Click Send

**Security Note:** Account numbers are masked in responses

---

### 10.2 Get Saved Payment Details

**Purpose:** Retrieve user's saved bank account information

| Property | Value |
|---|---|
| **Method** | `GET` |
| **URL** | `http://localhost:5454/api/payment-details` |
| **Authentication** | JWT Token Required |
| **Headers** | `Authorization: Bearer {JWT_TOKEN}` |

**Expected Response:**
```json
{
  "id": 1,
  "accountNumber": "****7890",
  "accountHolderName": "John Doe",
  "ifsc": "SBIN0001234",
  "bankName": "State Bank of India"
}
```

**Test Steps in Postman:**
1. Create new request, set method to `GET`
2. Enter URL: `http://localhost:5454/api/payment-details`
3. Add header: `Authorization` = `Bearer {jwt_token}`
4. Click Send

---

## 11. WITHDRAWAL API - Fund Withdrawal

### 11.1 Request Withdrawal

**Purpose:** Request to withdraw funds from wallet

| Property | Value |
|---|---|
| **Method** | `POST` |
| **URL** | `http://localhost:5454/api/withdrawal/{amount}` |
| **Authentication** | JWT Token Required |
| **Headers** | `Authorization: Bearer {JWT_TOKEN}` |
| **Path Variable** | `amount = 5000` |

**Example URL:**
```
http://localhost:5454/api/withdrawal/5000
```

**Expected Response:**
```json
{
  "id": 1,
  "amount": 5000,
  "user": {
    "id": 1,
    "email": "user@example.com"
  },
  "status": "PENDING",
  "date": "2026-02-22T10:30:00Z"
}
```

**Test Steps in Postman:**
1. Create new request, set method to `POST`
2. Enter URL: `http://localhost:5454/api/withdrawal/5000`
3. Add header: `Authorization` = `Bearer {jwt_token}`
4. Click Send
5. Withdrawal status starts as PENDING

---

### 11.2 Get Withdrawal History

**Purpose:** Retrieve user's withdrawal requests

| Property | Value |
|---|---|
| **Method** | `GET` |
| **URL** | `http://localhost:5454/api/withdrawal` |
| **Authentication** | JWT Token Required |
| **Headers** | `Authorization: Bearer {JWT_TOKEN}` |

**Expected Response:**
```json
[
  {
    "id": 1,
    "amount": 5000,
    "status": "PENDING",
    "date": "2026-02-22T10:30:00Z"
  },
  {
    "id": 2,
    "amount": 2000,
    "status": "COMPLETED",
    "date": "2026-02-21T15:45:00Z"
  }
]
```

**Test Steps in Postman:**
1. Create new request, set method to `GET`
2. Enter URL: `http://localhost:5454/api/withdrawal`
3. Add header: `Authorization` = `Bearer {jwt_token}`
4. Click Send

---

### 11.3 Admin: Proceed with Withdrawal

**Purpose:** Admin approval/rejection of withdrawal request

| Property | Value |
|---|---|
| **Method** | `PATCH` |
| **URL** | `http://localhost:5454/api/admin/withdrawal/{id}/proceed/{accept}` |
| **Authentication** | Admin JWT Token Required |
| **Headers** | `Authorization: Bearer {admin_jwt_token}` |
| **Path Variables** | `id = 1`, `accept = true` or `false` |

**Example URL:**
```
http://localhost:5454/api/admin/withdrawal/1/proceed/true
```

**Expected Response:**
```json
{
  "id": 1,
  "amount": 5000,
  "status": "COMPLETED",
  "date": "2026-02-22T10:30:00Z"
}
```

**Test Steps in Postman:**
1. Create new request, set method to `PATCH`
2. Enter URL: `http://localhost:5454/api/admin/withdrawal/1/proceed/true`
3. Add header: `Authorization` = `Bearer {admin_jwt_token}`
4. Click Send
5. Use `true` to approve or `false` to reject

---

## 12. TRANSACTION API - Transaction History

### 12.1 Get Transaction History (To be implemented)

**Purpose:** Retrieve wallet transaction history

| Property | Value |
|---|---|
| **Method** | `GET` |
| **URL** | `http://localhost:5454/api/transactions` |
| **Authentication** | JWT Token Required |
| **Headers** | `Authorization: Bearer {JWT_TOKEN}` |
| **Query Parameters** | `type=DEPOSIT` (optional filter) |

**Note:** This API is currently under development (Step 115)

---

## Testing Best Practices

### 1. Environment Variables in Postman

Instead of hardcoding tokens, use Postman environment variables:

1. Click **Environment** in top-left
2. Create new environment "Trading Platform"
3. Add variables:
   - `base_url`: `http://localhost:5454`
   - `jwt_token`: (empty initially)
   - `coin_id`: `bitcoin`

4. Use in requests:
   ```
   {{base_url}}/api/users/profile
   Authorization: Bearer {{jwt_token}}
   ```

### 2. Save JWT Token from Signup

After successful signup:
1. Go to **Tests** tab in signup request
2. Add script:
```javascript
if (pm.response.code === 201) {
    pm.environment.set("jwt_token", pm.response.json().jwt);
}
```

3. Every subsequent request will automatically use saved token

### 3. Test Sequence Workflow

**Required Order for Testing:**

```
1. HOME API (GET /) → Verify server
2. AUTH API (POST /auth/signup) → Get JWT token
3. AUTH API (POST /api/users/verification/EMAIL/send-otp) → Test 2FA
4. COIN API (GET /coins) → Test public endpoints
5. USER API (GET /api/users/profile) → Test protected endpoint
6. WALLET API (GET /api/wallet) → Get wallet balance
7. PAYMENT API (POST /api/payment) → Initiate payment
8. PAYMENT DETAILS API → Save payment method
9. ASSET API (GET /api/asset) → Check holdings
10. ORDER API (POST /api/orders/pay) → Create order
11. WITHDRAWAL API (POST /api/withdrawal) → Request withdrawal
12. WATCHLIST API (PATCH /api/watchlist/add/coin) → Add to watchlist
```

### 4. Common Headers to Add

**For All Protected Endpoints:**
```
Authorization: Bearer {JWT_TOKEN}
Content-Type: application/json
```

**Optional for Some APIs:**
```
Accept: application/json
```

### 5. Response Status Codes to Expect

| Status | Meaning |
|---|---|
| `200 OK` | Request successful, data returned |
| `201 CREATED` | Resource created successfully |
| `400 BAD REQUEST` | Invalid request data |
| `401 UNAUTHORIZED` | Missing/invalid JWT token |
| `403 FORBIDDEN` | Access denied |
| `404 NOT FOUND` | Resource not found |
| `500 INTERNAL ERROR` | Server error |

### 6. Common Error Responses

**Missing JWT Token:**
```json
{
  "message": "Unauthorized",
  "status": 403
}
```

**Invalid Email (Signup):**
```json
{
  "message": "email is already used with another account"
}
```

**Insufficient Wallet Balance:**
```json
{
  "message": "Insufficient balance in wallet"
}
```

### 7. Testing Tips

1. **Create Test Collections** - Group related requests
2. **Use Pre-request Scripts** - Auto-generate OTPs or tokens
3. **Enable Logging** - Save all request/response logs
4. **Test Data** - Use test email addresses for signup
5. **Rate Limiting** - Add delays between requests
6. **Documentation** - Keep notes of test results

### 8. Database Reset for Testing

If data becomes inconsistent:

```bash
# MySQL terminal
DROP DATABASE ez_trad_db;
CREATE DATABASE ez_trad_db;
```

Then restart the Spring Boot application - Hibernate will recreate tables.

---

## Summary Checklist

- [ ] Server running on `http://localhost:5454`
- [ ] Database connected and tables created
- [ ] JWT tokens generating on signup
- [ ] Protected endpoints requiring authorization
- [ ] Public endpoints accessible without token
- [ ] Payment gateways configured (Stripe & Razorpay)
- [ ] Email service sending OTPs and verification emails
- [ ] All 13 API sections tested successfully

---

## Next Steps After Testing

1. **Integration Testing** - Test cross-API flows
2. **Load Testing** - Use tools like JMeter for performance
3. **Security Testing** - Test JWT expiry, SQL injection, CORS
4. **UI Integration** - Connect frontend application
5. **Production Deployment** - Update configurations for live environment

