-- === EXTENSIONS ===
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- === SCHEMA CREATION ===
CREATE SCHEMA IF NOT EXISTS public;
CREATE SCHEMA IF NOT EXISTS history;

-- === TABLES IN PUBLIC SCHEMA ===
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'public' AND tablename = 'owner') THEN
        CREATE TABLE public.owner (
            id SERIAL PRIMARY KEY,
            name TEXT NOT NULL,
            created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
            updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
        );
    END IF;

    IF NOT EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'public' AND tablename = 'cars') THEN
        CREATE TABLE public.cars (
            id SERIAL PRIMARY KEY,
            owner_id INTEGER NOT NULL REFERENCES public.owner(id) ON DELETE CASCADE,
            model TEXT NOT NULL,
            registration_no TEXT UNIQUE NOT NULL,
            created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
            updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
        );
    END IF;
END
$$;

-- === UPDATED_AT TRIGGER ===
CREATE OR REPLACE FUNCTION set_updated_at()
RETURNS TRIGGER AS $$
BEGIN
  NEW.updated_at = now();
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Triggers (won’t be duplicated)
DO $$
BEGIN
  IF NOT EXISTS (
    SELECT 1 FROM pg_trigger WHERE tgname = 'trg_owner_updated_at'
  ) THEN
    CREATE TRIGGER trg_owner_updated_at
    BEFORE UPDATE ON public.owner
    FOR EACH ROW
    EXECUTE FUNCTION set_updated_at();
  END IF;

  IF NOT EXISTS (
    SELECT 1 FROM pg_trigger WHERE tgname = 'trg_cars_updated_at'
  ) THEN
    CREATE TRIGGER trg_cars_updated_at
    BEFORE UPDATE ON public.cars
    FOR EACH ROW
    EXECUTE FUNCTION set_updated_at();
  END IF;
END
$$;

-- === TABLES IN HISTORY SCHEMA ===
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'history' AND tablename = 'owner') THEN
        CREATE TABLE history.owner (
            event_id BIGSERIAL PRIMARY KEY,
            original_id INTEGER,
            name TEXT,
            created_at TIMESTAMPTZ,
            updated_at TIMESTAMPTZ,
            changed_at TIMESTAMPTZ DEFAULT now()
        );
    END IF;

    IF NOT EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'history' AND tablename = 'cars') THEN
        CREATE TABLE history.cars (
            event_id BIGSERIAL PRIMARY KEY,
            original_id INTEGER,
            owner_id INTEGER,
            model TEXT,
            registration_no TEXT,
            created_at TIMESTAMPTZ,
            updated_at TIMESTAMPTZ,
            changed_at TIMESTAMPTZ DEFAULT now()
        );
    END IF;
END
$$;

-- === CREATE DEBEZIUM USER IF NOT EXISTS ===
DO $$
BEGIN
  IF NOT EXISTS (SELECT FROM pg_roles WHERE rolname = 'debezium') THEN
    CREATE ROLE debezium WITH LOGIN PASSWORD 'dbz';
  END IF;
END
$$;

-- GRANT PERMISSIONS TO DEBEZIUM USER
GRANT CONNECT ON DATABASE postgres TO debezium;
GRANT USAGE ON SCHEMA public TO debezium;
GRANT SELECT ON ALL TABLES IN SCHEMA public TO debezium;
ALTER ROLE debezium WITH REPLICATION;

-- === CREATE PUBLICATION (if not exists) ===
DO $$
BEGIN
  IF NOT EXISTS (
    SELECT 1 FROM pg_catalog.pg_publication WHERE pubname = 'debezium_pub'
  ) THEN
    CREATE PUBLICATION debezium_pub FOR TABLE public.owner, public.cars;
  END IF;
END
$$;
