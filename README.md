# Sports Betting API

## Description
This project implements a simple sports betting system for car races (Ferrari, BMW, Audi, Honda). Users can place bets on specific cars and retrieve information about the total bet amounts.

## Technologies
- Java 17
- Spring Boot 3.3.8
- Maven

## Installation & Running

### 1. Clone the repository
```sh
git clone https://github.com/bondoleks/SpringBootBetAPI.git
```

### 2. Run the application
```sh
mvn clean spring-boot:run
```

### 3. Run tests
```sh
mvn test
```

## API Endpoints

### 1. Place a Bet
`POST /api/bets`
#### Request (JSON):
```json
{
  "userName": "Tom",
  "car": "FERRARI",
  "amount": 100.50
}
```
#### Response:
```json
{"message": "Bet placed successfully"}
```

### 2. Get Total Bet Amounts
`GET /api/bets`

#### Optional Parameters:
- `car` – Car name (if provided, returns total bets only for this car)

#### Response (JSON):
```json
{
  "FERRARI": 250.75,
  "BMW": 150.00
}
```

## Usage Examples
### Placing a Bet
```sh
curl -X POST "http://localhost:port/api/bets" -H "Content-Type: application/json" -d '{"userName":1,"car":"FERRARI","amount":100.50}'
```

### Retrieving All Bets
```sh
curl -X GET "http://localhost:port/api/bets"
```

### Retrieving Bets for a Specific Car
```sh
curl -X GET "http://localhost:port/api/bets?car=FERRARI"
```