BEGIN TRANSACTION;

CREATE TABLE Administrator (
    username VARCHAR (256) NOT NULL
                           PRIMARY KEY,
    pwd      VARCHAR (256) NOT NULL
);


CREATE TABLE Bike (
    name               VARCHAR (256) NOT NULL,
    bike_type          VARCHAR (16)  NOT NULL,
    license_plate_code VARCHAR (32)  NOT NULL,
    bike_image         VARCHAR (256),
    bike_barcode       VARCHAR (10)  PRIMARY KEY,
    bike_rental_price  FLOAT         NOT NULL,
    currency_unit      VARCHAR (3)   NOT NULL,
    create_date        DATE,
    creator            VARCHAR (256),
    CONSTRAINT FK_Bike_Creator FOREIGN KEY (
        creator
    )
    REFERENCES Administrator (username) 
);


CREATE TABLE BikeInDock (
    dock_id      INTEGER,
    bike_barcode VARCHAR (10) NOT NULL,
    CONSTRAINT PK_Bike_In_Dock PRIMARY KEY (
        dock_id,
        bike_barcode
    ),
    CONSTRAINT FK_BikeInDock_Dock FOREIGN KEY (
        dock_id
    )
    REFERENCES Dock (dock_id),
    CONSTRAINT FK_BikeInDock_Bike FOREIGN KEY (
        bike_barcode
    )
    REFERENCES Bike (bike_barcode) 
);


CREATE TABLE BikeStatus (
    bike_barcode    VARCHAR (10) NOT NULL
                                 PRIMARY KEY,
    current_status  VARCHAR (4),
    total_rent_time INTEGER,
    current_battery FLOAT,
    CONSTRAINT FK_BikeStatus_Barcode FOREIGN KEY (
        bike_barcode
    )
    REFERENCES Bike (bike_barcode),
    CONSTRAINT Check_BikeStatus_Total_Rent_Time CHECK (total_rent_time >= 0),
    CONSTRAINT Check_BikeStatus_Battery CHECK (current_battery >= 0) 
);


CREATE TABLE Dock (
    name         VARCHAR (256),
    dock_id      INTEGER       PRIMARY KEY AUTOINCREMENT,
    dock_address VARCHAR (256),
    dock_area    FLOAT,
    num_dock     INTEGER,
    dock_image   VARCHAR (256) 
);


CREATE TABLE EcoBikeTransaction (
    transaction_id     INTEGER       NOT NULL
                                     PRIMARY KEY AUTOINCREMENT,
    transaction_amount FLOAT         NOT NULL,
    transaction_time   VARCHAR (256) NOT NULL,
    transaction_detail VARCHAR (256),
    creditcard_number  VARCHAR (25)  NOT NULL,
    rent_id            INTEGER
);


CREATE TABLE Invoice (
    invoice_id     INTEGER       NOT NULL,
    transaction_id INTEGER       NOT NULL
                                 PRIMARY KEY,
    rent_id        INTEGER       NOT NULL
                                 REFERENCES CustomerRent (rent_id),
    date_issued    VARCHAR (256),
    CONSTRAINT FK_Invoice_Transaction FOREIGN KEY (
        transaction_id
    )
    REFERENCES EcoBikeTransaction (transaction_id),
    CONSTRAINT FK_Invoice_Customer FOREIGN KEY (
        rent_id
    )
    REFERENCES Customer (customer_id) 
);


CREATE TABLE RentBike (
    rent_id      INTEGER       NOT NULL
                               REFERENCES CustomerRent (rent_id) 
                               PRIMARY KEY AUTOINCREMENT,
    bike_barcode VARCHAR (10)  NOT NULL,
    start_time   VARCHAR (256) NOT NULL,
    end_time     VARCHAR (256),
    rent_period  INTEGER,
    CONSTRAINT FK_RentBike_Bike FOREIGN KEY (
        bike_barcode
    )
    REFERENCES Bike (bike_barcode),
    CONSTRAINT FK_RenBike_Customer FOREIGN KEY (
        rent_id
    )
    REFERENCES Customer (customer_id) 
);
