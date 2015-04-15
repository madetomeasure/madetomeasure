CREATE TRIGGER t_subscribers_updated_at BEFORE UPDATE ON subscribers FOR EACH ROW EXECUTE PROCEDURE update_modified_timestamp();
