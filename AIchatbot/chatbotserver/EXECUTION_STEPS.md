# 📋 PROJECT EXECUTION STEPS - Complete Guide

## Real Time Crypto Market Details Chat Bot - Step-by-Step Breakdown

**Project**: Real Time Crypto Market Details Chat Bot
**Version**: 1.0.0-SNAPSHOT
**Total Steps**: 12
**Execution Time**: 30 minutes (setup to running)

---

## 🎯 EXECUTION STEPS TABLE

| Step | Description | Files Involved | Purpose | Execution Time |
|------|-------------|-----------------|---------|-----------------|
| **STEP 0** | Project Setup & Prerequisites | pom.xml, mvnw | Install dependencies, verify Java/Maven | 5 min |
| **STEP 1** | Configure Server & Database | application.properties | Set port (5454), database URL, credentials | 3 min |
| **STEP 2** | Create Database | MySQL terminal | Create `crypto_chatbot` database | 1 min |
| **STEP 3** | Create Home Controller | HomeController.java | GET / health check endpoint | - |
| **STEP 4** | Create Response Class | ApiResponse.java | Standard API response wrapper | - |
| **STEP 5** | Configure APIs & Service Interface | ChatbotService.java, application.properties | Inject Gemini/CoinGecko keys, define service contract | 5 min |
| **STEP 6** | Implement Service Logic | ChatbotServiceImplement.java | Create service implementation class | - |
| **STEP 7** | Create Coin Data Model | Coin.java | Define cryptocurrency data structure (26 fields) | - |
| **STEP 8** | Integrate CoinGecko API | ChatbotServiceImplement.java | Implement coin lookup and market data fetching | - |
| **STEP 9** | Create Chat Controller | ChatbotController.java | Create REST endpoint for crypto data | - |
| **STEP 10** | Create Request Payload DTO | PromptBody.java | Define request body structure | - |
| **STEP 11** | Integrate Gemini AI | ChatbotServiceImplement.java | Implement AI chat functionality | - |
| **STEP 12** | Create Chat Endpoints | ChatbotController.java | Create endpoints for simple chat (JSON & text) | - |

---

## 📖 DETAILED STEP-BY-STEP EXECUTION

### **STEP 0: Project Setup & Prerequisites**

**Files Involved:**
- pom.xml
- mvnw / mvnw.cmd

**What to Do:**
```bash
# 1. Verify Java installation
java -version
# Required: Java 21+

# 2. Verify Maven installation
.\mvnw --version
# Maven should be available via wrapper

# 3. Verify MySQL is running
mysql -u root -p
# Should connect successfully
```

**Outcome:** Development environment ready
**Time Estimate:** 5 minutes

---

### **STEP 1: Configure Server & Database**

**Files Involved:**
- `src/main/resources/application.properties`

**What to Do:**

Edit `application.properties` and update these properties:

```properties
# Server Configuration
spring.application.name=chatbotserver
server.port=5454

# Database Configuration
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://localhost:3306/crypto_chatbot
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD  # ← CHANGE THIS
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Gemini API Configuration
gemini.api.key=YOUR_GEMINI_API_KEY              # ← ADD YOUR KEY
gemini.api.base-url=https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent

# CoinGecko API Configuration
coingecko.api.base-url=https://api.coingecko.com/api/v3
coingecko.api.key=                              # ← Optional for free tier
```

**Key Changes Required:**
- Replace `YOUR_MYSQL_PASSWORD` with actual MySQL password
- Replace `YOUR_GEMINI_API_KEY` with Gemini API key from https://aistudio.google.com/apikey
- Leave coingecko.api.key empty for free tier

**Outcome:** Application configured with credentials
**Time Estimate:** 3 minutes

---

### **STEP 2: Create Database**

**Files Involved:**
- MySQL Server (external)

**What to Do:**

Open MySQL terminal and run:

```bash
mysql -u root -p
```

Then execute:

```sql
CREATE DATABASE crypto_chatbot;
EXIT;
```

**Verification:**
```bash
mysql -u root -p crypto_chatbot -e "SELECT 'Database created successfully';"
```

**Outcome:** `crypto_chatbot` database created and ready
**Time Estimate:** 1 minute

---

### **STEP 3: Create Home Controller**

**File:**
- `src/main/java/com/eztrad/chatbotserver/controller/HomeController.java`

**Purpose:**
- Health check endpoint
- Verifies application is running
- GET / endpoint

**Code:**
```java
@RestController
public class HomeController {
    @GetMapping("/")
    public ResponseEntity<ApiResponse> Home() {
        ApiResponse response = new ApiResponse();
        response.setMessage("Welcome to the Chatbot Server API!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
```

**Endpoint:**
- URL: `GET http://localhost:5454/`
- Response: Welcome message

**Outcome:** Health check endpoint working
**Status:** ✅ Created

---

### **STEP 4: Create Response Class**

**File:**
- `src/main/java/com/eztrad/chatbotserver/response/ApiResponse.java`

**Purpose:**
- Standard response wrapper
- Used by all endpoints
- Contains message and data fields

**Code:**
```java
@Data
public class ApiResponse {
    private String message;      // Status/description
    private Object data;         // Response payload
}
```

**Usage:**
```java
ApiResponse response = new ApiResponse();
response.setMessage("Success");
response.setData(someData);
return response;
```

**Outcome:** Response wrapper ready for all endpoints
**Status:** ✅ Created

---

### **STEP 5: Configure APIs & Service Interface**

**Files Involved:**
- `src/main/resources/application.properties` (already done in STEP 1)
- `src/main/java/com/eztrad/chatbotserver/service/ChatbotService.java`

**What to Do:**

Create ChatbotService interface with method signatures:

```java
public interface ChatbotService {
    ApiResponse getCoinDetails(String prompt) throws Exception;
    String simpleChat(String prompt);
}
```

**Configuration Injection:**
- In implementation class, inject properties using `@Value`:
```java
@Value("${gemini.api.key}")
private String geminiApiKey;

@Value("${gemini.api.base-url}")
private String geminiApiBaseUrl;

@Value("${coingecko.api.base-url}")
private String coinGeckoApiBaseUrl;
```

**Outcome:** Service interface defined, APIs configured
**Time Estimate:** 5 minutes
**Status:** ✅ Created

---

### **STEP 6: Implement Service Logic**

**File:**
- `src/main/java/com/eztrad/chatbotserver/service/ChatbotServiceImplement.java`

**Purpose:**
- Implement ChatbotService interface
- Business logic for API calls
- Helper methods for data parsing

**Methods to Implement:**

1. **getCoinDetails()** - Main public method
2. **simpleChat()** - Main public method
3. **makeApiRequest()** - Private helper for CoinGecko
4. **resolveCoinId()** - Private helper for coin lookup
5. **convertToDouble()** - Private helper for type conversion
6. **getNumber()** - Private helper for nested values

**Code Skeleton:**
```java
@Service
public class ChatbotServiceImplement implements ChatbotService {
    
    @Value("${gemini.api.key}")
    private String geminiApiKey;
    
    // Implementation methods...
}
```

**Outcome:** Service logic implemented
**Status:** ✅ Created

---

### **STEP 7: Create Coin Data Model**

**File:**
- `src/main/java/com/eztrad/chatbotserver/dto/Coin.java`

**Purpose:**
- Represent cryptocurrency market data
- JPA Entity for database persistence
- 26 fields for complete data

**Fields Include:**
- Basic: id, symbol, name, image
- Prices: currentPrice, high24h, low24h
- Market: marketCap, marketCapRank
- Volume: totalVolume
- Changes: priceChange24h, priceChangePercentage24h
- Supply: circulatingSupply, totalSupply
- And 10+ more...

**Code:**
```java
@Data
@Entity
public class Coin {
    @Id
    private String id;
    private String symbol;
    private String name;
    private Double currentPrice;
    private Long marketCap;
    // ... 20+ more fields
}
```

**Outcome:** Data model ready to hold crypto information
**Status:** ✅ Created

---

### **STEP 8: Integrate CoinGecko API**

**File:**
- `src/main/java/com/eztrad/chatbotserver/service/ChatbotServiceImplement.java`

**Purpose:**
- Fetch real-time cryptocurrency data
- Resolve coin ID from user input
- Parse API response into Coin object

**Methods Implemented:**

1. **resolveCoinId(String prompt)**
   - Search CoinGecko `/search` endpoint
   - Match user input to coin ID
   - Example: "bitcoin" → "bitcoin", "btc" → "bitcoin"

2. **makeApiRequest(String prompt)**
   - Call CoinGecko `/coins/{id}` endpoint
   - Extract 26 market data fields
   - Return Coin object

3. **Helper Methods**
   - convertToDouble() - Safe type conversion
   - getNumber() - Extract nested values from maps

**API Calls:**
```
GET https://api.coingecko.com/api/v3/search?query=bitcoin
GET https://api.coingecko.com/api/v3/coins/bitcoin
```

**Outcome:** CoinGecko integration working
**Status:** ✅ Created

---

### **STEP 9: Create Chat Controller**

**File:**
- `src/main/java/com/eztrad/chatbotserver/controller/ChatbotController.java`

**Purpose:**
- Handle HTTP requests for chat endpoints
- Route to service methods
- Return responses

**Endpoints:**

1. **POST /ai/chat**
   - Input: `{"prompt":"bitcoin"}`
   - Output: Coin object with market data
   - Calls: `chatbotService.getCoinDetails()`

**Annotation:**
```java
@RestController
@RequestMapping("/ai/chat")
public class ChatbotController {
    // Endpoints...
}
```

**Outcome:** Main endpoint created
**Status:** ✅ Created

---

### **STEP 10: Create Request Payload DTO**

**File:**
- `src/main/java/com/eztrad/chatbotserver/dto/PromptBody.java`

**Purpose:**
- Map request JSON to Java object
- Single field: prompt
- Used by all endpoints

**Code:**
```java
@Data
public class PromptBody {
    public String prompt;  // User input
}
```

**Usage:**
```java
@PostMapping
public ResponseEntity<ApiResponse> getCoinDetails(@RequestBody PromptBody prompt) {
    // prompt.getPrompt() contains user input
}
```

**Outcome:** Request payload DTO ready
**Status:** ✅ Created

---

### **STEP 11: Integrate Gemini AI**

**File:**
- `src/main/java/com/eztrad/chatbotserver/service/ChatbotServiceImplement.java`

**Purpose:**
- Send prompts to Google Gemini
- Get AI responses
- Handle JSON formatting

**Method:**
```java
public String simpleChat(String prompt) {
    String geminiUrl = geminiApiBaseUrl + "?key=" + geminiApiKey;
    // Build request body in Gemini format
    // Send POST request
    // Return response
}
```

**Gemini Request Format:**
```json
{
  "contents": [
    {
      "parts": [
        {
          "text": "user prompt here"
        }
      ]
    }
  ]
}
```

**Outcome:** Gemini AI integration working
**Status:** ✅ Created

---

### **STEP 12: Create Chat Endpoints**

**File:**
- `src/main/java/com/eztrad/chatbotserver/controller/ChatbotController.java`

**Purpose:**
- Create /ai/chat/simple endpoints
- Support JSON and plain text input
- Return AI responses

**Endpoints:**

1. **POST /ai/chat/simple (JSON)**
   ```
   Content-Type: application/json
   Body: {"prompt":"What is blockchain?"}
   Returns: Gemini response in JSON
   ```

2. **POST /ai/chat/simple (Plain Text)**
   ```
   Content-Type: text/plain
   Body: What is blockchain?
   Returns: Gemini response in JSON
   ```

**Code:**
```java
@PostMapping(value = "/simple", consumes = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<String> simpleChatHandler(@RequestBody PromptBody prompt) {
    String response = chatbotService.simpleChat(prompt.getPrompt());
    return new ResponseEntity<>(response, HttpStatus.OK);
}

@PostMapping(value = "/simple", consumes = MediaType.TEXT_PLAIN_VALUE)
public ResponseEntity<String> simpleChatHandlerPlain(@RequestBody String prompt) {
    String response = chatbotService.simpleChat(prompt);
    return new ResponseEntity<>(response, HttpStatus.OK);
}
```

**Outcome:** All endpoints complete and functional
**Status:** ✅ Created

---

## 🏗️ ARCHITECTURE FLOW

```
STEP 0: Prerequisites ✓
    ↓
STEP 1: Configure ✓
    ↓
STEP 2: Database ✓
    ↓
STEP 3: HomeController ✓
    ↓
STEP 4: ApiResponse ✓
    ↓
STEP 5: Service Interface & Config ✓
    ↓
STEP 6: Service Implementation ✓
    ↓
STEP 7: Coin Model ✓
    ↓
STEP 8: CoinGecko Integration ✓
    ↓
STEP 9: Chat Controller ✓
    ↓
STEP 10: PromptBody DTO ✓
    ↓
STEP 11: Gemini Integration ✓
    ↓
STEP 12: Chat Endpoints ✓
    ↓
BUILD & RUN ✓
```

---

## 📊 FILES CREATED BY STEP

| Step | File Created | Lines | Purpose |
|------|--------------|-------|---------|
| STEP 0 | pom.xml | 108 | Maven dependencies |
| STEP 1 | application.properties | 26 | Configuration |
| STEP 2 | (Database) | - | MySQL setup |
| STEP 3 | HomeController.java | 40 | Health endpoint |
| STEP 4 | ApiResponse.java | 25 | Response wrapper |
| STEP 5 | ChatbotService.java | 40 | Service interface |
| STEP 6 | ChatbotServiceImplement.java | 220+ | Business logic |
| STEP 7 | Coin.java | 120 | Data model |
| STEP 8 | (Same as STEP 6) | - | CoinGecko methods |
| STEP 9 | ChatbotController.java | 65 | Chat controller |
| STEP 10 | PromptBody.java | 20 | Request DTO |
| STEP 11 | (Same as STEP 6) | - | Gemini method |
| STEP 12 | (Same as STEP 9) | - | Chat endpoints |

---

## 🧪 TESTING BY STEP

### After STEP 3
```bash
curl http://localhost:5454/
# Expected: Welcome message
```

### After STEP 8
```bash
curl -X POST http://localhost:5454/ai/chat \
  -H "Content-Type: application/json" \
  -d '{"prompt":"bitcoin"}'
# Expected: Coin market data
```

### After STEP 12
```bash
curl -X POST http://localhost:5454/ai/chat/simple \
  -H "Content-Type: application/json" \
  -d '{"prompt":"What is blockchain?"}'
# Expected: AI response
```

---

## ⏱️ TIME BREAKDOWN

| Phase | Duration |
|-------|----------|
| STEP 0-2: Setup & Config | 10 minutes |
| STEP 3-7: Controllers & Models | 5 minutes |
| STEP 8-12: Integrations & Endpoints | 10 minutes |
| Build & Test | 5 minutes |
| **TOTAL** | **30 minutes** |

---

## ✅ COMPLETION CHECKLIST

- [ ] STEP 0: Prerequisites verified
- [ ] STEP 1: application.properties configured
- [ ] STEP 2: Database created
- [ ] STEP 3: HomeController created
- [ ] STEP 4: ApiResponse created
- [ ] STEP 5: Service interface created
- [ ] STEP 6: Service implementation created
- [ ] STEP 7: Coin model created
- [ ] STEP 8: CoinGecko integration tested
- [ ] STEP 9: Chat controller created
- [ ] STEP 10: PromptBody created
- [ ] STEP 11: Gemini integration tested
- [ ] STEP 12: Chat endpoints tested
- [ ] Build: `.\mvnw clean install` ✓
- [ ] Run: `.\mvnw spring-boot:run` ✓
- [ ] Test: All 4 endpoints working ✓

---

## 🎯 EXECUTION SUMMARY

**Total Steps**: 12
**Total Files**: 8 Java + 2 Config
**Total Code**: 600+ lines
**Total Comments**: 200+ lines
**Setup Time**: 30 minutes
**Status**: ✅ **READY FOR PRODUCTION**

---

**Start with STEP 0 and proceed in order. Each step depends on previous steps!**

For detailed information, refer to the other documentation files:
- README.md - Complete setup guide
- PROJECT_WORKFLOW.md - Architecture details
- POSTMAN_ENDPOINTS.md - API testing examples
- QUICK_REFERENCE.md - Quick commands

