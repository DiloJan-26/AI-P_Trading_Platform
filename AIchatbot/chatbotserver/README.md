# Real Time Crypto Market Details Chat Bot - Complete Project Guide

## 📋 Table of Contents
1. [Overview](#overview)
2. [Quick Start](#quick-start)
3. [Prerequisites](#prerequisites)
4. [Installation Steps](#installation-steps)
5. [Configuration](#configuration)
6. [Running the Application](#running-the-application)
7. [API Endpoints](#api-endpoints)
8. [Testing](#testing)
9. [Troubleshooting](#troubleshooting)
10. [Project Structure](#project-structure)

---

## Overview

This is a **Spring Boot chatbot application** that combines two powerful APIs:

1. **CoinGecko API** - Real-time cryptocurrency market data
2. **Google Gemini API** - AI-powered chat responses

### Key Features
✅ Fetch real-time crypto prices, market cap, volume, and trends
✅ Ask any question to Gemini AI about cryptocurrency
✅ RESTful API with easy-to-use endpoints
✅ Both JSON and plain text request formats supported
✅ Comprehensive error handling and API key management

### Technology Stack
- **Framework**: Spring Boot 4.0.3
- **Language**: Java 21
- **Database**: MySQL
- **APIs**: CoinGecko, Google Gemini
- **Build Tool**: Maven

---

## Quick Start

### For Experienced Developers

```bash
# Clone/extract project
cd chatbotserver

# Update application.properties with your API keys
nano src/main/resources/application.properties

# Create database
mysql -u root -p < CREATE DATABASE crypto_chatbot;

# Build and run
mvn clean install
mvn spring-boot:run

# Test endpoints
curl http://localhost:5454/
curl -X POST http://localhost:5454/ai/chat -H "Content-Type: application/json" -d '{"prompt":"bitcoin"}'
```

---

## Prerequisites

### System Requirements
- **Java**: 21 or higher
- **Maven**: 3.6 or higher
- **MySQL**: 5.7 or higher

### API Keys
1. **Gemini API Key**
   - Get from: https://aistudio.google.com/apikey
   - Free tier available
   - Choose a supported model (gemini-1.5-flash recommended)

2. **CoinGecko API Key** (Optional)
   - Free tier: No key needed
   - Pro tier: Get from https://www.coingecko.com/en/api

### Installation Check
```bash
# Check Java version
java -version

# Check Maven version
mvn -version

# Check MySQL is running
mysql -u root -p -e "SELECT VERSION();"
```

---

## Installation Steps

### Step 1: Create Database
```bash
mysql -u root -p
```

In MySQL terminal:
```sql
CREATE DATABASE crypto_chatbot;
EXIT;
```

### Step 2: Clone/Extract Project
```bash
cd /path/to/chatbotserver
```

### Step 3: Configure API Keys
Open `src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/crypto_chatbot
spring.datasource.username=root
spring.datasource.password=your_mysql_password

# Gemini API Configuration
gemini.api.key=YOUR_GEMINI_API_KEY_HERE
gemini.api.base-url=https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent

# CoinGecko API Configuration (optional)
coingecko.api.base-url=https://api.coingecko.com/api/v3
coingecko.api.key=YOUR_COINGECKO_PRO_KEY_HERE
```

### Step 4: Install Dependencies
```bash
mvn clean install
```

This downloads all required libraries (SpringBoot, MySQL, Lombok, etc.)

---

## Configuration

### application.properties File Structure

```ini
# Application Basics
spring.application.name=chatbotserver
server.port=5454

# Database Connection
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://localhost:3306/crypto_chatbot
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Gemini AI Configuration
# IMPORTANT: Use v1beta endpoint, not v1
# Check supported models for your API key
gemini.api.key=YOUR_API_KEY
gemini.api.base-url=https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent

# CoinGecko Configuration
coingecko.api.base-url=https://api.coingecko.com/api/v3
coingecko.api.key=  # Leave empty for free tier
```

### Configuration Notes
- **Port 5454**: Change if already in use
- **MySQL URL**: Format: `jdbc:mysql://host:port/database`
- **Gemini Endpoint**: MUST be `v1beta`, not `v1`
- **CoinGecko Key**: Optional for free tier

---

## Running the Application

### Method 1: Maven Command (Recommended)
```bash
mvn spring-boot:run
```

Application will start on `http://localhost:5454`

### Method 2: Build and Run JAR
```bash
# Build JAR
mvn clean package

# Run JAR
java -jar target/chatbotserver-0.0.1-SNAPSHOT.jar
```

### Method 3: IDE (IntelliJ/Eclipse)
1. Open project in IDE
2. Right-click `ChatbotserverApplication.java`
3. Select "Run" or "Debug"

### Verify Application Started
```bash
# Should return welcome message
curl http://localhost:5454/

# Response:
# {"message":"Welcome to the Chatbot Server API!","data":null}
```

---

## API Endpoints

### 1️⃣ Home Endpoint (Health Check)

**Request:**
```http
GET http://localhost:5454/
```

**Response (200):**
```json
{
  "message": "Welcome to the Chatbot Server API!",
  "data": null
}
```

---

### 2️⃣ Crypto Market Data Endpoint

**Request:**
```http
POST http://localhost:5454/ai/chat
Content-Type: application/json

{
  "prompt": "bitcoin"
}
```

**Valid Prompts:**
- "bitcoin", "ethereum", "cardano"
- "btc", "eth", "ada" (symbols)
- "what is the price of doge" (natural language)

**Response (200):**
```json
{
  "message": "Coin data fetched successfully",
  "data": {
    "id": "bitcoin",
    "symbol": "btc",
    "name": "Bitcoin",
    "image": "https://assets.coingecko.com/coins/images/1/large/bitcoin.png",
    "currentPrice": 45250.50,
    "marketCap": 885000000000,
    "marketCapRank": 1,
    "totalVolume": 35000000000,
    "priceChange24h": 1250.75,
    "priceChangePercentage24h": 2.85,
    "circulatingSupply": 21000000,
    "totalSupply": 21000000
  }
}
```

---

### 3️⃣ AI Chat Endpoint (JSON)

**Request:**
```http
POST http://localhost:5454/ai/chat/simple
Content-Type: application/json

{
  "prompt": "What is blockchain technology?"
}
```

**Response (200):**
```json
{
  "candidates": [
    {
      "content": {
        "parts": [
          {
            "text": "Blockchain is a distributed ledger technology..."
          }
        ],
        "role": "model"
      },
      "finishReason": "STOP",
      "index": 0
    }
  ],
  "usageMetadata": {
    "promptTokenCount": 10,
    "candidatesTokenCount": 85,
    "totalTokenCount": 95
  }
}
```

---

### 4️⃣ AI Chat Endpoint (Plain Text)

**Request:**
```http
POST http://localhost:5454/ai/chat/simple
Content-Type: text/plain

What is the difference between Bitcoin and Ethereum?
```

**Response:** Same JSON format as above

---

## Testing

### Using cURL

```bash
# Test home
curl http://localhost:5454/

# Test coin data
curl -X POST http://localhost:5454/ai/chat \
  -H "Content-Type: application/json" \
  -d '{"prompt":"bitcoin"}'

# Test AI chat
curl -X POST http://localhost:5454/ai/chat/simple \
  -H "Content-Type: application/json" \
  -d '{"prompt":"What is DeFi?"}'

# Test plain text chat
curl -X POST http://localhost:5454/ai/chat/simple \
  -H "Content-Type: text/plain" \
  -d "Explain blockchain"
```

### Using Postman

1. Create new request → POST
2. URL: `http://localhost:5454/ai/chat`
3. Headers: `Content-Type: application/json`
4. Body (raw): `{"prompt":"bitcoin"}`
5. Click "Send"

See `POSTMAN_ENDPOINTS.md` for detailed examples

---

## Troubleshooting

### Error: "404 from Gemini - model not found"

**Cause:** Wrong endpoint or model name

**Solution:**
- Change endpoint to `v1beta` (not `v1`)
- Update `gemini.api.base-url`:
```properties
gemini.api.base-url=https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent
```
- Verify API key at: https://aistudio.google.com/apikey

---

### Error: "coin not found"

**Cause:** Invalid coin name or symbol

**Solution:**
- Use valid names: bitcoin, ethereum, cardano, solana
- Use symbols: btc, eth, ada, sol
- System auto-resolves to bitcoin if unclear

Valid coins: https://api.coingecko.com/api/v3/coins/list

---

### Error: "Cannot connect to MySQL"

**Cause:** MySQL not running or wrong credentials

**Solution:**
```bash
# Start MySQL
sudo service mysql start  # Linux
brew services start mysql-community@5.7  # macOS
# Windows: Start MySQL from Services

# Check credentials
mysql -u root -p
# Enter your password

# Create database
CREATE DATABASE crypto_chatbot;
```

---

### Error: "400 Bad Request"

**Cause:** Invalid JSON or missing Content-Type header

**Solution:**
- Set header: `Content-Type: application/json`
- Validate JSON format: `{"prompt":"text"}`
- Include `prompt` field in all requests

---

### Error: "Maven not found"

**Cause:** Maven not installed or not in PATH

**Solution:**
```bash
# Install Maven
# macOS:
brew install maven

# Linux:
sudo apt-get install maven

# Windows:
choco install maven

# Verify
mvn -version
```

---

## Project Structure

```
chatbotserver/
│
├── src/main/java/com/eztrad/chatbotserver/
│   ├── ChatbotserverApplication.java       # Main entry point
│   │
│   ├── controller/
│   │   ├── HomeController.java             # GET / endpoint
│   │   └── ChatbotController.java          # POST /ai/chat* endpoints
│   │
│   ├── service/
│   │   ├── ChatbotService.java             # Service interface
│   │   └── ChatbotServiceImplement.java    # Service implementation
│   │                                        # (API calls, data parsing)
│   │
│   ├── dto/
│   │   ├── Coin.java                       # Coin data model
│   │   └── PromptBody.java                 # Request payload
│   │
│   └── response/
│       └── ApiResponse.java                # Response wrapper
│
├── src/main/resources/
│   ├── application.properties               # Configuration (EDIT THIS!)
│   └── application.properties.example       # Example template
│
├── pom.xml                                  # Maven dependencies
├── PROJECT_WORKFLOW.md                      # This file
├── POSTMAN_ENDPOINTS.md                     # Postman testing guide
└── README.md                                # Additional docs
```

### File Responsibilities

| File | Purpose |
|------|---------|
| `ChatbotserverApplication.java` | Starts Spring Boot app |
| `HomeController.java` | Handles GET / endpoint |
| `ChatbotController.java` | Handles POST /ai/chat endpoints |
| `ChatbotService.java` | Interface defining service contract |
| `ChatbotServiceImplement.java` | Implements all business logic |
| `Coin.java` | Maps CoinGecko API response to object |
| `PromptBody.java` | Maps request JSON to object |
| `ApiResponse.java` | Standardizes all responses |
| `application.properties` | Configuration & API keys |

---

## Code Comments Guide

All Java files include detailed comments with execution flow:

- **Step 1**: Database setup
- **Step 2**: Database creation
- **Step 3**: Home controller setup
- **Step 4**: Response class definition
- **Step 5**: Configuration injection
- **Step 6**: Service implementation
- **Step 7**: Data model (Coin DTO)
- **Step 8**: CoinGecko API integration
- **Step 9**: Controller endpoints
- **Step 10**: Request payload mapping
- **Step 11**: Gemini AI integration
- **Step 12**: Chat endpoints

Follow these comments to understand the execution flow!

---

## Common Code Paths

### Getting Crypto Data Flow
```
1. User sends POST to /ai/chat with {"prompt":"bitcoin"}
2. ChatbotController.getCoinDetails() receives request
3. Calls ChatbotService.getCoinDetails(prompt)
4. Service calls resolveCoinId(prompt) → CoinGecko /search
5. Service calls makeApiRequest(coinId) → CoinGecko /coins/{id}
6. Parses response into Coin object
7. Returns ApiResponse with Coin data
8. Client receives JSON with market data
```

### AI Chat Flow
```
1. User sends POST to /ai/chat/simple with {"prompt":"What is blockchain?"}
2. ChatbotController.simpleChatHandler() receives request
3. Calls ChatbotService.simpleChat(prompt)
4. Formats prompt in Gemini request format
5. Sends POST to Gemini API
6. Returns raw Gemini JSON response
7. Client receives AI response
```

---

## Deployment

### Build for Production
```bash
mvn clean package -DskipTests
```

Creates: `target/chatbotserver-0.0.1-SNAPSHOT.jar`

### Docker Deployment (Optional)

Create `Dockerfile`:
```dockerfile
FROM openjdk:21-jdk
COPY target/chatbotserver-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

Build and run:
```bash
docker build -t crypto-chatbot .
docker run -p 5454:5454 crypto-chatbot
```

---

## Support & Resources

- **CoinGecko API**: https://docs.coingecko.com/reference/introduction
- **Gemini API**: https://ai.google.dev/docs
- **Spring Boot**: https://spring.io/projects/spring-boot
- **Maven**: https://maven.apache.org/
- **Java Docs**: https://docs.oracle.com/en/java/javase/21/

---

## License & Notes

This is a demonstration project for learning purposes.
Ensure you follow the APIs' terms of service before deployment.

**Last Updated**: 2026-02-24
**Version**: 1.0.0-SNAPSHOT

