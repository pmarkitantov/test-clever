INSERT INTO patient_profile (first_name, last_name, status_id, old_client_guid)
VALUES
  ('Ivan',  'Ivanov',  200, '11111111-1111-1111-1111-111111111111'),
  ('Pavel', 'Pavlov',  210, '22222222-2222-2222-2222-222222222222')
ON CONFLICT DO NOTHING;
