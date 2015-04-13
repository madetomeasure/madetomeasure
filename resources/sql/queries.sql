--name: update-user!
-- update an existing user record
UPDATE users
SET first_name = :first_name, last_name = :last_name, email = :email
WHERE id = :id

-- name: get-user
-- retrieve a used given the id.
SELECT * FROM users
WHERE id = :id

--name: create-subscriber!
-- creates a new subscriber
INSERT INTO subscribers
(address, first_name, last_name)
VALUES (:address, :first_name, :last_name);
