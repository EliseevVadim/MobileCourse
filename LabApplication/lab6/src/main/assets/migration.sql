CREATE TABLE IF NOT EXISTS IncomeCategories
(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    categoryName VARCHAR(255) NOT NULL UNIQUE
);
//
CREATE TABLE IF NOT EXISTS ExpenseCategories
(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    categoryName VARCHAR(255) NOT NULL UNIQUE
);
//
CREATE TABLE IF NOT EXISTS Accounts
(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    accountName VARCHAR(255) NOT NULL UNIQUE,
    balance REAL NOT NULL
);
//
CREATE TABLE IF NOT EXISTS Savings
(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(255) NOT NULL,
    quantity REAL NOT NULL,
    accountId INTEGER NOT NULL,
    FOREIGN KEY (accountId) REFERENCES Accounts(id) ON DELETE CASCADE
);
//
CREATE TABLE IF NOT EXISTS Turnovers
(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    turnoverDate INTEGER NOT NULL,
    name VARCHAR(255) NOT NULL,
    quantity REAL NOT NULL,
    accountId INTEGER NOT NULL,
    FOREIGN KEY (accountId) REFERENCES Accounts(id) ON DELETE CASCADE
);
//
CREATE TABLE IF NOT EXISTS Incomes
(
    turnoverId INTEGER PRIMARY KEY,
    categoryId INTEGER NOT NULL,
    FOREIGN KEY (turnoverId) REFERENCES Turnovers(id) ON DELETE CASCADE,
    FOREIGN KEY (categoryId) REFERENCES IncomeCategories(id) ON DELETE CASCADE
);
//
CREATE TABLE IF NOT EXISTS Expenses
(
    turnoverId INTEGER PRIMARY KEY,
    categoryId INTEGER NOT NULL,
    FOREIGN KEY (turnoverId) REFERENCES Turnovers(id) ON DELETE CASCADE,
    FOREIGN KEY (categoryId) REFERENCES ExpenseCategories(id) ON DELETE CASCADE
);
//
CREATE TABLE IF NOT EXISTS Obligations
(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(255) NOT NULL,
    quantity REAL NOT NULL
);
//
CREATE TABLE IF NOT EXISTS Loans
(
    obligationId INTEGER PRIMARY KEY,
    deadLine INTEGER NOT NULL,
    FOREIGN KEY (obligationId) REFERENCES Obligations(id) ON DELETE CASCADE
);
//
CREATE TABLE IF NOT EXISTS Debits
(
    obligationId INTEGER PRIMARY KEY,
    FOREIGN KEY (obligationId) REFERENCES Obligations(id) ON DELETE CASCADE
);
//
INSERT INTO IncomeCategories (categoryName) VALUES ("Зарплата");
//
INSERT INTO IncomeCategories (categoryName) VALUES ("Пенсия");
//
INSERT INTO IncomeCategories (categoryName) VALUES ("Подарок");
//
INSERT INTO IncomeCategories (categoryName) VALUES ("Стипендия");
//
INSERT INTO IncomeCategories (categoryName) VALUES ("Страховка");
//
INSERT INTO IncomeCategories (categoryName) VALUES ("Продажа имущества");
//
INSERT INTO IncomeCategories (categoryName) VALUES ("Находка");
//
INSERT INTO IncomeCategories (categoryName) VALUES ("Наследство");
//
INSERT INTO IncomeCategories (categoryName) VALUES ("Дивиденды");
//
INSERT INTO IncomeCategories (categoryName) VALUES ("Аренда жилья");
//
INSERT INTO IncomeCategories (categoryName) VALUES ("Халтура");
//
INSERT INTO ExpenseCategories (categoryName) VALUES ("Автомобиль");
//
INSERT INTO ExpenseCategories (categoryName) VALUES ("Комиссия");
//
INSERT INTO ExpenseCategories (categoryName) VALUES ("Услуги");
//
INSERT INTO ExpenseCategories (categoryName) VALUES ("Коммунальные услуги");
//
INSERT INTO ExpenseCategories (categoryName) VALUES ("Мебель");
//
INSERT INTO ExpenseCategories (categoryName) VALUES ("Медицина");
//
INSERT INTO ExpenseCategories (categoryName) VALUES ("Обувь");
//
INSERT INTO ExpenseCategories (categoryName) VALUES ("Одежда");
//
INSERT INTO ExpenseCategories (categoryName) VALUES ("Продукты питания");
//
INSERT INTO ExpenseCategories (categoryName) VALUES ("Развлечения");
//
INSERT INTO ExpenseCategories (categoryName) VALUES ("Регулярные платежи");
//
INSERT INTO ExpenseCategories (categoryName) VALUES ("Техника");
//
INSERT INTO ExpenseCategories (categoryName) VALUES ("Транспорт");
//
INSERT INTO ExpenseCategories (categoryName) VALUES ("Хозяйственные товары");