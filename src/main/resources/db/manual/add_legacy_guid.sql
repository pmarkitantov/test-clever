ALTER TABLE patient_note
    ADD COLUMN IF NOT EXISTS legacy_guid VARCHAR(36);

DO $$
    BEGIN
        IF NOT EXISTS (
            SELECT 1 FROM pg_constraint WHERE conname = 'uq_patient_note_legacy_guid'
        ) THEN
            ALTER TABLE patient_note
                ADD CONSTRAINT uq_patient_note_legacy_guid UNIQUE (legacy_guid);
        END IF;
    END $$;