CREATE TABLE transactions (
  id BIGINT NOT NULL AUTO_INCREMENT,
  from_account_id BIGINT UNSIGNED NOT NULL,
  to_account_id BIGINT UNSIGNED NOT NULL,
  amount BIGINT NOT NULL CHECK (amount > 0),
  created_on   TIMESTAMP  NOT NULL DEFAULT NOW(),
  PRIMARY KEY (id),
  CONSTRAINT fk_from_account_id_sender FOREIGN KEY (from_account_id) REFERENCES accounts(id),
  CONSTRAINT fk_to_account_id_receiver FOREIGN KEY (to_account_id) REFERENCES accounts(id)
);