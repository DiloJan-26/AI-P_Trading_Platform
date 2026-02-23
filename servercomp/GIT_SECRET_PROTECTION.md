# Important: Preventing Secret Keys in Git

## GitHub Secret Scanning Issue

If you receive a GitHub push protection error about exposed Stripe API keys, follow these steps:

### The Error Message
```
remote: - Push cannot contain secrets
remote:   —— Stripe Test API Secret Key ————————————————————————
remote:     locations:
remote:       - commit: xxxxxx
remote:         path: servercomp/src/main/resources/application.properties:26
```

---

## Solution Steps

### 1. Remove Secret from application.properties

Edit `src/main/resources/application.properties` and replace your actual key with a placeholder:

**Before:**
```properties
stripe.api.key=sk_test_51AbCdEfGhIjKlMnOpQrStUvWxYz1234567890
```

**After:**
```properties
stripe.api.key=your_stripe_secret_key_here
```

### 2. Create or Update .gitignore

Create a `.gitignore` file in the project root (if not exists) and add:

```gitignore
# Application properties with secrets
src/main/resources/application.properties
target/classes/application.properties

# IDE files
.idea/
*.iml
.vscode/

# Build files
target/
build/

# OS files
.DS_Store
Thumbs.db
```

### 3. Use application.properties.example

We've created `application.properties.example` with safe placeholders. This file CAN be committed to Git.

When other developers clone the project:
1. Copy `application.properties.example` to `application.properties`
2. Fill in their own Stripe secret key
3. Never commit `application.properties`

### 4. Remove from Git History

If the secret was already committed, remove it from Git history:

```powershell
# Remove the file from Git tracking (but keep local copy)
git rm --cached src/main/resources/application.properties

# Commit the removal
git add .gitignore
git commit -m "Remove application.properties from Git tracking"

# Push the changes
git push origin main
```

### 5. Rewrite Git History (If needed)

If GitHub still blocks due to old commits containing the secret:

**Option A: Allow the Secret on GitHub (Quick)**
- Click the URL provided in the error message
- This allows this specific secret to be pushed
- Not recommended for real production keys!

**Option B: Remove from History (Recommended)**

```powershell
# Install git-filter-repo (if not installed)
# Download from: https://github.com/newren/git-filter-repo

# Remove file from all commits
git filter-repo --path src/main/resources/application.properties --invert-paths

# Force push to GitHub
git push origin main --force
```

**Warning:** Force pushing rewrites history. Coordinate with team members!

---

## Best Practices for Production

### Use Environment Variables

**application.properties:**
```properties
stripe.api.key=${STRIPE_SECRET_KEY}
spring.datasource.password=${DB_PASSWORD}
```

**Set environment variables:**

**Windows (PowerShell):**
```powershell
$env:STRIPE_SECRET_KEY="sk_test_your_key"
$env:DB_PASSWORD="your_db_password"
```

**Linux/Mac:**
```bash
export STRIPE_SECRET_KEY="sk_test_your_key"
export DB_PASSWORD="your_db_password"
```

**Docker/Cloud:**
Add to your deployment configuration (e.g., Kubernetes secrets, AWS Parameter Store, etc.)

### Use Spring Profiles

Create separate property files:

- `application.properties` - Common config (safe to commit)
- `application-dev.properties` - Development config (gitignored)
- `application-prod.properties` - Production config (gitignored)

**application.properties:**
```properties
spring.profiles.active=dev
```

**application-dev.properties:**
```properties
stripe.api.key=sk_test_your_test_key
```

**application-prod.properties:**
```properties
stripe.api.key=sk_live_your_live_key
```

Add to `.gitignore`:
```gitignore
application-dev.properties
application-prod.properties
```

---

## Stripe Key Security Checklist

✅ Never commit secret keys to Git  
✅ Use `application.properties.example` with placeholders  
✅ Add `application.properties` to `.gitignore`  
✅ Use environment variables in production  
✅ Rotate keys if accidentally exposed  
✅ Use test keys (sk_test_) for development  
✅ Keep live keys (sk_live_) extremely secure  

---

## If Your Key Was Exposed

1. **Go to Stripe Dashboard** → Developers → API Keys
2. **Delete the exposed key** immediately
3. **Create a new secret key**
4. **Update your local `application.properties`**
5. **Update production environment variables**
6. **Clean Git history** using steps above

---

## Current Project Setup

✅ `application.properties` - Contains placeholder `your_stripe_secret_key_here`  
✅ `application.properties.example` - Safe template for sharing  
✅ Razorpay removed completely  
✅ Only Stripe integration remains  

**To run the project:**
1. Copy your actual Stripe secret key
2. Edit `src/main/resources/application.properties`
3. Replace `your_stripe_secret_key_here` with your key
4. **DO NOT** commit this file to Git!

