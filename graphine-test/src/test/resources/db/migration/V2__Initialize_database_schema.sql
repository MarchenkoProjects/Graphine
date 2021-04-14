INSERT INTO films(id, title, year, budget, gross, tagline, was_released)
VALUES (1, 'Iron Man', 2008, 140000000, 585000000, 'Get ready for a different breed of heavy metal hero.', TRUE),
       (2, 'The Incredible Hulk', 2008, 150000000, 264000000, 'This June, a hero shows his true colors', TRUE),
       (3, 'Iron Man 2', 2010, 200000000, 623000000, 'It''s not the armor that makes the hero, but the man inside.',
        TRUE),
       (4, 'Thor', 2011, 150000000, 449000000, 'Two worlds. One hero.', TRUE),
       (5, 'Captain America: The First Avenger', 2011, 140000000, 370000000, 'When patriots become heroes', TRUE),
       (6, 'The Avengers', 2012, 220000000, 1518000000, 'Avengers Assemble!', TRUE),
       (7, 'Iron Man Three', 2013, 200000000, 1214000000, 'Unleash the power behind the armor.', TRUE),
       (8, 'Thor: The Dark World', 2013, 170000000, 644000000, 'There was darkness.', TRUE),
       (9, 'Captain America: The Winter Soldier', 2014, 170000000, 714000000, 'In heroes we trust.', TRUE),
       (10, 'Guardians of the Galaxy', 2014, 170000000, 773000000, 'From the studio that brought you "The Avengers".',
        TRUE),
       (11, 'Avengers: Age of Ultron', 2015, 250000000, 1402000000, 'A new age begins', TRUE),
       (12, 'Ant-Man', 2015, 130000000, 519000000, 'No shield. No armor. No problem.', TRUE),
       (13, 'Captain America: Civil War', 2016, 250000000, 1153000000, 'United we stand. Divided we fall', TRUE),
       (14, 'Doctor Strange', 2016, 165000000, 677000000, 'Open your mind. Change your reality.', TRUE),
       (15, 'Guardians of the Galaxy Vol. 2', 2017, 200000000, 863000000, 'Obviously.', TRUE),
       (16, 'Spider-Man: Homecoming', 2017, 175000000, 880000000, 'Homework can wait. The city can''t.', TRUE),
       (17, 'Thor: Ragnarok', 2017, 180000000, 853000000, 'Let The Games Begin', TRUE),
       (18, 'Black Panther', 2018, 200000000, 1347000000, 'Hero. Legend. King.', TRUE),
       (19, 'Avengers: Infinity War', 2018, 321000000, 2048000000, 'Where will you be, when it all ends?', TRUE),
       (20, 'Ant-Man and the Wasp', 2018, 162000000, 622000000, 'Real heroes. Not actual size.', TRUE),
       (21, 'Captain Marvel', 2019, 160000000, 1128000000, 'Discover what makes a (her)o.', TRUE),
       (22, 'Avengers: Endgame', 2019, 356000000, 2797000000, 'Part of the journey is the end.', TRUE),
       (23, 'Spider-Man: Far from Home', 2019, 160000000, 1131000000, '', TRUE),
       (24, 'Black Widow', 2021, NULL, NULL, '', FALSE),
       (25, 'Shang-Chi and the Legend of the Ten Rings', 2021, NULL, NULL, '', FALSE),
       (26, 'Eternals', 2021, NULL, NULL, '', FALSE),
       (27, 'Spider-Man: No Way Home', 2021, NULL, NULL, '', FALSE);

ALTER SEQUENCE seq_films_id START WITH 28;