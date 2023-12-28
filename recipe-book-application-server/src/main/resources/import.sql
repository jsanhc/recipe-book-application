-- Recipe data
-- Husband
-- prepare time < 2
insert into recipes(reference, name, link, portions, prepare_time, meal, instructions) values ('Husband-12345A', 'Bittman Chinese Chicken With Bok Choy', 'https://www.youtube.com/watch?v=dQw4w9WgXcQ', 10, 1.10,'lunch', 'Make sauce. Steam bok choy and chicken. Carry on steaming. Pour sauce over.');
insert into ingredients(id, name, amount, unit, recipe_id) values (1, 'Chicken Breast', 20, 'units', 'Husband-12345A');
insert into ingredients(id, name, amount, unit, recipe_id) values (2, 'Bok Choy', 2, 'units', 'Husband-12345A');
insert into ingredients(id, name, amount, unit, recipe_id) values (3, 'Sauce', 100.50, 'deciliters', 'Husband-12345A');
insert into member(id, member, recipe_id) values (1, 'HUSBAND', 'Husband-12345A');
-- prepare time = 2
insert into recipes(reference, name, link, portions, prepare_time, meal, instructions) values ('Husband-22345A', 'Paella', 'https://www.youtube.com/watch?v=T-eDbjeTA3E', 2, 2.00, 'lunch', 'Make sauce. Steam bok choy and chicken. Carry on steaming. Pour sauce over.');
insert into ingredients(id, name, amount, unit, recipe_id) values (4, 'Chicken Breast', 20, 'units', 'Husband-22345A');
insert into ingredients(id, name, amount, unit, recipe_id) values (5, 'Bok Choy', 2, 'units', 'Husband-22345A');
insert into ingredients(id, name, amount, unit, recipe_id) values (6, 'Sauce', 100.50, 'deciliters', 'Husband-22345A');
insert into member(id, member, recipe_id) values (2, 'HUSBAND', 'Husband-22345A');
-- prepare time > 2
insert into recipes(reference, name, portions, prepare_time, meal, instructions) values ('Husband-32345A', 'Rosolli', 4, 3.00, 'lunch', 'Make sauce. Steam bok choy and chicken. Carry on steaming. Pour sauce over.');
insert into ingredients(id, name, amount, unit, recipe_id) values (7, 'Chicken Breast', 20, 'units', 'Husband-32345A');
insert into ingredients(id, name, amount, unit, recipe_id) values (8, 'Bok Choy', 2, 'units', 'Husband-32345A');
insert into ingredients(id, name, amount, unit, recipe_id) values (9, 'Sauce', 100.50, 'deciliters', 'Husband-32345A');
insert into member(id, member, recipe_id) values (3, 'HUSBAND', 'Husband-32345A');
-- Grandson
-- prepare time < 2
insert into recipes(reference, name, link, portions, prepare_time, meal, instructions) values ('Grandson-12345A', 'Bittman Chinese Chicken With Bok Choy', 'https://www.youtube.com/watch?v=dQw4w9WgXcQ', 20, 1.20,'lunch', 'Make sauce. Steam bok choy and chicken. Carry on steaming. Pour sauce over.');
insert into ingredients(id, name, amount, unit, recipe_id) values (10, 'Chicken Breast', 20, 'units', 'Grandson-12345A');
insert into ingredients(id, name, amount, unit, recipe_id) values (11, 'Bok Choy', 2, 'units', 'Grandson-12345A');
insert into ingredients(id, name, amount, unit, recipe_id) values (12, 'Sauce', 100.50, 'deciliters', 'Grandson-12345A');
insert into member(id, member, recipe_id) values (4, 'GRANDSON', 'Grandson-12345A');
-- prepare time = 2
insert into recipes(reference, name, link, portions, prepare_time, meal, instructions) values ('Grandson-22345A', 'Paella', 'https://www.youtube.com/watch?v=T-eDbjeTA3E', 4, 2.00, 'lunch', 'Make sauce. Steam bok choy and chicken. Carry on steaming. Pour sauce over.');
insert into ingredients(id, name, amount, unit, recipe_id) values (13, 'Chicken Breast', 20, 'units', 'Grandson-22345A');
insert into ingredients(id, name, amount, unit, recipe_id) values (14, 'Bok Choy', 2, 'units', 'Grandson-22345A');
insert into ingredients(id, name, amount, unit, recipe_id) values (15, 'Sauce', 100.50, 'deciliters', 'Grandson-22345A');
insert into member(id, member, recipe_id) values (5, 'GRANDSON', 'Grandson-22345A');
-- prepare time > 2
insert into recipes(reference, name, portions, prepare_time, meal, instructions) values ('Grandson-32345A', 'Rosolli', 4, 3.00, 'lunch', 'Make sauce. Steam bok choy and chicken. Carry on steaming. Pour sauce over.');
insert into ingredients(id, name, amount, unit, recipe_id) values (16, 'Chicken Breast', 20, 'units', 'Grandson-32345A');
insert into ingredients(id, name, amount, unit, recipe_id) values (17, 'Bok Choy', 2, 'units', 'Grandson-32345A');
insert into ingredients(id, name, amount, unit, recipe_id) values (18, 'Sauce', 100.50, 'deciliters', 'Grandson-32345A');
insert into member(id, member, recipe_id) values (6, 'GRANDSON', 'Grandson-32345A');
-- Daughter
-- prepare time < 2
insert into recipes(reference, name, link, portions, prepare_time, meal, instructions) values ('Daughter-12345A', 'Bittman Chinese Chicken With Bok Choy', 'https://www.youtube.com/watch?v=dQw4w9WgXcQ', 30, 1.30,'lunch', 'Make sauce. Steam bok choy and chicken. Carry on steaming. Pour sauce over.');
insert into ingredients(id, name, amount, unit, recipe_id) values (19, 'Chicken Breast', 20, 'units', 'Daughter-12345A');
insert into ingredients(id, name, amount, unit, recipe_id) values (20, 'Bok Choy', 2, 'units', 'Daughter-12345A');
insert into ingredients(id, name, amount, unit, recipe_id) values (21, 'Sauce', 100.50, 'deciliters', 'Daughter-12345A');
insert into member(id, member, recipe_id) values (7, 'DAUGHTER', 'Daughter-12345A');
-- prepare time = 2
insert into recipes(reference, name, link, portions, prepare_time, meal, instructions) values ('Daughter-22345A', 'Paella', 'https://www.youtube.com/watch?v=T-eDbjeTA3E', 6, 2.00, 'lunch', 'Make sauce. Steam bok choy and chicken. Carry on steaming. Pour sauce over.');
insert into ingredients(id, name, amount, unit, recipe_id) values (22, 'Chicken Breast', 20, 'units', 'Daughter-22345A');
insert into ingredients(id, name, amount, unit, recipe_id) values (23, 'Bok Choy', 2, 'units', 'Daughter-22345A');
insert into ingredients(id, name, amount, unit, recipe_id) values (24, 'Sauce', 100.50, 'deciliters', 'Daughter-22345A');
insert into member(id, member, recipe_id) values (8, 'DAUGHTER', 'Daughter-22345A');
-- prepare time > 2
insert into recipes(reference, name, portions, prepare_time, meal, instructions) values ('Daughter-32345A', 'Rosolli', 4, 3.00, 'lunch', 'Make sauce. Steam bok choy and chicken. Carry on steaming. Pour sauce over.');
insert into ingredients(id, name, amount, unit, recipe_id) values (25, 'Chicken Breast', 20, 'units', 'Daughter-32345A');
insert into ingredients(id, name, amount, unit, recipe_id) values (26, 'Bok Choy', 2, 'units', 'Daughter-32345A');
insert into ingredients(id, name, amount, unit, recipe_id) values (27, 'Sauce', 100.50, 'deciliters', 'Daughter-32345A');
insert into member(id, member, recipe_id) values (9, 'DAUGHTER', 'Daughter-32345A');