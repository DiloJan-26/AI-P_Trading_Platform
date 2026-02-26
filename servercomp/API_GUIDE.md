# 🚀 AI-P Trading Platform - Complete API Testing Guide & Architecture Documentation

## Table of Contents
1. [Project Overview](#project-overview)
2. [System Architecture](#system-architecture)
3. [Technology Stack](#technology-stack)
4. [Security & Identity Management](#security--identity-management)
5. [ACID Properties Implementation](#acid-properties-implementation)
6. [Database Schema](#database-schema)
7. [API Endpoints - Complete Testing Guide](#api-endpoints---complete-testing-guide)
8. [Step-by-Step Workflow](#step-by-step-workflow)
9. [Interview Q&A Guide](#interview-qa-guide)

---

## 📋 Project Overview

### **Simple Story (School Kid Explanation)**

Imagine a **Digital Trading Store** where:
- 👤 **Users** come to buy and sell digital coins (like cryptocurrency)
- 💳 **Wallet** is like a piggy bank where users keep their money
- 📊 **Coins** are products that users can trade
- 🔐 **Security** is like a locked safe - only the owner can access their money
- 💰 **Payments** are handled through Stripe (like swiping a credit card)
- 📝 **Orders** track what each user bought or sold
- 🚚 **Withdrawals** let users take money out

### **What the Platform Does**

The AI-P Trading Platform is a **full-stack cryptocurrency trading application** that enables users to:
1. Register and authenticate securely with JWT tokens
2. Enable two-factor authentication (2FA) for enhanced security
3. View real-time cryptocurrency market data via CoinGecko API
4. Buy and sell cryptocurrencies with wallet balance management
5. Transfer money between wallets
6. Process payments through Stripe payment gateway
7. Request withdrawals to bank accounts
8. Track transaction history and portfolio

### **Project Type**
- **Backend Framework**: Spring Boot 4.0.1
- **Database**: MySQL
- **Authentication**: JWT (JSON Web Tokens)
- **Payment Gateway**: Stripe
- **External API**: CoinGecko (Market Data)
- **Language**: Java 21
- **Architecture Pattern**: RESTful API with Service-Repository Pattern

---

## 🏗️ System Architecture

### **Architecture Diagram**

```
┌─────────────────────────────────────────────────────────────────┐
│                      FRONTEND (React/Vue)                        │
│                  (http://localhost:5173)                         │
└────────────────────────┬────────────────────────────────────────┘
                         │ HTTPS/REST API Calls
                         ↓
┌─────────────────────────────────────────────────────────────────┐
│                    SPRING BOOT BACKEND                            │
│                  (http://localhost:5454)                          │
├─────────────────────────────────────────────────────────────────┤
│ ┌──────────────────────────────────────────────────────────────┐ │
│ │         PRESENTATION LAYER (Controllers)                      │ │
│ │ ┌─────────────┐ ┌─────────────┐ ┌─────────────┐              │ │
│ │ │AuthController│ WalletController  PaymentController...      │ │
│ │ └─────────────┘ └─────────────┘ └─────────────┘              │ │
│ └────────────────────────┬─────────────────────────────────────┘ │
│                          ↓                                         │
│ ┌──────────────────────────────────────────────────────────────┐ │
│ │          BUSINESS LOGIC LAYER (Services)                     │ │
│ │ ┌──────────────────────────────────────────────────────────┐ │ │
│ │ │UserService  WalletService  OrderService PaymentService..│ │ │
│ │ └──────────────────────────────────────────────────────────┘ │ │
│ └────────────────────────┬─────────────────────────────────────┘ │
│                          ↓                                         │
│ ┌──────────────────────────────────────────────────────────────┐ │
│ │       DATA ACCESS LAYER (Repositories/JPA)                    │ │
│ │ ┌──────────────────────────────────────────────────────────┐ │ │
│ │ │UserRepository  WalletRepository  OrderRepository  ...    │ │ │
│ │ └──────────────────────────────────────────────────────────┘ │ │
│ └────────────────────────┬─────────────────────────────────────┘ │
│                          ↓                                         │
│ ┌──────────────────────────────────────────────────────────────┐ │
│ │      SECURITY LAYER (JWT, Spring Security)                   │ │
│ │ ┌─────────────┐  ┌──────────────┐  ┌──────────────┐         │ │
│ │ │JwtProvider  │  │JwtValidator  │  │AppConfig     │         │ │
│ │ └─────────────┘  └──────────────┘  └──────────────┘         │ │
│ └────────────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────────────────┐
│              DATABASE LAYER (MySQL)                              │
│  ┌────────┐ ┌────────┐ ┌────────┐ ┌────────┐ ┌────────┐       │
│  │  Users │ │ Wallet │ │ Orders │ │ Assets │ │ Coins  │       │
│  └────────┘ └────────┘ └────────┘ └────────┘ └────────┘       │
│  ┌────────┐ ┌────────┐ ┌────────┐ ┌────────┐ ┌────────┐       │
│  │Payment │ │Withdrawal │ Transactions  │ Watchlist           │
│  └────────┘ └────────┘ └────────┘ └────────┘ └────────┘       │
└─────────────────────────────────────────────────────────────────┘
```

### **Data Flow Diagram**

```
User Request
    ↓
Controller (Route & Validate)
    ↓
Service (Business Logic)
    ↓
Repository (Database Query)
    ↓
Database (Persistence)
    ↓
Response back through same layers
```

---

## 🛠️ Technology Stack

| Component | Technology | Purpose |
|-----------|-----------|---------|
| **Framework** | Spring Boot 4.0.1 | REST API Development |
| **Database** | MySQL | Persistent Data Storage |
| **ORM** | JPA/Hibernate | Object-Relational Mapping |
| **Authentication** | JWT (JJWT 0.13.0) | Stateless Authentication |
| **Security** | Spring Security | Authorization & Authentication |
| **Payment** | Stripe API | Payment Processing |
| **Email** | JavaMailSender | OTP & Verification Emails |
| **API Docs** | None (Use Postman) | API Documentation & Testing |
| **Build Tool** | Maven | Dependency Management |
| **Language** | Java 21 | Programming Language |

---

## 🔐 Security & Identity Management

### **Authentication Architecture**

#### **1. JWT Token-Based Authentication**

```java
// JwtConstant.java - Token Configuration
public class JwtConstant {
    public static final String SECRET_KEY = "here_is_the_key";
    public static final String JWT_HEADER = "Authorization";
}

// JwtProvider.java - Token Generation
public static String generateToken(Authentication auth) {
    String jwt = Jwts.builder()
        .issuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + 86400000L)) // 24 hours
        .claim("email", auth.getName())
        .claim("authorities", roles)
        .signWith(key)
        .compact();
    return jwt;
}
```

**How It Works:**
1. User signs up/logs in with email & password
2. Server validates credentials against database
3. JWT token generated with email claim + 24-hour expiration
4. Token sent to frontend
5. Frontend stores token in localStorage
6. Every API request includes token in `Authorization: Bearer <token>`
7. Server validates token before processing request

**Security Features:**
- ✅ **HMAC-SHA256** signing algorithm (secure cryptographic hash)
- ✅ **Expiration**: Tokens expire after 24 hours
- ✅ **Claims-based**: Email embedded in token for identity
- ✅ **Stateless**: No server-side session storage needed

---

#### **2. Two-Factor Authentication (2FA)**

**Flow:**
```
User Login
    ↓
Email & Password Verification ✅
    ↓
Check if 2FA Enabled
    ├─ YES: Generate 6-digit OTP
    │       ↓
    │   Send OTP via Email
    │       ↓
    │   User enters OTP
    │       ↓
    │   Verify OTP against database
    │       ↓
    │   Issue JWT Token
    │
    └─ NO: Issue JWT Token directly
```

**Implementation:**

```java
// OtpUtils.java - OTP Generation
public static String generateOTP() {
    return String.valueOf((int)(Math.random() * 1000000));
}

// TwoFactorAuth.java - Embedded Entity
@Embeddable
public class TwoFactorAuth {
    private boolean isEnabled = false;
    private VerificationType verificationType;
}

// AuthController.java - 2FA Logic
if(user.getTwoFactorAuth().isEnabled()) {
    String otp = OtpUtils.generateOTP();
    TwoFactorOTP twoFactorOtp = new TwoFactorOTP();
    twoFactorOtp.setOtp(otp);
    twoFactorOtp.setUser(user);
    twoFactorOtpService.createTwoFactorOtp(twoFactorOtp);
    emailService.sendVerificationOtpEmail(user.getEmail(), otp);
}
```

---

#### **3. Spring Security Configuration**

```java
// AppConfig.java - Security Filter Chain
@Configuration
public class AppConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Session is STATELESS (no server-side storage)
            .sessionManagement(management -> 
                management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // Authorization Rules
            .authorizeHttpRequests(Authorize -> Authorize
                .requestMatchers("/api/**").authenticated()  // Protected endpoints
                .anyRequest().permitAll())                    // Public endpoints
            
            // Custom JWT Filter
            .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
            
            // Security Features
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()));
        
        return http.build();
    }
}
```

---

#### **4. Role-Based Access Control (RBAC)**

```java
// USER_ROLE.java - Enum
public enum USER_ROLE {
    ROLE_ADMIN,
    ROLE_CUSTOMER
}

// User.java - Entity
@Entity
public class User {
    // ...
    private USER_ROLE role = USER_ROLE.ROLE_CUSTOMER;
}

// Endpoint Authorization (Using Spring Security in future):
// @PreAuthorize("hasRole('ADMIN')")
// @GetMapping("/admin/withdrawal-requests")
```

---

#### **5. Identity Access Management (IAM) Technologies Used**

| Technology | Purpose | Implementation |
|-----------|---------|-----------------|
| **JWT** | Stateless authentication | JwtProvider + JwtTokenValidator |
| **Spring Security** | Authorization & authentication framework | SecurityFilterChain in AppConfig |
| **Password Hashing** | Secure password storage | Spring Security (implicitly) |
| **CORS** | Cross-origin request handling | CorsConfigurationSource in AppConfig |
| **Bearer Token** | Token transmission | Authorization header with "Bearer " prefix |
| **Email Verification** | 2FA verification | EmailService + OTP |
| **User Details Service** | Custom user loading | CustomUserDetailsService |
| **Filter Chain** | Request-level security | JwtTokenValidator (OncePerRequestFilter) |

---

### **Annotations for Security**

```java
// @Embedded - Embedded objects
@Embedded
private TwoFactorAuth twoFactorAuth;

// @JsonProperty - Hide sensitive data
@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
private String password;

// @Entity, @Table - Entity mapping
@Entity
@Table(name = "users")

// @Id, @GeneratedValue - Primary key
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;

// @OneToOne, @ManyToOne, @OneToMany - Relationships
@OneToOne
private Wallet wallet;

// @Transactional - Transaction management
@Transactional
public void buyAsset(...) { }
```

---

## ✅ ACID Properties Implementation

### **ACID Definition**

**ACID** = Atomicity, Consistency, Isolation, Durability

### **1. Atomicity (All or Nothing)**

**Definition**: Transaction either completes fully or fails completely.

**Implementation in Trading Platform:**

```java
// OrderServiceImplement.java - Buy Asset Transaction
@Transactional  // ← Spring annotation ensures atomicity
public Order buyAsset(Long coinId, double quantity, User user) 
        throws Exception {
    // Step 1: Deduct from wallet
    wallet.setBalance(wallet.getBalance().subtract(BigDecimal.valueOf(...)));
    walletRepository.save(wallet);
    
    // Step 2: Create order
    Order order = new Order();
    order.setUser(user);
    order.setOrderType(OrderType.BUY);
    order.setStatus(OrderStatus.SUCCESS);
    orderRepository.save(order);
    
    // Step 3: Create order item
    OrderItem orderItem = new OrderItem();
    orderItem.setOrder(order);
    orderItemRepository.save(orderItem);
    
    // Step 4: Create/update asset
    Asset asset = new Asset();
    asset.setUser(user);
    asset.setCoin(coin);
    asset.setQuantity(quantity);
    assetRepository.save(asset);
    
    // ✅ If ANY step fails: ENTIRE transaction rolls back
    // ❌ All database changes are undone (wallet not debited, order not created, etc.)
    // ✅ If all succeed: ENTIRE transaction commits
}
```

**How @Transactional Works:**
- Spring wraps method in a database transaction
- If exception occurs → ROLLBACK (undo all changes)
- If method completes → COMMIT (save all changes)

---

### **2. Consistency (Valid State)**

**Definition**: Database remains in valid state before/after transaction.

**Implementation:**

```java
// Constraints in Models
@Entity
public class Order {
    @Column(nullable = false)
    private OrderType orderType;
    
    @Column(nullable = false)
    private BigDecimal price;
    
    @Column(nullable = false)
    private OrderStatus status;
}

// Business Logic Validation
public Order processOrder(...) throws Exception {
    // Validation: User must have sufficient balance
    if(wallet.getBalance().compareTo(BigDecimal.valueOf(amount)) < 0) {
        throw new Exception("Insufficient balance");
    }
    
    // Validation: Quantity must be positive
    if(quantity <= 0) {
        throw new Exception("Invalid quantity");
    }
    
    // Validation: Coin must exist
    if(coin == null) {
        throw new Exception("Coin not found");
    }
    
    // ✅ Only proceed if all constraints are satisfied
}
```

**Consistency Mechanisms:**
- `@Column(nullable = false)` - Database constraints
- Business logic validation in service layer
- Foreign key constraints (OneToOne, ManyToOne)
- Enum validation (OrderType, OrderStatus, etc.)

---

### **3. Isolation (Concurrent Access)**

**Definition**: Concurrent transactions don't interfere with each other.

**Implementation:**

```java
// Database uses MVCC (Multi-Version Concurrency Control)
// Default isolation level: READ_COMMITTED

@Transactional(isolation = Isolation.READ_COMMITTED)
public Wallet addBalance(Wallet wallet, Long amount) {
    wallet.setBalance(wallet.getBalance().add(BigDecimal.valueOf(amount)));
    return walletRepository.save(wallet);
}

// Scenario: Two users buying coin simultaneously
User1 wants to buy 5 coins (price = 1000 each = 5000 balance needed)
User2 wants to buy 3 coins (price = 1000 each = 3000 balance needed)

// With Isolation:
// ✅ User1's transaction sees wallet balance at START of transaction
// ✅ User2's transaction sees wallet balance at START of transaction
// ✅ Both complete independently without interference
// ✅ Final balance = Original - 5000 - 3000 (correct)
```

**Isolation Levels Supported:**
1. **READ_UNCOMMITTED** - Lowest isolation (dirty reads possible)
2. **READ_COMMITTED** - Default, prevents dirty reads
3. **REPEATABLE_READ** - Prevents dirty + non-repeatable reads
4. **SERIALIZABLE** - Highest isolation (transactions like serial execution)

---

### **4. Durability (Permanent Storage)**

**Definition**: Once committed, data persists despite failures.

**Implementation:**

```java
// MySQL with InnoDB (default, supports durability)
spring.datasource.url=jdbc:mysql://localhost:3306/ez_trad_db
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

// Hibernate DDL Auto
spring.jpa.hibernate.ddl-auto=update  // ← Creates tables if not exist

// Data is written to disk via:
// 1. Redo Log - Records all changes
// 2. Double Write Buffer - Prevents corruption
// 3. Sync to Disk - Data persists

// Example: User's wallet balance is updated
wallet.setBalance(new BigDecimal("10000"));
walletRepository.save(wallet);
// ✅ Data written to disk
// ✅ Even if server crashes, data survives on disk
// ✅ Next restart, data is recovered from disk
```

---

### **ACID Summary Table**

| Property | Trading Platform Implementation | Benefit |
|----------|--------------------------------|---------|
| **Atomicity** | @Transactional on critical operations | Money never half-credited or debited |
| **Consistency** | Database constraints + validation | Invalid states prevented |
| **Isolation** | READ_COMMITTED isolation level | Concurrent trades don't interfere |
| **Durability** | MySQL InnoDB persistence | Data survives system failures |

---

## 📊 Database Schema

### **Entity Relationship Diagram (ERD)**

```
┌──────────────────┐
│      Users       │
│──────────────────│
│ id (PK)          │
│ fullName         │
│ email (UNIQUE)   │
│ password         │
│ role             │
│ twoFactorAuth    │
└────────┬─────────┘
         │
    ┌────┴─────────────────────────────────────┐
    │                                            │
    ↓                                            ↓
┌───────────────┐                        ┌─────────────────┐
│   Wallet      │                        │  TwoFactorOTP   │
│───────────────│                        │─────────────────│
│ id (PK)       │                        │ id (PK)         │
│ user_id (FK)  │ OneToOne               │ user_id (FK)    │
│ balance       │ ◄──────────►           │ otp             │
└───────────────┘                        │ jwt             │
    │                                    └─────────────────┘
    │ OneToMany
    ↓
┌──────────────────────┐
│ WalletTransaction    │
│──────────────────────│
│ id (PK)              │
│ wallet_id (FK)       │
│ transactionType      │
│ amount               │
│ date                 │
│ purpose              │
└──────────────────────┘

┌──────────────────┐
│    Orders        │
│──────────────────│
│ id (PK)          │ ManyToOne
│ user_id (FK)     │ ◄────► Users
│ orderType        │
│ status           │
│ price            │
│ timestamp        │
└────────┬─────────┘
         │ OneToOne
         ↓
    ┌──────────────┐
    │  OrderItem   │
    │──────────────│
    │ id (PK)      │
    │ order_id(FK) │
    │ coin_id (FK) │
    │ quantity     │
    │ buyPrice     │
    │ sellPrice    │
    └────┬─────────┘
         │
         ↓ ManyToOne
    ┌──────────────┐
    │    Coins     │
    │──────────────│
    │ id (PK)      │
    │ coinId       │
    │ symbol       │
    │ name         │
    │ currentPrice │
    │ marketCap    │
    └──────────────┘

┌──────────────────┐
│     Assets       │
│──────────────────│
│ id (PK)          │ ManyToOne
│ user_id (FK)     │ ◄────► Users
│ coin_id (FK)     │ ManyToOne
│ quantity         │ ◄────► Coins
│ buyPrice         │
└──────────────────┘

┌──────────────────┐
│   Watchlist      │
│──────────────────│
│ id (PK)          │ OneToOne
│ user_id (FK)     │ ◄────► Users
│ coins (ManyToMany)
└──────────────────┘

┌──────────────────────┐
│  PaymentOrder        │
│──────────────────────│
│ id (PK)              │
│ user_id (FK)         │
│ amount               │
│ paymentMethod        │
│ status               │
└──────────────────────┘

┌──────────────────────┐
│  PaymentDetails      │
│──────────────────────│
│ id (PK)              │
│ user_id (FK)         │
│ accountNumber        │
│ accountHolderName    │
│ IFSC                 │
│ bankName             │
└──────────────────────┘

┌──────────────────────┐
│  Withdrawal          │
│──────────────────────│
│ id (PK)              │
│ user_id (FK)         │
│ amount               │
│ status               │
│ date                 │
└──────────────────────┘
```

---

## 🔌 API Endpoints - Complete Testing Guide

### **Base URL**: `http://localhost:5454`

### **POSTMAN Setup Instructions**

1. **Create New Workspace**
   - Open Postman
   - Click "Create Workspace"
   - Name: "AI-P Trading Platform"

2. **Create Environment Variables**
   ```json
   {
     "baseUrl": "http://localhost:5454",
     "jwt": "your_token_here",
     "userId": "1",
     "walletId": "1",
     "coinId": "bitcoin",
     "orderId": "1",
     "paymentId": "cs_test_..."
   }
   ```

3. **Use Variables in Requests**
   - URL: `{{baseUrl}}/api/users/profile`
   - Header: `Authorization: Bearer {{jwt}}`

---

### **1️⃣ AUTHENTICATION ENDPOINTS**

#### **A. User Signup**

```
POST /auth/signup
Content-Type: application/json

Request Body:
{
  "fullName": "John Doe",
  "email": "john@example.com",
  "password": "SecurePass123"
}

Response (201 Created):
{
  "jwt": "eyJhbGciOiJIUzI1NiJ9...",
  "status": true,
  "message": "register success",
  "isTwoFactorAuthEnabled": false,
  "session": null
}

✅ What Happens:
1. Email is checked for uniqueness
2. User created in database
3. Watchlist auto-created for user
4. JWT token generated with 24-hour expiration
5. Token contains email claim + authorities
```

**Testing Checklist:**
- [ ] Valid email format ✅
- [ ] Unique email ✅
- [ ] Password stored securely ✅
- [ ] JWT token returned ✅
- [ ] User role = ROLE_CUSTOMER ✅
- [ ] 2FA disabled by default ✅

---

#### **B. User Login (No 2FA)**

```
POST /auth/signin
Content-Type: application/json

Request Body:
{
  "email": "john@example.com",
  "password": "SecurePass123"
}

Response (200 OK):
{
  "jwt": "eyJhbGciOiJIUzI1NiJ9...",
  "status": true,
  "message": "Login success",
  "isTwoFactorAuthEnabled": false,
  "session": null
}

✅ What Happens:
1. User credentials verified against database
2. Password compared (Spring Security handles hashing)
3. JWT token generated
4. Token sent to frontend
```

---

#### **C. User Login (With 2FA)**

**Scenario**: User has 2FA enabled

```
POST /auth/signin
Content-Type: application/json

Request Body:
{
  "email": "john@example.com",
  "password": "SecurePass123"
}

Response (202 Accepted):
{
  "jwt": null,
  "status": true,
  "message": "Please verify with otp",
  "isTwoFactorAuthEnabled": true,
  "session": "temp-session-id"
}

Email Sent:
To: john@example.com
Subject: Your OTP for AI-P Trading Platform
Body: Your OTP is: 123456 (valid for limited time)

✅ What Happens:
1. Credentials verified
2. 2FA enabled check triggers
3. 6-digit OTP generated using OtpUtils
4. OTP stored in TwoFactorOTP table linked to user
5. OTP sent via EmailService
6. Temporary session returned (optional)
7. No JWT token yet (security measure)
```

---

#### **D. Verify 2FA OTP**

```
POST /auth/two-factor/otp/123456
Content-Type: application/json

Response (200 OK):
{
  "jwt": "eyJhbGciOiJIUzI1NiJ9...",
  "status": true,
  "message": "2FA verification successful",
  "isTwoFactorAuthEnabled": true,
  "session": null
}

✅ What Happens:
1. OTP extracted from URL parameter
2. Matched against stored OTP in database
3. OTP validity checked (time-based)
4. If correct: JWT token generated
5. TwoFactorOTP record deleted
6. If incorrect: Exception thrown (401)
```

---

### **2️⃣ USER PROFILE ENDPOINTS**

#### **A. Get User Profile**

```
GET /api/users/profile
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...

Response (200 OK):
{
  "id": 1,
  "fullName": "John Doe",
  "email": "john@example.com",
  "password": null,  // Excluded by @JsonProperty(access = WRITE_ONLY)
  "role": "ROLE_CUSTOMER",
  "twoFactorAuth": {
    "isEnabled": false,
    "verificationType": null
  }
}

✅ What Happens:
1. JWT token validated by JwtTokenValidator
2. Email extracted from token claims
3. User fetched from database
4. Password excluded from response (security)
5. Profile returned to frontend
```

**Security Notes:**
- ✅ Token required (authenticated endpoint)
- ✅ Password not exposed
- ✅ Token expiration checked (24 hours)

---

#### **B. Enable 2FA (Send OTP)**

```
POST /api/users/verification/EMAIL/send-otp
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...

Response (200 OK):
{
  "status": true,
  "message": "Otp send successfully"
}

Email Sent:
Subject: Your OTP for 2FA Setup
Body: Your OTP is: 654321

✅ What Happens:
1. User identified from JWT token
2. Check if verification code already exists
3. If not, generate new VerificationCode
4. 6-digit OTP created
5. Email sent to user
6. VerificationCode record saved in database
```

---

#### **C. Verify OTP & Enable 2FA**

```
POST /api/users/enable-two-factor/verify-otp/654321
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...

Response (200 OK):
{
  "jwt": "eyJhbGciOiJIUzI1NiJ9...",
  "status": true,
  "message": "Two-factor enabled",
  "isTwoFactorAuthEnabled": true,
  "session": null
}

✅ What Happens:
1. OTP verified against database
2. TwoFactorAuth.isEnabled set to true
3. TwoFactorAuth.verificationType = EMAIL
4. User record updated in database
5. New JWT token generated
6. VerificationCode deleted (one-time use)
```

---

#### **D. Forgot Password - Request OTP**

```
POST /api/users/reset-password/send-otp
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
Content-Type: application/json

Request Body:
{
  "sendTo": "john@example.com",
  "verificationType": "EMAIL"
}

Response (200 OK):
{
  "status": true,
  "message": "Password reset otp sent to email"
}

✅ What Happens:
1. User identified
2. ForgotPasswordToken created
3. OTP generated (6 digits)
4. Email sent with reset link/OTP
5. Token stored with expiration
6. Email verification setup
```

---

#### **E. Verify Password Reset OTP**

```
PATCH /api/users/reset-password/verify-otp
Content-Type: application/json

Request Body:
{
  "otp": "789012",
  "password": "NewSecurePass456"
}

Response (200 OK):
{
  "status": true,
  "message": "Password reset successful"
}

✅ What Happens:
1. OTP verified against ForgotPasswordToken
2. New password set
3. Password updated in User record
4. ForgotPasswordToken deleted
5. Email confirmation sent (optional)
```

---

### **3️⃣ WALLET ENDPOINTS**

#### **A. Get User Wallet**

```
GET /api/wallet
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...

Response (202 Accepted):
{
  "id": 1,
  "user": {
    "id": 1,
    "email": "john@example.com",
    "fullName": "John Doe"
  },
  "balance": 5000.00
}

✅ What Happens:
1. User extracted from JWT token
2. Wallet fetched (OneToOne relationship)
3. Balance returned (BigDecimal for precision)
4. Wallet auto-created if first-time (future feature)
```

**Database**: 
```sql
SELECT * FROM wallet WHERE user_id = 1;
-- Returns: id=1, user_id=1, balance=5000.00
```

---

#### **B. Deposit Funds (Stripe Payment)**

```
PUT /api/wallet/deposit?order_id=152&payment_id=cs_test_a1ENAIxxvO4nz014p6y...
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...

Response (202 Accepted):
{
  "id": 1,
  "user": {...},
  "balance": 10000.00  // Updated after deposit
}

✅ What Happens:
1. Order ID validated (payment must be completed)
2. Payment ID verified with Stripe
3. PaymentOrder status checked = SUCCESS
4. Wallet balance updated: balance + payment_amount
5. Transaction record created in WalletTransaction table
6. Email confirmation sent to user
```

**SQL Behind Scenes:**
```sql
-- Verify payment
SELECT * FROM payment_order WHERE id = 152 AND status = 'SUCCESS';

-- Update wallet
UPDATE wallet SET balance = balance + 5000 WHERE user_id = 1;

-- Record transaction
INSERT INTO wallet_transaction (wallet_id, type, amount, purpose, date)
VALUES (1, 'ADD_MONEY', 5000, 'Payment Deposit', NOW());
```

---

#### **C. Wallet to Wallet Transfer**

```
PUT /api/wallet/15/transfer
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
Content-Type: application/json

Request Body:
{
  "amount": 500,
  "purpose": "gift"
}

Response (202 Accepted - Sender's Wallet):
{
  "id": 1,
  "user": {...},
  "balance": 4500.00  // Deducted
}

✅ What Happens:
1. Sender identified from JWT
2. Receiver wallet fetched (ID = 15)
3. Validation:
   - Sender balance >= amount ✓
   - Amount > 0 ✓
   - Receiver exists ✓
4. Transaction in @Transactional block:
   - Sender wallet: balance -= 500
   - Receiver wallet: balance += 500
   - Two WalletTransaction records created
5. Both wallets saved atomically (ACID)
6. Confirmation email sent to both users
```

**Database Operations:**
```sql
-- For SENDER (user_id = 1)
UPDATE wallet SET balance = balance - 500 WHERE id = 1;
INSERT INTO wallet_transaction VALUES (..., 'WALLET_TRANSFER', -500, 'gift', NOW());

-- For RECEIVER (user_id = 2)
UPDATE wallet SET balance = balance + 500 WHERE id = 15;
INSERT INTO wallet_transaction VALUES (..., 'WALLET_TRANSFER', 500, 'gift', NOW());

-- Atomicity: Both succeed or both fail together
```

---

#### **D. Pay for Order from Wallet**

```
PUT /api/wallet/order/25/pay
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...

Response (202 Accepted):
{
  "id": 1,
  "user": {...},
  "balance": 2000.00  // Deducted for order
}

✅ What Happens:
1. Order fetched by ID (must exist)
2. User ownership verified
3. Order price extracted
4. Wallet balance checked >= order price
5. Wallet balance -= order price
6. Order status updated to PAID
7. Transaction recorded as BUY_ASSET
```

---

### **4️⃣ COIN/MARKET DATA ENDPOINTS**

#### **A. Get All Coins (Paginated)**

```
GET /api/coins?page=0
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...

Response (200 OK):
{
  "content": [
    {
      "id": 1,
      "coinId": "bitcoin",
      "symbol": "BTC",
      "name": "Bitcoin",
      "currentPrice": 45000.00,
      "marketCap": 900000000000,
      "priceChange24h": 2.5,
      "marketCapRank": 1,
      "circulatingSupply": 20000000
    },
    {
      "id": 2,
      "coinId": "ethereum",
      "symbol": "ETH",
      "name": "Ethereum",
      "currentPrice": 3000.00,
      "marketCap": 360000000000,
      "priceChange24h": 1.8,
      "marketCapRank": 2,
      "circulatingSupply": 120000000
    }
  ],
  "pageNumber": 0,
  "pageSize": 10,
  "totalPages": 10,
  "totalElements": 100
}

✅ What Happens:
1. CoinGecko API called to fetch market data
2. Data stored/updated in MySQL Coin table
3. Coins paginated (10 per page)
4. Price, market cap, ranking returned
5. Used for display in trading UI
```

**API Call Chain:**
```
Frontend → /api/coins → CoinController
→ CoinService.getCoinList(0)
→ REST call to CoinGecko API
→ Parse JSON response
→ Save/update in CoinRepository
→ Return to Frontend
```

---

#### **B. Get Top 50 Coins by Market Cap**

```
GET /api/coins/top50
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...

Response (200 OK):
[
  { "id": 1, "coinId": "bitcoin", "symbol": "BTC", "currentPrice": 45000, ... },
  { "id": 2, "coinId": "ethereum", "symbol": "ETH", "currentPrice": 3000, ... },
  ...
]

✅ What Happens:
1. CoinService.getTop50Coins() called
2. CoinGecko API fetched with market_cap sort
3. Top 50 coins returned
4. Used for market overview screen
```

---

#### **C. Search for Coin**

```
GET /api/coins/search?q=bitcoin
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...

Response (200 OK):
{
  "id": 1,
  "coinId": "bitcoin",
  "symbol": "BTC",
  "name": "Bitcoin",
  "currentPrice": 45000.00,
  "marketCap": 900000000000
}

✅ What Happens:
1. Query string 'bitcoin' parsed
2. Database searched OR CoinGecko API called
3. Matching coin returned
4. Used for coin lookup in trading
```

---

#### **D. Get Coin Chart Data**

```
GET /api/coins/1/chart?days=30
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...

Response (200 OK):
{
  "prices": [
    [1708214400000, 43000],
    [1708300800000, 43500],
    [1708387200000, 44000],
    ...
  ],
  "marketCaps": [...],
  "volumes": [...]
}

✅ What Happens:
1. CoinGecko API called with days parameter
2. Historical price data returned
3. Format: [timestamp_ms, price]
4. Used for chart.js visualization
5. 30-day default, customizable range
```

---

### **5️⃣ ORDER ENDPOINTS**

#### **A. Create Order**

```
POST /api/orders/pay
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
Content-Type: application/json

Request Body:
{
  "coinId": "bitcoin",
  "quantity": 0.5,
  "orderType": "BUY"
}

Response (200 OK):
{
  "id": 25,
  "user": {...},
  "orderType": "BUY",
  "price": 22500.00,
  "timestamp": "2024-02-25T10:30:00",
  "status": "PENDING",
  "orderItem": {
    "id": 50,
    "coin": {
      "coinId": "bitcoin",
      "symbol": "BTC",
      "currentPrice": 45000
    },
    "quantity": 0.5,
    "buyPrice": 45000.00,
    "sellPrice": null
  }
}

✅ What Happens:
1. Coin fetched from database
2. Order created with status = PENDING
3. OrderItem created (links coin to order)
4. Price calculated: quantity × currentPrice
5. Order returned to frontend
6. Frontend redirects to payment flow

Processing Chain:
BUY REQUEST → Validate coin & qty → Create order → Create order item → Return order → Wait for payment
```

---

#### **B. Get Order by ID**

```
GET /api/orders/25
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...

Response (200 OK):
{
  "id": 25,
  "user": {...},
  "orderType": "BUY",
  "price": 22500.00,
  "status": "PENDING",
  "orderItem": {...}
}

✅ What Happens:
1. Order fetched by ID
2. User ownership verified
3. Full order details returned
4. Used for order confirmation page
```

---

#### **C. Get All Orders for User**

```
GET /api/orders?order_type=BUY&asset_symbol=BTC
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...

Response (200 OK):
[
  { "id": 25, "orderType": "BUY", "price": 22500, ... },
  { "id": 26, "orderType": "BUY", "price": 15000, ... }
]

Query Parameters:
- order_type (optional): BUY or SELL
- asset_symbol (optional): BTC, ETH, etc.

✅ What Happens:
1. User extracted from JWT
2. Orders filtered by userId
3. Optional filters applied (order_type, symbol)
4. List returned for order history page
```

---

#### **D. Sell Cryptocurrency**

```
POST /api/orders/pay
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
Content-Type: application/json

Request Body:
{
  "coinId": "bitcoin",
  "quantity": 0.2,
  "orderType": "SELL"
}

Response (200 OK):
{
  "id": 30,
  "orderType": "SELL",
  "status": "SUCCESS",
  "price": 9000.00,  // 0.2 BTC × 45000
  "orderItem": {...}
}

✅ What Happens:
1. Asset found (user must own the coin)
2. Quantity validation (must have enough)
3. Order created with status = SUCCESS
4. Asset quantity decremented
5. Wallet balance += price
6. Transaction recorded as SELL_ASSET
7. Email confirmation sent

Complete Flow:
SELL REQUEST → Check asset ownership → Check quantity → Create order → Update asset → Update wallet → Record transaction → Send email
```

**Database Operations:**
```sql
-- Verify user owns asset
SELECT * FROM asset WHERE user_id = 1 AND coin_id = 1 AND quantity >= 0.2;

-- Create sell order
INSERT INTO orders (user_id, order_type, price, status, timestamp)
VALUES (1, 'SELL', 9000, 'SUCCESS', NOW());

-- Update asset
UPDATE asset SET quantity = quantity - 0.2 WHERE id = <asset_id>;
-- If quantity <= 1: DELETE FROM asset WHERE id = <asset_id>;

-- Update wallet
UPDATE wallet SET balance = balance + 9000 WHERE user_id = 1;

-- Record transaction
INSERT INTO wallet_transaction (wallet_id, type, amount, purpose)
VALUES (1, 'SELL_ASSET', 9000, 'BTC SELL 0.2', NOW());
```

---

### **6️⃣ PAYMENT ENDPOINTS**

#### **A. Create Payment Order**

```
POST /api/payment/STRIPE/amount/5000
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...

Response (201 Created):
{
  "paymentId": "cs_test_a1ENAIxxvO4nz014p6y...",
  "paymentLink": "https://checkout.stripe.com/pay/cs_test_...",
  "message": "Payment link created",
  "status": true
}

✅ What Happens:
1. Payment method = STRIPE
2. Amount = 5000 (cents: $50.00)
3. PaymentOrder record created with status = PENDING
4. Stripe API called:
   - Stripe.checkout.sessions.create()
   - PaymentIntent created
   - Hosted checkout URL returned
5. Frontend redirects user to Stripe checkout
6. User enters card details at Stripe (PCI-DSS compliant)

Stripe Integration Flow:
Backend → Stripe API → Returns PaymentIntent & Checkout URL → Frontend redirects user → User pays on Stripe → Redirect back to app
```

**Stripe Configuration:**
```properties
# application.properties
stripe.api.key=YOUR_STRIPE_TEST_SECRET_KEY
```

---

#### **B. Stripe Payment Callback (Frontend Redirect)**

```
After User Completes Payment on Stripe:

Redirect URL: http://localhost:5173/wallet?order_id=152&payment_id=cs_test_...

✅ What Happens (Frontend):
1. Capture URL parameters: order_id & payment_id
2. Display "Payment Processing..." message
3. Call PUT /api/wallet/deposit?order_id=152&payment_id=cs_test_...
4. Wallet balance updated
5. Display success message: "Funds Added Successfully"

Backend Flow:
1. Verify payment_id with Stripe API
2. Confirm payment status = SUCCESS
3. Update PaymentOrder status = SUCCESS
4. Update Wallet balance
5. Return updated wallet
```

---

### **7️⃣ ASSET ENDPOINTS**

#### **A. Get All User Assets**

```
GET /api/assets
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...

Response (200 OK):
[
  {
    "id": 1,
    "user": {...},
    "coin": {
      "id": 1,
      "coinId": "bitcoin",
      "symbol": "BTC",
      "name": "Bitcoin",
      "currentPrice": 45000
    },
    "quantity": 0.5,
    "buyPrice": 45000.00
  },
  {
    "id": 2,
    "user": {...},
    "coin": {
      "id": 2,
      "coinId": "ethereum",
      "symbol": "ETH",
      "name": "Ethereum",
      "currentPrice": 3000
    },
    "quantity": 2.0,
    "buyPrice": 2500.00
  }
]

✅ What Happens:
1. User extracted from JWT
2. All assets fetched where user_id = user.id
3. Coin data eagerly loaded
4. Portfolio value = sum(quantity × currentPrice) for each asset
5. Used for portfolio page display
```

---

#### **B. Get Asset by ID**

```
GET /api/assets/1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...

Response (200 OK):
{
  "id": 1,
  "quantity": 0.5,
  "buyPrice": 45000.00,
  "coin": {...},
  "user": {...}
}

✅ What Happens:
1. Asset fetched by ID
2. User ownership verified
3. Detailed asset info returned
```

---

#### **C. Get Assets by Coin**

```
GET /api/assets/coin/1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...

Response (200 OK):
[
  { "id": 1, "quantity": 0.5, "coin": {...}, ... }
]

✅ What Happens:
1. Find all assets where coin_id = 1 AND user_id = user.id
2. Return matching assets
3. Used for coin-specific portfolio view
```

---

### **8️⃣ TRANSACTION HISTORY ENDPOINTS**

#### **A. Get Wallet Transactions**

```
GET /api/wallet/transactions
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...

Response (200 OK):
[
  {
    "id": 1,
    "transactionType": "ADD_MONEY",
    "amount": 5000.00,
    "date": "2024-02-25T10:30:00",
    "purpose": "Payment Deposit"
  },
  {
    "id": 2,
    "transactionType": "BUY_ASSET",
    "amount": -22500.00,
    "date": "2024-02-25T11:00:00",
    "purpose": "BTC BUY 0.5"
  },
  {
    "id": 3,
    "transactionType": "WALLET_TRANSFER",
    "amount": -500.00,
    "date": "2024-02-25T12:00:00",
    "purpose": "gift to john@example.com"
  }
]

✅ What Happens:
1. User extracted from JWT
2. User's wallet found
3. All transactions fetched for that wallet
4. Ordered by date DESC (most recent first)
5. Used for transaction history/activity page

Transaction Types:
- ADD_MONEY: Deposit/payment added
- WALLET_TRANSFER: Money sent to/from another wallet
- BUY_ASSET: Money spent on crypto purchase
- SELL_ASSET: Money received from crypto sale
- WITHDRAWAL: Money withdrawn to bank account
```

---

#### **B. Get Transaction by ID**

```
GET /api/transactions/1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...

Response (200 OK):
{
  "id": 1,
  "transactionType": "ADD_MONEY",
  "amount": 5000.00,
  "date": "2024-02-25T10:30:00",
  "purpose": "Payment Deposit"
}

✅ What Happens:
1. Transaction fetched by ID
2. Wallet ownership verified
3. Transaction details returned
```

---

### **9️⃣ WITHDRAWAL ENDPOINTS**

#### **A. Request Withdrawal**

```
POST /api/withdrawal
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
Content-Type: application/json

Request Body:
{
  "amount": 1000.00
}

Response (201 Created):
{
  "id": 1,
  "user": {...},
  "amount": 1000.00,
  "status": "PENDING",
  "date": "2024-02-25T13:00:00"
}

✅ What Happens:
1. User extracted from JWT
2. Amount validation: must be > 0
3. Balance validation: wallet.balance >= amount
4. Withdrawal record created with status = PENDING
5. Withdrawal request stored in database
6. Admin notification sent
7. Response with withdrawal ID returned

Withdrawal Status Flow:
PENDING → (Admin Review) → SUCCESS / DECLINED
```

---

#### **B. Get Withdrawal History**

```
GET /api/withdrawal
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...

Response (200 OK):
[
  {
    "id": 1,
    "amount": 1000.00,
    "status": "SUCCESS",
    "date": "2024-02-25T13:00:00"
  },
  {
    "id": 2,
    "amount": 500.00,
    "status": "PENDING",
    "date": "2024-02-25T13:30:00"
  }
]

✅ What Happens:
1. User extracted from JWT
2. All withdrawals for user fetched
3. Ordered by date DESC
4. Status shown (PENDING, SUCCESS, DECLINED)
5. Used for withdrawal history page
```

---

#### **C. Admin - Get All Withdrawal Requests**

```
GET /api/admin/withdrawal
Authorization: Bearer {{adminJwt}}

Response (200 OK):
[
  {
    "id": 1,
    "user": { "id": 5, "email": "user5@example.com", ... },
    "amount": 1000.00,
    "status": "PENDING",
    "date": "2024-02-25T13:00:00"
  },
  ...
]

✅ What Happens (Admin Only):
1. Verify user has ROLE_ADMIN
2. All pending withdrawals fetched
3. Admin can review and approve/decline
4. Used for admin withdrawal management dashboard
```

---

#### **D. Admin - Approve/Decline Withdrawal**

```
PATCH /api/admin/withdrawal/1
Authorization: Bearer {{adminJwt}}
Content-Type: application/json

Request Body:
{
  "status": "SUCCESS"  // or "DECLINED"
}

Response (202 Accepted):
{
  "id": 1,
  "amount": 1000.00,
  "status": "SUCCESS",
  "message": "Withdrawal approved"
}

✅ What Happens:
1. Verify user = ROLE_ADMIN
2. Withdrawal fetched by ID
3. Status updated to SUCCESS/DECLINED
4. If SUCCESS:
   - Wallet balance -= amount (already deducted)
   - Transaction recorded as WITHDRAWAL
   - Bank transfer initiated (external system)
5. User notification sent
```

---

### **🔟 PAYMENT DETAILS ENDPOINTS**

#### **A. Add Bank Details**

```
POST /api/payment-details
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
Content-Type: application/json

Request Body:
{
  "accountNumber": "1234567890",
  "accountHolderName": "John Doe",
  "IFSC": "SBIN0001234",
  "bankName": "State Bank of India"
}

Response (201 Created):
{
  "id": 1,
  "accountNumber": "1234567890",
  "accountHolderName": "John Doe",
  "IFSC": "SBIN0001234",
  "bankName": "State Bank of India"
}

✅ What Happens:
1. User extracted from JWT
2. Bank details record created
3. Linked to user via OneToOne relationship
4. Used for withdrawal fund transfers
5. Payment gateway would use this for bank transfers
```

**Security Note:**
- In production, use encryption for account number
- Use tokenization with payment gateway
- Never store unencrypted card data

---

#### **B. Get Payment Details**

```
GET /api/payment-details
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...

Response (200 OK):
{
  "id": 1,
  "accountNumber": "1234567890",
  "accountHolderName": "John Doe",
  "IFSC": "SBIN0001234",
  "bankName": "State Bank of India"
}

✅ What Happens:
1. User extracted from JWT
2. PaymentDetails fetched where user_id = user.id
3. Returned for withdrawal form pre-population
```

---

### **1️⃣1️⃣ WATCHLIST ENDPOINTS**

#### **A. Get User Watchlist**

```
GET /api/watchlist
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...

Response (200 OK):
{
  "id": 1,
  "coins": [
    {
      "id": 1,
      "coinId": "bitcoin",
      "symbol": "BTC",
      "currentPrice": 45000.00
    },
    {
      "id": 2,
      "coinId": "ethereum",
      "symbol": "ETH",
      "currentPrice": 3000.00
    }
  ]
}

✅ What Happens:
1. User extracted from JWT
2. Watchlist fetched where user_id = user.id
3. All coins in watchlist returned
4. Used for "My Favorites" section
5. Watchlist auto-created on signup
```

---

#### **B. Add Coin to Watchlist**

```
PATCH /api/watchlist/add/1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...

Response (200 OK):
{
  "id": 1,
  "coins": [
    { "coinId": "bitcoin", ... },
    { "coinId": "ethereum", ... },
    { "coinId": "cardano", ... }  // Just added
  ]
}

✅ What Happens:
1. User extracted from JWT
2. Coin fetched by ID (must exist)
3. Coin added to user's watchlist
4. ManyToMany relationship updated
5. Watchlist returned with updated coin list
```

**SQL Behind Scenes:**
```sql
-- ManyToMany join table: watchlist_coin
INSERT INTO watchlist_coin (watchlist_id, coin_id)
VALUES (1, 3);
```

---

#### **C. Remove Coin from Watchlist**

```
PATCH /api/watchlist/remove/1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...

Response (200 OK):
{
  "id": 1,
  "coins": [
    { "coinId": "bitcoin", ... },
    { "coinId": "ethereum", ... }
    // Cardano removed
  ]
}

✅ What Happens:
1. User extracted from JWT
2. Coin fetched by ID
3. Coin removed from watchlist
4. ManyToMany join record deleted
5. Updated watchlist returned
```

---

### **🏠 HOME ENDPOINT**

#### **A. Public Health Check**

```
GET /
(No Authorization Required)

Response (200 OK):
{
  "message": "Welcome to AI-P Trading Platform API",
  "status": "UP",
  "version": "1.0.0"
}

✅ What Happens:
1. Public endpoint (permitted for all)
2. Used for health check
3. No authentication needed
4. Verifies server is running
```

---

### **Error Responses**

All endpoints follow consistent error format:

#### **401 Unauthorized**
```json
{
  "status": 401,
  "message": "Invalid or expired JWT token",
  "path": "/api/wallet"
}
```
Cause: Missing/invalid/expired JWT token

---

#### **403 Forbidden**
```json
{
  "status": 403,
  "message": "You don't have access to this resource",
  "path": "/api/wallet/2"
}
```
Cause: Insufficient permissions (trying to access another user's wallet)

---

#### **404 Not Found**
```json
{
  "status": 404,
  "message": "Order not found",
  "path": "/api/orders/999"
}
```
Cause: Resource doesn't exist

---

#### **400 Bad Request**
```json
{
  "status": 400,
  "message": "Insufficient wallet balance",
  "path": "/api/orders/pay"
}
```
Cause: Invalid request body or business logic validation failed

---

#### **500 Internal Server Error**
```json
{
  "status": 500,
  "message": "Something went wrong on the server",
  "path": "/api/payment/STRIPE/amount/5000"
}
```
Cause: Unexpected server error (log details)

---

## 📊 Step-by-Step Workflow

### **Complete User Journey**

```
1. SIGNUP
   ├─ User enters email, password, fullName
   ├─ POST /auth/signup
   ├─ Email checked for uniqueness
   ├─ User saved to database
   ├─ Watchlist auto-created
   └─ JWT token returned

2. LOGIN (NO 2FA)
   ├─ User enters email, password
   ├─ POST /auth/signin
   ├─ Credentials verified
   ├─ JWT token returned
   └─ User logged in

3. LOGIN (WITH 2FA) - FIRST TIME SETUP
   ├─ GET /api/users/profile → Get user
   ├─ POST /api/users/verification/EMAIL/send-otp → Send OTP
   ├─ User clicks link in email
   ├─ POST /api/users/enable-two-factor/verify-otp/{otp} → Verify & Enable
   └─ 2FA is now enabled

4. LOGIN (WITH 2FA) - SUBSEQUENT LOGINS
   ├─ User enters email, password
   ├─ POST /auth/signin
   ├─ Credentials verified
   ├─ 2FA enabled check → YES
   ├─ OTP generated and emailed
   ├─ User enters OTP from email
   ├─ POST /auth/two-factor/otp/{otp} → Verify OTP
   └─ JWT token returned → User logged in

5. DEPOSIT FUNDS (STRIPE)
   ├─ User clicks "Add Funds"
   ├─ User selects amount (e.g., $50)
   ├─ POST /api/payment/STRIPE/amount/5000 (cents)
   ├─ PaymentOrder created with status=PENDING
   ├─ Stripe checkout link returned
   ├─ Frontend redirects user to Stripe.com
   ├─ User enters card details on Stripe
   ├─ User completes payment on Stripe
   ├─ Stripe redirects to: http://localhost:5173/wallet?order_id=152&payment_id=cs_test_...
   ├─ Frontend captures payment_id from URL
   ├─ PUT /api/wallet/deposit?order_id=152&payment_id=cs_test_...
   ├─ Backend verifies payment with Stripe
   ├─ Wallet balance updated: 0 + 5000 = 5000
   ├─ Transaction recorded as ADD_MONEY
   └─ Success message displayed

6. VIEW PORTFOLIO
   ├─ GET /api/assets → Fetch all user assets
   ├─ Shows coins owned: BTC (0.5), ETH (2.0)
   ├─ Shows buy prices and current values
   └─ Calculate total portfolio value

7. BUY CRYPTOCURRENCY
   ├─ User selects Bitcoin, quantity 0.5
   ├─ Current price: $45,000
   ├─ Total cost: 0.5 × 45000 = $22,500
   ├─ POST /api/orders/pay → Create order
   ├─ Order status = PENDING
   ├─ PUT /api/wallet/order/{orderId}/pay → Pay from wallet
   ├─ Wallet balance: 5000 - 22500 = FAILED (insufficient balance)
   ├─ Error returned
   └─ User prompted to add more funds

   [Alternative: User has sufficient balance]
   ├─ Wallet balance: 50000 - 22500 = 27500
   ├─ Order status = SUCCESS
   ├─ Asset created: BTC (0.5) at buyPrice $45,000
   ├─ Transaction recorded as BUY_ASSET
   └─ Email confirmation sent

8. SELL CRYPTOCURRENCY
   ├─ User selects BTC from portfolio (owns 0.5)
   ├─ User selects "Sell" and quantity 0.2
   ├─ Current price: $45,000
   ├─ Sale proceeds: 0.2 × 45000 = $9,000
   ├─ POST /api/orders/pay (orderType=SELL)
   ├─ Asset quantity: 0.5 - 0.2 = 0.3 (updated)
   ├─ Wallet balance: 27500 + 9000 = 36500
   ├─ Order status = SUCCESS
   ├─ Transaction recorded as SELL_ASSET
   └─ Email confirmation sent

9. TRANSFER BETWEEN WALLETS
   ├─ User enters recipient wallet ID: 15
   ├─ User enters amount: 500
   ├─ PUT /api/wallet/15/transfer
   ├─ Validation: sender balance >= 500 ✓
   ├─ Sender wallet: 36500 - 500 = 36000
   ├─ Receiver wallet: X + 500 = X + 500
   ├─ Both WalletTransaction records created
   ├─ Atomicity: Both updated or both failed
   └─ Confirmation emails sent to both users

10. WITHDRAW FUNDS TO BANK
    ├─ User enters bank details (one-time or saved)
    ├─ POST /api/payment-details → Save bank account
    ├─ User enters withdrawal amount: 1000
    ├─ POST /api/withdrawal → Request withdrawal
    ├─ Withdrawal status = PENDING
    ├─ Wallet balance reserved: 36000 - 1000 = 35000
    ├─ Admin review (manual approval process)
    ├─ PATCH /api/admin/withdrawal/1 → Approve
    ├─ Bank transfer initiated (external system)
    ├─ Withdrawal status = SUCCESS
    ├─ Transaction recorded as WITHDRAWAL
    └─ Email confirmation sent

11. VIEW TRANSACTION HISTORY
    ├─ GET /api/wallet/transactions
    ├─ Shows all transactions in order:
    │  ├─ ADD_MONEY: +5000 (Payment)
    │  ├─ BUY_ASSET: -22500 (BTC 0.5)
    │  ├─ SELL_ASSET: +9000 (BTC 0.2)
    │  ├─ WALLET_TRANSFER: -500 (gift)
    │  └─ WITHDRAWAL: -1000 (bank)
    └─ Final balance: 36000 - 1000 = 35000

12. LOGOUT
    └─ Frontend clears JWT token from localStorage
       (No backend logout needed - JWT is stateless)
```

---

## 🎓 Interview Q&A Guide

### **Architecture Questions**

#### **Q1: Explain your project architecture**

**Answer:**
"The AI-P Trading Platform follows a **3-tier layered architecture**:

1. **Presentation Layer (Controllers)**
   - 12 REST controllers handle HTTP requests
   - Each controller maps to specific domain (Auth, Wallet, Payment, etc.)
   - Returns JSON responses

2. **Business Logic Layer (Services)**
   - Service interfaces define contracts
   - Implementations handle business logic
   - Order validation, payment processing, transaction management
   - Use @Transactional for ACID compliance

3. **Data Access Layer (Repositories)**
   - Spring JPA repositories for ORM
   - Abstract SQL operations
   - Return domain models

4. **Security Layer**
   - JWT token generation & validation
   - Spring Security configuration
   - Custom UserDetailsService for authentication
   - CORS handling

5. **Database Layer**
   - MySQL with InnoDB engine
   - 13+ entities with relationships
   - Supports ACID transactions

**Benefits:**
- Separation of concerns
- Easy to test (mock services)
- Scalable (add new services independently)
- Maintainable (changes isolated to one layer)"

---

#### **Q2: How does your authentication system work?**

**Answer:**
"We use **JWT (JSON Web Token) based stateless authentication**:

**Signup Flow:**
1. User sends email, password, fullName
2. Check email uniqueness
3. Store password (Spring Security handles hashing)
4. Create JWT token with email claim + 24-hour expiration
5. Return token to frontend

**Login Flow:**
1. User sends credentials
2. Validate against database
3. Generate new JWT token
4. Token expires in 24 hours (auto logout)

**API Request:**
1. Frontend includes token in `Authorization: Bearer <token>` header
2. JwtTokenValidator filter intercepts request
3. Extract token (remove 'Bearer ' prefix)
4. Verify signature using SECRET_KEY
5. Parse email claim
6. Create spring Security Authentication object
7. Allow request to proceed if valid

**Security:**
- Tokens are signed (HMAC-SHA256)
- Tokens are NOT encrypted (user can read but not modify)
- Modified token fails signature verification
- Server can validate token without database lookup (stateless)
- Token contains email claim for user identification

**2FA Enhancement:**
- If 2FA enabled, OTP generated and emailed
- JWT not issued until OTP verified
- Adds extra security layer"

---

#### **Q3: How do you handle concurrent transactions (ACID)?**

**Answer:**
"We ensure ACID properties at three levels:

**1. Atomicity (@Transactional)**
```java
@Transactional
public Order buyAsset(...) {
    // Deduct wallet
    wallet.setBalance(wallet.getBalance().subtract(amount));
    walletRepository.save(wallet);
    
    // Create order
    Order order = new Order();
    orderRepository.save(order);
    
    // Create asset
    Asset asset = new Asset();
    assetRepository.save(asset);
    
    // ALL succeed or ALL fail - no partial updates
}
```
If any step fails → Entire transaction rolls back

**2. Consistency**
- Database constraints: @Column(nullable = false)
- Business logic validation before operation
- Enum validation (OrderType, OrderStatus)
- Foreign key relationships prevent invalid data

**3. Isolation**
- Default isolation: READ_COMMITTED
- Concurrent transactions don't interfere
- Each transaction sees consistent snapshot
- Uses MVCC (Multi-Version Concurrency Control)

**4. Durability**
- MySQL InnoDB writes to disk
- Even if server crashes, data survives
- Redo logs ensure recovery

**Example Scenario:**
```
User1 transfers 500 to User2 simultaneously with User2 selling coin

Transaction 1 (Transfer):
- User1 wallet: 10000 - 500 = 9500
- User2 wallet: 5000 + 500 = 5500

Transaction 2 (Sell):
- User2 wallet: 5000 + 2000 = 7000

With isolation, both see User2's original balance (5000)
Result: User2 final balance = 5500 + 2000 = 7500 ✓

Without isolation, could be 7000 (incorrect)"

---

#### **Q4: What makes your system scalable?**

**Answer:**
"Our architecture is scalable at multiple levels:

1. **Layered Design**
   - Services are independent modules
   - Add new features without touching existing code
   - New service = new interface + implementation

2. **Database Optimization**
   - Indexes on frequently queried columns (email, userId)
   - Pagination for large datasets (coins, orders)
   - Connection pooling (HikariCP via Spring Boot)

3. **Stateless API**
   - No server-side sessions
   - JWT tokens validate without database lookup (after initial login)
   - Can scale horizontally (multiple server instances)
   - Load balancer can distribute requests

4. **Service Segregation**
   - CoinService handles market data
   - OrderService handles trading
   - PaymentService handles Stripe integration
   - Each can be scaled independently

5. **Caching Potential** (Not implemented, but ready)
   - Cache CoinGecko data (updates every hour)
   - Cache user profiles (for repeated requests)
   - Redis integration ready

6. **Database Optimization**
   - InnoDB supports concurrent reads
   - Read replicas possible for scaling reads
   - Sharding possible for large user base

**Current Limitations:**
- Single database instance (bottleneck)
- No caching layer
- Synchronous payment processing

**Future Scaling:**
- Add Redis cache
- Implement message queue (RabbitMQ)
- Database replication
- Microservices (separate Payment, Wallet services)"

---

### **Security Questions**

#### **Q5: How do you protect sensitive data?**

**Answer:**
"We protect sensitive data at multiple levels:

1. **Password Security**
   - Spring Security auto-configures PasswordEncoder
   - Passwords hashed using BCrypt (irreversible)
   - Never stored as plain text

2. **JWT Token Security**
   - SECRET_KEY is 64-character string (high entropy)
   - Tokens signed with HMAC-SHA256
   - Tokens contain expiration (24 hours)
   - Modified tokens fail signature check

3. **API Security**
   - HTTPS in production (SSL/TLS)
   - All /api/** endpoints require authentication
   - Public endpoints explicitly permitted

4. **Data Exposure**
   - Password excluded from responses: @JsonProperty(access = WRITE_ONLY)
   - Sensitive fields never exposed
   - User can view own profile only (ownership check)

5. **Payment Security**
   - Stripe integration for card processing (PCI-DSS compliant)
   - Never store card data on our server
   - Stripe returns Payment Intent ID (safe)
   - Bank account details encrypted in production

6. **Environment Configuration**
   - Secrets in application.properties (git ignored)
   - Different configs for dev/staging/production
   - Environment variables for sensitive data

**Potential Improvements:**
- Use OAuth2 for third-party integrations
- Implement rate limiting
- Add request signing for API security
- Use AES encryption for sensitive fields
- Implement token refresh (short + long token pairs)"

---

#### **Q6: What are your IAM (Identity & Access Management) technologies?**

**Answer:**
"Our IAM stack includes:

1. **Spring Security**
   - Authentication framework
   - SecurityFilterChain for request interception
   - Role-based access control (RBAC)

2. **JWT (JJWT Library)**
   - Token generation with Jwts.builder()
   - Token validation with Jwts.parser()
   - Claims-based user identification
   - Stateless authentication

3. **Custom Filters**
   - JwtTokenValidator extends OncePerRequestFilter
   - Intercepts every request
   - Validates token before controller execution

4. **UserDetailsService**
   - CustomUserDetailsService implements interface
   - Loads user from database
   - Converts database user to Spring Security User
   - Enables password verification

5. **User Roles**
   - USER_ROLE enum (ROLE_ADMIN, ROLE_CUSTOMER)
   - Stored in database
   - Used for authorization checks

6. **Email Verification**
   - OTP for 2FA
   - VerificationCode model for verification tokens
   - EmailService for sending OTPs

**Annotation-based Security:**
```java
@Embedded          // Embed 2FA config
@JsonProperty(...) // Hide passwords
@RequestHeader     // Extract JWT from header
@Entity            // JPA persistence
@Transactional     // Transaction security
```

**Future Enhancements:**
- @PreAuthorize for method-level security
- @Secured for annotation-based authorization
- OAuth2 integration
- Multi-factor authentication (already have 2FA)
- Token refresh mechanism"

---

#### **Q7: How do you handle CORS and cross-origin requests?**

**Answer:**
"We handle CORS in AppConfig.java:

```java
private CorsConfigurationSource corsConfigurationSource() {
    return request -> {
        CorsConfiguration config = new CorsConfiguration();
        
        // Which origins can access the API
        config.setAllowedOrigins(Arrays.asList(
            'http://localhost:5173',  // Frontend
            'http://localhost:3000'   // Alternative frontend
        ));
        
        // HTTP methods allowed
        config.setAllowedMethods(Collections.singletonList('*'));
        
        // Allow credentials (cookies, auth headers)
        config.setAllowCredentials(true);
        
        // Headers exposed to frontend
        config.setExposedHeaders(Arrays.asList('Authorization'));
        
        // Headers accepted
        config.setAllowedHeaders(Collections.singletonList('*'));
        
        // Cache CORS response for 1 hour
        config.setMaxAge(3600L);
        
        return config;
    };
}
```

**How It Works:**
1. Frontend (localhost:5173) makes request to backend (localhost:5454)
2. Browser checks CORS policy before allowing request
3. Server responds with CORS headers
4. Browser allows JavaScript to access response
5. Authorization header is exposed to frontend
6. Frontend can read JWT token from response

**Production Considerations:**
- Restrict origins to specific domain
- Set AllowedMethods to specific methods (GET, POST, PUT)
- Set AllowedHeaders explicitly
- Disable credentials if not needed"

---

### **Database & ORM Questions**

#### **Q8: Explain your database relationships**

**Answer:**
"We use multiple JPA relationship types:

1. **OneToOne (User ↔ Wallet)**
```java
@OneToOne
private Wallet wallet;
// Every user has exactly one wallet
// Wallet persists as long as user exists
// Delete user → Delete wallet
```

2. **OneToMany (User ↔ Orders)**
```java
// In User.java (implicit)
@OneToMany(mappedBy = 'user')
private List<Order> orders;

// In Order.java (actual definition)
@ManyToOne
private User user;
// One user can have many orders
// Many orders belong to one user
```

3. **ManyToOne (OrderItem ↔ Coin)**
```java
@ManyToOne
private Coin coin;
// Many order items can have same coin
// Order item belongs to one coin
```

4. **Embedded (User → TwoFactorAuth)**
```java
@Embedded
private TwoFactorAuth twoFactorAuth;
// TwoFactorAuth embedded in User table
// No separate table, just columns
```

5. **ManyToMany (Watchlist ↔ Coins)**
```java
@ManyToMany
private List<Coin> coins;
// Watchlist contains many coins
// Coin in many watchlists
// Join table: watchlist_coin
```

**Cascade Rules:**
```java
@OneToOne(cascade = CascadeType.ALL)
// Delete user → Delete wallet

@OneToMany(mappedBy = 'order', cascade = CascadeType.ALL)
// Delete order → Delete orderItem
```

**Lazy Loading:**
- OneToMany relationships are LAZY (fetch only when needed)
- Reduces database queries
- Can cause N+1 query problem if not careful

**Foreign Keys:**
- Database enforces referential integrity
- Cannot create order without valid user_id
- Cannot delete user with existing orders"

---

#### **Q9: How does Hibernate/JPA handle persistence?**

**Answer:**
"Hibernate handles persistence through multiple mechanisms:

1. **Entity Mapping**
```java
@Entity           // Class maps to table
@Table            // Specify table name
@Id               // Primary key
@GeneratedValue   // Auto-increment
@Column           // Column constraints
private String email;

// Hibernate generates SQL:
// CREATE TABLE users (id BIGINT AUTO_INCREMENT, email VARCHAR(255), ...)
```

2. **CRUD Operations**
```java
// CREATE
User user = new User();
userRepository.save(user);
// INSERT INTO users (fullName, email, ...) VALUES (...)

// READ
User user = userRepository.findById(1L);
// SELECT * FROM users WHERE id = 1

// UPDATE
user.setEmail('newemail@example.com');
userRepository.save(user);
// UPDATE users SET email = 'newemail@example.com' WHERE id = 1

// DELETE
userRepository.deleteById(1L);
// DELETE FROM users WHERE id = 1
```

3. **Relationships**
```java
// OneToOne
Order order = orderRepository.findById(1L);
OrderItem item = order.getOrderItem();
// SELECT * FROM order_item WHERE order_id = 1

// ManyToOne
List<Order> orders = orderRepository.findByUserId(1L);
// SELECT * FROM orders WHERE user_id = 1

// Lazy loading
user.getWallet(); // SELECT * FROM wallet WHERE user_id = 1
```

4. **Transactions**
```java
@Transactional
public void transfer(...) {
    // Start transaction
    wallet1.setBalance(...);
    wallet1Repository.save(wallet1);
    // UPDATE wallet SET balance = ... WHERE id = 1
    
    wallet2.setBalance(...);
    wallet2Repository.save(wallet2);
    // UPDATE wallet SET balance = ... WHERE id = 2
    
    // Commit transaction (if no exception)
    // All changes persist
}
```

5. **Query Generation**
```java
// Repository methods generate queries automatically
userRepository.findByEmail('john@example.com');
// SELECT * FROM users WHERE email = 'john@example.com'

orderRepository.findByUserIdAndOrderType(1, BUY);
// SELECT * FROM orders WHERE user_id = 1 AND order_type = 'BUY'
```

**Benefits:**
- Write Java code, not SQL
- Automatic table creation (ddl-auto=update)
- Object-oriented queries
- Automatic connection pooling
- Transaction management"

---

### **Payment Integration Questions**

#### **Q10: Walk me through Stripe payment integration**

**Answer:**
"Our Stripe integration has 4 main steps:

**Step 1: Create Payment Order**
```
User clicks 'Add Funds' → Selects amount $50 (5000 cents)
POST /api/payment/STRIPE/amount/5000

Backend:
1. Create PaymentOrder record (status = PENDING, amount = 5000, user = user, method = STRIPE)
2. Save to database
```

**Step 2: Generate Checkout Link**
```
PaymentService.createStripePaymentLink():
1. Call Stripe API: Stripe.checkout.sessions.create()
2. Parameters:
   - success_url: http://localhost:5173/wallet?order_id=152&payment_id={CHECKOUT_SESSION_ID}
   - cancel_url: http://localhost:5173/wallet?order_id=152&payment_id=null
   - mode: 'payment'
   - line_items: [{ price_data: { amount: 5000 }, quantity: 1 }]
   - customer_email: user@example.com
3. Stripe returns: checkoutSessionId + paymentLink
4. Return PaymentResponse with paymentLink to frontend
```

**Step 3: User Pays on Stripe**
```
Frontend:
1. Receives payment link from backend
2. Redirects user to Stripe checkout page
3. User enters card details (never touches our server)
4. User clicks 'Pay'
5. Stripe charges card
6. Stripe redirects user back to success_url
```

**Step 4: Verify & Deposit**
```
User redirected to: http://localhost:5173/wallet?order_id=152&payment_id=cs_test_...

Frontend:
1. Captures URL parameters
2. Calls: PUT /api/wallet/deposit?order_id=152&payment_id=cs_test_...

Backend:
1. Fetch PaymentOrder by order_id
2. Call Stripe API: Stripe.checkout.sessions.retrieve(payment_id)
3. Check session.paymentStatus == 'paid'
4. Update PaymentOrder.status = SUCCESS
5. Update Wallet.balance += 5000
6. Create WalletTransaction record
7. Send confirmation email
8. Return updated wallet
```

**Security Measures:**
- Card data never touches our server
- Stripe handles PCI compliance
- Payment ID validated with Stripe API
- Order ownership verified before updating
- Email confirmation prevents fraud

**Error Handling:**
- If session.paymentStatus != 'paid': throw exception
- If PaymentOrder not found: 404
- If user not authorized: 403
- Idempotent: Running twice updates once (order_id is unique)"

---

#### **Q11: What about handling failed payments?**

**Answer:**
"Failed payments are handled at multiple points:

**1. Card Decline**
```
User enters invalid/expired card on Stripe
Stripe rejects payment
Redirect to cancel_url: http://localhost:5173/wallet?order_id=152&payment_id=null

Frontend:
- Detect payment_id = null
- Show error: 'Payment failed, please try again'
- Allow user to retry

Backend:
- PaymentOrder.status remains PENDING
- Wallet.balance not updated
- No transaction created
```

**2. Timeout/Network Error**
```
Stripe API unreachable or request times out
Exception thrown in PaymentService
Return 500 error to frontend

Frontend:
- Show: 'Payment processing failed, please try again'
- Retry mechanism

Backend:
- PaymentOrder status remains PENDING
- Wallet not affected
- Transaction not created
```

**3. Webhook Validation** (Future Implementation)
```
// Currently not implemented, but should be:
@PostMapping('/stripe-webhook')
public void handleStripeWebhook(@RequestBody String payload) {
    // Verify webhook signature
    Stripe.webhooks.constructEvent(payload, sigHeader, endpointSecret)
    
    // Check if payment succeeded
    if (event.type == 'checkout.session.completed') {
        // Verify payment_id and update order
    }
}
```

**Best Practices:**
- Never update wallet until payment confirmed
- Email receipt only after SUCCESS status
- Implement retry mechanism (user can try again)
- Log all payment attempts (audit trail)
- Monitor failed payments (business analytics)"

---

### **Operational Questions**

#### **Q12: How do you handle database migrations?**

**Answer:**
"We use Hibernate auto schema generation:

**Configuration:**
```properties
spring.jpa.hibernate.ddl-auto=update
```

**Modes:**
- `validate`: Check schema matches entities (production)
- `update`: Update schema without deleting data (development)
- `create`: Drop and recreate (testing)
- `create-drop`: Create on startup, drop on shutdown (testing)
- `none`: Manual control

**Our Approach:**
1. During development: ddl-auto=update
   - New entity? Table auto-created
   - New field? Column auto-added
   - Change field type? Updated

2. Before production: Switch to ddl-auto=validate
   - Prevents accidental schema changes
   - Runs flyway/liquibase for controlled migrations

**Example Migration:**
```
// Add new field to User
@Entity
public class User {
    // ... existing fields ...
    private String phoneNumber;  // New field
}

// On server restart with ddl-auto=update:
// ALTER TABLE users ADD COLUMN phone_number VARCHAR(20);
```

**Limitations:**
- Auto migration can't handle complex changes
- Renaming columns loses data
- Dropping columns loses data
- No version control for schema

**Better Approach:**
- Use Liquibase or Flyway for production
- Version control migrations
- Rollback capability
- Track migration history"

---

#### **Q13: How do you handle errors and exceptions?**

**Answer:**
"We handle exceptions at multiple levels:

**1. Service Layer Validation**
```java
public Order buyAsset(...) throws Exception {
    // Check wallet balance
    if (wallet.getBalance() < amount) {
        throw new Exception('Insufficient balance');
    }
    
    // Check coin exists
    if (coin == null) {
        throw new Exception('Coin not found');
    }
    
    // Check quantity valid
    if (quantity <= 0) {
        throw new Exception('Invalid quantity');
    }
}
```

**2. Controller Exception Handling**
```java
@ExceptionHandler(Exception.class)
public ResponseEntity<ApiResponse> handleException(Exception e) {
    ApiResponse response = new ApiResponse();
    response.setStatus(false);
    response.setMessage(e.getMessage());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
}
```

**3. Database Constraints**
```java
@Entity
public class Order {
    @Column(nullable = false)
    private OrderType orderType;
    
    @Column(nullable = false)
    private BigDecimal price;
}
// If null: ConstraintViolationException thrown
```

**4. JWT Exception Handling**
```java
// In JwtTokenValidator
try {
    Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
} catch (JwtException e) {
    response.setStatus(401);
    response.setMessage('Invalid JWT token');
}
```

**5. Global Exception Handler** (Should implement)
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse> handleNotFound(EntityNotFoundException e) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new ApiResponse('Resource not found', false));
    }
    
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ApiResponse> handleInsufficientBalance(...) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ApiResponse('Insufficient balance', false));
    }
}
```

**Error Response Format:**
```json
{
  "status": false,
  "message": "Insufficient wallet balance",
  "path": "/api/orders/pay",
  "timestamp": "2024-02-25T10:30:00"
}
```

**Logging:**
```java
@Service
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    
    public Order buyAsset(...) throws Exception {
        logger.info('User {} attempting to buy {} {}'', user.getId(), quantity, coin.getSymbol());
        try {
            // ... business logic ...
            logger.info('Order created successfully: {}', order.getId());
        } catch (Exception e) {
            logger.error('Error creating order: {}', e.getMessage());
            throw e;
        }
    }
}
```"

---

### **Performance & Optimization Questions**

#### **Q14: How would you optimize database queries?**

**Answer:**
"Query optimization strategies:

1. **Pagination**
```java
// Instead of fetching all coins at once
public List<Coin> getAllCoins(Pageable pageable) {
    return coinRepository.findAll(pageable);
}
// GET /api/coins?page=0&size=10
// Fetches only 10 results instead of 1000+
```

2. **Lazy Loading**
```java
@OneToMany(fetch = FetchType.LAZY)
private List<Order> orders;
// Orders only fetched when accessed
// Saves initial query time
```

3. **Eager Loading (Be Careful)**
```java
@ManyToOne(fetch = FetchType.EAGER)
private User user;
// User fetched with order automatically
// Reduces N+1 queries but increases size
```

4. **Indexes**
```java
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    
    @Column(unique = true, nullable = false)
    @Index(name = 'idx_email')
    private String email;
}
// findByEmail queries use index
// Much faster than table scan
```

5. **Custom Queries**
```java
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query('SELECT o FROM Order o WHERE o.user.id = ?1 AND o.orderType = ?2')
    List<Order> findUserOrders(Long userId, OrderType orderType);
}
// Specific query instead of generic find
```

6. **Batch Operations**
```java
// Wrong: N individual queries
for (Order order : orders) {
    orderRepository.save(order);
}

// Right: Single batch insert
orderRepository.saveAll(orders);
```

7. **Caching** (Not implemented)
```java
@Cacheable('coins')
public List<Coin> getTopCoins() {
    // Result cached for 1 hour
}

// Clear cache on update
@CacheEvict('coins')
public void updateCoin(Coin coin) { }
```

**Monitoring:**
```properties
# Log SQL queries
spring.jpa.show-sql=true
logging.level.org.hibernate.SQL=DEBUG

# Monitor slow queries
slow_query_log=1
long_query_time=2
```"

---

## 📝 Summary for Interview Preparation

### **Key Points to Remember:**

1. **Architecture**: 3-tier layered (Controller → Service → Repository)
2. **Authentication**: JWT tokens with 24-hour expiration
3. **Security**: Spring Security + 2FA with OTP
4. **ACID**: @Transactional for atomicity, constraints for consistency
5. **Scalability**: Stateless design, independent services
6. **Payment**: Stripe integration with secure checkout
7. **Database**: MySQL with JPA/Hibernate ORM
8. **Error Handling**: Global exception handlers, validation at multiple layers

### **Technical Depth:**

- Can explain JWT token flow in detail
- Understand ACID properties implementation
- Know SQL generated by Hibernate
- Familiar with Spring Security filters
- Can discuss transaction isolation levels
- Understand OneToOne vs ManyToMany relationships
- Know Stripe payment flow and security

### **Problem-Solving:**

- Handle edge cases (insufficient balance, concurrent access)
- Design error responses
- Plan database migrations
- Optimize query performance
- Scale system horizontally

---

**This guide covers everything for a production-ready trading platform and interview preparation! Good luck!** 🚀


