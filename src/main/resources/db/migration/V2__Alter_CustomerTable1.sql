-- Remove the 'role' column
ALTER TABLE t_customer
DROP COLUMN role;

-- Add the 'updated_date' and 'profile_image_id' columns
ALTER TABLE t_customer
ADD COLUMN updated_date BIGINT NOT NULL,
ADD COLUMN profile_image_id VARCHAR(50) UNIQUE;
