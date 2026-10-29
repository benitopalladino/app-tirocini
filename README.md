# App Tirocini

Web application per la gestione dei tirocini universitari: anagrafica di studenti e aziende e gestione del ciclo di vita dei tirocini, con regole di business sulle date e sugli stati.

Il progetto è stato sviluppato durante un tirocinio formativo universitario in sviluppo back-end ed è stato poi approfondito come argomento della tesi triennale in Ingegneria Informatica.

## Funzionalità

- Gestione di **studenti** (matricola univoca), **aziende** (nome univoco, referente) e **tirocini** (creazione, modifica, eliminazione).
- Ricerca dei tirocini per azienda, stato e intervallo di date, con paginazione.
- Dashboard iniziale con i totali di studenti, aziende e tirocini.
- Validazione degli input e messaggi di errore dedicati per errori di validazione, regole di business e risorse non trovate.
- API REST di lettura per studenti, aziende e tirocini.

### Regole di business

- La data di fine di un tirocinio non può precedere la data di inizio.
- Gli stati ammessi sono `PROPOSED`, `ACTIVE` e `COMPLETED`. Le transizioni consentite sono `PROPOSED → ACTIVE` e `ACTIVE → COMPLETED`.
- Uno studente non può avere due tirocini `ACTIVE` con periodi sovrapposti.
- Matricola dello studente e nome dell'azienda devono essere univoci.
- Un'azienda con tirocini associati non può essere eliminata. Uno studente con tirocini `ACTIVE` o `COMPLETED` non può essere eliminato.

## Stack tecnologico

- Java 17
- Spring Boot 4.0.0 (Spring MVC, Spring Data JPA con Hibernate, Bean Validation)
- PostgreSQL 17
- Freemarker per le pagine web
- Lombok
- Maven (con wrapper incluso)
- Docker Compose per il database

## Architettura

Struttura a livelli, con separazione tra interfaccia, logica di business e accesso ai dati:

```
src/main/java/it/appTirocini/
├── controller/   controller MVC (pagine Freemarker)
├── api/          controller REST (JSON)
├── service/      logica di business e regole di validazione
├── repository/   accesso ai dati (Spring Data JPA)
├── entity/       entità JPA: Student, Company, Internship
├── dto/          oggetti di trasferimento dati
├── mapper/       conversione entità <-> DTO
└── error/        gestione centralizzata degli errori (MVC e REST)
```

## Modello dati

- **Student**: id (UUID), matricola, nome, cognome, email.
- **Company**: id (UUID), nome, città, nome e email del referente.
- **Internship**: id (UUID), studente, azienda, data di inizio, data di fine, stato, note.

Lo schema viene creato e aggiornato automaticamente da Hibernate all'avvio (`ddl-auto=update`). Non sono inclusi dati di esempio: all'avvio l'applicazione è vuota.

## Avvio in locale

Requisiti: JDK 17 e Docker.

1. Avvia il database PostgreSQL:

```
docker compose up -d
```

2. Avvia l'applicazione:

```
./mvnw spring-boot:run
```

3. Apri `http://localhost:8080` nel browser.

Per fermare il database: `docker compose down`.

Il database usa credenziali di sviluppo (`postgres` / `postgres`, database `tirocini`, porta `5432`), definite in `docker-compose.yml` e in `src/main/resources/application.properties`. Servono solo per l'uso in locale e vanno sostituite in qualsiasi ambiente reale.

## Pagine e API

| Percorso | Descrizione |
|---|---|
| `/` | Dashboard con i totali |
| `/students`, `/companies`, `/internships` | Elenco e gestione (nuovo, modifica, elimina) |
| `GET /api/students` | Elenco studenti in JSON |
| `GET /api/companies` | Elenco aziende in JSON |
| `GET /api/internships` | Elenco tirocini in JSON |

## Limiti noti

- Nessuna autenticazione né autorizzazione: tutte le pagine e le API sono accessibili.
- Le API REST sono solo di lettura.
- Un solo test automatico, che verifica il caricamento del contesto Spring.
- Docker è usato solo per il database: l'applicazione non è containerizzata.
- Le credenziali del database sono scritte nei file di configurazione (valori di sviluppo).
- Il log delle query SQL (`show-sql`) è attivo.

## Sviluppi futuri

- Autenticazione e autorizzazione con Spring Security.
- API REST complete (creazione, modifica, eliminazione).
- Test unitari e di integrazione sui servizi e sui controller.
- Dockerfile per l'applicazione e configurazione tramite variabili d'ambiente.
- Gestione delle migrazioni dello schema (per esempio con Flyway).
