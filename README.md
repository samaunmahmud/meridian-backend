# Meridian — Backend

A real-time market data backend built with Spring Boot, PostgreSQL, and Docker. Polls live stock prices from an external API on a schedule, stores full price history, and exposes a clean REST API for consumption by the [Meridian frontend](https://github.com/samaunmahmud/meridian-frontend).

Built as a portfolio project to practice production backend patterns: layered architecture, containerized local development, scheduled background jobs, and clean API design.

## Tech stack

| Technology | Purpose |
|---|---|
| Java 21 + Spring Boot 3 | Core backend framework |
| PostgreSQL 16 | Persistent storage for tickers and price history |
| Docker Compose | Local database orchestration |
| Spring Data JPA / Hibernate | ORM layer |
| Alpha Vantage API | External market data source |
| Maven | Build tool |

## Architecture

Market data API → Ingestion (scheduled job) → PostgreSQL → REST API → Frontend

The scheduler polls a small watchlist of tickers on a fixed interval, respecting the market data provider's free-tier rate limit. Each poll resolves the ticker (creating it if new) and appends a price history entry.

## Project structure

## Getting started

### Prerequisites
- Java 21
- Maven
- Docker Desktop
- A free Alpha Vantage API key: https://www.alphavantage.co/support/#api-key

### Setup

1. Clone the repo: git clone https://github.com/samaunmahmud/meridian-backend.git
   cd meridian-backend
2. Create your local `.env` file (never committed — see `.env.example` for the required shape): 
   POSTGRES_USER=meridian
   POSTGRES_PASSWORD=password
   POSTGRES_DB=meridian
   ALPHA_VANTAGE_API_KEY=
3. Start Postgres: docker compose up -d
4. Run the app: mvn spring-boot:run



The API will be available at `http://localhost:8080`.

## API endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/health` | Health check |
| GET | `/api/tickers` | List all tracked tickers |
| GET | `/api/prices/{symbol}` | Full price history for a ticker |

## Roadmap

- [x] Layered Spring Boot foundation
- [x] Dockerized Postgres with JPA schema
- [x] Scheduled polling job for live price data
- [x] REST API with DTOs and proper error handling
- [ ] Unit and integration tests (JUnit + Mockito)
- [ ] CI/CD pipeline via GitHub Actions
- [ ] WebSocket support for real-time price push
- [ ] Redis caching layer
- [ ] Kafka-based event-driven architecture