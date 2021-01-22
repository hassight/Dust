USE tahiti;

-- Coordinates Insertion for sites
INSERT INTO coordinates(id_coordinates, latitude, longitude)
VALUES (1,-17.551369353753934, -149.55843288787528),
(2,-17.745448888016877, -149.36367048630828),
(3,-17.522870830506164, -149.56533601215804),
(4,-17.5448616, -149.5743084),
(5,-17.730888, -149.330967),
(6,-17.508077334083655, -149.52224508783118),
(7,-17.605856390584634, -149.48949598534273),
(8,-17.519583491038734, -149.4007286812294),
(9,-17.5375238, -149.569155),
(10,-17.5571733, -149.5308777),
(11,-17.517117947364067, -149.39986686274287),
(12,-17.514652370201837, -149.3964195887967),
(13, -17.518261386969638, -149.5678252724696),
(14,-17.51760658618369, -149.57125849978897),
(15,-17.60663777741304, -149.60833735483786),
(16,-17.462860411679923, -149.8622084270014),
(17, -17.5204912073484, -149.77981097133718),
(18, -17.50477553536069, -149.52025896595657),
(19, -17.848897294229626, -149.19753559793836),
(20, -17.643786655055383, -149.43381969848113),
(21, -17.530375376885182, -149.90459254554645),
(22, -17.58598371764897, -149.61471653709398),
(23, -17.538703299521323, -149.56767633569706),
(24, -17.5222902318481, -149.54046488922606);

-- Coordinates Insertion for hotels
INSERT INTO coordinates(id_coordinates, latitude, longitude)
VALUES (25, -17.570781792091456, -149.61874673084156),
(26, -17.51791409409522, -149.50659827319623),
(27, -17.539543813465105, -149.36649545779457),
(28, -17.500719328223255, -149.76499809068045),
(29, -17.664398225813105, -149.59238618391683),
(30, -17.546229676260914, -149.5743231337083),
(31, -17.525873109246145, -149.5464152224956),
(32, -17.844906044840393, -149.26793804250477),
(33, -17.737109480728407, -149.271992473193),
(34, -17.747383747089128, -149.3302806427271),
(35, -17.6397377238737, -149.60827600126117),
(36, -17.843498038097014, -149.26702907039157),
(37, -17.75284753058199, -149.53688073581344);

-- Transport Insertion
INSERT INTO transport(id_transport, type, price)
VALUES (1, 'bus', 2),
(2, 'boat', 15);


-- Site Insertion
INSERT INTO site(id_site, name, type, price, id_coordinates)
VALUES (1,'Musée de la Perle Robert Wan', 'historic', 0, 1),
(2,'Musée Gauguin', 'historic', 0, 2),
(3,'Cathédrale Notre-Dame de Papeete', 'historic', 0, 3),
(4,'Temple protestant de Paofai', 'historic', 0, 4),
(5,'Nui Diving', 'activity', 49, 5),
(6,'James Norman Hall Museum', 'historic', 0, 6),
(7,'Mont Orohena', 'historic', 0, 7),
(8,'Cascades de Faarumai', 'activity', 193, 8),
(9,'Safari Jeep', 'activity', 81, 9),
(10,'Rainbow Park', 'activity', 30, 10),
(11,'Terevau', 'activity', 10, 11),
(12,'Place Vaiete', 'activity', 0, 12),
(13,'Faati City', 'activity', 36, 13),
(14,'Parc Bougainville', 'activity', 0, 14),
(15,'Musée de Tahiti et des Îles', 'historic', 6, 15),
(16,'Fare Tutava ', 'activity', 0, 16),
(17,'Lagoonarium de Moorea', 'activity', 30, 17),
(18,'Tombeau du roi Pomare V', 'historic', 0, 18),
(19,'Grotte Vaipori', 'activity', 0, 19),
(20, 'Marae Fare Hape', 'historic', 0, 20),
(21, 'Tiki Village', 'historic', 0, 21),
(22, 'Eleuthera Tahiti Diving Center', 'activity', 360, 22),
(23, 'Marché de Papeete', 'activity', 0, 23),
(24, 'API Dive', 'activity', 34, 24);

-- Hotel Insertion
INSERT INTO hotel(name, price, beach_name, id_coordinates)
 VALUES ('InterContinental Resort Tahiti', 203, 'Plage de Papeete', 25),
 ('Le Tahiti by Pearl Resorts', 211, 'Baie de Matavai', 26),
 ('Le Rocher de Tahiti', 276, 'Le rocher', 27),
 ('Sofitel kia ora moorea beach resort', 303, 'Moorea Beach', 28),
 ('Pension Te Miti', 48, 'Plage de sable blanc', 29),
 ('Fare Suisse', 85, 'Plage Lafayette', 30),
 ('Hotel Restaurant Le Royal Tahitien', 128, 'Plage de sable noir', 31),
 ('Villa BO', 98, 'Plage de Teahupoo', 32),
 ('Punatea Village', 71, 'Plage de Maui', 33),
 ('Villa Mitirapa', 270, 'Plage de Maui', 34),
 ('Pension de La Plage', 99, 'La plage publique de Papehue', 35),
 ('Vanira Lodge', 126, 'Plage de Maui', 36),
 ('Hiti Moana Villa', 92, 'Plage de Taharuu', 37);