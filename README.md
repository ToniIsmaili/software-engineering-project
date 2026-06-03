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

The app reads **one** environment variable: `DB_URL`. It must be a full **JDBC** URL (username and password in the string).

**Neon (cloud):** paste your JDBC URL from the Neon console, or convert `postgresql://...` → `jdbc:postgresql://...` (see [Docker (Neon)](#docker-neon--external-database) below).

**Local Postgres:**

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

## Docker (Neon / external database)

The API runs in Docker; the database stays on **[Neon](https://neon.tech)** (or any PostgreSQL you already have). You only need your **JDBC connection string** in a `.env` file.

### What you need

- [Docker Desktop](https://www.docker.com/products/docker-desktop/)  
- A Neon project with a connection string  
- Schema applied once on Neon (`db/ddl.sql`)

### Step 1 — Prepare Neon

1. In the Neon console, open your project → **Connection details**.  
2. Copy the connection string.  
3. In Neon’s **SQL Editor**, run the full contents of `db/ddl.sql` (creates all tables).  
4. Optional: run `mock-retailers.sql` / `mock-product-prices.sql` from the same editor if you want sample data.

### Step 2 — Create `.env` with your JDBC URL

```bash
copy .env.example .env
```

(On macOS/Linux: `cp .env.example .env`)

Edit `.env` and set `DB_URL` to your Neon JDBC URL.

**If Neon shows `postgresql://` (not JDBC), convert like this:**

```text
postgresql://alex:secret@ep-cool-name.eu-central-1.aws.neon.tech/neondb?sslmode=require
```

becomes:

```text
jdbc:postgresql://ep-cool-name.eu-central-1.aws.neon.tech/neondb?user=alex&password=secret&sslmode=require
```

Keep `sslmode=require` for Neon. If the password has special characters (`@`, `#`, `%`), URL-encode it in the `password=` parameter.

Set `JWT_SECRET` to any long random string.

### Step 3 — Run the API in Docker

From the project root:

```bash
docker compose up --build
```

First build can take several minutes. When Tomcat is up and there are no `DB_URL` errors, the API is at:

```text
http://localhost:8080/software-engineering-project/
```

`.env` is loaded automatically; it is listed in `.gitignore` — do not commit it.

### Optional — local Postgres instead of Neon

If you want a database inside Docker for offline dev:

```bash
docker compose -f docker-compose.yml -f docker-compose.local.yml up --build
```

That starts Postgres + API and ignores Neon for `DB_URL` (uses the local URL from `docker-compose.local.yml`).

### Step 2 — Test the API

**Sign up:**

```bash
curl -X POST http://localhost:8080/software-engineering-project/auth/signup ^
  -H "Content-Type: application/json" ^
  -d "{\"email\":\"docker@test.com\",\"password\":\"test12345\"}"
```

(On macOS/Linux use `\` instead of `^` for line breaks, or put the JSON on one line.)

**Log in** and copy `access_token`, then:

```bash
curl http://localhost:8080/software-engineering-project/products ^
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

### Step 4 — Optional sample data on Neon

Run `db/mock-retailers.sql` and `db/mock-product-prices.sql` in the Neon SQL Editor (after products exist with matching IDs).

### Step 5 — Environment variables

| Variable | Required | Description |
|----------|----------|-------------|
| `DB_URL` | Yes | Full JDBC URL, e.g. `jdbc:postgresql://HOST:5432/DB?user=U&password=P` |
| `JWT_SECRET` | Strongly recommended in production | Signing key for JWTs (set a long random string) |

See `.env.example` for a template.

### Step 6 — Stop

```bash
docker compose down
```

### Deploy the same image to the cloud (with Neon)

1. **Build and push** the image to a registry (Docker Hub, GitHub Container Registry, etc.):

   ```bash
   docker build -t youruser/seeu-api:latest .
   docker push youruser/seeu-api:latest
   ```

2. Use your existing **Neon** database (schema already on Neon from step 1).

3. **Run the container** on your platform with:
   - Image: `youruser/seeu-api:latest`
   - Port: **8080**
   - Env: `DB_URL` = same Neon JDBC URL as in `.env`, `JWT_SECRET`

4. **Public URL** will look like:
   `https://your-service.onrender.com/software-engineering-project/`

**Render (example):** New → Web Service → Deploy from GitHub → set **Dockerfile** path → add Postgres add-on or external DB → Environment → `DB_URL`, `JWT_SECRET` → Deploy.

**Railway (example):** New project → Add PostgreSQL → Add service from repo (Dockerfile) → Variables → `DB_URL` from Railway’s Postgres connect tab, `JWT_SECRET` → Generate domain.

Point your frontend at the HTTPS base URL including `/software-engineering-project`.

### Docker troubleshooting

**Build fails on Java 25 image**  
Pull latest Docker images, or install JDK 25 locally and report the Maven error from `docker compose build`.

**`DB_URL environment variable is not set` in api logs**  
The `api` service has no `DB_URL`. Check `docker-compose.yml` or your cloud env settings.

**Cannot connect to Neon / SSL errors**  
Use `sslmode=require` in `DB_URL`. Check Neon allows connections from your IP (or use pooled connection string if Neon recommends it).

**404 on `http://localhost:8080/`**  
Normal. Use `/software-engineering-project/...` paths.

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
