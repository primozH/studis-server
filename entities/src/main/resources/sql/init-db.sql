INSERT INTO klasius (sifra, opis, strokovni_naslov, raven_izobrazbe) VALUES (12001, 'Osnovnošolska izobrazba', 'zaključena osnovna šola', '1'), (13001, 'Nižja poklicna izobrazba', 'zaključni izpit', '3');

INSERT INTO vrsta_vpisa (sifra, vrsta_vpisa) VALUES (1, 'Prvi vpis v letnik/dodatno leto'), (2, 'Ponavljanje letnika'), (3, 'Nadaljevanje letnika'), (4, 'Podaljšanje statusa študenta'), (5, 'Vpis po merilih za prehode v višji letnik'), (6, 'Vpis v semester skupnega št. programa'), (7, 'Vpis po merilih za prehode v isti letnik'), (98, 'Vpis za zaključek');

INSERT INTO nacin_studija (sifra, opis, opis_ang) VALUES (1, 'redni', 'full-time'), (2, 'izredni', 'part-time');

INSERT INTO oblika_studija (sifra, opis, opis_ang) VALUES (1, 'na lokaciji', 'on site'), (2, 'na daljavo', 'distance learning'), (3, 'e-študij', 'e-learning');