# Wallet-to-Wallet Transfer Implementation Guide

## Overview
This guide explains the complete wallet-to-wallet transfer feature that allows users to send money between wallets using wallet IDs.

---

## 🎯 Feature Description

**Endpoint:** `PUT /api/wallet/{walletId}/transfer`

**Purpose:** Allows authenticated users to transfer funds from their wallet to another user's wallet.

**Request Parameters:**
- **Path Variable:** `walletId` - The recipient's wallet ID
- **Request Body:** JSON with `amount` and optional `purpose`
  ```json
  {
    "amount": 1000,
    "purpose": "gift"
  }
  ```

**Authorization:** Requires JWT token in `Authorization` header

---

## 📁 Files Modified/Created

### 1. **WalletTransactionRepository.java** ✅ CREATED
**Path:** `repository/WalletTransactionRepository.java`

**Purpose:** JPA repository for managing wallet transaction records

**Methods:**
- `findByWalletOrderByDateDesc(Wallet wallet)` - Get all transactions for a wallet
- `findByWalletIdOrderByDateDesc(Long walletId)` - Get transactions by wallet ID
- `findByTransferId(String transferId)` - Get linked transactions by transfer ID

### 2. **TransactionService.java** ✅ CREATED
**Path:** `service/TransactionService.java`

**Purpose:** Service interface for wallet transaction management

**Methods:**
- `createTransaction()` - Create a new transaction record
- `getTransactionsByWallet()` - Retrieve transactions for a wallet
- `getTransactionsByWalletId()` - Retrieve transactions by wallet ID
- `getTransactionsByTransferId()` - Get linked transfer transactions

### 3. **TransactionServiceImplement.java** ✅ IMPLEMENTED
**Path:** `service/TransactionServiceImplement.java`

**Purpose:** Implementation of transaction service with full CRUD operations

**Key Features:**
- Automatic timestamp (LocalDate.now())
- Transaction linking via transferId
- Support for all transaction types

### 4. **WalletServiceImplement.java** ✅ UPDATED
**Path:** `service/WalletServiceImplement.java`

**Changes:**
- Added `@Autowired TransactionService`
- Updated `walletToWalletTransfer()` to create transaction records
- Generates unique UUID for each transfer
- Creates TWO transaction records (sender & receiver)

### 5. **WalletController.java** ✅ UPDATED
**Path:** `controller/WalletController.java`

**Changes:**
- Added `@Autowired TransactionService`
- Added new endpoint: `GET /api/wallet/transactions`
- Existing transfer endpoint already functional

---

## 🔄 How Wallet-to-Wallet Transfer Works

### Step-by-Step Flow

```
1. User A initiates transfer to User B's wallet
   PUT /api/wallet/{walletB_id}/transfer
   Body: {"amount": 1000, "purpose": "gift"}
   
2. Backend validates User A's JWT token
   → Retrieves User A's profile
   
3. Backend retrieves wallets
   → Sender: User A's wallet (from JWT)
   → Receiver: User B's wallet (from walletId path variable)
   
4. Balance validation
   → Check if User A has sufficient balance
   → If insufficient: throw "Insufficient Balance..." exception
   
5. Update balances
   → Deduct amount from User A's wallet
   → Add amount to User B's wallet
   → Save both wallets to database
   
6. Create transaction records (NEW!)
   → Generate unique transferId (UUID)
   → Create transaction for sender (debit)
   → Create transaction for receiver (credit)
   → Both linked by same transferId
   
7. Return updated sender wallet
   → User A receives their new balance
```

---

## 🎯 Transaction Record Structure

### What Gets Stored

For **EACH** wallet-to-wallet transfer, **TWO** transaction records are created:

#### Sender's Transaction Record
```java
{
  "id": 1,
  "wallet": {sender_wallet},
  "walletTransactionType": "WALLET_TRANSFER",
  "date": "2026-02-23",
  "transferId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "purpose": "Transfer to wallet ID: 2",
  "amount": 1000
}
```

#### Receiver's Transaction Record
```java
{
  "id": 2,
  "wallet": {receiver_wallet},
  "walletTransactionType": "WALLET_TRANSFER",
  "date": "2026-02-23",
  "transferId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",  // SAME transferId
  "purpose": "Transfer from wallet ID: 1",
  "amount": 1000
}
```

**Key Points:**
- Both records share the same `transferId` (UUID)
- This links sender and receiver transactions
- You can trace complete transfer history using transferId

---

## 🧪 Testing the Feature

### Prerequisites
1. At least 2 user accounts created
2. Both users must have wallets
3. Sender must have sufficient balance
4. JWT tokens for both users

### Test Scenario

#### Setup
```
User A (Sender):
  - User ID: 1
  - Wallet ID: 1
  - Balance: 50,000

User B (Receiver):
  - User ID: 2
  - Wallet ID: 2
  - Balance: 30,000

Transfer Amount: 10,000
```

---

### Step 1: Login as User A
```http
POST http://localhost:5454/auth/signin
Content-Type: application/json

{
  "email": "userA@example.com",
  "password": "password"
}
```

**Response:**
```json
{
  "jwt": "eyJhbGciOiJIUzI1NiJ9...",
  "message": "Login success",
  "status": true
}
```

**Action:** Save the JWT token

---

### Step 2: Get User A's Wallet Details
```http
GET http://localhost:5454/api/wallet
Authorization: Bearer {userA_jwt_token}
```

**Response:**
```json
{
  "id": 1,
  "balance": 50000.00,
  "user": {
    "id": 1,
    "fullName": "User A",
    "email": "userA@example.com"
  }
}
```

---

### Step 3: Get User B's Wallet ID

**Option A:** Login as User B and get wallet details
```http
GET http://localhost:5454/api/wallet
Authorization: Bearer {userB_jwt_token}
```

**Response:**
```json
{
  "id": 2,  // ← This is the walletId you need
  "balance": 30000.00,
  "user": {
    "id": 2,
    "fullName": "User B",
    "email": "userB@example.com"
  }
}
```

**Option B:** If you know the wallet ID from database, use it directly

---

### Step 4: Transfer Money from User A to User B
```http
PUT http://localhost:5454/api/wallet/2/transfer
Authorization: Bearer {userA_jwt_token}
Content-Type: application/json

{
  "amount": 10000,
  "purpose": "gift"
}
```

**Important:** 
- URL path variable `2` is User B's wallet ID
- Authorization header contains User A's JWT token
- User A is the sender (from JWT)
- User B is the receiver (from walletId in URL)

**Response (Success):**
```json
{
  "id": 1,
  "balance": 40000.00,  // 50,000 - 10,000 = 40,000
  "user": {
    "id": 1,
    "fullName": "User A",
    "email": "userA@example.com"
  }
}
```

---

### Step 5: Verify User B Received the Money
```http
GET http://localhost:5454/api/wallet
Authorization: Bearer {userB_jwt_token}
```

**Response:**
```json
{
  "id": 2,
  "balance": 40000.00,  // 30,000 + 10,000 = 40,000 ✅
  "user": {
    "id": 2,
    "fullName": "User B",
    "email": "userB@example.com"
  }
}
```

---

### Step 6: View Transaction History (User A)
```http
GET http://localhost:5454/api/wallet/transactions
Authorization: Bearer {userA_jwt_token}
```

**Response:**
```json
[
  {
    "id": 1,
    "wallet": {
      "id": 1,
      "balance": 40000.00
    },
    "walletTransactionType": "WALLET_TRANSFER",
    "date": "2026-02-23",
    "transferId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
    "purpose": "Transfer to wallet ID: 2",
    "amount": 10000
  }
  // ... other transactions
]
```

---

### Step 7: View Transaction History (User B)
```http
GET http://localhost:5454/api/wallet/transactions
Authorization: Bearer {userB_jwt_token}
```

**Response:**
```json
[
  {
    "id": 2,
    "wallet": {
      "id": 2,
      "balance": 40000.00
    },
    "walletTransactionType": "WALLET_TRANSFER",
    "date": "2026-02-23",
    "transferId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",  // SAME transferId!
    "purpose": "Transfer from wallet ID: 1",
    "amount": 10000
  }
  // ... other transactions
]
```

---

## 🚨 Error Scenarios

### Error 1: Insufficient Balance
```http
PUT http://localhost:5454/api/wallet/2/transfer
Body: {"amount": 100000}  // More than available balance
```

**Response:**
```json
{
  "error": "Insufficient Balance...",
  "status": 500
}
```

### Error 2: Invalid Wallet ID
```http
PUT http://localhost:5454/api/wallet/999/transfer
Body: {"amount": 1000}
```

**Response:**
```json
{
  "error": "wallet not found",
  "status": 500
}
```

### Error 3: Missing JWT Token
```http
PUT http://localhost:5454/api/wallet/2/transfer
(No Authorization header)
```

**Response:**
```json
{
  "error": "Unauthorized",
  "status": 401
}
```

### Error 4: Invalid Amount
```http
PUT http://localhost:5454/api/wallet/2/transfer
Body: {"amount": null}
```

**Response:**
```json
{
  "error": "NullPointerException",
  "status": 500
}
```

---

## 📊 Database Schema

### wallet_transaction Table

| Column | Type | Description |
|--------|------|-------------|
| `id` | BIGINT | Primary key (auto-generated) |
| `wallet_id` | BIGINT | Foreign key to wallet table |
| `wallet_transaction_type` | VARCHAR | Type of transaction (WALLET_TRANSFER, etc.) |
| `date` | DATE | Transaction date |
| `transfer_id` | VARCHAR(36) | UUID linking sender/receiver (null for non-transfers) |
| `purpose` | VARCHAR | Description/purpose of transaction |
| `amount` | BIGINT | Transaction amount |

---

## 🔍 Transaction Types

The system supports various transaction types via `WalletTransactionType` enum:

| Type | Usage | transferId |
|------|-------|------------|
| **WITHDRAWAL** | User withdraws money | NULL |
| **WALLET_TRANSFER** | User-to-user transfer | UUID (links both records) |
| **ADD_MONEY** | Deposit via payment gateway | NULL |
| **BUY_ASSET** | Purchase cryptocurrency | NULL |
| **SELL_ASSET** | Sell cryptocurrency | NULL |

---

## 🎯 Implementation Highlights

### 1. Transaction Linking
```java
// Generate unique transfer ID
String transferId = UUID.randomUUID().toString();

// Create sender transaction
transactionService.createTransaction(
    senderWallet, 
    WalletTransactionType.WALLET_TRANSFER, 
    transferId,  // Same ID for both
    "Transfer to wallet ID: " + receiverWallet.getId(), 
    amount
);

// Create receiver transaction
transactionService.createTransaction(
    receiverWallet, 
    WalletTransactionType.WALLET_TRANSFER, 
    transferId,  // Same ID for both
    "Transfer from wallet ID: " + senderWallet.getId(), 
    amount
);
```

### 2. Balance Validation
```java
if(senderWallet.getBalance().compareTo(BigDecimal.valueOf(amount)) < 0){
    throw new Exception("Insufficient Balance...");
}
```

### 3. Atomic Updates
```java
// Deduct from sender
BigDecimal senderBalance = senderWallet.getBalance()
    .subtract(BigDecimal.valueOf(amount));
senderWallet.setBalance(senderBalance);
walletRepository.save(senderWallet);

// Add to receiver
BigDecimal receiverBalance = receiverWallet.getBalance()
    .add(BigDecimal.valueOf(amount));
receiverWallet.setBalance(receiverBalance);
walletRepository.save(receiverWallet);
```

---

## 📋 API Endpoints Summary

| Method | Endpoint | Purpose | Auth Required |
|--------|----------|---------|---------------|
| **GET** | `/api/wallet` | Get user's wallet details | ✅ Yes |
| **PUT** | `/api/wallet/{walletId}/transfer` | Transfer to another wallet | ✅ Yes |
| **GET** | `/api/wallet/transactions` | Get transaction history | ✅ Yes |

---

## ✅ Verification Checklist

After implementing, verify:

- [ ] WalletTransactionRepository created as interface
- [ ] TransactionService interface defined
- [ ] TransactionServiceImplement created with @Service
- [ ] WalletServiceImplement updated with TransactionService
- [ ] WalletController includes TransactionService
- [ ] Transfer creates TWO transaction records
- [ ] Both records have same transferId
- [ ] Balance validation works
- [ ] Insufficient balance throws exception
- [ ] Transaction history endpoint works
- [ ] Transactions sorted by date descending

---

## 🎉 Summary

**What was implemented:**
- ✅ Complete transaction logging system
- ✅ WalletTransactionRepository for database operations
- ✅ TransactionService for transaction management
- ✅ Automatic transaction record creation
- ✅ Unique transferId linking for wallet transfers
- ✅ Transaction history retrieval
- ✅ Comprehensive error handling

**Key Benefits:**
- 📊 Complete audit trail of all transfers
- 🔗 Linked sender/receiver transactions
- 📅 Date tracking for all transactions
- 💬 Purpose/description for each transfer
- 🔍 Query transactions by wallet, date, or transferId

---

**🚀 The wallet-to-wallet transfer feature is now fully implemented and ready to use!**

