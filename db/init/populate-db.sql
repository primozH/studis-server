/*
  Klasius sifrant
 */
INSERT INTO klasius (sifra, opis, strokovni_naslov, raven_izobrazbe)
VALUES
  (12001, 'Osnovnošolska izobrazba', 'zaključena osnovna šola', '1'),
  (13001, 'Nižja poklicna izobrazba', 'zaključni izpit', '3'),
  (14001, 'Srednja poklicna izobrazba', 'zaključni izpit', '4'),
  (15001, 'Srednja strokovna izobrazba', 'poklicna matura/zaključni izpit', '5'),
  (15002, 'Srednja splošna izobrazba', 'splošna matura', '5'),
  (16102, 'Višješolska izobrazba (predbolonjska)', 'inženir/tehnolog/višji/drugo', '6/1'),
  (16201, 'Specializacija po višješolski izobrazbi (predbolonjska)', 'specialist', '6/2'),
  (16202, 'Visokošolska strokovna izobrazba (predbolonjska)', 'diplomirani/diplomirani inženir', '6/2'),
  (17001, 'Specializacija po visokošolski strokovni izobrazbi (predbolonjska)', 'specialist', '7'),
  (17002, 'Visokošolska univerzitetna izobrazba (predbolonjska)', 'univerzitetni diplomirani/akademski/profesor...', '7'),
  (18101, 'Specializacija po univerzitetni izobrazbi (predbolonjska)', 'specialist', '8/1'),
  (18102, 'Magisterij znanosti (predbolonjska)', 'magister znanosti/umetnosti', '8/1'),
  (18201, 'Doktorat znanosti (predbolonjska)', 'doktor znanosti', '8/2'),
  (16101, ' Višja strokovna izobrazba', 'inženir/tehnolog', '6/1'),
  (16203, 'Visokošolska strokovna izobrazba (prva bolonjska stopnja)', 'diplomirani (VS)', '6/2'),
  (16204, 'Visokošolska univerzitetna izobrazba (prva bolonjska stopnja)', 'diplomirani (UN)', '6/2'),
  (17003, 'Magistrska izobrazba (druga bolonjska stopnja)', 'magister', '7'),
  (18202, 'Doktorat znanosti (tretja bolonjska stopnja)', 'doktor zannosti', '8/2');

/*
  Vrsta vpisa
 */
INSERT INTO vrsta_vpisa (sifra, vrsta_vpisa)
VALUES
  (1, 'Prvi vpis v letnik/dodatno leto'),
  (2, 'Ponavljanje letnika'),
  (3, 'Nadaljevanje letnika'),
  (4, 'Podaljšanje statusa študenta'),
  (5, 'Vpis po merilih za prehode v višji letnik'),
  (6, 'Vpis v semester skupnega št. programa'),
  (7, 'Vpis po merilih za prehode v isti letnik'),
  (98, 'Vpis za zaključek');

/*
  Nacin studija
 */
INSERT INTO nacin_studija (sifra, opis, opis_ang)
VALUES
  (1, 'redni', 'full-time'),
  (2, 'izredni', 'part-time');

/*
  Oblika studija
 */
INSERT INTO oblika_studija (sifra, opis, opis_ang)
VALUES
  (1, 'na lokaciji', 'on site'),
  (2, 'na daljavo', 'distance learning'),
  (3, 'e-študij', 'e-learning');

/*
  Studijski program
 */
INSERT INTO studijski_program (sifra, naziv, sifra_evs, stevilo_semestrov, stopnja, klasius)
VALUES
  ('VV', 'Računalništvo in matematika UNI-1.st', 1000407, 6, 2, 16204),
  ('L1', 'Računalništvo in matematika MAG 2.st', 1000471, 4, 3, 17003),
  ('VT', 'Računalništvo in informatika UNI-1.st', 1000468, 6, 2, 16204),
  ('VU', 'Računalništvo in informatika VS-1.st', 1000470, 6, 1, 16203),
  ('X6', 'Računalništvo in informatika DR-3.st', 1000474, 6, 4, 18202);

/*
  Studijsko leto
 */
INSERT INTO studijsko_leto (sifra, studijsko_leto)
VALUES
  (2015, '2015/2016'),
  (2016, '2016/2017'),
  (2017, '2017/2018'),
  (2018, '2018/2019');

/*
  Letnik
*/
INSERT INTO letnik (letnik)
VALUES
  (1),
  (2),
  (3);

/*
  Drzava
*/
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('AF', 'AFG', 004, 'Afghanistan ', 'Afganistan');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('AX', 'ALA', 248.0, 'Ålland Islands ', 'Alandski otoki', 'Otočje v Baltiku.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('AL', 'ALB', 008, 'Albania ', 'Albanija');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('DZ', 'DZA', 012, 'Algeria ', 'Alžirija', 'Koda po kabilskem nazivu: Dzayer.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('AS', 'ASM', 016, 'American Samoa ', 'Ameriška Samoa', 'Zunanji teritorij ZDA v južnem Tihem oceanu.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('AD', 'AND', 020, 'Andorra ', 'Andora');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('AO', 'AGO', 024, 'Angola ', 'Angola');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('AI', 'AIA', 660.0, 'Anguilla ', 'Angvila', 'Čezmorska skupnost Velike Britanije, predhodno je AI predstavljal francoski: Afar and Issas.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('AQ', 'ATA', 010, 'Antarctica ', 'Antarktika', 'Koda pa francoskem nazivu: Antarctique.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('AG', 'ATG', 028, 'Antigua and Barbuda ', 'Antigva in Barbuda', 'Otoška država v malih Antilih v Karibskem morju.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('AR', 'ARG', 032, 'Argentina', 'Argenitna');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('AM', 'ARM', 051, 'Armenia ', 'Armenija');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('AW', 'ABW', 533, 'Aruba ', 'Aruba', 'Otok v Karibskem morju, del kraljevine Nizozemske.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('AU', 'AUS', 036, 'Australia ', 'Avstralija');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('AT', 'AUT', 040, 'Austria ', 'Avstrija');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('AZ', 'AZE', 031, 'Azerbaijan ', 'Azerbajdžan');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('BS', 'BHS', 044, 'Bahamas ', 'Bahami');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('BH', 'BHR', 048, 'Bahrain ', 'Bahrajn');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('BD', 'BGD', 050, 'Bangladesh ', 'Bangladeš');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('BB', 'BRB', 052, 'Barbados ', 'Barbados');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('BY', 'BLR', 112, 'Belarus ', 'Belorusija', 'Bivši ISO naziv države: Byelorussian SSR.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('BE', 'BEL', 056, 'Belgium ', 'Belgija');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('BZ', 'BLZ', 084, 'Belize ', 'Belize');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('BJ', 'BEN', 204, 'Benin ', 'Benin', 'Bivši ISO naziv države: Dahomey (DY).');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('BM', 'BMU', 060, 'Bermuda ', 'Bermudi');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('BT', 'BTN', 064, 'Bhutan ', 'Butan');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('BO', 'BOL', 068, 'Bolivia, Plurinational State of ', 'Bolivija');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('BQ', 'BES', 535, 'Bonaire, Sint Eustatius and Saba ', 'Otočje Bonaire, Sv. Eustatij in Saba', 'Otočje v karibih pod nizozemsko upravo (the BES Islands). Bivši ISO naziv države: Bonaire, Saint Eustatius and Saba. BQ je prej predstavljal: British Antarctic Territory.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('BA', 'BIH', 070, 'Bosnia and Herzegovina ', 'Bosna in Hercegovina');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('BW', 'BWA', 072, 'Botswana ', 'Bocvana');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('BV', 'BVT', 074, 'Bouvet Island ', 'Bouvetov otok', 'Norveški otok v južnem Atlantskem oceanu.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('BR', 'BRA', 076, 'Brazil ', 'Brazilija');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('IO', 'IOT', 086, 'British Indian Ocean Territory ', 'Britansko ozemlje v Indijskem oceanu');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('BN ', 'BRN', 096, 'Brunei Darussalam ', 'Brunej', 'ISO naziv države po nazivu v ZN. Otoška država na otok Borneo v JV Aziji.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('BG', 'BGR', 100, 'Bulgaria ', 'Bolgarija');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('BF ', 'BFA', 854, 'Burkina Faso ', 'Burkina Faso', 'Bivši ISO naziv države: Upper Volta (HV).');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('BI', 'BDI', 108, 'Burundi ', 'Burundi ');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('KH', 'KHM', 116, 'Cambodia ', 'Kambodža', 'Koda po bivšem nazivu: Khmer Republic. Bivši ISO naziv države: Kampuchea.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('CM', 'CMR', 120, 'Cameroon ', 'Kamerun');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('CA', 'CAN', 124, 'Canada ', 'Kanada');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('CV', 'CPV', 132, 'Cape Verde ', 'Kapverdski otoki (Zelenortski otoki)', 'Otočje v Atlantskem oceanu ob Afriki.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('KY', 'CYM', 136, 'Cayman Islands ', 'Kajmanski otoki');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('CF', 'CAF', 140, 'Central African Republic ', 'Srednjeafriška republika', 'Prej znana kot francoska kolonija Ubangi-Shari.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('TD', 'TCD', 148, 'Chad ', 'Čad', 'Koda po francoskem nazivu: Tchad.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('CL', 'CHL', 152, 'Chile ', 'Čile');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('CN', 'CHN', 156, 'China ', 'Kitajska');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('CX', 'CXR', 162, 'Christmas Island ', 'Božični otok', 'Avstralsko ozemlje v Indijskem oceanu.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('CC', 'CCK', 166, 'Cocos (Keeling) Islands ', 'Kokosovi in Keelingovi otoki', 'Otočje pod upravo Avstralije v Indijskem oceanu.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('CO', 'COL', 170, 'Colombia ', 'Kolumbija');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('KM ', 'COM', '174', 'Comoros ', 'Komori', 'Otočje v Indijskem oceanu. Koda po nazivu v komorščini: Komori.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('CG', 'COG', 178, 'Congo ', 'Kongo', 'Srednji Kongo (celinska država brez morja).');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('CD', 'COD', 180, 'Congo, the Democratic Republic of the ', 'Demokratična republika Kongo', 'Bivše ime: Zaire (ZR), obmorska država.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('CK', 'COK', 184, 'Cook Islands ', 'Cookovi otoki');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('CR', 'CRI', 188, 'Costa Rica ', 'Kostarika');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('CI', 'CIV', 384, 'Côte d\'Ivoire', 'Slonokoščena obala');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('HR', 'HRV', 191, 'Croatia ', 'Hrvaška', 'Koda po nazivu v hrvaščini: Hrvatska.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('CU', 'CUB', 192, 'Cuba ', 'Kuba');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('CW', 'CUW', 531, 'Curaçao ', 'Kurasao', 'Spada v čezmorsko ozemlje Nizozemske, Nizozemski Antili.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('CY', 'CYP', 196, 'Cyprus ', 'Ciper');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('CZ', 'CZE', 203, 'Czech Republic ', 'Češka');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('DK', 'DNK', 208, 'Denmark ', 'Danska');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('DJ', 'DJI', 262, 'Djibouti ', 'Džibuti', 'Staro ime: French Afar and Issas (AI).');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('DM', 'DMA', 212, 'Dominica ', 'Dominika', 'Otoška država v malih Antilih v Karibskem morju.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('DO', 'DOM', 214, 'Dominican Republic ', 'Dominikanska republika');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('EC', 'ECU', 218, 'Ecuador ', 'Ekvador');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('EG', 'EGY', 818, 'Egypt ', 'Egipt');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('SV', 'SLV', 222, 'El Salvador ', 'Salvador');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('GQ', 'GNQ', 226, 'Equatorial Guinea ', 'Ekvatorialna Gvineja', 'Koda po francoskem nazivu: Guinée équatoriale.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('ER', 'ERI', 232, 'Eritrea ', 'Eritreja');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('EE', 'EST', 233, 'Estonia ', 'Estonija', 'Koda po estonskem nazivu: Eesti.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('ET', 'ETH', 231, 'Ethiopia ', 'Etiopija');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('FK', 'FRO', 234, 'Falkland Islands (Malvinas) ', 'Falkalndski otoki', 'Čezmorsko otočje velike Britanije.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('FO', 'FLK', 238, 'Faroe Islands ', 'Ferski otoki');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('FJ', 'FJI', 242, 'Fiji ', 'Fidži', 'Otočje v južnem Tihem oceanu.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('FI', 'FIN', 246, 'Finland ', 'Finska');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('FR', 'FRA', 250, 'France ', 'Francija');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('GF', 'GUF', 254, 'French Guiana ', 'Francoska Gvajana', 'Koda po francoskem nazivu: Guyane française.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('PF', 'PYF', 258, 'French Polynesia ', 'Francoska Polinezija', 'Čezmorsko otočje Francije v južnem Tihem oceanu. Koda po francoskem nazivu: Polynésie française.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('TF', 'ATF', 260, 'French Southern Territories ', 'Francoska južna ozemlja', 'Predstavlja francoske vulkanske otoke JV od Afrike v Indijskem oceanu in del antarktike, ki Franciji niso mednarodno priznani. Koda po francokem nazivu: Terres australes françaises.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('GA', 'GAB', 266, 'Gabon ', 'Gabon');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('GM', 'GMB', 270, 'Gambia ', 'Gambija');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('GE', 'GEO', 268, 'Georgia ', 'Gruzija', 'Koda GE je prej predstavljala Gilbertove in Ellisijine otoke.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('DE', 'DEU', 276, 'Germany ', 'Nemčija', 'Koda po nemškem nazivu: Deutschland. Koda pred 1990 v uporabi za Zahodno Nemčijo.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('GH', 'GHA', 288, 'Ghana ', 'Gana');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('GI', 'GIB', 292, 'Gibraltar ', 'Gibraltar');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('GR', 'GRC', 300, 'Greece ', 'Grčija');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('GL', 'GRL', 304, 'Greenland ', 'Grenlandija');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('GD', 'GRD', 308, 'Grenada ', 'Grenada');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('GP', 'GLP', 312, 'Guadeloupe ', 'Guadeloupe', 'Čezmorski otok Francije v Karibskem morju.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('GU', 'GUM', 316, 'Guam ', 'Guam', 'Zunanji teritorij ZDA v Tihem oceanu (tudi Guahan).');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('GT', 'GTM', 320, 'Guatemala ', 'Gvatemala');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('GG', 'GGY', 831, 'Guernsey ', 'Otok Guernsey', 'Bailwick of Goursey je Britanski otok ob Franciji.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('GN', 'GIN', 324, 'Guinea ', 'Gvineja');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('GW', 'GNB', 624, 'Guinea-Bissau ', 'Gvineja-Bissau');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('GY', 'GUY', 328, 'Guyana ', 'Gvajana');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('HT', 'HTI', 332, 'Haiti ', 'Haiti');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('HM', 'HMD', 334, 'Heard Island and McDonald Islands ', 'Otok Heard in otočje McDonald', 'Nenaseljeno otočje v Indijskem oceanu pod upravo Avstralije.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('VA', 'VAT', 336, 'Holy See (Vatican City State) ', 'Vatikan', 'Bivši ISO naziv države: Vatican City State (Vatikanska mestna država).');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('HN', 'HND', 340, 'Honduras ', 'Honduras');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('HK', 'HKG', 344, 'Hong Kong ', 'Hong Kong');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('HU', 'HUN', 348, 'Hungary ', 'Madžarska');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('IS', 'ISL', 352, 'Iceland ', 'Islandija', 'Koda po nazivu v islandščini: Ísland.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('IN', 'IND', 356, 'India ', 'Indija');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('ID', 'IDN', 360, 'Indonesia ', 'Indonezija');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('IR', 'IRN', 364, 'Iran, Islamic Republic of ', 'Iran');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('IQ', 'IRQ', 368, 'Iraq ', 'Irak');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('IE', 'IRL', 372, 'Ireland ', 'Irska');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('IM', 'IMN', 833, 'Isle of Man ', 'Otok Man', 'Spada neposredno pod Britansko krono a ni del Velike Britanije, nahaja se med Irsko in Veliko Britanijo.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('IL', 'ISR', 376, 'Israel ', 'Izrael');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('IT', 'ITA', 380, 'Italy ', 'Italija');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('JM', 'JAM', 388, 'Jamaica ', 'Jamajka');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('JP', 'JPN', 392, 'Japan ', 'Japonska');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('JE', 'JEY', 832, 'Jersey ', 'Otok Jersey', 'Bailwick of Jersey je Britanski otok med Anglijo in Francijo.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('JO', 'JOR', 400, 'Jordan ', 'Jordanija');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('KZ', 'KAZ', 398, 'Kazakhstan ', 'Kazahstan', 'Bivši ISO naziv države: Kazakstan.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('KE', 'KEN', 404, 'Kenya ', 'Kenija');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('KI', 'KIR', 296, 'Kiribati ', 'Kiribati', 'Razpršeno otočje v Tihem oceanu. Stari naziv: Gilbertovi otoki.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('KP', 'PRK', 408, 'Korea, Democratic People\'s Republic of ', 'Severna Koreja', 'ISO naziv države po uradnem nazivu v ZN (splošno ime: Severna Koreja).');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('KR', 'KOR', 410, 'Korea, Republic of ', 'Južna Koreja', 'ISO naziv države po uradnem nazivu v ZN (splošno ime: Južna Koreja).');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('KW', 'KWT', 414, 'Kuwait ', 'Kuvajt');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('KG', 'KGZ', 417, 'Kyrgyzstan ', 'Kirgizistan (Kirgizija)');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('LA', 'LAO', 418, 'Lao People\'s Democratic Republic ', 'Laos');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('LV', 'LVA', 428, 'Latvia ', 'Latvija');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('LB', 'LBN', 422, 'Lebanon ', 'Libanon');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('LS', 'LSO', 426, 'Lesotho ', 'Lesoto');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('LR', 'LBR', 430, 'Liberia ', 'Liberija');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('LY ', 'LBY', 434, 'Libya ', 'Libija', 'Bivši ISO naziv države: Libyan Arab Jamahiriya.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('LI', 'LIE', 438, 'Liechtenstein ', 'Lihtenštajn');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('LT', 'LTU', 440, 'Lithuania ', 'Litva');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('LU', 'LUX', 442, 'Luxembourg ', 'Luksemburg');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('MO', 'MAC', 446, 'Macao ', 'Makao', 'Bivši ISO naziv države: Macau.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('MK', 'MKD', 807, 'Macedonia, the former Yugoslav Republic of ', 'Makedonija', 'ISO naziv države glede na spor o nazivu države. Uradno domače ime države: Republika Makedonija.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('MG', 'MDG', 450, 'Madagascar ', 'Madagaskar');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('MW', 'MWI', 454, 'Malawi ', 'Malavi');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('MY', 'MYS', 458, 'Malaysia ', 'Malezija');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('MV', 'MDV', 462, 'Maldives ', 'Maldivi');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('ML', 'MLI', 466, 'Mali ', 'Mali');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('MT', 'MLT', 470, 'Malta ', 'Malta');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('MH', 'MHL', 584, 'Marshall Islands ', 'Maršalovi otoki', 'Majhno otočje v Tihem oceanu.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('MQ', 'MTQ', 474, 'Martinique ', 'Martinik', 'Čezmorski otok Francije v malih Antilih v Karibsekm morju.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('MR', 'MRT', 478, 'Mauritania ', 'Mavretanija');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('MU', 'MUS', 480, 'Mauritius ', 'Mauricius (Moris)', 'Domačini v kreolščini imenujejo otok: Moris.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('YT', 'MYT', 175, 'Mayotte ', 'Francoska skupnost Mejot', 'Čezmorska skupnost Francije ob vzhodni obali Afrike.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('MX', 'MEX', 484, 'Mexico ', 'Mehika');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('FM', 'FSM', 583, 'Micronesia, Federated States of ', 'Mikronezija', 'Bivši ISO naziv države: Micronesia. Nahaja se v Tihem oceanu.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('MD', 'MDA', 498, 'Moldova, Republic of ', 'Moldavija');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('MC', 'MCO', 492, 'Monaco ', 'Monako');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('MN', 'MNG', 496, 'Mongolia ', 'Mongolija');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('ME', 'MNE', 499, 'Montenegro ', 'Črna Gora');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('MS', 'MSR', 500, 'Montserrat ', 'Montserat', 'Otok v Antilih v Karibskem morju odvisen od Velike Britanije.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('MA', 'MAR', 504, 'Morocco ', 'Maroko');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('MZ', 'MOZ', 508, 'Mozambique ', 'Mozambik');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('MM', 'MMR', 104, 'Myanmar ', 'Mjanmar', 'Bivši naziv: Burma (BU).');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('NA', 'NAM', 516, 'Namibia ', 'Namibija');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('NR', 'NRU', 520, 'Nauru ', 'Nauru', 'Otoška država v Južnem Tihem oceanu.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('NP', 'NPL', 524, 'Nepal ', 'Nepal');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('NL', 'NLD', 528, 'Netherlands ', 'Nizozemska');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('NC', 'NCL', 540, 'New Caledonia ', 'Nova Kaledonija', 'Čezmorsko otočje Francije v Pacifiku.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('NZ', 'NZL', 554, 'New Zealand ', 'Nova Zelandija');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('NI', 'NIC', 558, 'Nicaragua ', 'Nikaragva');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('NE', 'NER', 562, 'Niger ', 'Niger ');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('NG', 'NGA', 566, 'Nigeria ', 'Nigerija');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('NU', 'NIU', 570, 'Niue ', 'Niu', 'Otoška država v Južnem Tihem oceanu.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('NF', 'NFK', 574, 'Norfolk Island ', 'Otok Norflok', 'Del Avstralije s samoupravo.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('MP', 'MNP', 580, 'Northern Mariana Islands ', 'Severni Marianski otoki', 'Ameriško otočje v severnem Tihem oceanu.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('NO', 'NOR', 578, 'Norway ', 'Norveška');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('OM', 'OMN', 512, 'Oman ', 'Oman');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('PK', 'PAK', 586, 'Pakistan ', 'Pakistan');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('PW', 'PLW', 585, 'Palau ', 'Palau ', 'Majhna otoška država v Tihem oceanu.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('PS', 'PSE', 275, 'Palestinian Territory, Occupied ', 'Palestina', 'Sestavljena iz Zahodnega brega in Gaze.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('PA', 'PAN', 591, 'Panama ', 'Panama');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('PG', 'PNG', 598, 'Papua New Guinea ', 'Papua Nova Gvineja');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('PY', 'PRY', 600, 'Paraguay ', 'Paragvaj');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('PE', 'PER', 604, 'Peru ', 'Peru');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('PH', 'PHL', 608, 'Philippines ', 'Filipini');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('PN', 'PCN', 612, 'Pitcairn ', 'Pitcairnovi otoki', 'Čezmorsko otočje Velike Britanije v Tihem oceanu.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('PL', 'POL', 616, 'Poland ', 'Poljska');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('PT', 'PRT', 620, 'Portugal ', 'Portugalska');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('PR', 'PRI', 630, 'Puerto Rico ', 'Portoriko');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('QA', 'QAT', 634, 'Qatar ', 'Katar');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('RE', 'REU', 638, 'Réunion ', 'Francoska skupnost Reunion', 'Čezmorska otoška skupnost Francije v Indijskem oceanu.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('RO', 'ROU', 642, 'Romania ', 'Romunija');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('RU', 'RUS', 643, 'Russian Federation ', 'Ruska federacija');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('RW', 'RWA', 646, 'Rwanda ', 'Ruanda');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('BL', 'BLM', 652, 'Saint Barthélemy ', 'Sveti Bartolomej', 'Čezmosrksa skupnost Francije.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('SH', 'SHN', 654, 'Saint Helena, Ascension and Tristan da Cunha ', 'Sveta Helena', 'Čezmorsko ozemlje Sveta Helena Velike Britanije v Atlantskem oceanu. Bivši ISO naziv države: Saint Helena.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('KN', 'KNA', 659, 'Saint Kitts and Nevis ', 'Sveti Kits in Nevis', 'Otoška državica v karibskih Malih Antilih. Bivši ISO naziv države: Saint Kitts-Nevis-Anguilla.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('LC', 'LCA', 662, 'Saint Lucia ', 'Sveta Lucija', 'Otoška država v južnem Karibskem morju.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('MF', 'MAF', 663, 'Saint Martin (French part) ', 'Otok svetega Martina', 'Čezmorsko otočje Francije v Karibskem morju. Nizozmski del otoka Sv. Martina ima kodo SX.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('PM', 'SPM', 666, 'Saint Pierre and Miquelon ', 'Sveta Pierre in Miquelon', 'Čezmorsko otočje Francije ob Kanadi in Grenlandiji.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('VC', 'VCT', 670, 'Saint Vincent and the Grenadines ', 'Sveti Vincent in Grenadini', 'Majhna otoška država v Karibskem otočju.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('WS', 'WSM', 882, 'Samoa ', 'Samoa', 'Koda nastala po bivšem nazivu: Western Samoa (Zahodna Samoa).');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('SM', 'SMR', 674, 'San Marino ', 'San Marino');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('ST', 'STP', 678, 'Sao Tome and Principe ', 'Sao Tome in Principe', 'Majhna otoška država v Gvinejskem zalivu ob Afriki.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('SA', 'SAU', 682, 'Saudi Arabia ', 'Savdska Arabija');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('SN', 'SEN', 686, 'Senegal ', 'Senegal');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('RS', 'SRB', 688, 'Serbia ', 'Srbija', 'Koda po uradnem nazivu: Republika Srbija.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('SC', 'SYC', 690, 'Seychelles ', 'Sejšeli');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('SL', 'SLE', 694, 'Sierra Leone ', 'Siera Leone');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('SG', 'SGP', 702, 'Singapore ', 'Singapur');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('SX', 'SXM', 534, 'Sint Maarten (Dutch part) ', 'Otok svetega.Martina (Nizozemska)', 'Francoski del otoka Sv. Martina ima ISO kodo MF. Nahaja se v Karibskem morju.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('SK', 'SVK', 703, 'Slovakia ', 'Slovaška', 'SK je prej predstavljal: Sikkim.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('SI', 'SVN', 705, 'Slovenia ', 'Slovenija');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('SB', 'SLB', 090, 'Solomon Islands ', 'Solomonovi otoki', 'Koda izhaja iz starega naziva: British Solomon Islands.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('SO', 'SOM', 706, 'Somalia ', 'Somalija');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('ZA', 'ZAF', 710, 'South Africa ', 'Južna afrika', 'Koda iz naziva v nizozemščini: Zuid-Afrika.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('GS', 'SGS', 239, 'South Georgia and the South Sandwich Islands ', 'Južna Georgia in Južni Sandwichevi otoki', 'Čezmorsko otočje Velike Britanije na jugu Atlantskega oceana.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('SS', 'SSD', 728, 'South Sudan ', 'Južni Sudan');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('ES', 'ESP', 724, 'Spain ', 'Španija', 'Koda po nazivu v spanščini: España.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('LK', 'LKA', 144, 'Sri Lanka ', 'Šri Lanka');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('SD', 'SDN', 729, 'Sudan ', 'Sudan');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('SR', 'SUR', 740, 'Suriname ', 'Surinam');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('SJ', 'SJM', 744, 'Svalbard and Jan Mayen ', 'Svalbard in Jan Majen ', 'Sestavljata ga dva arktična ozemlja pod suverenostjo Norveške: Svalbardski otoki in otok Jan Mayen.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('SZ', 'SWZ', 748, 'Swaziland ', 'Svazi');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('SE', 'SWE', 752, 'Sweden ', 'Švedska');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('CH', 'CHE', 756, 'Switzerland ', 'Švica', 'Koda je narejena po nazivu v latinščini: Confoederatio Helvetica.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('SY', 'SYR', 760, 'Syrian Arab Republic ', 'Sirija');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('TW', 'TWN', 158, 'Taiwan, Province of China ', 'Tajvan');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('TJ', 'TJK', 762, 'Tajikistan ', 'Tadžikistan');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('TZ', 'TZA', 834, 'Tanzania, United Republic of ', 'Tanzanija');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('TH', 'THA', 764, 'Thailand ', 'Tajska');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('TL', 'TLS', 626, 'Timor-Leste ', 'Vzhodni Timor', 'Bivši naziv: East Timor (TP). Majhna otoška država v JV Aziji.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('TG', 'TGO', 768, 'Togo ', 'Togo');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('TK', 'TKL', 772, 'Tokelau ', 'Tokelau', 'Trije koralni otoki pod upravo Nove Zelandije.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('TO', 'TON', 776, 'Tonga ', 'Tonga', 'Majhna otoška država v Tihem oceanu.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('TT', 'TTO', 780, 'Trinidad and Tobago ', 'Trinidad in Tobago');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('TN', 'TUN', 788, 'Tunisia ', 'Tunizija');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('TR', 'TUR', 792, 'Turkey ', 'Turčija');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('TM', 'TKM', 795, 'Turkmenistan ', 'Turkmenistan');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('TC', 'TCA', 796, 'Turks and Caicos Islands ', 'Tirški in Kajkoški otoki', 'Čezmorska skupnost Velike Britanije v Karibskem morju.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('TV', 'TUV', 798, 'Tuvalu ', 'Tuvalu', 'Majhna otoška država v Tihem oceanu.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('UG', 'UGA', 800, 'Uganda ', 'Uganda');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('UA', 'UKR', 804, 'Ukraine ', 'Ukrajina', 'Bivši ISO naziv države: Ukrainian SSR. ');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('AE', 'ARE', 784, 'United Arab Emirates ', 'Združeni Arabski Emirati');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('GB', 'GBR', 826, 'United Kingdom ', 'Velika Britanija', 'Koda po nazivu: Great Britain (iz uradnega naziva: United Kingdom of Great Britain and Northern Ireland). ');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('US', 'USA', 840, 'United States ', 'Združene države Amerike');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('UM', 'UMI', 581, 'United States Minor Outlying Islands ', 'ZDA zunanji otoki', 'Sestavljeno iz devetih manjših otokov ZDA: Baker Island, Howland Island, Jarvis Island, Johnston Atoll, Kingman Reef, Midway Islands, Navassa Island, Palmyra Atoll, and Wake Island.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('UY', 'URY', 858, 'Uruguay ', 'Urugvaj');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('UZ', 'UZB', 860, 'Uzbekistan ', 'Uzbekistan');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('VU', 'VUT', 548, 'Vanuatu ', 'Republika Vanuatu', 'Stari naziv: New Hebrides (NH).');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('VE', 'VEN', 862, 'Venezuela, Bolivarian Republic of ', 'Venezuela');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('VN', 'VNM', 704, 'Viet Nam ', 'Vietnam');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('VG', 'VGB', 092, 'Virgin Islands, British ', 'Britanski Deviški otoki', 'Čezmorska skupnost Velike Britanije v Karibskem morju.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('VI', 'VIR', 850, 'Virgin Islands, U.S. ', 'Ameriški Deviški otoki');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('WF', 'WLF', 876, 'Wallis and Futuna ', 'Otočje Valis in Futuna', 'Čezmorska skupnost Francije v Pacifiku.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('EH', 'ESH', 732, 'Western Sahara ', 'Zahodna Sahara', 'Bivši ISO naziv države: Spanish Sahara (koda po španskem nazivu: Sahara español).');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('YE', 'YEM', 887, 'Yemen ', 'Jemen', 'Bivši ISO naziv države: Republic of Yemen, koda se je uporabljala za Severni Jemen pred letom 1990.');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv) VALUES ('ZM', 'ZMB', 894, 'Zambia ', 'Zambija');
INSERT INTO drzava (iso_koda, iso_3_koda, numericna_oznaka, iso_naziv, slovenski_naziv, opombe) VALUES ('ZW', 'ZWE', 716, 'Zimbabwe ', 'Zimbabve', 'Naziv se je spremenil iz: Suthern Rhodesia (RH, Južna Rodezija).');

/*
  Obcina
*/
INSERT INTO obcina (sifra, ime) VALUES (213.0, 'Ankaran');
INSERT INTO obcina (sifra, ime) VALUES (1.0, 'Ajdovščina ');
INSERT INTO obcina (sifra, ime) VALUES (195.0, 'Apače ');
INSERT INTO obcina (sifra, ime) VALUES (2.0, 'Beltinci ');
INSERT INTO obcina (sifra, ime) VALUES (148.0, 'Benedikt ');
INSERT INTO obcina (sifra, ime) VALUES (149.0, 'Bistrica ob Sotli ');
INSERT INTO obcina (sifra, ime) VALUES (3.0, 'Bled ');
INSERT INTO obcina (sifra, ime) VALUES (150.0, 'Bloke ');
INSERT INTO obcina (sifra, ime) VALUES (4.0, 'Bohinj ');
INSERT INTO obcina (sifra, ime) VALUES (5.0, 'Borovnica ');
INSERT INTO obcina (sifra, ime) VALUES (6.0, 'Bovec ');
INSERT INTO obcina (sifra, ime) VALUES (151.0, 'Braslovče ');
INSERT INTO obcina (sifra, ime) VALUES (7.0, 'Brda ');
INSERT INTO obcina (sifra, ime) VALUES (8.0, 'Brezovica ');
INSERT INTO obcina (sifra, ime) VALUES (9.0, 'Brežice ');
INSERT INTO obcina (sifra, ime) VALUES (152.0, 'Cankova ');
INSERT INTO obcina (sifra, ime) VALUES (11.0, 'Celje ');
INSERT INTO obcina (sifra, ime) VALUES (12.0, 'Cerklje na Gorenjskem ');
INSERT INTO obcina (sifra, ime) VALUES (13.0, 'Cerknica ');
INSERT INTO obcina (sifra, ime) VALUES (14.0, 'Cerkno ');
INSERT INTO obcina (sifra, ime) VALUES (153.0, 'Cerkvenjak ');
INSERT INTO obcina (sifra, ime) VALUES (196.0, 'Cirkulane ');
INSERT INTO obcina (sifra, ime) VALUES (15.0, 'Črenšovci ');
INSERT INTO obcina (sifra, ime) VALUES (16.0, 'Črna na Koroškem ');
INSERT INTO obcina (sifra, ime) VALUES (17.0, 'Črnomelj ');
INSERT INTO obcina (sifra, ime) VALUES (18.0, 'Destrnik ');
INSERT INTO obcina (sifra, ime) VALUES (19.0, 'Divača ');
INSERT INTO obcina (sifra, ime) VALUES (154.0, 'Dobje ');
INSERT INTO obcina (sifra, ime) VALUES (20.0, 'Dobrepolje ');
INSERT INTO obcina (sifra, ime) VALUES (155.0, 'Dobrna ');
INSERT INTO obcina (sifra, ime) VALUES (21.0, 'Dobrova - Polhov Gradec ');
INSERT INTO obcina (sifra, ime) VALUES (156.0, 'Dobrovnik ');
INSERT INTO obcina (sifra, ime) VALUES (22.0, 'Dol pri Ljubljani ');
INSERT INTO obcina (sifra, ime) VALUES (157.0, 'Dolenjske Toplice ');
INSERT INTO obcina (sifra, ime) VALUES (23.0, 'Domžale ');
INSERT INTO obcina (sifra, ime) VALUES (24.0, 'Dornava ');
INSERT INTO obcina (sifra, ime) VALUES (25.0, 'Dravograd ');
INSERT INTO obcina (sifra, ime) VALUES (26.0, 'Duplek ');
INSERT INTO obcina (sifra, ime) VALUES (27.0, 'Gorenja vas - Poljane ');
INSERT INTO obcina (sifra, ime) VALUES (28.0, 'Gorišnica ');
INSERT INTO obcina (sifra, ime) VALUES (207.0, 'Gorje ');
INSERT INTO obcina (sifra, ime) VALUES (29.0, 'Gornja Radgona ');
INSERT INTO obcina (sifra, ime) VALUES (30.0, 'Gornji Grad ');
INSERT INTO obcina (sifra, ime) VALUES (31.0, 'Gornji Petrovci ');
INSERT INTO obcina (sifra, ime) VALUES (158.0, 'Grad ');
INSERT INTO obcina (sifra, ime) VALUES (32.0, 'Grosuplje ');
INSERT INTO obcina (sifra, ime) VALUES (159.0, 'Hajdina ');
INSERT INTO obcina (sifra, ime) VALUES (160.0, 'Hoče - Slivnica ');
INSERT INTO obcina (sifra, ime) VALUES (161.0, 'Hodoš ');
INSERT INTO obcina (sifra, ime) VALUES (162.0, 'Horjul ');
INSERT INTO obcina (sifra, ime) VALUES (34.0, 'Hrastnik ');
INSERT INTO obcina (sifra, ime) VALUES (35.0, 'Hrpelje - Kozina ');
INSERT INTO obcina (sifra, ime) VALUES (36.0, 'Idrija ');
INSERT INTO obcina (sifra, ime) VALUES (37.0, 'Ig ');
INSERT INTO obcina (sifra, ime) VALUES (38.0, 'Ilirska Bistrica ');
INSERT INTO obcina (sifra, ime) VALUES (39.0, 'Ivančna Gorica ');
INSERT INTO obcina (sifra, ime) VALUES (40.0, 'Izola ');
INSERT INTO obcina (sifra, ime) VALUES (41.0, 'Jesenice ');
INSERT INTO obcina (sifra, ime) VALUES (163.0, 'Jezersko ');
INSERT INTO obcina (sifra, ime) VALUES (42.0, 'Juršinci ');
INSERT INTO obcina (sifra, ime) VALUES (43.0, 'Kamnik ');
INSERT INTO obcina (sifra, ime) VALUES (44.0, 'Kanal ');
INSERT INTO obcina (sifra, ime) VALUES (45.0, 'Kidričevo ');
INSERT INTO obcina (sifra, ime) VALUES (46.0, 'Kobarid ');
INSERT INTO obcina (sifra, ime) VALUES (47.0, 'Kobilje ');
INSERT INTO obcina (sifra, ime) VALUES (48.0, 'Kočevje ');
INSERT INTO obcina (sifra, ime) VALUES (49.0, 'Komen ');
INSERT INTO obcina (sifra, ime) VALUES (164.0, 'Komenda ');
INSERT INTO obcina (sifra, ime) VALUES (50.0, 'Koper ');
INSERT INTO obcina (sifra, ime) VALUES (197.0, 'Kostanjevica na Krki ');
INSERT INTO obcina (sifra, ime) VALUES (165.0, 'Kostel ');
INSERT INTO obcina (sifra, ime) VALUES (51.0, 'Kozje ');
INSERT INTO obcina (sifra, ime) VALUES (52.0, 'Kranj ');
INSERT INTO obcina (sifra, ime) VALUES (53.0, 'Kranjska Gora ');
INSERT INTO obcina (sifra, ime) VALUES (166.0, 'Križevci ');
INSERT INTO obcina (sifra, ime) VALUES (54.0, 'Krško ');
INSERT INTO obcina (sifra, ime) VALUES (55.0, 'Kungota ');
INSERT INTO obcina (sifra, ime) VALUES (56.0, 'Kuzma ');
INSERT INTO obcina (sifra, ime) VALUES (57.0, 'Laško ');
INSERT INTO obcina (sifra, ime) VALUES (58.0, 'Lenart ');
INSERT INTO obcina (sifra, ime) VALUES (59.0, 'Lendava ');
INSERT INTO obcina (sifra, ime) VALUES (60.0, 'Litija ');
INSERT INTO obcina (sifra, ime) VALUES (61.0, 'Ljubljana ');
INSERT INTO obcina (sifra, ime) VALUES (62.0, 'Ljubno ');
INSERT INTO obcina (sifra, ime) VALUES (63.0, 'Ljutomer ');
INSERT INTO obcina (sifra, ime) VALUES (208.0, 'Log - Dragomer ');
INSERT INTO obcina (sifra, ime) VALUES (64.0, 'Logatec ');
INSERT INTO obcina (sifra, ime) VALUES (65.0, 'Loška dolina ');
INSERT INTO obcina (sifra, ime) VALUES (66.0, 'Loški Potok ');
INSERT INTO obcina (sifra, ime) VALUES (167.0, 'Lovrenc na Pohorju ');
INSERT INTO obcina (sifra, ime) VALUES (67.0, 'Luče ');
INSERT INTO obcina (sifra, ime) VALUES (68.0, 'Lukovica ');
INSERT INTO obcina (sifra, ime) VALUES (69.0, 'Majšperk ');
INSERT INTO obcina (sifra, ime) VALUES (198.0, 'Makole ');
INSERT INTO obcina (sifra, ime) VALUES (70.0, 'Maribor ');
INSERT INTO obcina (sifra, ime) VALUES (168.0, 'Markovci ');
INSERT INTO obcina (sifra, ime) VALUES (71.0, 'Medvode ');
INSERT INTO obcina (sifra, ime) VALUES (72.0, 'Mengeš ');
INSERT INTO obcina (sifra, ime) VALUES (73.0, 'Metlika ');
INSERT INTO obcina (sifra, ime) VALUES (74.0, 'Mežica ');
INSERT INTO obcina (sifra, ime) VALUES (169.0, 'Miklavž na Dravskem polju');
INSERT INTO obcina (sifra, ime) VALUES (75.0, 'Miren - Kostanjevica ');
INSERT INTO obcina (sifra, ime) VALUES (212.0, 'Mirna ');
INSERT INTO obcina (sifra, ime) VALUES (170.0, 'Mirna Peč ');
INSERT INTO obcina (sifra, ime) VALUES (76.0, 'Mislinja ');
INSERT INTO obcina (sifra, ime) VALUES (199.0, 'Mokronog - Trebelno ');
INSERT INTO obcina (sifra, ime) VALUES (77.0, 'Moravče ');
INSERT INTO obcina (sifra, ime) VALUES (78.0, 'Moravske Toplice ');
INSERT INTO obcina (sifra, ime) VALUES (79.0, 'Mozirje ');
INSERT INTO obcina (sifra, ime) VALUES (80.0, 'Murska Sobota ');
INSERT INTO obcina (sifra, ime) VALUES (81.0, 'Muta ');
INSERT INTO obcina (sifra, ime) VALUES (82.0, 'Naklo ');
INSERT INTO obcina (sifra, ime) VALUES (83.0, 'Nazarje ');
INSERT INTO obcina (sifra, ime) VALUES (84.0, 'Nova Gorica ');
INSERT INTO obcina (sifra, ime) VALUES (85.0, 'Novo mesto ');
INSERT INTO obcina (sifra, ime) VALUES (86.0, 'Odranci ');
INSERT INTO obcina (sifra, ime) VALUES (171.0, 'Oplotnica ');
INSERT INTO obcina (sifra, ime) VALUES (87.0, 'Ormož ');
INSERT INTO obcina (sifra, ime) VALUES (88.0, 'Osilnica ');
INSERT INTO obcina (sifra, ime) VALUES (89.0, 'Pesnica ');
INSERT INTO obcina (sifra, ime) VALUES (90.0, 'Piran ');
INSERT INTO obcina (sifra, ime) VALUES (91.0, 'Pivka ');
INSERT INTO obcina (sifra, ime) VALUES (92.0, 'Podčetrtek ');
INSERT INTO obcina (sifra, ime) VALUES (172.0, 'Podlehnik ');
INSERT INTO obcina (sifra, ime) VALUES (93.0, 'Podvelka ');
INSERT INTO obcina (sifra, ime) VALUES (200.0, 'Poljčane ');
INSERT INTO obcina (sifra, ime) VALUES (173.0, 'Polzela ');
INSERT INTO obcina (sifra, ime) VALUES (94.0, 'Postojna ');
INSERT INTO obcina (sifra, ime) VALUES (174.0, 'Prebold ');
INSERT INTO obcina (sifra, ime) VALUES (95.0, 'Preddvor ');
INSERT INTO obcina (sifra, ime) VALUES (175.0, 'Prevalje ');
INSERT INTO obcina (sifra, ime) VALUES (96.0, 'Ptuj ');
INSERT INTO obcina (sifra, ime) VALUES (97.0, 'Puconci ');
INSERT INTO obcina (sifra, ime) VALUES (98.0, 'Rače - Fram ');
INSERT INTO obcina (sifra, ime) VALUES (99.0, 'Radeče ');
INSERT INTO obcina (sifra, ime) VALUES (100.0, 'Radenci ');
INSERT INTO obcina (sifra, ime) VALUES (101.0, 'Radlje ob Dravi ');
INSERT INTO obcina (sifra, ime) VALUES (102.0, 'Radovljica ');
INSERT INTO obcina (sifra, ime) VALUES (103.0, 'Ravne na Koroškem ');
INSERT INTO obcina (sifra, ime) VALUES (176.0, 'Razkrižje ');
INSERT INTO obcina (sifra, ime) VALUES (209.0, 'Rečica ob Savinji ');
INSERT INTO obcina (sifra, ime) VALUES (201.0, 'Renče - Vogrsko ');
INSERT INTO obcina (sifra, ime) VALUES (104.0, 'Ribnica ');
INSERT INTO obcina (sifra, ime) VALUES (177.0, 'Ribnica na Pohorju ');
INSERT INTO obcina (sifra, ime) VALUES (106.0, 'Rogaška Slatina ');
INSERT INTO obcina (sifra, ime) VALUES (105.0, 'Rogašovci ');
INSERT INTO obcina (sifra, ime) VALUES (107.0, 'Rogatec ');
INSERT INTO obcina (sifra, ime) VALUES (108.0, 'Ruše ');
INSERT INTO obcina (sifra, ime) VALUES (178.0, 'Selnica ob Dravi ');
INSERT INTO obcina (sifra, ime) VALUES (109.0, 'Semič ');
INSERT INTO obcina (sifra, ime) VALUES (110.0, 'Sevnica ');
INSERT INTO obcina (sifra, ime) VALUES (111.0, 'Sežana ');
INSERT INTO obcina (sifra, ime) VALUES (112.0, 'Slovenj Gradec ');
INSERT INTO obcina (sifra, ime) VALUES (113.0, 'Slovenska Bistrica ');
INSERT INTO obcina (sifra, ime) VALUES (114.0, 'Slovenske Konjice ');
INSERT INTO obcina (sifra, ime) VALUES (179.0, 'Sodražica ');
INSERT INTO obcina (sifra, ime) VALUES (180.0, 'Solčava ');
INSERT INTO obcina (sifra, ime) VALUES (202.0, 'Središče ob Dravi ');
INSERT INTO obcina (sifra, ime) VALUES (115.0, 'Starše ');
INSERT INTO obcina (sifra, ime) VALUES (203.0, 'Straža ');
INSERT INTO obcina (sifra, ime) VALUES (204.0, 'Sv. Trojica v Slov. Goricah ');
INSERT INTO obcina (sifra, ime) VALUES (181.0, 'Sveta Ana ');
INSERT INTO obcina (sifra, ime) VALUES (182.0, 'Sveti Andraž v Slov. Goricah ');
INSERT INTO obcina (sifra, ime) VALUES (116.0, 'Sveti Jurij ob Ščavnici ');
INSERT INTO obcina (sifra, ime) VALUES (210.0, 'Sveti Jurij v Slov. Goricah ');
INSERT INTO obcina (sifra, ime) VALUES (205.0, 'Sveti Tomaž ');
INSERT INTO obcina (sifra, ime) VALUES (33.0, 'Šalovci ');
INSERT INTO obcina (sifra, ime) VALUES (183.0, 'Šempeter - Vrtojba ');
INSERT INTO obcina (sifra, ime) VALUES (117.0, 'Šenčur ');
INSERT INTO obcina (sifra, ime) VALUES (118.0, 'Šentilj ');
INSERT INTO obcina (sifra, ime) VALUES (119.0, 'Šentjernej ');
INSERT INTO obcina (sifra, ime) VALUES (120.0, 'Šentjur pri Celju ');
INSERT INTO obcina (sifra, ime) VALUES (211.0, 'Šentrupert ');
INSERT INTO obcina (sifra, ime) VALUES (121.0, 'Škocjan ');
INSERT INTO obcina (sifra, ime) VALUES (122.0, 'Škofja Loka ');
INSERT INTO obcina (sifra, ime) VALUES (123.0, 'Škofljica ');
INSERT INTO obcina (sifra, ime) VALUES (124.0, 'Šmarje pri Jelšah ');
INSERT INTO obcina (sifra, ime) VALUES (206.0, 'Šmarješke Toplice ');
INSERT INTO obcina (sifra, ime) VALUES (125.0, 'Šmartno ob Paki ');
INSERT INTO obcina (sifra, ime) VALUES (194.0, 'Šmartno pri Litiji ');
INSERT INTO obcina (sifra, ime) VALUES (126.0, 'Šoštanj ');
INSERT INTO obcina (sifra, ime) VALUES (127.0, 'Štore ');
INSERT INTO obcina (sifra, ime) VALUES (184.0, 'Tabor ');
INSERT INTO obcina (sifra, ime) VALUES (10.0, 'Tišina ');
INSERT INTO obcina (sifra, ime) VALUES (128.0, 'Tolmin ');
INSERT INTO obcina (sifra, ime) VALUES (129.0, 'Trbovlje ');
INSERT INTO obcina (sifra, ime) VALUES (130.0, 'Trebnje ');
INSERT INTO obcina (sifra, ime) VALUES (185.0, 'Trnovska vas ');
INSERT INTO obcina (sifra, ime) VALUES (186.0, 'Trzin ');
INSERT INTO obcina (sifra, ime) VALUES (131.0, 'Tržič ');
INSERT INTO obcina (sifra, ime) VALUES (132.0, 'Turnišče ');
INSERT INTO obcina (sifra, ime) VALUES (133.0, 'Velenje ');
INSERT INTO obcina (sifra, ime) VALUES (187.0, 'Velika Polana ');
INSERT INTO obcina (sifra, ime) VALUES (134.0, 'Velike Lašče ');
INSERT INTO obcina (sifra, ime) VALUES (188.0, 'Veržej ');
INSERT INTO obcina (sifra, ime) VALUES (135.0, 'Videm ');
INSERT INTO obcina (sifra, ime) VALUES (136.0, 'Vipava ');
INSERT INTO obcina (sifra, ime) VALUES (137.0, 'Vitanje ');
INSERT INTO obcina (sifra, ime) VALUES (138.0, 'Vodice ');
INSERT INTO obcina (sifra, ime) VALUES (139.0, 'Vojnik ');
INSERT INTO obcina (sifra, ime) VALUES (189.0, 'Vransko ');
INSERT INTO obcina (sifra, ime) VALUES (140.0, 'Vrhnika ');
INSERT INTO obcina (sifra, ime) VALUES (141.0, 'Vuzenica ');
INSERT INTO obcina (sifra, ime) VALUES (142.0, 'Zagorje ob Savi ');
INSERT INTO obcina (sifra, ime) VALUES (143.0, 'Zavrč ');
INSERT INTO obcina (sifra, ime) VALUES (144.0, 'Zreče ');
INSERT INTO obcina (sifra, ime) VALUES (190.0, 'Žalec ');
INSERT INTO obcina (sifra, ime) VALUES (146.0, 'Železniki ');
INSERT INTO obcina (sifra, ime) VALUES (191.0, 'Žetale ');
INSERT INTO obcina (sifra, ime) VALUES (147.0, 'Žiri ');
INSERT INTO obcina (sifra, ime) VALUES (192.0, 'Žirovnica ');
INSERT INTO obcina (sifra, ime) VALUES (193.0, 'Žužemberk ');

/*
  Posta
*/
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8341, 'Adlešiči');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5270, 'Ajdovščina');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (6280, 'Ankaran/Ancarano');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (9253, 'Apače');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8253, 'Artiče');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4275, 'Begunje na Gorenjskem');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1382, 'Begunje pri Cerknici');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (9231, 'Beltinci');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2234, 'Benedikt');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2345, 'Bistrica ob Dravi');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3256, 'Bistrica ob Sotli');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8259, 'Bizeljsko');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1223, 'Blagovica');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8283, 'Blanca');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4260, 'Bled');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4273, 'Blejska Dobrava');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (9265, 'Bodonci');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (9222, 'Bogojina');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4263, 'Bohinjska Bela');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4264, 'Bohinjska Bistrica');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4265, 'Bohinjsko jezero');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1353, 'Borovnica');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8294, 'Boštanj');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5230, 'Bovec');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5295, 'Branik');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3314, 'Braslovče');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5223, 'Breginj');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8280, 'Brestanica');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2354, 'Bresternica');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4243, 'Brezje');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1351, 'Brezovica pri Ljubljani');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8250, 'Brežice');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4210, 'Brnik - Aerodrom');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8321, 'Brusnice');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3255, 'Buče');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8276, 'Bučka ');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (9261, 'Cankova');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3000, 'Celje ');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3001, 'Celje - poštni predali');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4207, 'Cerklje na Gorenjskem');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8263, 'Cerklje ob Krki');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1380, 'Cerknica');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5282, 'Cerkno');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2236, 'Cerkvenjak');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2215, 'Ceršak');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2326, 'Cirkovce');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2282, 'Cirkulane');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5273, 'Col');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8251, 'Čatež ob Savi');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1413, 'Čemšenik');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5253, 'Čepovan');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (9232, 'Črenšovci');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2393, 'Črna na Koroškem');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (6275, 'Črni Kal');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5274, 'Črni Vrh nad Idrijo');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5262, 'Črniče');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8340, 'Črnomelj');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (6271, 'Dekani');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5210, 'Deskle');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2253, 'Destrnik');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (6215, 'Divača');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1233, 'Dob');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3224, 'Dobje pri Planini');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8257, 'Dobova');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1423, 'Dobovec');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5263, 'Dobravlje');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3204, 'Dobrna');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8211, 'Dobrnič');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1356, 'Dobrova');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (9223, 'Dobrovnik/Dobronak ');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5212, 'Dobrovo v Brdih');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1431, 'Dol pri Hrastniku');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1262, 'Dol pri Ljubljani');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1273, 'Dole pri Litiji');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1331, 'Dolenja vas');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8350, 'Dolenjske Toplice');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1230, 'Domžale');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2252, 'Dornava');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5294, 'Dornberk');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1319, 'Draga');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8343, 'Dragatuš');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3222, 'Dramlje');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2370, 'Dravograd');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4203, 'Duplje');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (6221, 'Dutovlje');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8361, 'Dvor');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2343, 'Fala');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (9208, 'Fokovci');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2313, 'Fram');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3213, 'Frankolovo');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1274, 'Gabrovka');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8254, 'Globoko');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5275, 'Godovič');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4204, 'Golnik');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3303, 'Gomilsko');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4224, 'Gorenja vas');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3263, 'Gorica pri Slivnici');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2272, 'Gorišnica');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (9250, 'Gornja Radgona');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3342, 'Gornji Grad');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4282, 'Gozd Martuljek');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (6272, 'Gračišče');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (9264, 'Grad');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8332, 'Gradac');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1384, 'Grahovo');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5242, 'Grahovo ob Bači');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5251, 'Grgar');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3302, 'Griže');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3231, 'Grobelno');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1290, 'Grosuplje');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2288, 'Hajdina');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8362, 'Hinje');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2311, 'Hoče');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (9205, 'Hodoš/Hodos');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1354, 'Horjul');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1372, 'Hotedršica');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1430, 'Hrastnik');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (6225, 'Hruševje');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4276, 'Hrušica');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5280, 'Idrija');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1292, 'Ig');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (6250, 'Ilirska Bistrica');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (6251, 'Ilirska Bistrica-Trnovo');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1295, 'Ivančna Gorica');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2259, 'Ivanjkovci');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1411, 'Izlake');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (6310, 'Izola/Isola');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2222, 'Jakobski Dol');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2221, 'Jarenina');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (6254, 'Jelšane');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4270, 'Jesenice');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8261, 'Jesenice na Dolenjskem');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3273, 'Jurklošter');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2223, 'Jurovski Dol');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2256, 'Juršinci');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5214, 'Kal nad Kanalom');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3233, 'Kalobje');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4246, 'Kamna Gorica');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2351, 'Kamnica');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1241, 'Kamnik');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5213, 'Kanal');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8258, 'Kapele');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2362, 'Kapla');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2325, 'Kidričevo');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1412, 'Kisovec');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (6253, 'Knežak');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5222, 'Kobarid');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (9227, 'Kobilje');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1330, 'Kočevje');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1338, 'Kočevska Reka');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2276, 'Kog');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5211, 'Kojsko');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (6223, 'Komen');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1218, 'Komenda');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (6000, 'Koper/Capodistria ');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (6001, 'Koper/Capodistria - poštni predali');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8282, 'Koprivnica');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5296, 'Kostanjevica na Krasu');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8311, 'Kostanjevica na Krki');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1336, 'Kostel');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (6256, 'Košana');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2394, 'Kotlje');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (6240, 'Kozina');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3260, 'Kozje');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4000, 'Kranj ');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4001, 'Kranj - poštni predali');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4280, 'Kranjska Gora');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1281, 'Kresnice');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4294, 'Križe');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (9206, 'Križevci');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (9242, 'Križevci pri Ljutomeru');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1301, 'Krka');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8296, 'Krmelj');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4245, 'Kropa');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8262, 'Krška vas');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8270, 'Krško');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (9263, 'Kuzma');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2318, 'Laporje');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3270, 'Laško');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1219, 'Laze v Tuhinju');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2230, 'Lenart v Slovenskih goricah');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (9220, 'Lendava/Lendva');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4248, 'Lesce');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3261, 'Lesično');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8273, 'Leskovec pri Krškem');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2372, 'Libeliče');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2341, 'Limbuš');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1270, 'Litija');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3202, 'Ljubečna');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1000, 'Ljubljana ');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1001, 'Ljubljana - poštni predali');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1231, 'Ljubljana - Črnuče');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1261, 'Ljubljana - Dobrunje');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1260, 'Ljubljana - Polje');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1210, 'Ljubljana - Šentvid');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1211, 'Ljubljana - Šmartno');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3333, 'Ljubno ob Savinji');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (9240, 'Ljutomer');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3215, 'Loče');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5231, 'Log pod Mangartom');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1358, 'Log pri Brezovici');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1370, 'Logatec');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1371, 'Logatec');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1434, 'Loka pri Zidanem Mostu');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3223, 'Loka pri Žusmu');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (6219, 'Lokev');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1318, 'Loški Potok');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2324, 'Lovrenc na Dravskem polju');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2344, 'Lovrenc na Pohorju');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3334, 'Luče');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1225, 'Lukovica');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (9202, 'Mačkovci');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2322, 'Majšperk');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2321, 'Makole');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (9243, 'Mala Nedelja');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2229, 'Malečnik');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (6273, 'Marezige');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2000, 'Maribor ');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2001, 'Maribor - poštni predali');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2206, 'Marjeta na Dravskem polju');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2281, 'Markovci');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (9221, 'Martjanci');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (6242, 'Materija');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4211, 'Mavčiče');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1215, 'Medvode');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1234, 'Mengeš');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8330, 'Metlika');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2392, 'Mežica');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2204, 'Miklavž na Dravskem polju');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2275, 'Miklavž pri Ormožu');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5291, 'Miren');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8233, 'Mirna');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8216, 'Mirna Peč');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2382, 'Mislinja');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4281, 'Mojstrana');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8230, 'Mokronog');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1251, 'Moravče');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (9226, 'Moravske Toplice');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5216, 'Most na Soči');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1221, 'Motnik');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3330, 'Mozirje');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (9000, 'Murska Sobota ');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (9001, 'Murska Sobota - poštni predali');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2366, 'Muta');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4202, 'Naklo');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3331, 'Nazarje');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1357, 'Notranje Gorice');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3203, 'Nova Cerkev');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5000, 'Nova Gorica ');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5001, 'Nova Gorica - poštni predali');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1385, 'Nova vas');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8000, 'Novo mesto');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8001, 'Novo mesto - poštni predali');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (6243, 'Obrov');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (9233, 'Odranci');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2317, 'Oplotnica');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2312, 'Orehova vas');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2270, 'Ormož');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1316, 'Ortnek');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1337, 'Osilnica');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8222, 'Otočec');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2361, 'Ožbalt');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2231, 'Pernica');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2211, 'Pesnica pri Mariboru');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (9203, 'Petrovci');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3301, 'Petrovče');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (6330, 'Piran/Pirano');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8255, 'Pišece');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (6257, 'Pivka');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (6232, 'Planina');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3225, 'Planina pri Sevnici');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (6276, 'Pobegi');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8312, 'Podbočje');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5243, 'Podbrdo');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3254, 'Podčetrtek');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2273, 'Podgorci');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (6216, 'Podgorje');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2381, 'Podgorje pri Slovenj Gradcu');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (6244, 'Podgrad');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1414, 'Podkum');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2286, 'Podlehnik');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5272, 'Podnanos');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4244, 'Podnart');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3241, 'Podplat');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3257, 'Podsreda');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2363, 'Podvelka');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2208, 'Pohorje');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2257, 'Polenšak');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1355, 'Polhov Gradec');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4223, 'Poljane nad Škofjo Loko');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2319, 'Poljčane');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1272, 'Polšnik');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3313, 'Polzela');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3232, 'Ponikva');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (6320, 'Portorož/Portorose');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (6230, 'Postojna');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2331, 'Pragersko');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3312, 'Prebold');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4205, 'Preddvor');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (6255, 'Prem');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1352, 'Preserje');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (6258, 'Prestranek');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2391, 'Prevalje');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3262, 'Prevorje');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1276, 'Primskovo ');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3253, 'Pristava pri Mestinju');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (9207, 'Prosenjakovci/Partosfalva');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5297, 'Prvačina');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2250, 'Ptuj');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2323, 'Ptujska Gora');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (9201, 'Puconci');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2327, 'Rače');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1433, 'Radeče');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (9252, 'Radenci');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2360, 'Radlje ob Dravi');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1235, 'Radomlje');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4240, 'Radovljica');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8274, 'Raka');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1381, 'Rakek');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4283, 'Rateče - Planica');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2390, 'Ravne na Koroškem');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (9246, 'Razkrižje');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3332, 'Rečica ob Savinji');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5292, 'Renče');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1310, 'Ribnica');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2364, 'Ribnica na Pohorju');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3272, 'Rimske Toplice');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1314, 'Rob');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5215, 'Ročinj');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3250, 'Rogaška Slatina');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (9262, 'Rogašovci');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3252, 'Rogatec');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1373, 'Rovte');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2342, 'Ruše');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1282, 'Sava');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (6333, 'Sečovlje/Sicciole');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4227, 'Selca');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2352, 'Selnica ob Dravi');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8333, 'Semič');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8281, 'Senovo');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (6224, 'Senožeče');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8290, 'Sevnica');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (6210, 'Sežana');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2214, 'Sladki Vrh');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5283, 'Slap ob Idrijci');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2380, 'Slovenj Gradec');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2310, 'Slovenska Bistrica');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3210, 'Slovenske Konjice');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1216, 'Smlednik');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5232, 'Soča');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1317, 'Sodražica');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3335, 'Solčava');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5250, 'Solkan');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4229, 'Sorica');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4225, 'Sovodenj');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5281, 'Spodnja Idrija');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2241, 'Spodnji Duplek');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (9245, 'Spodnji Ivanjci');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2277, 'Središče ob Dravi');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4267, 'Srednja vas v Bohinju');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8256, 'Sromlje ');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5224, 'Srpenica');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1242, 'Stahovica');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1332, 'Stara Cerkev');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8342, 'Stari trg ob Kolpi');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1386, 'Stari trg pri Ložu');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2205, 'Starše');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2289, 'Stoperce');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8322, 'Stopiče');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3206, 'Stranice');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8351, 'Straža');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1313, 'Struge');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8293, 'Studenec');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8331, 'Suhor');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2233, 'Sv. Ana v Slovenskih goricah');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2235, 'Sv. Trojica v Slovenskih goricah');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2353, 'Sveti Duh na Ostrem Vrhu');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (9244, 'Sveti Jurij ob Ščavnici');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3264, 'Sveti Štefan');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2258, 'Sveti Tomaž');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (9204, 'Šalovci');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5261, 'Šempas');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5290, 'Šempeter pri Gorici');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3311, 'Šempeter v Savinjski dolini');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4208, 'Šenčur');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2212, 'Šentilj v Slovenskih goricah');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8297, 'Šentjanž');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2373, 'Šentjanž pri Dravogradu');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8310, 'Šentjernej');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3230, 'Šentjur');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3271, 'Šentrupert');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8232, 'Šentrupert');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1296, 'Šentvid pri Stični');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8275, 'Škocjan');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (6281, 'Škofije');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4220, 'Škofja Loka');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3211, 'Škofja vas');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1291, 'Škofljica');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (6274, 'Šmarje');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1293, 'Šmarje - Sap');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3240, 'Šmarje pri Jelšah');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8220, 'Šmarješke Toplice');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2315, 'Šmartno na Pohorju');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3341, 'Šmartno ob Dreti');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3327, 'Šmartno ob Paki');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1275, 'Šmartno pri Litiji');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2383, 'Šmartno pri Slovenj Gradcu');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3201, 'Šmartno v Rožni dolini');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3325, 'Šoštanj');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (6222, 'Štanjel');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3220, 'Štore');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3304, 'Tabor');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3221, 'Teharje');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (9251, 'Tišina');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5220, 'Tolmin');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3326, 'Topolšica');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2371, 'Trbonje');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1420, 'Trbovlje');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8231, 'Trebelno ');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8210, 'Trebnje');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5252, 'Trnovo pri Gorici');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2254, 'Trnovska vas');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1222, 'Trojane');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1236, 'Trzin');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4290, 'Tržič');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8295, 'Tržišče');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1311, 'Turjak');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (9224, 'Turnišče');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8323, 'Uršna sela');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1252, 'Vače');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3320, 'Velenje ');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3322, 'Velenje - poštni predali');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8212, 'Velika Loka');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2274, 'Velika Nedelja');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (9225, 'Velika Polana');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1315, 'Velike Lašče');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8213, 'Veliki Gaber');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (9241, 'Veržej');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1312, 'Videm - Dobrepolje');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2284, 'Videm pri Ptuju');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8344, 'Vinica');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5271, 'Vipava');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4212, 'Visoko');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1294, 'Višnja Gora');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3205, 'Vitanje');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2255, 'Vitomarci');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1217, 'Vodice');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3212, 'Vojnik');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (5293, 'Volčja Draga');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2232, 'Voličina');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3305, 'Vransko');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (6217, 'Vremski Britof');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1360, 'Vrhnika');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2365, 'Vuhred');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2367, 'Vuzenica');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8292, 'Zabukovje ');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1410, 'Zagorje ob Savi');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1303, 'Zagradec');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2283, 'Zavrč');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8272, 'Zdole ');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4201, 'Zgornja Besnica');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2242, 'Zgornja Korena');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2201, 'Zgornja Kungota');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2316, 'Zgornja Ložnica');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2314, 'Zgornja Polskava');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2213, 'Zgornja Velka');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4247, 'Zgornje Gorje');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4206, 'Zgornje Jezersko');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2285, 'Zgornji Leskovec');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (1432, 'Zidani Most');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3214, 'Zreče');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4209, 'Žabnica');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (3310, 'Žalec');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4228, 'Železniki');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (2287, 'Žetale');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4226, 'Žiri');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (4274, 'Žirovnica');
INSERT INTO posta (postna_stevilka, naziv_poste) VALUES (8360, 'Žužemberk');


/*
Prazniki
 */
INSERT INTO praznik (datum, ime)
VALUES

  /* dela prosti dnevi */
  ('1900-01-01', 'Novo leto'),
  ('1900-01-02', 'Novo leto'),
  ('1900-02-08', 'Prešernov dan'),
  ('1900-04-27', 'dan upora proti okupatorju'),
  ('1900-05-01', 'praznik dela'),
  ('1900-05-02', 'praznik dela'),
  ('1900-06-25', 'dan državnosti'),
  ('1900-08-15', 'Marijino vnebovzetje'),
  ('1900-10-31', 'dan reformacije'),
  ('1900-11-01', 'dan spomina na mrtve'),
  ('1900-12-25', 'božič'),
  ('1900-12-26', 'dan samostojnosti in enotnosti'),

  /* premakljivi */
  ('2019-04-22', 'velikonočni ponedeljek'),
  ('2020-04-13', 'velikonočni ponedeljek'),
  ('2021-04-05', 'velikonočni ponedeljek'),
  ('2022-04-18', 'velikonočni ponedeljek');


/**
Sifrant PREDMET
 */

INSERT INTO predmet (sifra, naziv, semester, ects) VALUES

  /*
  BUN-RI
   */
  (63277, 'Programiranje 1', 0, 6),
  (63202, 'Osnove matematične analize', 0, 6),
  (63203, 'Diskretne strukture', 0, 6),
  (63204, 'Osnove digitalnih vezij', 0, 6),
  (63205, 'Fizika', 0, 6),
  (63278, 'Programiranje 2', 1, 6),
  (63207, 'Linearna algebra', 1, 6),
  (63212, 'Arhitektura računalniških sistemov', 1, 6),
  (63209, 'Računalniške komunikacije', 1, 6),
  (63215, 'Osnove informacijskih sistemov', 1, 6),

  (63279, 'Algoritmi in podatkovne strukture 1', 0, 6),
  (63208, 'Osnove podatkovnih baz', 0, 6),
  (63213, 'Verjetnost in statistika', 0, 6),
  (63218, 'Organizacija računalniških sistemov', 0, 6),
  (63283, 'Izračunljivost in računska zahtevnost', 0, 6),
  (63216, 'Teorija informacij in sistemov', 1, 6),
  (63280, 'Algoritmi in podatkovne strukture 2', 1, 6),
  (63217, 'Operacijski sistemi', 1, 6),

  (63214, 'Osnove umetne inteligence', 0, 6),
  (63256, 'Tehnologije programske opreme', 1, 6),
  (63281, 'Diplomski seminar', 1, 6),

  (63219, 'Matematično modeliranje', 1, 6),
  (63220, 'Principi programskih jezikov', 1, 6),
  (63221, 'Računalniške tehnologije', 1, 6),

  (63249, 'Elektronsko poslovanje', 0, 6),
  (63250, 'Organizacija in management', 1, 6),
  (63251, 'Uvod v odkrivanje znanj iz podatkov', 0, 6),

  (63252, 'Razvoj informacijskih sistemov', 0, 6),
  (63226, 'Tehnologija upravljanja podatkov', 0, 6),
  (63253, 'Planiranje in upravljanje informatike', 1, 6),

  (63254, 'Postopki razvoja programske opreme', 0, 6),
  (63255, 'Spletno programiranje', 0, 6),
  (63287, 'Programiranje specifičnih platform', 1, 6),

  (63257, 'Modeliranje računalniških omrežij', 0, 6),
  (63258, 'Komunikacijski protokoli', 0, 6),
  (63259, 'Brezžična in mobilna omrežja', 1, 6),

  (63260, 'Digitalno načrtovanje', 0, 6),
  (63261, 'Porazdeljeni sistemi', 0, 6),
  (63262, 'Zanesljivost in zmogljivost računalniških sistemov', 1, 6),

  (63263, 'Analiza algoritmov in hevristično reševanje problemov', 0, 6),
  (63264, 'Sistemska programska oprema', 0, 6),
  (63265, 'Prevajalniki', 1, 6),

  (63266, 'Inteligentni sistemi', 0, 6),
  (63267, 'Umetno zaznavanje', 0, 6),
  (63268, 'Razvoj inteligentnih sistemov', 1, 6),

  (63269, 'Računalniška grafika in tehnologija iger', 0, 6),
  (63270, 'Multimedijski sistemi', 0, 6),
  (63271, 'Osnove oblikovanja', 1, 6),

  (63222, 'Angleški jezik nivo A', 1, 3),
  (63223, 'Angleški jezik nivo B', 0, 3),
  (63224, 'Angleški jezik nivo C', 1, 3),
  (63225, 'Izbrana poglavja iz računalništva in informatike', 0, 6),
  (63241, 'Računalništvo v praksi 1', 0, 3),
  (63242, 'Računalništvo v praksi 2', 1, 3),
  (63284, 'Tehnične veščine', 0, 3),
  (63248, 'Ekonomika in podjetništvo', 1, 6),


  (63701, 'Uvod v računalništvo', 0, 6),
  (63702, 'Programiranje 1', 0, 6),
  (63703, 'Računalniška arhitektura', 0, 6),
  (63704, 'Matematika', 0, 6),
  (63705, 'Diskretne strukture', 0, 6),
  (63706, 'Programiranje 2', 1, 6),
  (63707, 'Podatkovne baze', 1, 6),
  (63708, 'Računalniške komunikacije', 1, 6),
  (63709, 'Operacijski sistemi', 1, 6),
  (63710, 'Osnove verjetnosti in statistike', 1, 6),

  (63711, 'Algoritmi in podatkovne strukture 1', 0, 6),
  (63712, 'Elektronsko in mobilno poslovanje', 0, 6),
  (63713, 'Podatkovne baze 2', 0, 6),
  (63714, 'Informacijski sistemi', 0, 6),
  (63715, 'Grafično oblikovanje', 0, 6),
  (63716, 'Komunikacijsko protokoli in omrežna varnost', 0, 6),
  (63717, 'Organizacija računalnikov', 0, 6),
  (63718, 'Digitalna vezja', 0, 6),
  (63719, 'Računalniška grafika', 0, 6),
  (63720, 'Umetna inteligenca', 0, 6),
  (63721, 'Uporabniški vmesniki', 1, 6),
  (63722, ' Prevajalniki in navidezni stroji', 1, 6),
  (63723, 'Algoritmi in podatkovne strukture 2', 1, 6),
  (63724, 'Testiranje in kakovost', 1, 6),
  (63725, 'Razvoj informacijskih sistemov', 1, 6),
  (63726, 'Produkcija multimedijskih gradiv', 1, 6),
  (63744, 'Digitalno procesiranje signalov', 0, 6),
  (63727, 'Spletne tehnologije', 1, 6),
  (63728, 'Vhodno-izhodne naprave', 1, 6),
  (63729, 'Načrtovanje digitalnih naprav', 1, 6),
  (63765, 'Podatkovno rudarjenje', 1, 6),
  (63769, 'Programski jezik C', 0, 6),
  (63767, 'Tehnične veščine', 0, 3),
  (63766, 'Tehnične veščine 2', 1, 3),
  (63749, 'Izbrana poglavja iz računalništva in informatike', 0, 6),
  (63732, 'Tehnologija programske opreme', 0, 6),
  (63770, 'Diplomski seminar', 1, 6);

INSERT INTO del_predmetnika (sifra, tip) VALUES
  (1, 'obvezni predmet'),
  (2, 'strokovni izbirni predmet'),
  (3, 'splošni izbirni predmet'),
  (4, 'modulski predmet');

INSERT INTO predmetnik (del_predmetnika, letnik, predmet, studijski_program, studijsko_leto) VALUES
  /*
  PRVI LETNIK BUN-RI
  OBVEZNI
   */
  (1, 1, 63202, 1000468, 2018),
  (1, 1, 63277, 1000468, 2018),
  (1, 1, 63203, 1000468, 2018),
  (1, 1, 63204, 1000468, 2018),
  (1, 1, 63205, 1000468, 2018),
  (1, 1, 63278, 1000468, 2018),
  (1, 1, 63207, 1000468, 2018),
  (1, 1, 63212, 1000468, 2018),
  (1, 1, 63209, 1000468, 2018),
  (1, 1, 63215, 1000468, 2018),
  /*
  DRUGI LETNIK BUN-RI
  OBVEZNI
   */
  (1, 2, 63279, 1000468, 2018),
  (1, 2, 63208, 1000468, 2018),
  (1, 2, 63213, 1000468, 2018),
  (1, 2, 63218, 1000468, 2018),
  (1, 2, 63283, 1000468, 2018),
  (1, 2, 63216, 1000468, 2018),
  (1, 2, 63280, 1000468, 2018),
  (1, 2, 63217, 1000468, 2018),
  /*
  STROKOVNI
   */
  (2, 2, 63219, 1000468, 2018),
  (2, 2, 63220, 1000468, 2018),
  (2, 2, 63221, 1000468, 2018),
  /*
  SPLOŠNI
   */
  (3, 2, 63222, 1000468, 2018),
  (3, 2, 63223, 1000468, 2018),
  (3, 2, 63224, 1000468, 2018),
  (3, 2, 63225, 1000468, 2018),
  (3, 2, 63241, 1000468, 2018),
  (3, 2, 63242, 1000468, 2018),
  (3, 2, 63284, 1000468, 2018),
  (3, 2, 63248, 1000468, 2018),
  /*
  TRETJI LETNIK BUN-RI
   */
  (1, 3, 63214, 1000468, 2018),
  (1, 3, 63256, 1000468, 2018),
  (1, 3, 63281, 1000468, 2018),
  /*
  SPLOŠNI
   */
  (3, 3, 63222, 1000468, 2018),
  (3, 3, 63223, 1000468, 2018),
  (3, 3, 63224, 1000468, 2018),
  (3, 3, 63225, 1000468, 2018),
  (3, 3, 63241, 1000468, 2018),
  (3, 3, 63242, 1000468, 2018),
  (3, 3, 63284, 1000468, 2018),
  (3, 3, 63248, 1000468, 2018),

  (3, 3, 63219, 1000468, 2018),
  (3, 3, 63220, 1000468, 2018),
  (3, 3, 63221, 1000468, 2018),
  /*
  PRVI LETNIK VS-RI
   */
  (1, 1, 63701, 1000470, 2018),
  (1, 1, 63702, 1000470, 2018),
  (1, 1, 63703, 1000470, 2018),
  (1, 1, 63704, 1000470, 2018),
  (1, 1, 63705, 1000470, 2018),
  (1, 1, 63706, 1000470, 2018),
  (1, 1, 63707, 1000470, 2018),
  (1, 1, 63708, 1000470, 2018),
  (1, 1, 63709, 1000470, 2018),
  (1, 1, 63710, 1000470, 2018);

INSERT INTO predmetnik (del_predmetnika, letnik, predmet, studijski_program, studijsko_leto, modul) VALUES
  /*
  MODULI
   */
  /*
  Informacijski sistemi
   */
  (4, 3, 63249, 1000468, 2018, 0),
  (4, 3, 63250, 1000468, 2018, 0),
  (4, 3, 63251, 1000468, 2018, 0),
  /*
  Obvladovanje informatike
   */
  (4, 3, 63252, 1000468, 2018, 1),
  (4, 3, 63226, 1000468, 2018, 1),
  (4, 3, 63253, 1000468, 2018, 1),
  /*
  Razvoj programske opreme
   */
  (4, 3, 63254, 1000468, 2018, 2),
  (4, 3, 63255, 1000468, 2018, 2),
  (4, 3, 63287, 1000468, 2018, 2),
  /*
  Računalniška omrežja
   */
  (4, 3, 63257, 1000468, 2018, 3),
  (4, 3, 63258, 1000468, 2018, 3),
  (4, 3, 63259, 1000468, 2018, 3),
  /*
  Računalniški sistemi
   */
  (4, 3, 63260, 1000468, 2018, 4),
  (4, 3, 63261, 1000468, 2018, 4),
  (4, 3, 63262, 1000468, 2018, 4),
  /*
  Algoritmi in sistemski programi
   */
  (4, 3, 63263, 1000468, 2018, 5),
  (4, 3, 63264, 1000468, 2018, 5),
  (4, 3, 63265, 1000468, 2018, 5),
  /*
  Umetna inteligenca
   */
  (4, 3, 63266, 1000468, 2018, 6),
  (4, 3, 63267, 1000468, 2018, 6),
  (4, 3, 63268, 1000468, 2018, 6),
  /*
  Medijske tehnologije
   */
  (4, 3, 63269, 1000468, 2018, 7),
  (4, 3, 63270, 1000468, 2018, 7),
  (4, 3, 63271, 1000468, 2018, 7);


/*
  Uporabniki
*/
/*
  Uporabniki
*/
INSERT INTO `uporabnik` (`id_uporabnik`, `tip`, `davcna_stevilka`, `email`, `emso`, `geslo`, `ime`, `priimek`, `spremenjeno`, `uporabnisko_ime`, `ustvarjeno`, `zadnja_prijava`, `drzavljanstvo`) VALUES
  (1, 'Skrbnik', NULL, 'admin@fri.uni-lj.si', NULL, '$31$16$8_suS4gpyEmol2ldJyOIwOMU9es7_Nqsn83n9zepQeY', 'Slavko', 'Horvat', NULL, 'admin', '2018-04-05 21:10:52', NULL, NULL),
  (2, 'Referent', NULL, 'sonja.zagorc@fri.uni-lj.si', NULL, '$31$16$jsM957_X1cP9IvSvKCtIxyC0M1CHXJLv_cyqPllLbh0', 'Sonja', 'Zagorc', NULL, 'sz1077', '2018-04-05 21:10:52', NULL, NULL),
  (3, 'Referent', NULL, 'slavko.cebasevic@fri.uni-lj.si', NULL, '$31$16$cU-rrWNRRm7USv1cdXhAcATqFRk1WZTIPHf0Dzmh2hg', 'Slavko', 'Čebašević', NULL, 'sc7447', '2018-04-05 21:10:52', NULL, NULL),
  (4, 'Referent', NULL, 'peter.pirnar@fri.uni-lj.si', NULL, '$31$16$tEUzOB8z3wQUNgepU0zAEp6DDFyYIbWW9cwaronbXoQ', 'Peter', 'Pirnar', NULL, 'pp1544', '2018-04-05 21:10:52', NULL, NULL),
  (5, 'Referent', NULL, 'andraz.omrzel@fri.uni-lj.si', NULL, '$31$16$-KWTHd5UbQyE7eITecdCY632fPkcV62k8Fj1qc7C6j0', 'Andraž', 'Omrzel', NULL, 'ao4054', '2018-04-05 21:10:52', NULL, NULL),
  (6, 'Ucitelj', NULL, 'marko.bajec@fri.uni-lj.si', NULL, '$31$16$x-HNnK7qPj7aXp1llR2DICOADdQbyq1u7HXqyUAzLVs', 'Marko', 'Bajec', NULL, 'mp1602', '2018-04-05 21:10:52', NULL, NULL),
  (7, 'Ucitelj', NULL, 'andrej.bauer@fri.uni-lj.si', NULL, '$31$16$1_UlAawVY_8fs84NJgbdwZjJvLPxa_ZbYeO3OvBCyeU', 'Andrej', 'Bauer', NULL, 'kk8840', '2018-04-05 21:10:52', NULL, NULL),
  (8, 'Ucitelj', NULL, 'zoran.bosnic@fri.uni-lj.si', NULL, '$31$16$VcdI73VpZwsnL8ZRUi17RkdQL0dmuovs8EPrNHw-mq8', 'Zoran', 'Bosnić', NULL, 'ep4703', '2018-04-05 21:10:52', NULL, NULL),
  (9, 'Ucitelj', NULL, 'narvika.bovcon@fri.uni-lj.si', NULL, '$31$16$bP4DKTzrykawfZFQS4eBXF3Yt2tBV2-kRII4-RQEODM', 'Narvika', 'Bocon', NULL, 'mn9347', '2018-04-05 21:10:53', NULL, NULL),
  (10, 'Ucitelj', NULL, 'patricio.bulic@fri.uni-lj.si', NULL, '$31$16$H275BPAqlrxvWr28LwwWmAQR2EMe58dF8l7C1fkTtn4', 'Patricio', 'Bulić', NULL, 'ms7967', '2018-04-05 21:10:53', NULL, NULL),
  (11, 'Ucitelj', NULL, 'nina.bostic.bishop@fri.uni-lj.si', NULL, '$31$16$pyWiStDESyFya0KcLmCcGkLLrpUa7PYVwa_GAnjrfgA', 'Nina', 'Bostič Bishop', NULL, 'lz1773', '2018-04-05 21:10:53', NULL, NULL),
  (12, 'Ucitelj', NULL, 'mojca.ciglaric@fri.uni-lj.si', NULL, '$31$16$ekpNcIfqlOHV9BHRdehB_MIomr4uDR2eXNx1WmuBhV8', 'Mojca', 'Ciglarič', NULL, 'ak5709', '2018-04-05 21:10:53', NULL, NULL),
  (13, 'Ucitelj', NULL, 'tomaz.dobravec@fri.uni-lj.si', NULL, '$31$16$T02iycjlqQEOqBtYyF6PRV9-cA0U0_xgXZMgqbTUaWQ', 'Tomaž', 'Dobravec', NULL, 'ps5680', '2018-04-05 21:10:53', NULL, NULL),
  (14, 'Ucitelj', NULL, 'mateja.drnovsek@fri.uni-lj.si', NULL, '$31$16$cOZk-iRl0ohGxOy_dTmER-vsNjY1QW06CxRUmNeWrVI', 'Mateja', 'Drnovšek', NULL, 'lz9780', '2018-04-05 21:10:53', NULL, NULL),
  (15, 'Ucitelj', NULL, 'gasper.fijavz@fri.uni-lj.si', NULL, '$31$16$u7Aq3eXNi3_yEUds1niqhltc-qZTH-llSGgLG4ISHis', 'Gašper', 'Fijavž', NULL, 'ek6015', '2018-04-05 21:10:53', NULL, NULL),
  (16, 'Ucitelj', NULL, 'tomaz.hovelja@fri.uni-lj.si', NULL, '$31$16$emIyV32d_qjY86QR-0oxX-5v6IFmtAbc6MHr7EAn05w', 'Tomaž', 'Hovelja', NULL, 'mk4553', '2018-04-05 21:10:53', NULL, NULL),
  (17, 'Ucitelj', NULL, 'matjaz.branko.juric@fri.uni-lj.si', NULL, '$31$16$kC2hJTOXHtad_21T5KAVky-ilcmCSS7j4u5ZYpwO5PU', 'Matjaž Branko', 'Jurič', NULL, 'ps8838', '2018-04-05 21:10:53', NULL, NULL),
  (18, 'Ucitelj', NULL, 'aleksandar.jurisic@fri.uni-lj.si', NULL, '$31$16$hXYp10ZVFYd1cgIqPuyllojqSKRa8DBfetvP5ijkwZk', 'Aleksandar', 'Jurišić', NULL, 'ms5718', '2018-04-05 21:10:53', NULL, NULL),
  (19, 'Ucitelj', NULL, 'viljan.mahnic@fri.uni-lj.si', NULL, '$31$16$COPnLqyIvhuckOg_wYB7Xth5GcSc_wL_O6z-iOVaUmE', 'Viljan', 'Mahnič', NULL, 'jh5873', '2018-04-05 21:10:53', NULL, NULL),
  (20, 'Ucitelj', NULL, 'nezka.mramor.kosta@fri.uni-lj.si', NULL, '$31$16$w0eoQt_VoJp0loVBd6db7-t0f5l2xZjTQQkdYbB-UHc', 'Nežka', 'Mramor Kosta', NULL, 'mk1851', '2018-04-05 21:10:54', NULL, NULL),
  (21, 'Ucitelj', NULL, 'nikolaj.zimic@fri.uni-lj.si', NULL, '$31$16$Eq4VOArjFAbS8-w1o4OcXvHI5Qo13GGUyprC7g-Z4kw', 'Nikolaj', 'Zimic', NULL, 'sk2381', '2018-04-05 21:10:54', NULL, NULL),
  (22, 'Ucitelj', NULL, 'borut.paul.kersevan@fri.uni-lj.si', NULL, '$31$16$8Hgvvfnej7GFyRaQE3WMLorvUwzf8G4mlWLLE_rSgcM', 'Borut Paul', 'Kerševan', NULL, 'ek2982', '2018-04-05 21:10:54', NULL, NULL),
  (23, 'Ucitelj', NULL, 'bostjan.slivnik@fri.uni-lj.si', NULL, '$31$16$K2y7_iwRDFs1K1U3dZI-1lu5EMliy7aAoyPWaKDoX5U', 'Boštjan', 'Slivnik', NULL, 'zh1695', '2018-04-05 21:10:54', NULL, NULL),
  (24, 'Ucitelj', NULL, 'dejan.lavbic@fri.uni-lj.si', NULL, '$31$16$n5e03P7dlA4-nfe2ZcGh4e9DEgKAWkjtnyYi9xusvXM', 'Dejan', 'Lavbič', NULL, 'zh5172', '2018-04-05 21:10:54', NULL, NULL),
  (25, 'Ucitelj', NULL, 'bojan.orel@fri.uni-lj.si', NULL, '$31$16$2WDfzK8Qv8oRCdBpmOLgdYoQ6B65CfyycvZ27h6fPUI', 'Bojan', 'Orel', NULL, 'jz4603', '2018-04-05 21:10:54', NULL, NULL),
  (26, 'Ucitelj', NULL, 'branko.ster@fri.uni-lj.si', NULL, '$31$16$IqHIKBp9b3J-jcxJL0HtfDCsTIwG1C3Y8zEXXqWAtqo', 'Branko', 'Šter', NULL, 'sc8702', '2018-04-05 21:10:54', NULL, NULL),
  (27, 'Ucitelj', NULL, 'igor.kononenko@fri.uni-lj.si', NULL, '$31$16$vYttEx7W--iPQUuRGRRB2ehK4HlQK5VGHtvKyZu83qg', 'Igor', 'Kononenko', NULL, 'zk7249', '2018-04-05 21:10:54', NULL, NULL),
  (28, 'Ucitelj', NULL, 'borut.robic@fri.uni-lj.si', NULL, '$31$16$mFbY8P1w48EETNq0GgXe-__QUl5Lv42cGi9cMvZUI-0', 'Borut', 'Robič', NULL, 'sh5950', '2018-04-05 21:10:54', NULL, NULL),
  (29, 'Ucitelj', NULL, 'zan.horvat@fri.uni-lj.si', NULL, '$31$16$q9S0PqoH7a6OidZduIeZkCUxb5A_fEc0myccHv8wU6o', 'Uroš', 'Lotrič', NULL, 'zh1735', '2018-04-05 21:10:54', NULL, NULL),
  (30, 'Ucitelj', NULL, 'franc.solina@fri.uni-lj.si', NULL, '$31$16$fxiJrNsmvo6LYsuAhqcnKvFsMXlLQid7PUIoQaqRL6g', 'Franc', 'Solina', NULL, 'az4989', '2018-04-05 21:10:54', NULL, NULL),
  (31, 'Ucitelj', NULL, 'rok.zitko@fri.uni-lj.si', NULL, '$31$16$S7Qos2FPOao3qf11_cVvfYdFcvYpCgJRuUUgMpGq8EU', 'Rok', 'Žitko', NULL, 'ec4734', '2018-04-05 21:10:55', NULL, 705),
  (32, 'Ucitelj', NULL, 'denis.trcek@fri.uni-lj.si', NULL, '$31$16$WQ6uAUZnRKvD_f6mPA4tysICTFbgBvjOvgSRzasTJ_0', 'Denis', 'Trček', NULL, 'pk9396', '2018-04-05 21:10:55', NULL, 705),
  (33, 'Ucitelj', NULL, 'blaz.zupan@fri.uni-lj.si', NULL, '$31$16$-q_BR4rg9Zc9hoS2LraY7NvrYnXN35Pfcm0aupWmaEQ', 'Blaž', 'Zupan', NULL, 'ap8563', '2018-04-05 21:10:55', NULL, NULL),
  (34, 'Ucitelj', NULL, 'matjaz.kukar@fri.uni-lj.si', NULL, '$31$16$xpJ51Uc9cn7XkixsMCPBEsxQ-WDYFn9As9De1Euje74', 'Matjaž', 'Kukar', NULL, 'mh5768', '2018-04-05 21:10:55', NULL, 705),
  (35, 'Ucitelj', NULL, 'rok.rupnik@fri.uni-lj.si', NULL, '$31$16$HvkVckLNpfckr8Jnt2fp5HeHgNbNqmqAlEyX8O1SqdE', 'Rok', 'Rupnik', NULL, 'mh5528', '2018-04-05 21:10:55', NULL, NULL),
  (36, 'Ucitelj', NULL, 'miha.mraz@fri.uni-lj.si', NULL, '$31$16$SYRIIu99VaaiJJPuTc--iVQXPxe7086P8deny6vOq2E', 'Miha', 'Mraz', NULL, 'mk8834', '2018-04-05 21:10:55', NULL, NULL),
  (37, 'Ucitelj', NULL, 'marko.robnik.sikonja@fri.uni-lj.si', NULL, '$31$16$vNca_PNgXoxfvn6c1tR9GoFQ5vicOVQqJ3p6NxSGVFE', 'Marko', 'Robnik Šikonja', NULL, 'pc6640', '2018-04-05 21:10:55', NULL, NULL),
  (38, 'Ucitelj', NULL, 'matej.kristan@fri.uni-lj.si', NULL, '$31$16$L4Keh7wD51csVV50lsO4vjIdcAyIVI5Q_CCe7i9GnnY', 'Matej', 'Kristan', NULL, 'kz5339', '2018-04-05 21:10:55', NULL, NULL),
  (39, 'Ucitelj', NULL, 'danijel.skocaj@fri.uni-lj.si', NULL, '$31$16$sYx-basBhftKLF_Xi7kAWlfsvj2bIY8SaHgeXH7389Y', 'Danijel', 'Skočaj', NULL, 'th9718', '2018-04-05 21:10:55', NULL, NULL),
  (40, 'Ucitelj', NULL, 'matija.marolt@fri.uni-lj.si', NULL, '$31$16$QvLXOkylRe_pUyc92mlC8N9SmIt12Ic4hVABPskmFRs', 'Matija', 'Marolt', NULL, 'mo6822', '2018-04-05 21:10:55', NULL, NULL),
  (41, 'Ucitelj', NULL, 'janez.demsar@fri.uni-lj.si', NULL, '$31$16$zAFPA2-MlDomzmZkkZyv4vwfxfOROyh2dNBhBp0XALg', 'Janez', 'Demšar', NULL, 'ls6412', '2018-04-05 21:10:56', NULL, NULL),
  (42, 'Ucitelj', NULL, 'robert.rozman@fri.uni-lj.si', NULL, '$31$16$13KSKA2qacwTa7-vDhwtVjtMqf1zPqHbAfwWLTNf2iM', 'Robert', 'Rozman', NULL, 'eh7880', '2018-04-05 21:10:56', NULL, NULL),
  (43, 'Ucitelj', NULL, 'polona.oblak@fri.uni-lj.si', NULL, '$31$16$CUKA-4IrX8sJ-uPvQAJ3yCdeUlz7XiTwuIsGl8Milqs', 'Polona', 'Oblak', NULL, 'ph8082', '2018-04-05 21:10:56', NULL, NULL),
  (44, 'Ucitelj', NULL, 'peter.peer@fri.uni-lj.si', NULL, '$31$16$YdkOKNj3Ijl2_UcL0QU6djMozZqs8uHG1ziLufl0Jyw', 'Peter', 'Peer', NULL, 'kc6188', '2018-04-05 21:10:56', NULL, NULL),


  (45, 'Ucitelj', NULL, 'andraz.sustar@student.uni-lj.si', NULL, '$31$16$0AceQKwB2UlF9O86cp7AMsjJCVMVBkY6hnDuGmCP_iY', 'Andraž', 'Šuštar', NULL, 'as2959', '2018-04-05 21:10:56', NULL, NULL),
  (46, 'Ucitelj', NULL, 'luka.pirnar@student.uni-lj.si', NULL, '$31$16$VJy6ZXbxfT218gDsDFj5NbfNhcOXfn3nUlLR95CM8eE', 'Luka', 'Pirnar', NULL, 'lp5724', '2018-04-05 21:10:56', NULL, NULL),
  (47, 'Ucitelj', NULL, 'peter.hrovat@student.uni-lj.si', NULL, '$31$16$i90SnKBdsVTR4g3CQ1Y67MmQpA4bKVx5EEgYR9ISBcU', 'Peter', 'Hrovat', NULL, 'ph2482', '2018-04-05 21:10:56', NULL, NULL),
  (48, 'Ucitelj', NULL, 'pavel.zagorc@student.uni-lj.si', NULL, '$31$16$lf98YWTIFOgC-XbFuC0lA05YhmCRyXOQTj46KhmXVJo', 'Pavel', 'Zagorc', NULL, 'pz8896', '2018-04-05 21:10:56', NULL, NULL),
  (49, 'Ucitelj', NULL, 'klara.zagorc@student.uni-lj.si', NULL, '$31$16$7B_zXCntHSXA9v_krT1uqwFh8Qy2oyjo4jP4BQvvLo8', 'Klara', 'Zagorc', NULL, 'kz8472', '2018-04-05 21:10:56', NULL, NULL),
  (50, 'Ucitelj', NULL, 'primoz.hrovat@gmx.com', NULL, '$31$16$irG9VKXkRV0pxhGRhYZWDZZGO71V--R5_sLsBOWPUWI', 'Luka', 'Kovač', NULL, 'lk3317', '2018-04-05 21:10:56', NULL, NULL),

  (51, 'Student', NULL, 'primoz.hrovat@gmx.com', NULL, '$31$16$irG9VKXkRV0pxhGRhYZWDZZGO71V--R5_sLsBOWPUWI', 'Matjaž', 'Črednik', NULL, 'mc5552', '2018-04-05 21:10:56', NULL, NULL),
  (52, 'Student', NULL, 'primoz.hrovat@gmx.com', NULL, '$31$16$irG9VKXkRV0pxhGRhYZWDZZGO71V--R5_sLsBOWPUWI', 'Špela', 'Šuštar', NULL, 'ss1235', '2018-04-05 21:10:56', NULL, NULL),
  (53, 'Student', NULL, 'primoz.hrovat@gmx.com', NULL, '$31$16$irG9VKXkRV0pxhGRhYZWDZZGO71V--R5_sLsBOWPUWI', 'Marko', 'Čop', NULL, 'mc5689', '2018-04-05 21:10:56', NULL, NULL),
  (54, 'Student', NULL, 'primoz.hrovat@gmx.com', NULL, '$31$16$irG9VKXkRV0pxhGRhYZWDZZGO71V--R5_sLsBOWPUWI', 'Mitja', 'Ćusar', NULL, 'mc5546', '2018-04-05 21:10:56', NULL, NULL),
  (55, 'Student', NULL, 'primoz.hrovat@gmx.com', NULL, '$31$16$irG9VKXkRV0pxhGRhYZWDZZGO71V--R5_sLsBOWPUWI', 'Primož', 'Žmire', NULL, 'pz4587', '2018-04-05 21:10:56', NULL, NULL),
  (56, 'Student', NULL, 'primoz.hrovat@gmx.com', NULL, '$31$16$irG9VKXkRV0pxhGRhYZWDZZGO71V--R5_sLsBOWPUWI', 'Miha', 'Novak', NULL, 'mn1256', '2018-04-05 21:10:56', NULL, NULL),
  (57, 'Student', NULL, 'primoz.hrovat@gmx.com', NULL, '$31$16$irG9VKXkRV0pxhGRhYZWDZZGO71V--R5_sLsBOWPUWI', 'Patricija', 'Kos', NULL, 'pk5397', '2018-04-05 21:10:56', NULL, NULL),
  (58, 'Student', NULL, 'primoz.hrovat@gmx.com', NULL, '$31$16$irG9VKXkRV0pxhGRhYZWDZZGO71V--R5_sLsBOWPUWI', 'Franc', 'Dren', NULL, 'fd3354', '2018-04-05 21:10:56', NULL, NULL),
  (59, 'Student', NULL, 'primoz.hrovat@gmx.com', NULL, '$31$16$irG9VKXkRV0pxhGRhYZWDZZGO71V--R5_sLsBOWPUWI', 'Eva', 'Marolt', NULL, 'em6875', '2018-04-05 21:10:56', NULL, NULL),
  (60, 'Student', NULL, 'primoz.hrovat@gmx.com', NULL, '$31$16$irG9VKXkRV0pxhGRhYZWDZZGO71V--R5_sLsBOWPUWI', 'Mojca', 'Neber', NULL, 'mn9823', '2018-04-05 21:10:56', NULL, NULL),
  (61, 'Student', NULL, 'primoz.hrovat@gmx.com', NULL, '$31$16$irG9VKXkRV0pxhGRhYZWDZZGO71V--R5_sLsBOWPUWI', 'Ana', 'Morder', NULL, 'am3165', '2018-04-05 21:10:56', NULL, NULL),
  (62, 'Student', NULL, 'primoz.hrovat@gmx.com', NULL, '$31$16$irG9VKXkRV0pxhGRhYZWDZZGO71V--R5_sLsBOWPUWI', 'Neja', 'Goluša', NULL, 'ng1689', '2018-04-05 21:10:56', NULL, NULL);
/*
  Skrbnik
*/
INSERT INTO `skrbnik` (`id_uporabnik`) VALUES
  (1);

/*
  Referent
*/
INSERT INTO `referent` (`id_uporabnik`) VALUES
  (2),
  (3),
  (4),
  (5);

/*
  Ucitelj
*/
INSERT INTO `ucitelj` (`id_uporabnik`) VALUES
  (6),
  (7),
  (8),
  (9),
  (10),
  (11),
  (12),
  (13),
  (14),
  (15),
  (16),
  (17),
  (18),
  (19),
  (20),
  (21),
  (22),
  (23),
  (24),
  (25),
  (26),
  (27),
  (28),
  (29),
  (30),
  (31),
  (32),
  (33),
  (34),
  (35),
  (36),
  (37),
  (38),
  (39),
  (40),
  (41),
  (42),
  (43),
  (44),
  (45),
  (46),
  (47),
  (48),
  (49),
  (50);

/*
  Student
*/
INSERT INTO `student` (`id_uporabnik`, `drzava_rojstva`, `kraj_rojstva`,
                       `naslov_stalno`, `naslov_zacasno`, `obcina_rojstva`, `tel_stevilka`,
                       `vpisna_stevilka`, `drzava_stalno`, `drzava_zacasno`, `obcina_stalno`,
                       `obcina_zacasno`, `posta_stalno`, `privzeti_naslov`) VALUES
  (51, NULL, NULL, 'Ljubljanska cesta 15b', NULL, NULL, '070123123', 63150001, 705, NULL, 85, NULL, 8000, 'Novomeška 1'),
  (52, NULL, NULL, NULL, 'Mariborska 14', NULL, '070123123', 63150002, 705, NULL, NULL, NULL, NULL, 'Mariborska 15'),
  (53, NULL, NULL, NULL, NULL, NULL, '070123123', 63150003, NULL, NULL, NULL, NULL, NULL, NULL),
  (54, NULL, NULL, NULL, NULL, NULL, '070123123', 63150004, NULL, NULL, NULL, NULL, NULL, NULL),
  (55, NULL, NULL, NULL, NULL, NULL, '070123123', 63150005, NULL, NULL, NULL, NULL, NULL, NULL),
  (56, NULL, NULL, NULL, NULL, NULL, '070123123', 63150006, NULL, NULL, NULL, NULL, NULL, NULL),
  (57, NULL, NULL, NULL, NULL, NULL, '070123123', 63150007, NULL, NULL, NULL, NULL, NULL, NULL),
  (58, NULL, NULL, NULL, NULL, NULL, '070123123', 63150008, NULL, NULL, NULL, NULL, NULL, NULL),
  (59, NULL, NULL, NULL, NULL, NULL, '070123123', 63150009, NULL, NULL, NULL, NULL, NULL, NULL),
  (60, NULL, NULL, NULL, NULL, NULL, '070123123', 63150010, NULL, NULL, NULL, NULL, NULL, NULL),
  (61, NULL, NULL, NULL, NULL, NULL, '070123123', 63150011, NULL, NULL, NULL, NULL, NULL, NULL),
  (62, NULL, NULL, NULL, NULL, NULL, '070123123', 63150012, NULL, NULL, NULL, NULL, NULL, NULL);

/*
  Vpis
 */
INSERT INTO vpis (student, studijsko_leto, letnik, nacin_studija, oblika_studija, studijski_program, vrsta_vpisa, potrjen) VALUES
  (51, 2015, 1, 1, 1, 1000468, 1, 1),
  (52, 2015, 1, 1, 1, 1000468, 1, 1),
  (52, 2016, 1, 1, 1, 1000468, 1, 1),
  (53, 2015, 2, 1, 1, 1000468, 1, 1),
  (54, 2015, 1, 1, 1, 1000470, 1, 1),
  (55, 2015, 1, 1, 1, 1000471, 1, 1),
  (56, 2015, 1, 1, 1, 1000468, 1, 1),
  (56, 2016, 2, 1, 1, 1000468, 1, 1),
  (57, 2016, 1, 2, 2, 1000470, 1, 1),
  (57, 2017, 2, 2, 2, 1000470, 1, 1),
  (57, 2018, 2, 2, 2, 1000470, 2, 1);

/*
  Zetoni
*/
INSERT INTO zeton (prosta_izbira, student, vrsta_vpisa, letnik, nacin_studija, oblika_studija, studijski_program, studijsko_leto)
VALUES
  (false, 51, 1, 2, 1, 1, 1000468, 2018),
  (false, 51, 2, 2, 1, 1, 1000468, 2018),
  (false, 52, 1, 2, 1, 1, 1000468, 2018),
  (true, 53, 1, 3, 1, 1, 1000468, 2018),
  (false, 54, 1, 3, 1, 1, 1000468, 2018),
  (false, 55, 1, 1, 1, 1, 1000470, 2018);
UPDATE zeton SET izkoriscen=1 WHERE student=54;

/*
  Izvajanje predmeta
 */

INSERT INTO predmet_izvajanje (nosilec1, nosilec2, nosilec3, predmet, studijsko_leto)
VALUES
  /*
    Univerzitetni
   */
  (6, null, null, 63208, 2018),
  (6, null, null, 63252, 2018),
  (7, null, null, 63220, 2018),
  (8, null, null, 63214, 2018),
  (8, null, null, 63287, 2018),
  (8, null, null, 63209, 2018),
  (9, null, null, 63271, 2018),
  (10, null, null, 63260, 2018),
  (10, null, null, 63218, 2018),
  (11, null, null, 63222, 2018),
  (11, null, null, 63223, 2018),
  (11, null, null, 63224, 2018),
  (12, null, null, 63258, 2018),
  (13, null, null, 63264, 2018),
  (14, null, null, 63248, 2018),
  (15, null, null, 63203, 2018),
  (16, null, null, 63250, 2018),
  (17, null, null, 63254, 2018),
  (18, null, null, 63213, 2018),
  (19, null, null, 63277, 2018),
  (20, null, null, 63202, 2018),
  (21, null, null, 63204, 2018),
  (22, null, null, 63205, 2018),
  (23, null, null, 63278, 2018),
  (24, null, null, 63215, 2018),
  (25, null, null, 63207, 2018),
  (26, null, null, 63212, 2018),
  (27, null, null, 63279, 2018),
  (28, null, null, 63283, 2018),
  (29, null, null, 63216, 2018),
  (28, null, null, 63280, 2018),
  (28, null, null, 63217, 2018),
  (19, null, null, 63256, 2018),
  (30, null, null, 63281, 2018),
  (20, null, null, 63219, 2018),
  (31, null, null, 63221, 2018),
  (32, null, null, 63249, 2018),
  (33, null, null, 63251, 2018),
  (34, null, null, 63226, 2018),
  (35, null, null, 63253, 2018),
  (24, null, null, 63255, 2018),
  (36, null, null, 63257, 2018),
  (21, null, null, 63259, 2018),
  (29, null, null, 63261, 2018),
  (36, null, null, 63262, 2018),
  (37, null, null, 63263, 2018),
  (23, null, null, 63265, 2018),
  (27, null, null, 63266, 2018),
  (38, null, null, 63267, 2018),
  (39, null, null, 63268, 2018),
  (38, null, null, 63270, 2018),
  (40, null, null, 63269, 2018),
  (null, null, null, 63225, 2018),
  (15, null, null, 63242, 2018),
  (null, null, null, 63284, 2018),


  /*
  Visokošolski
   */
  (39, null, null, 63701, 2018),
  (41, null, null, 63702, 2018),
  (42, null, null, 63703, 2018),
  (43, null, null, 63704, 2018),
  (15, null, null, 63705, 2018),
  (13, null, null, 63706, 2018),
  (34, null, null, 63707, 2018),
  (12, null, null, 63708, 2018),
  (44, null, null, 63709, 2018),
  (18, null, null, 63710, 2018);


INSERT INTO rok (datum_cas, prostor, izvajalec, studijsko_leto, predmet)
VALUES
  ('2019-06-02 10:00:00', 'P22', 39, 2018, 63268);

INSERT INTO predmet_student (predmet, student, studijsko_leto)
VALUES
  (63279, 57, 2018),
  (63208, 57, 2018),
  (63213, 57, 2018),
  (63218, 57, 2018),
  (63283, 57, 2018),
  (63216, 57, 2018),
  (63280, 57, 2018),
  (63217, 57, 2018);