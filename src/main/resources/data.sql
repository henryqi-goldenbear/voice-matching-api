insert into experiences (title, description, container_type) values
('Grounding Breathwork', 'A calming guided breathwork session for regulation and reset.', 'Grounding'),
('Celebration Circle', 'A high-energy small-group experience for momentum and joy.', 'Celebratory')
on conflict do nothing;
