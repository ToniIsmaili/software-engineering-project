# SEEU Software Engineering Project

A REST API for tracking product prices across retailers. Built with **Java**, **Jersey (JAX-RS)**, and **PostgreSQL**, packaged as a WAR for **Apache Tomcat**.

You can register users, log in with JWT, manage products/retailers/prices, and run scraper jobs per retailer.

---

## What you need installed

| Tool | Version (approx.) | Why |
|------|-------------------|-----|
| **JDK** | 25 (see `pom.xml`) | Compile and run the app |
| **Maven** | 3.9+ | Build the WAR |
| **PostgreSQL** | 14+ | Database |
| **Tomcat** | 10.1+ (Jakarta EE) | Host the API locally |

---

## Setup (step by step)

### 1. Clone the repository

```bash
git clone https://github.com/ToniIsmaili/software-engineering-project.git
cd software-engineering-project
```

### 2. Create a PostgreSQL database

Open `psql`, pgAdmin, or any SQL client and run:

```sql
CREATE DATABASE seeu_project;
```

Use any database name you like — just keep it consistent in the connection URL below.

### 3. Create tables (schema)

From the project root:

```bash
psql -U postgres -d seeu_project -f db/ddl.sql
```

On Windows PowerShell, same command if `psql` is on your PATH. Adjust `-U` and the database name to match your machine.

This creates tables for products, retailers, prices, users, scrapers, jobs, and logs.

### 4. Set the database connection (`DB_URL`)

The app reads **one** environment variable: `DB_URL`. It must be a full JDBC URL, including username and password:

```text
jdbc:postgresql://localhost:5432/seeu_project?user=postgres&password=YOUR_PASSWORD
```

**IntelliJ / Smart Tomcat**

1. Run → Edit Configurations → your Tomcat run config  
2. Environment variables → add `DB_URL` with the value above  

**Windows (PowerShell, current session)**

```powershell
$env:DB_URL = "jdbc:postgresql://localhost:5432/seeu_project?user=postgres&password=YOUR_PASSWORD"
```

**Linux / macOS**

```bash
export DB_URL="jdbc:postgresql://localhost:5432/seeu_project?user=postgres&password=YOUR_PASSWORD"
```

If `DB_URL` is missing when Tomcat starts, the app will fail on startup with `DB_URL environment variable is not set`.

### 5. Build the project

```bash
mvn clean package
```

The WAR file is produced at:

```text
target/software-engineering-project.war
```

### 6. Run on Tomcat

**Option A — IntelliJ (recommended if you use it)**

1. Open the project in IntelliJ  
2. Configure Tomcat (or use the existing Smart Tomcat setup)  
3. Set `DB_URL` on the run configuration  
4. Deploy and start the server  

**Option B — Manual Tomcat**

1. Copy `target/software-engineering-project.war` into Tomcat’s `webapps/` folder  
2. Set `DB_URL` in Tomcat’s environment (e.g. `setenv.sh` / `setenv.bat`, or service environment)  
3. Start Tomcat  

The API is served at:

```text
http://localhost:8080/software-engineering-project/
```

Example: list products (after you have a token — see below):

```text
GET http://localhost:8080/software-engineering-project/products
```

There is no `/api` prefix; Jersey is mapped to `/*` on the WAR context path.

### 8. Create a user and get a token

Protected routes need a JWT.

**Sign up** (public):

```http
POST http://localhost:8080/software-engineering-project/auth/signup
Content-Type: application/json

{
  "email": "you@example.com",
  "password": "your-password"
}
```

**Log in** (public):

```http
POST http://localhost:8080/software-engineering-project/auth/login
Content-Type: application/json

{
  "email": "you@example.com",
  "password": "your-password"
}
```

Response includes `access_token` and `refresh_token`. Use the access token on other calls:

```http
Authorization: Bearer <access_token>
```

**Refresh** when the access token expires (~15 minutes):

```http
POST http://localhost:8080/software-engineering-project/auth/refresh
Content-Type: application/json

{
  "refresh_token": "<refresh_token>"
}
```

### 9. Quick sanity check

With a valid token:

```http
GET http://localhost:8080/software-engineering-project/products
GET http://localhost:8080/software-engineering-project/products/categories
GET http://localhost:8080/software-engineering-project/retailers
```

You should get JSON back. CORS is open (`*`) so a browser frontend on another origin can call the API.

---

## Running tests

Integration tests hit the real database. Set `DB_URL` the same way as for Tomcat, then:

```bash
mvn test -DskipTests=false
```

By default, `pom.xml` has `skipTests` set to `true`, so a plain `mvn test` does not run them. In IntelliJ, run a test class with `DB_URL` set in the run configuration environment.

---

## Troubleshooting

**`DB_URL environment variable is not set`**  
Tomcat started without `DB_URL`. Add it to the run configuration or Tomcat environment and restart.

**`401 Unauthorized` on every request**  
Missing or expired token. Log in again or call `/auth/refresh`. Only access tokens work in `Authorization` (not the refresh token).

**`409` on signup**  
Email already exists.

**Login fails after signup**  
Signup stores a BCrypt hash. Login compares plain password to that hash — use the same password you signed up with.

**Empty product list but seed ran**  
Check you are calling the correct host/port/context path and sending the Bearer header.

**Foreign key error when loading `mock-product-prices.sql`**  
Run retailers first, and ensure product IDs in the DB match `db/products-with-ids.json` (or regenerate prices with `generate-mock-prices.py`).

**Port 8080 already in use**  
Stop the other process or change Tomcat’s HTTP port.

---

## Course note

This repository is part of the SEEU Software Engineering course. For team-specific deployment or grading instructions, follow your course guidelines in addition to this README.
