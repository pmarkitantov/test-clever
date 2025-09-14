CREATE TABLE IF NOT EXISTS company_user
(
    id    BIGSERIAL PRIMARY KEY,
    login VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS patient_profile
(
    id              BIGSERIAL PRIMARY KEY,
    first_name      VARCHAR(255),
    last_name       VARCHAR(255),
    old_client_guid VARCHAR(255),
    status_id       SMALLINT NOT NULL
);

CREATE TABLE IF NOT EXISTS patient_note
(
    id                       BIGSERIAL PRIMARY KEY,
    created_date_time        TIMESTAMPTZ NOT NULL,
    last_modified_date_time  TIMESTAMPTZ NOT NULL,
    created_by_user_id       BIGINT REFERENCES company_user (id),
    last_modified_by_user_id BIGINT REFERENCES company_user (id),
    note                     VARCHAR(4000),
    patient_id               BIGINT      NOT NULL REFERENCES patient_profile (id),
    legacy_guid              CHAR(36) UNIQUE
);

-- тестовые данные
INSERT INTO patient_profile (first_name, last_name, status_id, old_client_guid)
VALUES ('Ivan', 'Ivanov', 200, '11111111-1111-1111-1111-111111111111'),
       ('Pavel', 'Pavlov', 210, '22222222-2222-2222-2222-222222222222')
ON CONFLICT DO NOTHING;