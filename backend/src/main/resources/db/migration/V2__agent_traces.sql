CREATE TABLE runtime.agent_trace (
    dataset_epoch uuid NOT NULL,
    mission_id uuid NOT NULL,
    attempt integer NOT NULL,
    event_id uuid NOT NULL,
    event_type text NOT NULL,
    details jsonb NOT NULL CHECK (jsonb_typeof(details) = 'object'),
    created_at timestamptz NOT NULL,
    PRIMARY KEY (dataset_epoch, event_id),
    FOREIGN KEY (dataset_epoch, mission_id, attempt) REFERENCES runtime.mission_attempt(dataset_epoch, mission_id, attempt)
);