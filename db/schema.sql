CREATE TABLE IF NOT EXISTS company_user (
  id BIGSERIAL PRIMARY KEY,
  login VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS patient_profile (
  id BIGSERIAL PRIMARY KEY,
  first_name VARCHAR(255),
  last_name  VARCHAR(255),
  old_client_guid VARCHAR(255),
  status_id SMALLINT NOT NULL
);

CREATE TABLE IF NOT EXISTS patient_note (
  id BIGSERIAL PRIMARY KEY,
  legacy_guid VARCHAR(36) NOT NULL UNIQUE,
  patient_id BIGINT NOT NULL REFERENCES patient_profile(id),
  note TEXT,
  created_date_time TIMESTAMP WITH TIME ZONE,
  last_modified_date_time TIMESTAMP WITH TIME ZONE,
  created_by_user_id BIGINT REFERENCES company_user(id),
  last_modified_by_user_id BIGINT REFERENCES company_user(id)
);
