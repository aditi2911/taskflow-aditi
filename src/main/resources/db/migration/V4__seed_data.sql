INSERT INTO users (id, name, email, password) VALUES (
    'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
    'Test User',
    'test@example.com',
    '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewdBPj4J/HS.iK8i'
);

INSERT INTO projects (id, name, description, owner_id) VALUES (
    'b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
    'Sample Project',
    'This is a seeded project',
    'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11'
);

INSERT INTO tasks (title, status, priority, project_id, assignee_id) VALUES
    ('First Task', 'todo', 'low', 'b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11'),
    ('Second Task', 'in_progress', 'medium', 'b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11'),
    ('Third Task', 'done', 'high', 'b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11');