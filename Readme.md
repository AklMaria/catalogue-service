# Catalogue Service (Spring Boot + Postgres) — Hexagonal Architecture (Ports & Adapters)

Questo repository contiene il **microservizio Catalogue Service**, indipendente, scritto in **Java + Spring Boot** con **PostgreSQL**.
Architettura adottata: **Hexagonal Architecture (Ports & Adapters)** con cartella `infrastructure` e **mapper** (web/persistence).

---

## Funzionalità e Modello Dati

### Tabelle
1) **resources**
- `id` (UUID)
- `name` (string)
- `description` (string)

2) **slots**
- `id` (UUID)
- `start_date` (TIMESTAMPTZ)
- `end_date` (TIMESTAMPTZ)

3) **resource_availability** (many-to-many “arricchita”)
- `id` (UUID)
- `resource_id` (FK → resources.id)
- `slot_id` (FK → slots.id)
- `capacity` (int)
- `consumed` (int)

> Scelta di progetto: **Catalogue è “descrittivo”**.  
> `consumed` è **read-only** in questo microservizio e verrà aggiornato dal secondo MS (booking/reservation).

---

## Requisiti
- Docker + Docker Compose
- (Opzionale) Java 17 + Maven (se avvio senza Docker)
- (Opzionale) DataGrip/pgAdmin
- (Opzionale) Apidog per test API

---

## Architettura (Hex + infrastructure)

Struttura ad alto livello:

- `domain`  
  Modello di dominio puro (nessuna dipendenza Spring/JPA).
- `application`  
  Use case + ports:
    - `application.port.in` → Use case (porte in ingresso)
    - `application.port.out` → Porte verso l’esterno (DB, servizi esterni…)
    - `application.service` → Implementazioni dei use case
- `infrastructure/adapter`  
  Adapters e mapping:
    - `in.web` → controller + DTO + web mapper
    - `out.persistence` → entity JPA + repository + persistence mapper + adapter

### Dipendenze tra layer (regola)
- `infrastructure` → dipende da `application` e `domain`
- `application` → dipende da `domain`
- `domain` → non dipende da nulla (solo Java)

---

## Gestione Date/Timezone (Slot)
- In DB usiamo `TIMESTAMPTZ`.
- In persistence (JPA Entity) usiamo `OffsetDateTime`.
- In dominio usiamo `ZonedDateTime`.
- La conversione avviene nel `SlotPersistenceMapper`.

---

## Error Handling Globale
È presente un `@RestControllerAdvice` globale:

`com.catalogueservice.infrastructure.adapter.in.web.exception.GlobalExceptionHandler`

Gestisce:
- `IllegalArgumentException` → **400 Bad Request** con JSON
- `MethodArgumentNotValidException` (`@Valid`) → **400** con `fieldErrors`

---

## Configurazione (env vars / 12-factor)
L’app usa variabili d’ambiente (con default per sviluppo):

- `SERVER_PORT` (default `8081`)
- `DB_URL` (default `jdbc:postgresql://localhost:5432/catalogue_db`)
- `DB_USER` (default `catalogue_user`)
- `DB_PASSWORD` (default `catalogue_pass`)

---

## Migrazioni DB (Flyway)
Le tabelle vengono create automaticamente all’avvio tramite Flyway.

Path:
- `src/main/resources/db/migration`

Tabelle attese:
- `resources`
- `slots`
- `resource_availability`
- `flyway_schema_history`

---

## Avvio con Docker Compose (consigliato)

### Start (build + run)
Dalla root del progetto:

```bash
docker compose up --build -d