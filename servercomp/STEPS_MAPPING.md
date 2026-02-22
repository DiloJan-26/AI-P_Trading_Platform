# AI-P Trading Platform - Development Steps Mapping

## Complete Step-by-Step Guide Organized by File

This document maps all development steps (numbered comments) to their respective files in the project.

---

## Configuration & Setup Steps (Step 1-2)

| Step | File | Location | Description |
|------|------|----------|-------------|
| **1** | `application.properties` | Line 3 | Server port configuration (commented: 5455) |
| **2** | `application.properties` | Line 6 | MySQL driver class and database configuration |
| **115-117** | `application.properties` | Line 6 | Updated server port (5454) |
| **118** | `application.properties` | Line 15 | Database cleanup instructions |

---

## Domain Enums Steps (Step 5-8)

| Step | File | Location | Description |
|------|------|----------|-------------|
| **5** | `domain/USER_ROLE.java` | Line 1 | User role enum created (ROLE_ADMIN, ROLE_CUSTOMER) |
| **8** | `domain/VerificationType.java` | Line 5 | Verification type enum (MOBILE, EMAIL) |
| **69** | `domain/OrderStatus.java` | Line 3 | Order status enum (PENDING, FAILED, CANCELLED, etc.) |
| **68** | `domain/OrderType.java` | Line 3 | Order type enum (BUY, SELL) |
| **73** | `domain/WalletTransactionType.java` | Line 3 | Wallet transaction type enum (WITHDRAWAL, BUY_ASSET, SELL_ASSET, etc.) |
| **91** | `domain/WithdrawalStatus.java` | Line 3 | Withdrawal status enum (PENDING, SUCCESS, DECLINED) |
| **107** | `domain/PaymentOrderStatus.java` | Line 3 | Payment order status enum (PENDING, SUCCESS, FAILED) |
| **108** | `domain/PaymentMethod.java` | Line 3 | Payment method enum (RAZORPAY, STRIPE) |

---

## Model/Entity Classes Steps (Step 4, 7, 9-10, 30, 44, 50, 58, 63-64, 70, 72, 82, 90, 96, 101-102)

| Step | File | Location | Description |
|------|------|----------|-------------|
| **4** | `model/User.java` | Line 1 | User class created with basic properties |
| **6** | `model/User.java` | Line 24 | User role added to User class |
| **7** | `model/TwoFactorAuth.java` | Line 1 | Two-factor auth model created |
| **9** | `model/User.java` | Line 22 | Two-factor auth embedded in User class |
| **30** | `model/TwoFactorOTP.java` | Line 10 | Two-factor OTP entity for 2FA |
| **44** | `model/VerificationCode.java` | Line 8 | Verification code model for email/mobile OTP |
| **50** | `model/ForgotPasswordToken.java` | Line 7 | Forgot password token model |
| **58** | `model/Coin.java` | Line 14 | Coin model for CoinGecko API integration |
| **63** | `model/Wallet.java` | Line 7 | Wallet model (1-1 with User) |
| **64** | `model/Order.java` | Line 11 | Order model for trading orders |
| **70** | `model/OrderItem.java` | Line 4 | OrderItem model for order details |
| **72** | `model/WalletTransaction.java` | Line 8 | Wallet transaction tracking model |
| **82** | `model/Asset.java` | Line 4 | Asset model for user crypto holdings |
| **90** | `model/Withdrawal.java` | Line 7 | Withdrawal model for withdrawal requests |
| **96** | `model/Watchlist.java` | Line 8 | Watchlist model for favorite coins |
| **101-102** | `model/PaymentDetails.java` | N/A | Payment details model (bank info) |
| **106** | `model/PaymentOrder.java` | Line 9 | Payment order model for payment tracking |

---

## Controller Steps (Step 3, 11, 20, 43, 61, 71, 80, 86, 95, 100, 105, 114)

| Step | File | Location | Description |
|------|------|----------|-------------|
| **-3** | `controller/HomeController.java` | Line 1 | Home controller with public endpoint |
| **11** | `controller/AuthController.java` | Line 23 | Authentication controller (signup/signin) |
| **20** | `controller/HomeController.java` | Line 16 | Secured endpoint added to HomeController |
| **40** | `controller/AuthController.java` | Line 163 | 2FA OTP verification endpoint |
| **43** | `controller/UserController.java` | Line 21 | User profile and 2FA management controller |
| **61** | `controller/CoinController.java` | Line 14 | Coin market data endpoints |
| **71** | `controller/WalletController.java` | Line 2 | Wallet management controller |
| **80** | `controller/OrderController.java` | Line 19 | Order creation and management controller |
| **86** | `controller/AssetController.java` | Line 8 | User asset portfolio controller |
| **95** | `controller/WithdrawalController.java` | Line 15 | Withdrawal request handling controller |
| **100** | `controller/WatchListController.java` | Line 17 | Watchlist management controller |
| **105** | `controller/PaymentDetailsController.java` | Line 15 | Payment details management controller |
| **114** | `controller/PaymentController.java` | Line 14 | Payment gateway integration controller |

---

## Service Interface Steps (Step 31, 41, 46, 52, 54, 58, 62-64, 73-74, 83, 92, 97, 103, 109)

| Step | File | Location | Description |
|------|------|----------|-------------|
| **31** | `service/TwoFactorOTPService.java` | Line 5 | 2FA OTP service interface |
| **41** | `service/UserService.java` | Line 6 | User service interface |
| **46** | `service/VerificationCodeService.java` | Line 8 | Verification code service interface |
| **52** | `service/ForgotPasswordService.java` | Line 7 | Forgot password service interface |
| **54** | `service/ForgotPasswordService.java` | Line 7 | Forgot password service (refer to Step 52) |
| **58** | `service/CoinService.java` | Line 7 | Coin API service interface |
| **62** | `service/WalletService.java` | Line 7 | Wallet service interface |
| **73** | `service/OrderService.java` | Line 11 | Order service interface |
| **83** | `service/AssetService.java` | Line 9 | Asset service interface |
| **92** | `service/WithdrawalService.java` | Line 8 | Withdrawal service interface |
| **97** | `service/WatchlistService.java` | Line 7 | Watchlist service interface |
| **103** | `service/PaymentDetailsService.java` | N/A | Payment details service interface |
| **109** | `service/PaymentService.java` | Line 9 | Payment service interface |

---

## Service Implementation Steps (Step 21, 23, 32, 34, 37-39, 42, 47, 53, 59-60, 65-67, 75-76, 85-88, 93-95, 98-100, 103-104, 111-113)

| Step | File | Location | Description |
|------|------|----------|-------------|
| **21** | `service/CustomUserDetailsService.java` | Line 15 | Custom user details service for Spring Security |
| **23** | `service/CustomUserDetailsService.java` | Line 27 | User/password setup from database |
| **32** | `service/TwoFactorOTPServiceImplement.java` | Line 15 | 2FA OTP service implementation |
| **34** | `service/TwoFactorOTPServiceImplement.java` | Line 18 | TwoFactorOTP repository autowired |
| **37** | `service/TwoFactorOTPServiceImplement.java` | Line 18 | Used in AuthController for OTP handling |
| **38** | `service/EmailService.java` | Line 11 | Email service for sending OTP emails |
| **39** | `service/AuthController.java` | Line 152 | Email OTP sending in signin flow |
| **42** | `service/UserServiceImplement.java` | Line 12 | User service implementation |
| **47** | `service/VerificationCodeServiceImplement.java` | Line 14 | Verification code service implementation |
| **53** | `service/ForgotPasswordServiceImplement.java` | Line 13 | Forgot password service implementation |
| **59** | `service/CoinServiceImplement.java` | Line 21 | Coin service implementation |
| **60** | `service/CoinServiceImplement.java` | Line 21 | Coin repository referenced |
| **65** | `service/WalletServiceImplement.java` | Line 14 | Wallet service implementation |
| **66** | `service/WalletServiceImplement.java` | Line 15 | Wallet repository autowired |
| **67** | `service/WalletServiceImplement.java` | Line 69 | Order model referenced |
| **75** | `service/OrderServiceImplement.java` | Line 19 | Order service implementation |
| **76** | `service/OrderServiceImplement.java` | Line 78 | CreateOrderItem method in buyAsset |
| **77** | `service/OrderServiceImplement.java` | Line 29 | OrderItem repository autowired |
| **78** | `service/OrderServiceImplement.java` | Line 82 | Buy asset implementation |
| **85** | `service/AssetServiceImplement.java` | Line 12 | Asset service implementation |
| **86** | `service/AssetServiceImplement.java` | Line 65 | Asset controller reference |
| **87** | `service/OrderServiceImplement.java` | Line 105 | Asset creation in buyAsset |
| **88** | `service/OrderServiceImplement.java` | Line 132 | Asset selling logic |
| **93** | `service/WithdrawalServiceImplement.java` | Line 14 | Withdrawal service implementation |
| **94** | `service/WithdrawalServiceImplement.java` | Line 15 | Withdrawal repository creation |
| **95** | `service/WithdrawalServiceImplement.java` | Line 61 | Withdrawal controller reference |
| **98** | `service/WatchlistServiceImplement.java` | Line 12 | Watchlist service implementation |
| **99** | `service/WatchlistServiceImplement.java` | Line 13 | Watchlist repository creation |
| **100** | `service/WatchlistServiceImplement.java` | Line 60 | WatchListController reference |
| **103** | `service/PaymentDetailsServiceImplement.java` | Line 10 | Payment details service implementation |
| **104** | `service/PaymentDetailsServiceImplement.java` | Line 10 | Payment details repository creation |
| **111** | `service/PaymentServiceImplement.java` | Line 21 | Payment service implementation |
| **112** | `service/PaymentServiceImplement.java` | Line 21 | Payment order repository creation |
| **113** | `service/PaymentServiceImplement.java` | Line 76 | Razorpay & Stripe dependencies added to pom.xml |

---

## Request/Response Classes Steps (Step 27, 55, 56, 57, 81, 110)

| Step | File | Location | Description |
|------|------|----------|-------------|
| **27** | `response/AuthResponse.java` | Line 5 | Auth response DTO for signup/signin |
| **55** | `request/ForgotPasswordTokenRequest.java` | Line 5 | Forgot password token request DTO |
| **56** | `request/ResetPasswordRequest.java` | Line 5 | Reset password request DTO |
| **57** | `response/ApiResponse.java` | Line 5 | Generic API response DTO |
| **81** | `request/CreateOrderRequest.java` | Line 6 | Create order request DTO |
| **110** | `response/PaymentResponse.java` | Line 5 | Payment response for gateway integration |

---

## Configuration & Security Steps (Step 16-19, 25, 26, 28)

| Step | File | Location | Description |
|------|------|----------|-------------|
| **16** | `config/AppConfig.java` | Line 10 | Security filter chain configuration |
| **17** | `config/JwtTokenValidator.java` | Line 24 | JWT token validator filter |
| **18** | `config/JwtConstant.java` | Line 3 | JWT secret key and header constant |
| **19** | `config/JwtTokenValidator.java` | Line 34 | Bearer token parsing (substring 7 chars) |
| **25** | `config/JwtProvider.java` | Line 12 | JWT token generation and email extraction |
| **26** | `controller/AuthController.java` | Line 81 | JWT token creation in signup |
| **28** | `controller/AuthController.java` | Line 88 | Signup method completion with AuthResponse |

---

## Utility & Miscellaneous Steps (Step 36, 45, 48-49, 71-72, 89)

| Step | File | Location | Description |
|------|------|----------|-------------|
| **36** | `utils/OtpUtils.java` | Line 5 | OTP generation utility (6-digit random) |
| **45** | `request/CreateOrderRequest.java` | N/A | Referenced in order flow |
| **48** | `controller/UserController.java` | Line 53 | Verification code OTP sending |
| **49** | `controller/UserController.java` | Line 70 | 2FA authentication enabling |
| **71** | `service/WalletServiceImplement.java` | Line 92 | Wallet controller creation |
| **72** | `controller/WalletController.java` | Line 43 | Wallet-to-wallet transfer endpoint |
| **89** | `controller/WalletController.java` | Line 74 | Order payment from wallet |

---

## Summary Statistics

| Category | Count |
|----------|-------|
| **Total Steps** | 118 |
| **Files Involved** | 50+ |
| **Models** | 15 |
| **Services** | 13 |
| **Controllers** | 12 |
| **Domains/Enums** | 8 |
| **Configuration** | 4 |
| **Request/Response** | 6 |

---

## Development Flow Overview

### Phase 1: Core Setup (Steps 1-2)
- Database and server configuration

### Phase 2: Domain & Enumerations (Steps 5-8, 68-73, 91, 107-108)
- Define all enum types and roles

### Phase 3: Core Models (Steps 4, 7, 9-10, 30)
- User, TwoFactorAuth, TwoFactorOTP models

### Phase 4: Authentication & Security (Steps 11-28, 31-40)
- AuthController, JwtProvider, CustomUserDetailsService
- TwoFactorOTP and EmailService

### Phase 5: User Management & Verification (Steps 41-57)
- UserService, VerificationCodeService, ForgotPasswordService
- Password reset flow

### Phase 6: Cryptocurrency Data (Steps 58-61)
- Coin model and CoinController for API integration

### Phase 7: Wallet & Transactions (Steps 62-73)
- Wallet, WalletTransaction, and transfer functionality

### Phase 8: Trading System (Steps 74-88)
- Order, OrderItem, Asset models and services
- Buy/Sell order processing

### Phase 9: Withdrawal System (Steps 90-95)
- Withdrawal model and controller

### Phase 10: Watchlist Feature (Steps 96-100)
- Watchlist model and service

### Phase 11: Payment Integration (Steps 101-115)
- PaymentDetails, PaymentOrder, Payment gateways (Razorpay, Stripe)

---

## Key Dependencies & Integrations

- **JWT (JJWT)**: Steps 18, 25, 26
- **Razorpay**: Steps 113, 111-114
- **Stripe**: Steps 113, 111-114
- **CoinGecko API**: Steps 58-61
- **Email Service**: Steps 38-39
- **Spring Security**: Steps 14, 16-17, 21, 23

---

*Document Last Updated: February 2026*
*Total Development Steps: 118*

