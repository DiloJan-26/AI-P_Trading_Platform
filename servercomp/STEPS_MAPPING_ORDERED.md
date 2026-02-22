# AI-P Trading Platform - Development Steps (Numerical Order)

## Complete Sequential Development Flow

This document lists all 118 development steps in numerical order, showing the exact progression from start to finish.

---

| Step | File | Location | Description |
|------|------|----------|-------------|
| **1** | `application.properties` | Line 3 | Set server port configuration. Initially commented as port 5455 before development starts. This is the first configuration point for the application. |
| **2** | `application.properties` | Line 6 | Configure MySQL database driver and connection details. Define datasource URL, username, password, and JDBC driver class for database connectivity. |
| **3** | `controller/HomeController.java` | Line 1 | Create the initial HomeController with a public endpoint. This serves as the entry point for testing the application without authentication. |
| **4** | `model/User.java` | Line 1 | Create the core User entity class with @Entity annotation. Define basic properties: id (auto-generated), fullName, email, and password fields. |
| **5** | `domain/USER_ROLE.java` | Line 1 | Create USER_ROLE enum to define user roles. Includes ROLE_ADMIN and ROLE_CUSTOMER for role-based access control in the system. |
| **6** | `model/User.java` | Line 24 | Add USER_ROLE property to the User class. Set default role to ROLE_CUSTOMER for new users registering on the platform. |
| **7** | `model/TwoFactorAuth.java` | Line 1 | Create TwoFactorAuth model as an embedded entity. Contains isEnabled flag and VerificationType for 2FA configuration. |
| **8** | `domain/VerificationType.java` | Line 5 | Create VerificationType enum to support multiple verification methods. Includes MOBILE and EMAIL options for OTP delivery. |
| **9** | `model/User.java` | Line 22 | Embed TwoFactorAuth object into User class using @Embedded annotation. Initialize with new TwoFactorAuth() for automatic 2FA support. |
| **10** | `model/User.java` | Line 22 | Finalize User model with complete schema including 2FA configuration. User entity now has all basic properties needed for authentication. |
| **11** | `controller/AuthController.java` | Line 23 | Create AuthController REST endpoint for authentication. Implement @RestController with @RequestMapping("/auth") for signup/signin endpoints. |
| **12** | `controller/AuthController.java` | Line 88 | Test the signup endpoint using Postman or REST client. POST to http://localhost:5455/auth/signup with user credentials to verify endpoint works. |
| **13** | `pom.xml` | N/A | Add JWT (JSON Web Token) dependencies to pom.xml. Include jjwt-api, jjwt-impl, and jjwt-jackson for JWT token generation and parsing. |
| **14** | `config/AppConfig.java` | N/A | Uncomment Spring Security dependency in pom.xml to enable security framework. This activates the Spring Security auto-configuration. |
| **15** | `config/AppConfig.java` | N/A | Verify Spring Security is working by accessing the application. A default login page should appear with auto-generated credentials shown in terminal logs. |
| **16** | `config/AppConfig.java` | Line 10 | Create AppConfig class with @Configuration annotation. Implement SecurityFilterChain bean to configure HTTP security filters and endpoint protection. |
| **17** | `config/JwtTokenValidator.java` | Line 24 | Create JwtTokenValidator filter extending OncePerRequestFilter. This filter validates JWT tokens on every HTTP request to protected endpoints. |
| **18** | `config/JwtConstant.java` | Line 3 | Define JwtConstant class with JWT_HEADER and SECRET_KEY constants. The SECRET_KEY is a 64-character string used for token signing and verification. |
| **19** | `config/JwtTokenValidator.java` | Line 34 | Implement Bearer token parsing logic in the validator. Extract the actual JWT by removing "Bearer " prefix (7 characters) from Authorization header. |
| **20** | `controller/HomeController.java` | Line 16 | Add a secured endpoint /api to HomeController. This endpoint requires authentication and returns a protected message only for authenticated users. |
| **21** | `service/CustomUserDetailsService.java` | Line 15 | Create CustomUserDetailsService implementing UserDetailsService interface. Override Spring Security's default user loading mechanism with database lookup. |
| **22** | `service/CustomUserDetailsService.java` | N/A | Implement loadUserByUsername method to fetch user credentials from repository. Convert database user to Spring Security UserDetails object for authentication. |
| **23** | `service/CustomUserDetailsService.java` | Line 27 | Setup user and password authentication from repository/database. Fetch user by email from UserRepository and return Spring Security User object with credentials. |
| **24** | `controller/AuthController.java` | Line 54 | Add email existence validation in signup method. Check if user with same email already exists before allowing new registration. |
| **25** | `config/JwtProvider.java` | Line 12 | Create JwtProvider utility class for JWT operations. Implement generateToken() method using JJWT library to create signed JWT tokens with user email claims. |
| **26** | `controller/AuthController.java` | Line 81 | Generate and include JWT token in signup response. Call JwtProvider.generateToken() and set the token in AuthResponse object. |
| **27** | `response/AuthResponse.java` | Line 5 | Create AuthResponse DTO (Data Transfer Object) class using Lombok @Data. Include jwt, status, message, isTwoFactorAuthEnabled, and session fields. |
| **28** | `controller/AuthController.java` | Line 88 | Complete signup method with proper response handling. Return AuthResponse with JWT token and status=true on successful registration. |
| **29** | `controller/AuthController.java` | Line 105 | Create login method by copying and modifying signup endpoint. Authenticate user credentials and return JWT token on successful login attempt. |
| **30** | `model/TwoFactorOTP.java` | Line 10 | Create TwoFactorOTP entity for storing OTP data. Include id, otp, user (OneToOne), and jwt fields with appropriate JPA annotations. |
| **31** | `service/TwoFactorOTPService.java` | Line 5 | Create TwoFactorOTPService interface defining contract for 2FA operations. Methods: createTwoFactorOtp, findByUser, findById, verifyTwoFactorOtp, deleteTwoFactorOtp. |
| **32** | `service/TwoFactorOTPServiceImplement.java` | Line 15 | Implement TwoFactorOTPService interface with business logic. Create service implementation class annotated with @Service. |
| **33** | `service/TwoFactorOTPServiceImplement.java` | N/A | Implement all interface methods for OTP management. Include createTwoFactorOtp, findByUser, findById, verifyTwoFactorOtp, and deleteTwoFactorOtp methods. |
| **34** | `service/TwoFactorOTPServiceImplement.java` | Line 18 | Autowire TwoFactorOTPRepository into the service. Use @Autowired annotation to inject repository for database operations on TwoFactorOTP entities. |
| **35** | `controller/AuthController.java` | Line 129 | Add conditional logic in signin to check if 2FA is enabled. If enabled, generate OTP and skip immediate JWT token return. |
| **36** | `utils/OtpUtils.java` | Line 5 | Create OtpUtils utility class with static OTP generation method. Generate 6-digit random number as OTP string using Random class. |
| **37** | `controller/AuthController.java` | Line 139 | Integrate OTP generation in signin flow when 2FA enabled. Call OtpUtils.generateOTP() and create TwoFactorOTP record. |
| **38** | `service/EmailService.java` | Line 11 | Create EmailService class using Spring's JavaMailSender. Implement sendVerificationOtpEmail method to send OTP via email. |
| **39** | `controller/AuthController.java` | Line 152 | Add email OTP sending in signin flow. Call emailService.sendVerificationOtpEmail() to send generated OTP to user's email address. |
| **40** | `controller/AuthController.java` | Line 163 | Create 2FA OTP verification endpoint at /two-factor/otp/{otp}. Verify the provided OTP against stored value and return JWT token if correct. |
| **41** | `service/UserService.java` | Line 6 | Create UserService interface for user-related operations. Define methods: findUserProfileByJwt, findUserByEmail, findUserById, enableTwoFactorAuthentication, updatePassword. |
| **42** | `service/UserServiceImplement.java` | Line 12 | Implement UserService interface with @Service annotation. Provide implementations for all user management operations using UserRepository. |
| **43** | `controller/UserController.java` | Line 21 | Create UserController for user profile and 2FA management endpoints. Implement GET profile and PATCH 2FA enable/disable endpoints. |
| **44** | `model/VerificationCode.java` | Line 8 | Create VerificationCode entity for storing email/mobile verification OTPs. Include id, otp, user, email, mobile, and verificationType fields. |
| **45** | `model/VerificationCode.java` | N/A | Complete VerificationCode model with all required JPA mappings. Ensure OneToOne relationship with User and proper field validation. |
| **46** | `service/VerificationCodeService.java` | Line 8 | Create VerificationCodeService interface for verification code operations. Methods for creating, retrieving, and deleting verification codes. |
| **47** | `service/VerificationCodeServiceImplement.java` | Line 14 | Implement VerificationCodeService with @Service annotation. Provide database operations using VerificationCodeRepository. |
| **48** | `controller/UserController.java` | Line 53 | Add endpoint to send verification OTP via email or SMS. Implement /verification/{verificationType}/send-otp endpoint in UserController. |
| **49** | `controller/UserController.java` | Line 70 | Add endpoint to enable 2FA after OTP verification. Implement /enable-two-factor/verify-otp/{otp} endpoint to activate 2FA. |
| **50** | `model/ForgotPasswordToken.java` | Line 7 | Create ForgotPasswordToken entity for password reset flow. Include id, user, otp, verificationType, and sendTo fields for token management. |
| **51** | `repository/ForgotPasswordRepository.java` | Line 6 | Create ForgotPasswordRepository interface extending JpaRepository. Enable CRUD operations on ForgotPasswordToken entities. |
| **52** | `service/ForgotPasswordService.java` | Line 7 | Create ForgotPasswordService interface for password reset operations. Methods: createToken, findById, findByUser, deleteToken. |
| **53** | `service/ForgotPasswordServiceImplement.java` | Line 13 | Implement ForgotPasswordService with @Service annotation. Use ForgotPasswordRepository for database operations on reset tokens. |
| **54** | `controller/UserController.java` | Line 101 | Add forgot password OTP sending endpoint at /reset-password/send-otp. Accept email, generate OTP, and send verification link/code. |
| **55** | `request/ForgotPasswordTokenRequest.java` | Line 5 | Create ForgotPasswordTokenRequest DTO using @Data annotation. Include sendTo (email/phone) and verificationType fields for OTP delivery method. |
| **56** | `request/ResetPasswordRequest.java` | Line 5 | Create ResetPasswordRequest DTO using @Data annotation. Include otp and password fields for password reset validation and update. |
| **57** | `response/ApiResponse.java` | Line 5 | Create generic ApiResponse DTO for API responses. Include message and status fields for consistent response format across endpoints. |
| **58** | `model/Coin.java` | Line 14 | Create Coin entity model for cryptocurrency data from CoinGecko API. Map JSON properties: id, symbol, name, currentPrice, marketCap, priceChange24h, etc. |
| **59** | `service/CoinServiceImplement.java` | Line 21 | Implement CoinService with methods to fetch data from CoinGecko API. Include getCoinList, getMarketChart, searchCoin, getTop50Coins, getTradingCoins methods. |
| **60** | `repository/CoinRepository.java` | Line 8 | Create CoinRepository extending JpaRepository for Coin entity. Enable database persistence of cryptocurrency market data. |
| **61** | `controller/CoinController.java` | Line 14 | Create CoinController with endpoints for market data retrieval. Implement GET endpoints: /coins, /{coinId}/chart, /search, /top50, /trading, /details/{coinId}. |
| **62** | `service/WalletService.java` | Line 7 | Create WalletService interface for wallet operations. Methods: getUserWallet, walletToWalletTransfer, addBalance, payOrderPayment, findWalletById. |
| **63** | `model/Wallet.java` | Line 7 | Create Wallet entity with OneToOne relationship to User. Include id and balance fields using BigDecimal for precision in financial calculations. |
| **64** | `model/Order.java` | Line 11 | Create Order entity for trading orders. Include ManyToOne relationship with User, orderType, status, price, timestamp, and OneToOne OrderItem. |
| **65** | `service/WalletServiceImplement.java` | Line 14 | Implement WalletService with @Service annotation. Provide wallet management and balance update operations using WalletRepository. |
| **66** | `repository/WalletRepository.java` | Line 15 | Create WalletRepository extending JpaRepository for Wallet persistence. Enable database operations on user wallets. |
| **67** | `service/WalletServiceImplement.java` | Line 69 | Reference Order model in wallet service for payment operations. Import and use Order class in wallet transaction methods. |
| **68** | `domain/OrderType.java` | Line 3 | Create OrderType enum with BUY and SELL values. Define trading order types for buy and sell operations. |
| **69** | `domain/OrderStatus.java` | Line 3 | Create OrderStatus enum with values: PENDING, FAILED, CANCELLED, PARTIALLY_FAILED, ERROR, SUCCESS. Track order lifecycle states. |
| **70** | `model/OrderItem.java` | Line 4 | Create OrderItem entity containing order details. Include quantity, coin (ManyToOne), buyPrice, sellPrice, and OneToOne relationship to Order. |
| **71** | `controller/WalletController.java` | Line 2 | Create WalletController for wallet management endpoints. Implement endpoints for viewing balance, transfers, and order payments. |
| **72** | `model/WalletTransaction.java` | Line 8 | Create WalletTransaction entity for transaction history. Include ManyToOne wallet, transactionType, date, transferId, purpose, and amount fields. |
| **73** | `domain/WalletTransactionType.java` | Line 3 | Create WalletTransactionType enum with transaction types: WITHDRAWAL, WALLET_TRANSFER, ADD_MONEY, BUY_ASSET, SELL_ASSET. |
| **74** | `service/OrderServiceImplement.java` | Line 19 | Implement OrderService with business logic for order processing. Include createOrder, processOrder, buyAsset, sellAsset methods. |
| **75** | `repository/OrderRepository.java` | Line 8 | Create OrderRepository extending JpaRepository for Order persistence. Enable order query and storage operations. |
| **76** | `service/OrderServiceImplement.java` | Line 78 | Create createOrderItem private method in buyAsset logic. Construct OrderItem with coin, quantity, buyPrice, and sellPrice details. |
| **77** | `repository/OrderItemRepository.java` | Line 6 | Create OrderItemRepository extending JpaRepository for OrderItem persistence. Enable database operations on order items. |
| **78** | `service/OrderServiceImplement.java` | Line 82 | Implement buyAsset method with @Transactional annotation. Create order, deduct wallet balance, mark order as SUCCESS, and create/update asset holdings. |
| **79** | `service/OrderServiceImplement.java` | Line 120 | Implement sellAsset method to process cryptocurrency sales. Check asset availability, create sell order, add to wallet, and update asset quantity. |
| **80** | `controller/OrderController.java` | Line 19 | Create OrderController for trading endpoints. Implement POST /pay for order creation and GET endpoints for order retrieval and history. |
| **81** | `request/CreateOrderRequest.java` | Line 6 | Create CreateOrderRequest DTO using @Data annotation. Include coinId, quantity, and orderType fields for order creation requests. |
| **82** | `model/Asset.java` | Line 4 | Create Asset entity for user cryptocurrency holdings. Include ManyToOne relationships with Coin and User, quantity, and buyPrice fields. |
| **83** | `service/AssetService.java` | Line 9 | Create AssetService interface for asset portfolio management. Methods: createAsset, updateAsset, deleteAsset, getAssetById, findAssetByUserIdAndCoinId. |
| **84** | `repository/AssetRepository.java` | Line 8 | Create AssetRepository extending JpaRepository for Asset persistence. Enable asset query and management operations. |
| **85** | `service/AssetServiceImplement.java` | Line 12 | Implement AssetService with @Service annotation. Provide asset portfolio management operations using AssetRepository. |
| **86** | `controller/AssetController.java` | Line 8 | Create AssetController for user portfolio endpoints. Implement GET endpoints to retrieve assets by ID, coin, or list all user assets. |
| **87** | `service/OrderServiceImplement.java` | Line 105 | Create new asset record when buying cryptocurrency. Check if asset exists; if not, create new; if yes, update quantity. |
| **88** | `service/OrderServiceImplement.java` | Line 132 | Implement asset selling logic with update/delete functionality. Deduct sold quantity from asset; delete if remaining balance <= 1. |
| **89** | `controller/WalletController.java` | Line 74 | Add order payment endpoint for wallet-based purchases. Implement PUT /order/{orderId}/pay to deduct wallet balance on order completion. |
| **90** | `model/Withdrawal.java` | Line 7 | Create Withdrawal entity for withdrawal requests. Include ManyToOne User, amount, status (WithdrawalStatus), and date fields. |
| **91** | `domain/WithdrawalStatus.java` | Line 3 | Create WithdrawalStatus enum with values: PENDING, SUCCESS, DECLINED. Track withdrawal request processing state. |
| **92** | `service/WithdrawalService.java` | Line 8 | Create WithdrawalService interface for withdrawal operations. Methods: requestWithdrawal, proceedWithWithdrawal, getUsersWithdrawalHistory, getAllWithdrawalRequest. |
| **93** | `service/WithdrawalServiceImplement.java` | Line 14 | Implement WithdrawalService with @Service annotation. Provide withdrawal request and processing logic using WithdrawalRepository. |
| **94** | `repository/WithdrawalRepository.java` | Line 15 | Create WithdrawalRepository extending JpaRepository for Withdrawal persistence. Enable withdrawal query and processing operations. |
| **95** | `controller/WithdrawalController.java` | Line 15 | Create WithdrawalController for withdrawal management endpoints. Implement POST for requests, PATCH for admin approval, and GET for history. |
| **96** | `model/Watchlist.java` | Line 8 | Create Watchlist entity for user favorite coins tracking. Include OneToOne User and ManyToMany List<Coin> for coin collection. |
| **97** | `service/WatchlistService.java` | Line 7 | Create WatchlistService interface for watchlist operations. Methods: createWatchlist, findUserWatchlist, addItemToWatchlist, removeItemFromWatchlist. |
| **98** | `service/WatchlistServiceImplement.java` | Line 12 | Implement WatchlistService with @Service annotation. Provide watchlist management operations using WatchlistRepository. |
| **99** | `repository/WatchlistRepository.java` | Line 13 | Create WatchlistRepository extending JpaRepository for Watchlist persistence. Enable watchlist query and management operations. |
| **100** | `controller/WatchListController.java` | Line 17 | Create WatchListController for watchlist management endpoints. Implement GET and PATCH endpoints for retrieving and managing favorite coins. |
| **101** | `model/PaymentDetails.java` | N/A | Create PaymentDetails entity for storing bank account information. Include accountNumber, accountHolderName, IFSC, bankName, and User relationship. |
| **102** | `repository/PaymentDetailsRepository.java` | N/A | Create PaymentDetailsRepository extending JpaRepository for PaymentDetails persistence. Enable bank details storage and retrieval. |
| **103** | `service/PaymentDetailsService.java` | N/A | Create PaymentDetailsService interface for payment details operations. Methods: addPaymentDetails, getUsersPaymentDetails. |
| **104** | `service/PaymentDetailsServiceImplement.java` | Line 10 | Implement PaymentDetailsService with @Service annotation. Provide bank details management using PaymentDetailsRepository. |
| **105** | `controller/PaymentDetailsController.java` | Line 15 | Create PaymentDetailsController for bank details management endpoints. Implement POST to add and GET to retrieve user payment details. |
| **106** | `model/PaymentOrder.java` | Line 9 | Create PaymentOrder entity for payment gateway integration. Include Long amount, status (PaymentOrderStatus), paymentMethod, and ManyToOne User. |
| **107** | `domain/PaymentOrderStatus.java` | Line 3 | Create PaymentOrderStatus enum with values: PENDING, SUCCESS, FAILED. Track payment processing state. |
| **108** | `domain/PaymentMethod.java` | Line 3 | Create PaymentMethod enum with values: RAZORPAY, STRIPE. Define supported payment gateway options. |
| **109** | `service/PaymentService.java` | Line 9 | Create PaymentService interface for payment operations. Methods: createOrder, getPaymentOrderById, proceedPaymentOrder, createRazorpayPaymentLink, createStripePaymentLink. |
| **110** | `response/PaymentResponse.java` | Line 5 | Create PaymentResponse DTO for payment gateway responses. Include paymentId, paymentLink, message fields for gateway integration. |
| **111** | `service/PaymentServiceImplement.java` | Line 21 | Implement PaymentService with @Service annotation. Integrate Razorpay and Stripe payment gateways for order payments. |
| **112** | `repository/PaymentOrderRepository.java` | Line 21 | Create PaymentOrderRepository extending JpaRepository for PaymentOrder persistence. Enable payment order tracking and querying. |
| **113** | `pom.xml` | N/A | Add Razorpay and Stripe Maven dependencies to pom.xml. Include com.razorpay:razorpay-java and com.stripe:stripe-java libraries. |
| **114** | `controller/PaymentController.java` | Line 14 | Create PaymentController for payment gateway endpoints. Implement POST /payment/{paymentMethod}/amount/{amount} for initiating payments. |
| **115** | `controller/WalletController.java` | Line 103 | Add wallet deposit endpoint for payment completion. Implement PUT /deposit to add funds after successful payment verification. |
| **116** | `controller/TransactionController.java` | N/A | Create TransactionController for transaction history endpoints. Implement GET endpoints to retrieve user transaction records (experimental feature). |
| **117** | `application.properties` | Line 6 | Update server port configuration from 5455 to 5454. Reflect final port setting for application deployment. |
| **118** | `application.properties` | Line 15 | Add database cleanup instructions and payment API key setup. Include notes for database reset and configuration of Stripe/Razorpay credentials. |

---

## Key Milestones

### Phase 1: Setup (Steps 1-2)
- Configure database and server

### Phase 2: Authentication (Steps 3-28)
- Build login/signup with JWT tokens

### Phase 3: 2FA Security (Steps 29-40)
- Implement two-factor authentication with OTP

### Phase 4: User Management (Steps 41-57)
- User profiles, verification codes, password reset

### Phase 5: Cryptocurrency (Steps 58-61)
- Coin data integration from CoinGecko API

### Phase 6: Wallet System (Steps 62-73)
- Wallet management and transactions

### Phase 7: Trading (Steps 74-88)
- Order processing, buy/sell assets

### Phase 8: Withdrawals (Steps 90-95)
- Withdrawal requests and processing

### Phase 9: Watchlist (Steps 96-100)
- Favorite coins management

### Phase 10: Payment Gateway (Steps 101-115)
- Razorpay and Stripe integration

### Phase 11: Final Setup (Steps 116-118)
- Transaction tracking and database configuration

---

## File Count by Category

- **Models**: 15 files (User, Wallet, Order, Coin, etc.)
- **Controllers**: 12 files (Auth, User, Wallet, Order, etc.)
- **Services**: 13 files (interface + implementation pairs)
- **Repositories**: 10+ files (Database access)
- **Domains/Enums**: 8 files (roles, statuses, types)
- **Requests/Responses**: 6 files (DTOs)
- **Configuration**: 4 files (JWT, Security)
- **Utils**: 1 file (OTP generation)

---

## Step Dependencies

Each step builds upon previous steps:
- **Step 4** (User model) → Used in steps 6, 9, 11
- **Step 11** (AuthController) → Uses steps 4, 5, 7, 8
- **Step 25** (JWT Provider) → Used in steps 26, 28, 29
- **Step 64** (Order model) → Used in steps 74-88
- **Step 73** (WalletTransaction) → Used in steps 89, 115

---

*Total Steps: 118*
*Total Files Involved: 50+*
*Development Timeline: Complete crypto trading platform with payment integration*

