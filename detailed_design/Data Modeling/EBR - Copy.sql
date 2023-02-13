create table Administrator(username varchar(256) not null primary key,
							pwd varchar(256) not null);

create table Bike(name varchar(256) not null,
					bike_type varchar(16) not null,
					license_plate_code varchar(32) not null,
					bike_image varchar(256),
					bike_barcode int not null primary key identity(1,1),
					bike_rental_price float not null,
					currency_unit varchar(3) not null,
					create_date date,
					creator varchar(256),
					constraint FK_Bike_Creator foreign key (creator) references Administrator(username));

create table Dock(name varchar(256),
				dock_id int not null primary key identity(1,1),
				dock_address varchar(256),
				dock_area float,
				num_available_bike int,
				num_free_dock int);

create table BikeInDock(dock_id int not null,
						bike_barcode int not null,
						constraint PK_Bike_In_Dock primary key (dock_id, bike_barcode),
						constraint FK_BikeInDock_Dock foreign key (dock_id) references Dock(dock_id),
						constraint FK_BikeInDock_Bike foreign key (bike_barcode) references Bike(bike_barcode));

create table Customer(customer_name varchar(256) not null,
						customer_id int not null identity(1,1) primary key,
						customer_email varchar(128) not null);

create table CreditCard(cardholder_name varchar(256) not null,
						creditcard_number varchar(25) not null primary key,
						issuing_bank varchar(128) not null,
						security_code varchar(8) not null,
						balance float,
						constraint Check_CardBalance check (balance >=0));

create table RentBike(customer_id int not null,
					bike_barcode int not null,
					start_time time not null,
					end_time time,
					rent_period int,
					constraint PK_Rent_Bike primary key (customer_id, bike_barcode),
					constraint FK_RentBike_Bike foreign key (bike_barcode) references Bike(bike_barcode),
					constraint FK_RenBike_Customer foreign key (customer_id) references Customer(customer_id),
					constraint Check_RenBike_Time check (end_time > start_time));


create table BikeStatus(bike_barcode int not null primary key,
						current_status varchar(4),
						total_rent_time int,
						current_battery float,
						constraint FK_BikeStatus_Barcode foreign key (bike_barcode) references Bike(bike_barcode),
						constraint Check_BikeStatus_Total_Rent_Time check (total_rent_time >=0),
						constraint Check_BikeStatus_Battery check (current_battery >=0),
						constraint Check_BikeStatus_Status check (current_status = 'free' or current_status = 'rent'));

create table EcoBikeTransaction(transaction_id varchar(32) not null primary key,
						transaction_amount float not null,
						transaction_time datetime not null,
						transaction_detail varchar(256),
						creditcard_number varchar(25) not null,
						constraint FK_Transaction_CreditCard foreign key (creditcard_number) references CreditCard(creditcard_number),
						constraint Check_Amount check (transaction_amount > 0));

create table Invoice(invoice_id varchar(256) not null,
					transaction_id varchar(32) not null,
					customer_id int not null,
					constraint PK_InvoiceTransactionCustomer primary key (invoice_id, transaction_id, customer_id),
					constraint FK_Invoice_Transaction foreign key (transaction_id) references EcoBikeTransaction(transaction_id),
					constraint FK_Invoice_Customer foreign key (customer_id) references Customer(customer_id));
