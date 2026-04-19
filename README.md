# Voice Matching API

Java Spring Boot REST API that ports the provided Express server to Java and adds production-oriented data processing optimizations for higher throughput and stream efficiency.

## Features

- Profile creation and lookup
- Experience listing
- Voice check-in endpoint
- Optional ElevenLabs speech-to-text from base64 audio
- AI profile extraction service abstraction
- Supabase/Postgres-backed persistence via Spring Data JDBC
- Match and circle orchestration
- Async processing and connection pooling tuned for production workloads
- Batch-friendly, low-copy request handling and optimized query paths

## Throughput optimization approach

This project is structured to improve production-scale throughput by:

- Reusing HTTP connections for external APIs
- Using async execution for external network-bound calls
- Reducing unnecessary DB round trips where practical
- Adding indexes for hot lookup/update paths
- Using streaming-friendly request decoding and bounded executor pools
- Enabling HikariCP pooling and compression support

A realistic 30% throughput improvement depends on your baseline, infra, schema size, and traffic mix, so validate with load tests in your environment.

## Stack

- Java 21
- Spring Boot 3
- Spring Web
- Spring Data JDBC
- PostgreSQL
- Jackson
- Maven

## Environment variables

- `PORT` (optional)
- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `ELEVENLABS_API_KEY` (optional)
- `ANTHROPIC_API_KEY` (optional)

## Run

```bash
mvn spring-boot:run
```

## API

- `POST /profiles`
- `GET /profiles/{id}`
- `GET /experiences`
- `POST /voice`
- `GET /matches/{profileId}`
- `PATCH /matches/{id}`
