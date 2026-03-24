CREATE DATABASE StoreMaster;

-- Users Table
CREATE TABLE Users (
    user_id INT IDENTITY(1,1) PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL
);

-- Suppliers Table
CREATE TABLE Suppliers (
    supplier_id INT IDENTITY(1,1) PRIMARY KEY,
    supplier_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100)
);

-- Categories Table
CREATE TABLE Categories (
    category_id INT IDENTITY(1,1) PRIMARY KEY,
    category_name VARCHAR(50) NOT NULL
);

-- Products Table
CREATE TABLE Products (
    product_id INT IDENTITY(1,1) PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    quantity INT NOT NULL,
    supplier_id INT NOT NULL,
    category_id INT,
    FOREIGN KEY (supplier_id) REFERENCES Suppliers(supplier_id),
    FOREIGN KEY (category_id) REFERENCES Categories(category_id)
);

-- Stock Table
CREATE TABLE Stock (
    stock_id INT IDENTITY(1,1) PRIMARY KEY,
    product_id INT NOT NULL,
    quantity_added INT NOT NULL,
    added_date DATETIME NOT NULL,
    purchase_price DECIMAL(10,2),
    FOREIGN KEY (product_id) REFERENCES Products(product_id)
);

-- Sales Table
CREATE TABLE Sales (
    sale_id INT IDENTITY(1,1) PRIMARY KEY,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    sale_date DATETIME NOT NULL,
    sold_by INT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES Products(product_id),
    FOREIGN KEY (sold_by) REFERENCES Users(user_id)
);