--name: delete-subscriber!
-- deletes a subscriber
DELETE FROM subscribers
WHERE id = :id;
