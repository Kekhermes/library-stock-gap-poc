ALTER TABLE runtime.conversation_turn ADD COLUMN agent_role text NOT NULL DEFAULT 'INVESTIGATOR'
    CHECK (agent_role IN ('INVESTIGATOR', 'RULE_AGENT'));