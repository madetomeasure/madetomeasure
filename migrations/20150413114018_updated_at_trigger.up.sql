CREATE OR REPLACE FUNCTION update_modified_timestamp() RETURNS TRIGGER 
LANGUAGE plpgsql
AS
$$
BEGIN
    IF (NEW != OLD) THEN
        NEW.updated_at = CURRENT_TIMESTAMP;
        RETURN NEW;
    END IF;
    RETURN OLD;
END;
$$;
