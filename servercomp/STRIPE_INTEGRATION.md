# Quick Start Guide - Stripe Payment Integration

## ✅ What Was Done
2. **Integrated Stripe** payment gateway properly
3. **Fixed payment_id issue** - now included in success URL
4. **Secured secrets** - added .gitignore rules
5. **Created documentation** for setup and troubleshooting

---

## 🚀 Quick Setup (5 Minutes)

### Step 1: Get Your Stripe Secret Key
1. Go to https://dashboard.stripe.com/test/apikeys
2. Sign up or log in
3. Copy your **Secret key** (starts with `sk_test_`)

### Step 2: Configure Application
1. Open `src/main/resources/application.properties`
2. Find this line:
   ```properties
   stripe.api.key=your_stripe_secret_key_here
   ```
3. Replace with your actual key:
   ```properties
   stripe.api.key=sk_test_51AbCdEfGhIjK...
   ```

### Step 3: Build and Run
```powershell
mvn clean install
mvn spring-boot:run
```

That's it! Your Stripe integration is ready. 🎉

---

## 🧪 Testing the Payment Flow

### Using Postman

#### 1. Login (Get JWT Token)
```
POST http://localhost:5454/auth/signin
Body (JSON):
{
  "email": "your_user@example.com",
  "password": "your_password"
}
```
**Save the JWT token from response**

#### 2. Create Payment Order
```
POST http://localhost:5454/api/api/payment/STRIPE/amount/5000
Headers:
  Authorization: Bearer {your_jwt_token}
```

**Response:**
```json
{
  "payment_url": "https://checkout.stripe.com/c/pay/cs_test_..."
}
```

#### 3. Complete Payment
1. Copy the `payment_url`
2. Open it in your browser
3. Use Stripe test card: **4242 4242 4242 4242**
4. Expiry: **12/34** (any future date)
5. CVC: **123** (any 3 digits)
6. Click "Pay"

#### 4. You'll Be Redirected To
```
http://localhost:5173/wallet?order_id=1&payment_id=cs_test_abc123xyz
```
✅ Notice both `order_id` and `payment_id` are now present!

#### 5. Deposit to Wallet (Your Frontend Should Do This)
```
PUT http://localhost:5454/api/wallet/deposit?order_id=1&payment_id=cs_test_abc123xyz
Headers:
  Authorization: Bearer {your_jwt_token}
```

**Success Response:**
```json
{
  "id": 1,
  "balance": 56000.00,
  "user": {
    "id": 1,
    "fullName": "John Doe",
    "email": "john@example.com"
  }
}
```

---

## 📋 Stripe Test Cards

| Card Number | Scenario |
|-------------|----------|
| `4242 4242 4242 4242` | ✅ Successful payment |
| `4000 0000 0000 0002` | ❌ Card declined |
| `4000 0025 0000 3155` | 🔐 Requires authentication |
| `4000 0000 0000 9995` | ⚠️ Insufficient funds |

Use any future expiry date and any 3-digit CVC.

---

## 🔐 Before Pushing to Git

⚠️ **IMPORTANT:** Remove your real Stripe key before committing!

```powershell
# 1. Edit application.properties and change back to:
stripe.api.key=your_stripe_secret_key_here

# 2. Remove from Git tracking (keeps local file)
git rm --cached src/main/resources/application.properties

# 3. Commit and push
git add .gitignore
git add .
git commit -m "Integrate Stripe payment, remove Razorpay"
git push origin main
```

The `.gitignore` file now protects `application.properties` from being committed.

---

## 📁 Files Changed

| File | Change                |
|------|-----------------------|
| `domain/PaymentMethod.java` | STRIPE added          |
| `service/PaymentService.java` | Stripe call methods   |
| `service/PaymentServiceImplement.java` | Stripe implementation |
| `controller/PaymentController.java` | Simplified to Stripe  |
| `pom.xml` | Removed old dependency |
| `application.properties` | Stripe key            |
| `.gitignore` | Protected sensitive files |

---

## ❓ Troubleshooting

### "Required parameter 'payment_id' is not present"
✅ **FIXED!** The success URL now includes `{CHECKOUT_SESSION_ID}` which Stripe replaces with the actual session ID.

### "balance is null"
Check that:
1. Wallet is created for the user (during registration)
2. `WalletServiceImplement.addBalance()` handles null balance

### GitHub blocks push with secret scanner
Follow steps in `GIT_SECRET_PROTECTION.md`

### Payment verification fails
1. Check your Stripe secret key is correct
2. Verify it starts with `sk_test_` for test mode
3. Check console logs for Stripe API errors

---

## 🎯 Next Steps

### Frontend Integration
Your React frontend needs to:

1. **Redirect to Stripe Checkout**
   ```javascript
   // After getting payment_url from backend
   window.location.href = response.payment_url;
   ```

2. **Extract Parameters from Success URL**
   ```javascript
   // On /wallet page
   const params = new URLSearchParams(window.location.search);
   const orderId = params.get('order_id');
   const paymentId = params.get('payment_id');
   ```

3. **Call Wallet Deposit API**
   ```javascript
   fetch(`http://localhost:5454/api/wallet/deposit?order_id=${orderId}&payment_id=${paymentId}`, {
     method: 'PUT',
     headers: {
       'Authorization': `Bearer ${jwtToken}`
     }
   });
   ```

### Production Deployment
1. Get **Live API Key** from Stripe (starts with `sk_live_`)
2. Use environment variables instead of properties file
3. Update success/cancel URLs to your production domain

---

## ✅ Summary

**What Works Now:**
- ✅ Stripe payment gateway fully integrated
- ✅ Payment URL generation
- ✅ Session ID in success callback
- ✅ Payment verification
- ✅ Wallet deposit after payment
- ✅ Secrets protected from Git
