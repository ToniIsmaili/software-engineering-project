CREATE TABLE products (
                          id UUID PRIMARY KEY,

                          name VARCHAR(255) NOT NULL,
                          description TEXT,
                          brand VARCHAR(255),
                          category VARCHAR(255),
                          specifications TEXT
);

CREATE TABLE retailers (
                           id UUID PRIMARY KEY,

                           name VARCHAR(255) NOT NULL,
                           url TEXT
);

CREATE TABLE product_prices (
                                id UUID PRIMARY KEY,

                                price INTEGER NOT NULL,
                                availability BOOLEAN NOT NULL DEFAULT FALSE,
                                last_updated TIMESTAMPTZ,

                                product_id UUID NOT NULL,
                                retailer_id UUID NOT NULL,

                                CONSTRAINT fk_product
                                    FOREIGN KEY (product_id)
                                        REFERENCES products (id)
                                        ON DELETE CASCADE,

                                CONSTRAINT fk_retailer
                                    FOREIGN KEY (retailer_id)
                                        REFERENCES retailers (id)
                                        ON DELETE CASCADE
);

CREATE TABLE scrapers (
                          id UUID PRIMARY KEY,

                          status VARCHAR(20) NOT NULL,
                          last_run TIMESTAMPTZ,

                          retailer_id UUID NOT NULL,

                          CONSTRAINT fk_retailer
                              FOREIGN KEY (retailer_id)
                                  REFERENCES retailers (id)
                                  ON DELETE CASCADE,

                          CONSTRAINT chk_scraper_status
                              CHECK (status IN ('IDLE', 'RUNNING'))
);

CREATE TABLE users (
                       id UUID PRIMARY KEY,

                       email VARCHAR(255) NOT NULL UNIQUE,
                       password_hash TEXT NOT NULL,

                       is_verified BOOLEAN NOT NULL DEFAULT FALSE,

                       role VARCHAR(50) NOT NULL DEFAULT 'USER',

                       created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

                       CONSTRAINT chk_user_role
                           CHECK (role IN ('USER', 'ADMIN'))
);