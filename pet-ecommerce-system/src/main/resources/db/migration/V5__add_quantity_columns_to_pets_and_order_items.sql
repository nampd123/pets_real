-- Add quantity columns if they do not exist yet.
-- This migration is safe to run on existing databases that may already have the column.

ALTER TABLE pets
    ADD COLUMN IF NOT EXISTS quantity INT NOT NULL DEFAULT 1;

ALTER TABLE order_items
    ADD COLUMN IF NOT EXISTS quantity INT NOT NULL DEFAULT 1;
