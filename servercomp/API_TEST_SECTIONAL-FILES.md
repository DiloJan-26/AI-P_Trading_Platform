# API Test Sectional Files Mapping

This document maps each API section to its related files for comprehensive API testing and understanding.

---

## 1. HOME API

| Component | File | Purpose |
|---|---|---|
| **Controller** | `HomeController.java` | Entry point, public & protected endpoints |
| **Domain** | - | No domain enums needed |
| **Model** | - | No database model |
| **Service** | - | No business logic needed |
| **Repository** | - | No database queries |
| **Request** | - | No request DTO |
| **Response** | - | Plain text response |
| **Config** | `AppConfig.java` | Security setup for public/protected routes |

**Endpoints:**
- `GET /` (public) - Step -3
- `GET /api` (protected) - Step 20

---

## 2. AUTH API

| Component | File | Purpose |
|---|---|---|
| **Controller** | `AuthController.java` | Login, register, logout endpoints |
| **Domain** | `USER_ROLE.java` | User role enumeration |
| **Model** | `User.java`, `TwoFactorAuth.java`, `TwoFactorOTP.java`, `VerificationCode.java` | User account, 2FA settings, OTP data |
| **Service** | `UserService.java`, `UserServiceImplement.java`, `CustomUserDetailsService.java`, `TwoFactorOTPService.java`, `TwoFactorOTPServiceImplement.java`, `VerificationCodeService.java`, `VerificationCodeServiceImplement.java` | User authentication, 2FA logic, verification |
| **Repository** | `UserRepository.java`, `TwoFactorOTPRepository.java`, `VerificationCodeRepository.java` | Query user data, OTP, verification codes |
| **Request** | `ForgotPasswordTokenRequest.java`, `ResetPasswordRequest.java` | Password reset requests |
| **Response** | `AuthResponse.java`, `ApiResponse.java` | Auth token, user info response |
| **Config** | `JwtProvider.java`, `JwtTokenValidator.java`, `JwtConstant.java` | JWT generation, validation, constants |
| **Utils** | - | Email verification utilities |

**Related Operations:**
- User registration with role assignment
- Login with JWT token generation
- 2FA enablement and OTP verification
- Password reset flow

---

## 3. USER API

| Component | File | Purpose |
|---|---|---|
| **Controller** | `UserController.java` | User profile, update, fetch endpoints |
| **Domain** | `USER_ROLE.java`, `VerificationType.java` | Role & verification types |
| **Model** | `User.java`, `VerificationCode.java` | User profile data, verification |
| **Service** | `UserService.java`, `UserServiceImplement.java`, `VerificationCodeService.java`, `VerificationCodeServiceImplement.java`, `EmailService.java` | User CRUD operations, email verification |
| **Repository** | `UserRepository.java`, `VerificationCodeRepository.java` | User queries, verification lookups |
| **Request** | - | Uses User model directly |
| **Response** | `ApiResponse.java` | Generic API response |
| **Config** | `AppConfig.java`, `JwtTokenValidator.java` | Authentication filter |
| **Utils** | - | User validation utilities |

**Related Operations:**
- Get user profile
- Update user information
- Verify user email
- Fetch user by ID

---

## 4. COIN API

| Component | File | Purpose |
|---|---|---|
| **Controller** | `CoinController.java` | Fetch coins, market data endpoints |
| **Domain** | - | No enums needed |
| **Model** | `Coin.java` | Cryptocurrency data (price, market cap, etc.) |
| **Service** | `CoinService.java`, `CoinServiceImplement.java` | Coin data retrieval & processing |
| **Repository** | `CoinRepository.java` | Database coin queries |
| **Request** | - | Query parameters only |
| **Response** | `ApiResponse.java` | Coin list/details response |
| **Config** | - | Standard security applies |
| **Utils** | - | Market data formatting |

**Related Operations:**
- List all coins
- Get coin by ID
- Search coins by name/symbol
- Fetch market prices

---

## 5. ASSET API

| Component | File | Purpose |
|---|---|---|
| **Controller** | `AssetController.java` | User asset holdings endpoints |
| **Domain** | - | No enums needed |
| **Model** | `Asset.java` | User cryptocurrency holdings |
| **Service** | `AssetService.java`, `AssetServiceImplement.java`, `WalletService.java`, `WalletServiceImplement.java` | Asset management, wallet integration |
| **Repository** | `AssetRepository.java`, `WalletRepository.java` | Asset queries, wallet balance lookups |
| **Request** | - | Uses Asset model |
| **Response** | `ApiResponse.java` | Asset list response |
| **Config** | `AppConfig.java`, `JwtTokenValidator.java` | User authentication required |
| **Utils** | - | Asset valuation calculations |

**Related Operations:**
- Get user assets
- Calculate asset portfolio value
- Track asset quantities
- Link to wallet holdings

---

## 6. ORDER API

| Component | File | Purpose |
|---|---|---|
| **Controller** | `OrderController.java` | Buy/sell order management endpoints |
| **Domain** | `OrderType.java`, `OrderStatus.java` | Order type (BUY/SELL), status (PENDING/COMPLETED) |
| **Model** | `Order.java`, `OrderItem.java` | Order details, line items |
| **Service** | `OrderService.java`, `OrderServiceImplement.java`, `WalletService.java`, `WalletServiceImplement.java` | Order creation, execution, wallet updates |
| **Repository** | `OrderRepository.java`, `OrderItemRepository.java`, `WalletRepository.java` | Order queries, item lookups, wallet balance |
| **Request** | `CreateOrderRequest.java` | Order creation payload |
| **Response** | `ApiResponse.java` | Order confirmation response |
| **Config** | `AppConfig.java`, `JwtTokenValidator.java` | User authentication required |
| **Utils** | - | Order validation, price calculations |

**Related Operations:**
- Create BUY order
- Create SELL order
- Cancel order
- Get order history
- Update order status

---

## 7. WALLET API

| Component | File | Purpose |
|---|---|---|
| **Controller** | `WalletController.java` | Wallet balance, transfer endpoints |
| **Domain** | `WalletTransactionType.java` | Transaction type (DEPOSIT/WITHDRAWAL/TRANSFER) |
| **Model** | `Wallet.java`, `WalletTransaction.java` | Wallet balance, transaction ledger |
| **Service** | `WalletService.java`, `WalletServiceImplement.java`, `TransactionService.java`, `TransactionServiceImplement.java` | Wallet operations, transaction logging |
| **Repository** | `WalletRepository.java`, `WalletTransactionRepository.java` | Wallet queries, transaction history |
| **Request** | - | Transfer amount parameters |
| **Response** | `ApiResponse.java` | Wallet balance response |
| **Config** | `AppConfig.java`, `JwtTokenValidator.java` | User authentication required |
| **Utils** | - | Balance calculations, fund availability checks |

**Related Operations:**
- Get wallet balance
- Add funds to wallet
- Transfer between wallets
- Wallet transaction history

---

## 8. PAYMENT API

| Component | File | Purpose |
|---|---|---|
| **Controller** | `PaymentController.java` | Payment processing endpoints |
| **Domain** | `PaymentMethod.java`, `PaymentOrderStatus.java` | Payment method (CARD/UPI), status |
| **Model** | `PaymentOrder.java`, `PaymentDetails.java` | Payment order details, card/UPI info |
| **Service** | `PaymentService.java`, `PaymentServiceImplement.java`, `PaymentDetailsService.java`, `PaymentDetailsServiceImplement.java` | Payment processing, gateway integration |
| **Repository** | `PaymentOrderRepository.java`, `PaymentDetailsRepository.java` | Payment queries, stored payment methods |
| **Request** | - | Payment payload |
| **Response** | `PaymentResponse.java`, `ApiResponse.java` | Payment confirmation, status |
| **Config** | `application.properties` | Stripe & Razorpay API keys |
| **Utils** | - | Payment validation, gateway communication |

**Related Operations:**
- Initiate payment
- Verify payment status
- Store payment method
- Payment history

---

## 9. PAYMENT DETAILS API

| Component | File | Purpose |
|---|---|---|
| **Controller** | `PaymentDetailsController.java` | Save/manage payment methods endpoints |
| **Domain** | `PaymentMethod.java` | Payment method types |
| **Model** | `PaymentDetails.java`, `User.java` | Stored payment info, user reference |
| **Service** | `PaymentDetailsService.java`, `PaymentDetailsServiceImplement.java` | Payment method CRUD operations |
| **Repository** | `PaymentDetailsRepository.java`, `UserRepository.java` | Payment details queries |
| **Request** | - | Payment method data |
| **Response** | `ApiResponse.java` | Saved payment methods list |
| **Config** | `AppConfig.java`, `JwtTokenValidator.java` | User authentication required |
| **Utils** | - | Payment data encryption/masking |

**Related Operations:**
- Add payment method
- List saved payment methods
- Delete payment method
- Update payment method

---

## 10. TRANSACTION API

| Component | File | Purpose |
|---|---|---|
| **Controller** | `TransactionController.java` | Transaction history endpoints |
| **Domain** | `WalletTransactionType.java` | Transaction classification |
| **Model** | `WalletTransaction.java`, `Wallet.java` | Transaction records, wallet reference |
| **Service** | `TransactionService.java`, `TransactionServiceImplement.java` | Transaction retrieval, filtering |
| **Repository** | `WalletTransactionRepository.java`, `WalletRepository.java` | Transaction queries by user/type |
| **Request** | - | Filter parameters (date range, type) |
| **Response** | `ApiResponse.java` | Transaction list response |
| **Config** | `AppConfig.java`, `JwtTokenValidator.java` | User authentication required |
| **Utils** | - | Transaction sorting, pagination |

**Related Operations:**
- Get transaction history
- Filter by transaction type
- Get transaction details
- Export transaction report

---

## 11. WATCHLIST API

| Component | File | Purpose |
|---|---|---|
| **Controller** | `WatchListController.java` | Manage watchlist endpoints |
| **Domain** | - | No enums needed |
| **Model** | `Watchlist.java`, `Coin.java`, `User.java` | Watchlist entries, coin reference, user |
| **Service** | `WatchlistService.java`, `WatchlistServiceImplement.java`, `CoinService.java` | Watchlist CRUD, coin price updates |
| **Repository** | `WatchlistRepository.java`, `CoinRepository.java`, `UserRepository.java` | Watchlist queries |
| **Request** | - | Coin ID for add/remove |
| **Response** | `ApiResponse.java` | Updated watchlist response |
| **Config** | `AppConfig.java`, `JwtTokenValidator.java` | User authentication required |
| **Utils** | - | Watchlist formatting, price alerts |

**Related Operations:**
- Add coin to watchlist
- Remove coin from watchlist
- Get user watchlist
- Get watchlist with current prices

---

## 12. WITHDRAWAL API

| Component | File | Purpose |
|---|---|---|
| **Controller** | `WithdrawalController.java` | Withdrawal request endpoints |
| **Domain** | `WithdrawalStatus.java` | Withdrawal status (PENDING/COMPLETED/REJECTED) |
| **Model** | `Withdrawal.java`, `Wallet.java`, `User.java` | Withdrawal request, wallet deduction, user |
| **Service** | `WithdrawalService.java`, `WithdrawalServiceImplement.java`, `WalletService.java` | Withdrawal processing, wallet updates |
| **Repository** | `WithdrawalRepository.java`, `WalletRepository.java`, `UserRepository.java` | Withdrawal queries, wallet balance |
| **Request** | - | Withdrawal amount, method |
| **Response** | `ApiResponse.java` | Withdrawal confirmation response |
| **Config** | `AppConfig.java`, `JwtTokenValidator.java` | User authentication required |
| **Utils** | - | Amount validation, status updates |

**Related Operations:**
- Request withdrawal
- Get withdrawal history
- Update withdrawal status
- Cancel pending withdrawal

---

## 13. FORGOT PASSWORD API

| Component | File | Purpose |
|---|---|---|
| **Controller** | `AuthController.java` (password endpoints) | Password reset flow endpoints |
| **Domain** | - | No enums needed |
| **Model** | `ForgotPasswordToken.java`, `User.java` | Reset token, user reference |
| **Service** | `ForgotPasswordService.java`, `ForgotPasswordServiceImplement.java`, `EmailService.java`, `UserService.java` | Token generation, email sending, password update |
| **Repository** | `ForgotPasswordRepository.java`, `UserRepository.java` | Token queries, user password update |
| **Request** | `ForgotPasswordTokenRequest.java`, `ResetPasswordRequest.java` | Email, token, new password |
| **Response** | `ApiResponse.java` | Success/failure response |
| **Config** | - | Email configuration (via EmailService) |
| **Utils** | - | Token generation, expiry validation |

**Related Operations:**
- Request password reset
- Send reset email with token
- Validate reset token
- Update password with token

---

## File Dependencies Summary

### By Folder Type:

**Controllers** (12 files)
- Entry points for each API section
- Handle HTTP requests/responses
- Delegate to services

**Models** (15 files)
- Database entities with @Entity
- Define data structure
- Embedded components (TwoFactorAuth)

**Services** (26 files)
- Business logic layer
- Interface + Implementation pattern
- Connect controllers to repositories

**Repositories** (14 files)
- Database access layer
- JPA queries
- Spring Data interfaces

**Domain Enums** (8 files)
- Status values
- Type classifications
- Role definitions

**Request/Response** (6 files)
- Data Transfer Objects (DTOs)
- API input/output schemas
- Response wrappers

**Config** (4 files)
- Security configuration
- JWT token management
- Application setup

---

## Cross-Cutting Concerns

| Component | Used By | Purpose |
|---|---|---|
| **JwtProvider** | AuthController, All Protected APIs | Token generation |
| **JwtTokenValidator** | AppConfig, All Protected APIs | Token validation filter |
| **CustomUserDetailsService** | Spring Security | Load user for authentication |
| **EmailService** | AuthController, ForgotPasswordService | Send verification/reset emails |
| **AppConfig** | Entire Application | Security & CORS setup |

---

## Testing Sequence Recommendation

1. **HOME API** - Verify server health (public endpoint)
2. **AUTH API** - Test registration, login, 2FA flow
3. **USER API** - Test profile operations (requires auth)
4. **COIN API** - Test cryptocurrency data retrieval
5. **ASSET API** - Test user holdings (requires auth)
6. **WALLET API** - Test wallet balance & transfers (requires auth)
7. **PAYMENT DETAILS API** - Test payment method storage
8. **PAYMENT API** - Test payment processing
9. **ORDER API** - Test buy/sell orders
10. **TRANSACTION API** - Test transaction history
11. **WATCHLIST API** - Test watchlist management
12. **WITHDRAWAL API** - Test withdrawal requests
13. **FORGOT PASSWORD API** - Test password recovery

---

## Notes

- **Protected APIs** require valid JWT token in Authorization header
- **Services** use interface + implementation pattern for flexibility
- **Repositories** extend JpaRepository for CRUD operations
- **Enums** in `domain` folder define allowed values
- **Config files** in resources folder define external service keys (Stripe, Razorpay)

