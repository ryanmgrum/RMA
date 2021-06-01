-- RMA DML File
--Product Categories
insert into productCategories(categoryId, categoryName) values
(0, 'Car Parts'), (1, 'Kitchen'), (2, 'Food'), (3, 'Electronics')
;

-- Products
insert into products(productId, categoryId, productName, price) values
-- Car Parts
(0, 0, 'Tire', 150.0), (1, 0, 'Engine', 2000.0), (2, 0, 'Headlight', 50.0),
(3, 0, 'Steering Wheel', 200.0), (4, 0, 'Brake Pad', 50.0), (5, 0, 'Window', 100.0),
(6, 0, 'Windshield', 250.0),
-- Kitchen
(0, 1, 'Spoon', 5.0), (1, 1, 'Fork', 6.0), (2, 1, 'Knife', 10.0), (3, 1, 'Plate', 10.0),
(4, 1, 'Sponge', 4.0), (5, 1, 'Pot', 15.0), (6, 1, 'Pan', 12.0), (7, 1, 'Napkin', 1.0),
-- Food
(0, 2, 'Apple', 2.0), (1, 2, 'Grape', 6.0), (2, 2, 'Orange', 3.0), (3, 2, 'Pineapple', 4.0),
(4, 2, 'Strawberry', 5.0), (5, 2, 'Blueberry', 2.0), (6, 2, 'Pear', 3.0),
-- Electronics
(0, 3, 'Flip Phone', 150.0), (1, 3, 'Smartphone', 250.0), (2, 3, 'Speaker', 65.0),
(3, 3, 'Mouse', 20.0), (4, 3, 'Keyboard', 25.0), (5, 3, 'Monitor', 350.0),
(6, 3, 'USB Stick', 20.0), (7, 3, 'Hard Drive', 250.0), (8, 3, 'Solid State Drive', 10.0)
;

-- Customers
insert into customers(customerId, customerName) values
(0, 'Big Food Inc.'), (1, 'Electronia Inc.'), (2, 'Everything Inc.');

-- CustomerAddresses
insert into customerAddresses(
addressId, customerId, businessName, address1, address2, city, county, stateOrProvince,
zip, country, phone, fax
)values
-- Big Food Inc.
(0, 0, 'George''s Corner Store', '63 S Mission Rd', '', 'Eastborough', 'Sedgwick', 'KS', '67207',
'United States of America', '621-187-3888', ''),
(1, 0, 'Matt''s Mini-Mart', '2263 Gage Hill Rd', 'Door 2', 'Hopkinton', 'Merrimack', 'NH', '03229',
'United States of America', '603-584-8662', '487-568-9855'),
-- Electronia Inc.
(2, 1, 'Fried Electronics', '12 S Lander St', '', 'Seattle', 'King', 'WA', '98134',
'United States of America', '144-783-5468', '135-874-5577'),
(3, 1, 'Solid State Store', '250 Industrial Park St', 'Building #6', 'Pittsfield', 'Somerset', 'ME', '04967', 'United States of America', '207-689-7885', ''),
-- Everything Inc.
(4, 2, 'Open Box Mart', '100 W 100 S', '200 N 350 E', 'Redmond', 'Sevier', 'UT', '84652',
'United States of America', '122-345-6789', ''),
(5, 2, 'Five Finger Discounts', '18 Burnette St', '', 'Chattanooga', 'Hamilton', 'TN', '37408',
'United States of America', '423-879-6856', '423-555-5556')
;

-- purchaseOrders
insert into purchaseOrders(poNumber, addressId) values
-- George's Corner Store
('3C2218QN', 0), ('T3Q39VIT', 0),
-- Matt's Mini-Mart
('PO3A23FE', 1), ('458695345', 1),
-- Fried Electronics
('48593FF29', 2), ('WVO392G45', 2),
-- Solid State Store
('219389VF3', 3), ('9483945FGSHI', 3),
-- Open Box Mart
('693823V342', 4), ('AFUH374F7V34', 4),
-- Five Finger Discounts
('ZEIETJ39483', 5), ('W4589384VFG', 5)
;

-- purchaseOrderProducts
insert into purchaseOrderProducts(
purchaseOrderProductId, poNumber, productId, categoryId, quantity, orderDate, deliverDate
)values
-- George's Corner Store, PO 3C2218QN
(0, '3C2218QN', 0, 2, 200, '01/01/2021', '01/07/2021'),
(1, '3C2218QN', 1, 2, 100, '01/01/2021', '01/07/2021'),
(2, '3C2218QN', 5, 2, 1000, '01/01/2021', '01/07/2021'),
-- George's Corner Store, PO T3Q39VIT
(3, 'T3Q39VIT', 2, 2, 50, '01/25/2021', '01/30/2021'),
(4, 'T3Q39VIT', 1, 2, 53, '01/25/2021', '01/30/2021'),
(5, 'T3Q39VIT', 0, 2, 20, '01/25/2021', '01/30/2021'),
(6, 'T3Q39VIT', 6, 2, 10, '01/25/2021', '01/30/2021'),
-- Matt's Mini-Mart, PO PO3A23FE
(7, 'PO3A23FE', 0, 2, 1, '01/26/2021', '02/01/2021'),
-- Matt's Mini-Mart, PO 458695345
(8, '458695345', 6, 1, 200, '03/05/2021', '03/06/2021'),
(9, '458695345', 2, 1, 10, '03/05/2021', '03/06/2021'),
-- Fried Electronics, PO 48593FF29
(10, '48593FF29', 0, 3, 250, '03/14/2021', '03/20/2021'),
(11, '48593FF29', 2, 3, 50, '03/14/2021', '03/20/2021'),
(12, '48593FF29', 6, 3, 1000, '03/14/2021', '03/20/2021'),
-- Fried Electronics, PO WVO392G45
(13, 'WVO392G45', 1, 3, 15, '03/01/2021', '03/08/2021'),
(14, 'WVO392G45', 4, 3, 33, '03/01/2021', '03/08/2021'),
-- Solid State Store, PO 219389VF3
(15, '219389VF3', 7, 3, 30, '04/22/2021', '04/28/2021'),
(16, '219389VF3', 5, 3, 15, '04/22/2021', '04/28/2021'),
(17, '219389VF3', 3, 3, 150, '04/22/2021', '04/28/2021'),
(18, '219389VF3', 1, 3, 50, '04/22/2021', '04/28/2021'),
-- Solid State Store, PO 9483945FGSHI
(19, '9483945FGSHI', 2, 3, 40, '04/01/2021', '04/10/2021'),
-- Open Box Mart, PO 693823V342
(20, '693823V342', 1, 3, 10, '02/20/2021', '02/25/2021'),
(21, '693823V342', 1, 0, 25, '02/20/2021', '02/25/2021'),
(22, '693823V342', 2, 1, 50, '02/20/2021', '02/25/2021'),
(23, '693823V342', 4, 1, 600, '02/20/2021', '02/25/2021'),
(24, '693823V342', 6, 2, 16, '02/20/2021', '02/25/2021'),
(25, '693823V342', 4, 3, 20, '02/20/2021', '02/25/2021'),
-- Open Box Mart, PO AFUH374F7V34
(26, 'AFUH374F7V34', 1, 1, 30, '05/22/2021', '05/28/2021'),
(27, 'AFUH374F7V34', 3, 2, 16, '05/22/2021', '05/28/2021'),
(28, 'AFUH374F7V34', 0, 3, 65, '05/22/2021', '05/28/2021'),
(29, 'AFUH374F7V34', 0, 1, 73, '05/22/2021', '05/28/2021'),
(30, 'AFUH374F7V34', 2, 2, 10, '05/22/2021', '05/28/2021'),
-- Five Finger Discounts, PO ZEIETJ39483
(31, 'ZEIETJ39483', 7, 3, 60, '08/10/2021', '08/16/2021'),
(32, 'ZEIETJ39483', 6, 3, 200, '08/10/2021', '08/16/2021'),
(33, 'ZEIETJ39483', 2, 2, 10, '08/10/2021', '08/16/2021'),
(34, 'ZEIETJ39483', 1, 1, 29, '08/10/2021', '08/16/2021'),
(35, 'ZEIETJ39483', 1, 2, 33, '08/10/2021', '08/16/2021'),
(36, 'ZEIETJ39483', 0, 1, 77, '08/10/2021', '08/16/2021'),
(37, 'ZEIETJ39483', 0, 2, 21, '08/10/2021', '08/16/2021'),
(38, 'ZEIETJ39483', 0, 3, 44, '08/10/2021', '08/16/2021'),
(39, 'ZEIETJ39483', 4, 1, 45, '08/10/2021', '08/16/2021'),
-- Five Finger Discounts, PO W4589384VFG
(40, 'W4589384VFG', 2, 2, 2, '06/01/2021', '06/02/2021')
;

-- rmaStatuses
insert into rmaStatuses(statusId, description) values
(0, 'Diagnose'), (1, 'Pulled with Tech'), (2, 'Dropped at Shop'),
(3, 'Waiting for Replacement'), (4, 'Ready for installation'),
(5, 'Closed')
;

-- Dispositions
insert into dispositions (dispositionId, disposition) values
(11, 'Put item back into inventory, credit customer'),
(12, 'Repair and add to inventory, credit customer'),
(21, 'Credit, no return of item'),
(31, 'Replace item, credit customer'),
(41, 'Replace item, scrap when returned, credit customer'),
(51, 'Reject returned item, return item to customer'),
(61, 'Scrap item, credit customer')
;

-- Return Reason Codes
insert into returnReasonCodes (returnReasonCode, description)
values
('BROKEN-FLD', 'Scrapped by Customer'),
('BROKEN-RET', 'Damaged-Replace when returned'),
('BROKEN-XSHIP', 'Damaged-Replace Immediately'),
('ERROR', 'Customer ordered wrong item'),
('SHP_ERROR-STK', 'Wrong Item shipped in error')
;