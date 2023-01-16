
INSERT INTO nation (iso, name) VALUES ('ITA', 'Italy');
INSERT INTO nation (iso, name) VALUES ('AUS', 'Australia');
INSERT INTO nation (iso, name) VALUES ('CAN', 'Canada');
INSERT INTO nation (iso, name) VALUES ('JPN', 'Japan');
INSERT INTO nation (iso, name) VALUES ('KOR', 'South Korea');

INSERT INTO profile (email, password, role) VALUES ('poorify@gmail.com', 'poorify', 'U');
INSERT INTO endUser (profile, alias, birthdate, nation, isPublic) VALUES (1, 'Poorify', '2022-10-04', 'ITA', 1);
INSERT INTO profile (email, password, role) VALUES ('alessandro@gmail.com', 'alessandro', 'U');
INSERT INTO endUser (profile, alias, birthdate, nation, isPublic) VALUES (2, 'Alakay Fisher', '2001-08-14', 'ITA', 1);
INSERT INTO profile (email, password, role) VALUES ('coldplay@gmail.com', 'coldplay', 'A');
INSERT INTO artist (profile, alias, bio) VALUES (3, 'Coldplay', '');
INSERT INTO profile (email, password, role) VALUES ('kevin@gmail.com', 'kevin', 'O');
INSERT INTO overseer (profile) VALUES (4);
INSERT INTO profile (email, password, role) VALUES ('chiara@gmail.com', 'chiara', 'U');
INSERT INTO endUser (profile, alias, birthdate, nation, isPublic) VALUES (5, 'Clare Ingram', '2002-03-30', 'ITA', 1);

INSERT INTO album (artist, title, tracks, duration, year, type) VALUES (3, 'Parachutes', 10, 41 * 60 + 55, 2000, 'Album');
INSERT INTO album (artist, title, tracks, duration, year, type) VALUES (3, 'A Rush of Blood to the Head', 11, 54 * 60 + 11, 2002, 'Album');
INSERT INTO album (artist, title, tracks, duration, year, type) VALUES (3, 'X&Y', 13, 60 * 60 + 2 * 60, 2005, 'Album');
INSERT INTO album (artist, title, tracks, duration, year, type) VALUES (3, 'Viva La Vida or Death and All His Friends', 10, 45 * 60 + 55, 2008, 'Album');
INSERT INTO album (artist, title, tracks, duration, year, type) VALUES (3, 'Mylo Xyloto', 14, 44 * 60 + 10, 2011, 'Album');
INSERT INTO album (artist, title, tracks, duration, year, type) VALUES (3, 'Magic', 1, 4 * 60 + 45, 2014, 'Single');

INSERT INTO track (album, title, track, duration, plays, genre) VALUES (1, 'Don''t Panic', 1, 137, 998721, 3);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (1, 'Shiver', 2, 304, 75196, 3);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (1, 'Spies', 3, 319, 590948, 3);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (1, 'Sparks', 4, 227, 646280, 3);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (1, 'Yellow', 5, 267, 692487, 3);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (1, 'Trouble', 6, 273, 854900, 3);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (1, 'Parachutes', 7, 46, 813979, 3);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (1, 'High Speed', 8, 256, 422861, 3);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (1, 'We Never Change', 9, 249, 526906, 3);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (1, 'Everything''s Not Lost - Includes Hidden Track ''Life Is For Living''', 10, 436, 472722, 3);

INSERT INTO track (album, title, track, duration, plays, genre) VALUES (2, 'Politik', 1, 319, 189054, 2);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (2, 'In My Place', 2, 227, 699184, 2);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (2, 'God Put a Smile upon Your Face', 3, 297, 942718, 2);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (2, 'The Scientist', 4, 310, 86321, 2);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (2, 'Clocks', 5, 308, 777970, 2);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (2, 'Daylight', 6, 328, 706200, 2);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (2, 'Green Eyes', 7, 223, 782994, 2);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (2, 'Warning Sign', 8, 331, 199660, 2);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (2, 'A Whisper', 9, 238, 335693, 2);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (2, 'A Rush of Blood to the Head', 10, 351, 120209, 2);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (2, 'Amsterdam', 11, 319, 90796, 2);

INSERT INTO track (album, title, track, duration, plays, genre) VALUES (3, 'Square One', 1, 288, 636071, 2);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (3, 'What If', 2, 299, 593893, 2);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (3, 'White Shadows', 3, 328, 688050, 2);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (3, 'Fix You', 4, 296, 260049, 2);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (3, 'Talk', 5, 311, 591748, 2);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (3, 'X&Y', 6, 274, 771826, 2);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (3, 'Speed of Sound', 7, 288, 882587, 2);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (3, 'A Message', 8, 285, 989843, 2);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (3, 'Low', 9, 332, 447094, 2);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (3, 'The Hardest Part', 10, 263, 912856, 2);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (3, 'Swallowed in the Sea', 11, 239, 132553, 2);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (3, 'Twisted Logic', 12, 272, 360488, 2);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (3, 'Til Kingdom Come', 13, 251, 979599, 2);

INSERT INTO track (album, title, track, duration, plays, genre) VALUES (4, 'Life in Technicolor', 1, 149, 188814, 1);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (4, 'Cemeteries of London', 2, 201, 993179, 1);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (4, 'Lost!', 3, 236, 301727, 1);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (4, '42', 4, 237, 159539, 1);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (4, 'Lovers in Japan', 5, 411, 241900, 1);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (4, 'Yes', 6, 427, 854460, 1);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (4, 'Viva La Vida', 7, 242, 570349, 1);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (4, 'Violet Hill', 8, 223, 537248, 1);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (4, 'Strawberry Swing', 9, 250, 53579, 1);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (4, 'Death and All His Friends', 10, 379, 303036, 1);

INSERT INTO track (album, title, track, duration, plays, genre) VALUES (5, 'Mylo Xyloto', 1, 42, 702900, 1);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (5, 'Hurts Like Heaven', 2, 242, 268595, 1);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (5, 'Paradise', 3, 279, 658011, 1);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (5, 'Charlie Brown', 4, 285, 307765, 1);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (5, 'Us Against the World', 5, 240, 978895, 1);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (5, 'M.M.I.X.', 6, 48, 164186, 1);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (5, 'Every Teardrop Is a Waterfall', 7, 241, 452620, 1);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (5, 'Major Minus', 8, 210, 469768, 1);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (5, 'U.F.O.', 9, 138, 854314, 1);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (5, 'Princess of China', 10, 239, 957473, 1);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (5, 'Up in Flames', 11, 193, 729300, 1);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (5, 'A Hopeful Transmission', 12, 33, 415201, 1);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (5, 'Don''t Let It Break Your Heart', 13, 234, 945770, 1);
INSERT INTO track (album, title, track, duration, plays, genre) VALUES (5, 'Up with the Birds', 14, 226, 240693, 1);

INSERT INTO track (album, title, track, duration, plays, genre) VALUES (6, 'Magic', 1, 4 * 60 + 45, 20693, 1);

