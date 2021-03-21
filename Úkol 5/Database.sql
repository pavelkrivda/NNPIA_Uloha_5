INSERT  INTO product (name, path_to_image) VALUES ('Asus PadFone', 'product.png');
INSERT  INTO product (name, path_to_image) VALUES ('Asus PadFone 2', 'product.png');
INSERT  INTO product (name, path_to_image) VALUES ('Asus PadFone Infinity', 'product.png');
INSERT  INTO product (name, path_to_image) VALUES ('Asus PadFone Infinity 2', 'product.png');
INSERT  INTO product (name, path_to_image) VALUES ('Asus PadFone mini', 'product.png');
INSERT  INTO product (name, path_to_image) VALUES ('Asus PadFone E', 'product.png');
INSERT  INTO product (name, path_to_image) VALUES ('Asus PadFone Infinity Lite', 'product.png');
INSERT  INTO product (name, path_to_image) VALUES ('Asus ZenFone 5 (2014)', 'product.png');
INSERT  INTO product (name, path_to_image) VALUES ('Asus ZenFone 4 (2014)', 'product.png');
INSERT  INTO product (name, path_to_image) VALUES ('Asus ZenFone 6 (2014)', 'product.png');


INSERT INTO address (city, state, postal_code) VALUES ('Praha', 'Czech Republic', '14000');
INSERT INTO address (city, state, postal_code) VALUES ('Brno', 'Czech Republic', '63600');
INSERT INTO address (city, state, postal_code) VALUES ('Hradec Kralove', 'Czech Republic', '50002');
INSERT INTO address (city, state, postal_code) VALUES ('Pardubice', 'Czech Republic', '55351');
INSERT INTO address (city, state, postal_code) VALUES ('Kladno', 'Czech Republic', '27201');
INSERT INTO address (city, state, postal_code) VALUES ('Liberec', 'Czech Republic', '46007');
INSERT INTO address (city, state, postal_code) VALUES ('Jihlava', 'Czech Republic', '56801');
INSERT INTO address (city, state, postal_code) VALUES ('Znojmo', 'Czech Republic', '66902');


INSERT INTO person (first_name, last_name, age, address_id) VALUES ('Pepa', 'Novak', 52, 1);
INSERT INTO person (first_name, last_name, age, address_id) VALUES ('Karel','Svoboda', 15, 2);
INSERT INTO person (first_name, last_name, age, address_id) VALUES ('Tomas','Novotny', 23, 3);
INSERT INTO person (first_name, last_name, age, address_id) VALUES ('Katerina','Cerna', 61, 4);
INSERT INTO person (first_name, last_name, age, address_id) VALUES ('Lenka','Prochazkova', 24, 5);
INSERT INTO person (first_name, last_name, age, address_id) VALUES ('Ondrej','Kucera', 29, 6);
INSERT INTO person (first_name, last_name, age, address_id) VALUES ('Helena','Horak', 34, 7);
INSERT INTO person (first_name, last_name, age, address_id) VALUES ('Franta','Pokorny', 44, 8);


INSERT INTO order_form (state, person_id) VALUES ('NEW', 1);
INSERT INTO order_form (state, person_id) VALUES ('PAID', 2);
INSERT INTO order_form (state, person_id) VALUES ('DELIVERED', 3);
INSERT INTO order_form (state, person_id) VALUES ('DELIVERED', 4);
INSERT INTO order_form (state, person_id) VALUES ('PAID', 5);
INSERT INTO order_form (state, person_id) VALUES ('NEW', 6);
INSERT INTO order_form (state, person_id) VALUES ('PAID', 7);
INSERT INTO order_form (state, person_id) VALUES ('DELIVERED', 8);


INSERT  INTO order_has_product (amount, order_id, product_id) VALUES (5, 1, 1);
INSERT  INTO order_has_product (amount, order_id, product_id) VALUES (4, 2, 2);
INSERT  INTO order_has_product (amount, order_id, product_id) VALUES (12, 3, 3);
INSERT  INTO order_has_product (amount, order_id, product_id) VALUES (2, 4, 4);
INSERT  INTO order_has_product (amount, order_id, product_id) VALUES (2, 5, 5);
INSERT  INTO order_has_product (amount, order_id, product_id) VALUES (9, 6, 6);
INSERT  INTO order_has_product (amount, order_id, product_id) VALUES (3, 7, 7);
INSERT  INTO order_has_product (amount, order_id, product_id) VALUES (1, 8, 8);


