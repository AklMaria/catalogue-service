CREATE TABLE resources (
                           id           UUID PRIMARY KEY,
                           name         VARCHAR(200) NOT NULL,
                           description  TEXT
);

CREATE TABLE slots (
                       id          UUID PRIMARY KEY,
                       start_date  TIMESTAMPTZ NOT NULL,
                       end_date    TIMESTAMPTZ NOT NULL,
                       CONSTRAINT chk_slot_dates CHECK (end_date > start_date)
);

CREATE TABLE resource_availability (
                                       id           UUID PRIMARY KEY,
                                       resource_id  UUID NOT NULL REFERENCES resources(id) ON DELETE CASCADE,
                                       slot_id      UUID NOT NULL REFERENCES slots(id) ON DELETE CASCADE,
                                       capacity     INT NOT NULL,
                                       consumed     INT NOT NULL,
                                       CONSTRAINT uq_resource_slot UNIQUE (resource_id, slot_id),
                                       CONSTRAINT chk_capacity CHECK (capacity >= 0),
                                       CONSTRAINT chk_consumed CHECK (consumed >= 0 AND consumed <= capacity)
);

CREATE INDEX idx_ra_resource ON resource_availability(resource_id);
CREATE INDEX idx_ra_slot ON resource_availability(slot_id);
