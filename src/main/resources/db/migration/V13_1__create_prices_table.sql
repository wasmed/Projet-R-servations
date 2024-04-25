CREATE TABLE prices (
    id INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(30) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    show_id INT NOT NULL,
    FOREIGN KEY (show_id) REFERENCES shows(id)
);