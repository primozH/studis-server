
/* global angular */
angular
    .module('studis')
    .controller('VpisniListCtrl', VpisniListCtrl)
    .directive('samoCrke', function() {
      return {
        require: 'ngModel',
        link: function (scope, element, attr, ngModelCtrl) {
          function fromUser(text) {
            var transformedInput = text.replace(/[^a-zA-ZčČšŠžŽđĐćĆ]/g, '');
            if(transformedInput !== text) {
                ngModelCtrl.$setViewValue(transformedInput);
                ngModelCtrl.$render();
            } return transformedInput; 
          } ngModelCtrl.$parsers.push(fromUser);
        }
      }; 
    })
    .directive('samoStevilke', function() {
      return {
        require: 'ngModel',
        link: function (scope, element, attr, ngModelCtrl) {
          function fromUser(text) {
            var transformedInput = text.replace(/[^0-9]/g, '');
            if(transformedInput !== text) {
                ngModelCtrl.$setViewValue(transformedInput);
                ngModelCtrl.$render();
            } return transformedInput; 
          } ngModelCtrl.$parsers.push(fromUser);
        }
      }; 
    });


function VpisniListCtrl($scope, $window, $routeParams, vpislist, studen){
    $scope.vl_rojstvo = new Date(1996, 0, 1); //year month(0=januar) day
    $scope.spoli = [ {code: 'ZENSKI', ime: 'ženski'}, {code: 'MOSKI', ime: 'moški'}];
    $scope.drzave = [{code:4,ime:"Afganistan"},{code:8,ime:"Albanija"},{code:10,ime:"Antarktika"},{code:12,ime:"Alžirija"},{code:16,ime:"AmeriškaSamoa"},{code:20,ime:"Andora"},{code:24,ime:"Angola"},{code:28,ime:"AntigvainBarbuda"},{code:31,ime:"Azerbajdžan"},{code:32,ime:"Argenitna"},{code:36,ime:"Avstralija"},{code:40,ime:"Avstrija"},{code:44,ime:"Bahami"},{code:48,ime:"Bahrajn"},{code:50,ime:"Bangladeš"},{code:51,ime:"Armenija"},{code:52,ime:"Barbados"},{code:56,ime:"Belgija"},{code:60,ime:"Bermudi"},{code:64,ime:"Butan"},{code:68,ime:"Bolivija"},{code:70,ime:"BosnainHercegovina"},{code:72,ime:"Bocvana"},{code:74,ime:"Bouvetovotok"},{code:76,ime:"Brazilija"},{code:84,ime:"Belize"},{code:86,ime:"BritanskoozemljevIndijskemoceanu"},{code:90,ime:"Solomonoviotoki"},{code:92,ime:"BritanskiDeviškiotoki"},{code:96,ime:"Brunej"},{code:100,ime:"Bolgarija"},{code:104,ime:"Mjanmar"},{code:108,ime:"Burundi"},{code:112,ime:"Belorusija"},{code:116,ime:"Kambodža"},{code:120,ime:"Kamerun"},{code:124,ime:"Kanada"},{code:132,ime:"Kapverdskiotoki(Zelenortskiotoki)"},{code:136,ime:"Kajmanskiotoki"},{code:140,ime:"Srednjeafriškarepublika"},{code:144,ime:"ŠriLanka"},{code:148,ime:"Čad"},{code:152,ime:"Čile"},{code:156,ime:"Kitajska"},{code:158,ime:"Tajvan"},{code:162,ime:"Božičniotok"},{code:166,ime:"KokosoviinKeelingoviotoki"},{code:170,ime:"Kolumbija"},{code:174,ime:"Komori"},{code:175,ime:"FrancoskaskupnostMejot"},{code:178,ime:"Kongo"},{code:180,ime:"DemokratičnarepublikaKongo"},{code:184,ime:"Cookoviotoki"},{code:188,ime:"Kostarika"},{code:191,ime:"Hrvaška"},{code:192,ime:"Kuba"},{code:196,ime:"Ciper"},{code:203,ime:"Češka"},{code:204,ime:"Benin"},{code:208,ime:"Danska"},{code:212,ime:"Dominika"},{code:214,ime:"Dominikanskarepublika"},{code:218,ime:"Ekvador"},{code:222,ime:"Salvador"},{code:226,ime:"EkvatorialnaGvineja"},{code:231,ime:"Etiopija"},{code:232,ime:"Eritreja"},{code:233,ime:"Estonija"},{code:234,ime:"Falkalndskiotoki"},{code:238,ime:"Ferskiotoki"},{code:239,ime:"JužnaGeorgiainJužniSandwicheviotoki"},{code:242,ime:"Fidži"},{code:246,ime:"Finska"},{code:248,ime:"Alandskiotoki"},{code:250,ime:"Francija"},{code:254,ime:"FrancoskaGvajana"},{code:258,ime:"FrancoskaPolinezija"},{code:260,ime:"Francoskajužnaozemlja"},{code:262,ime:"Džibuti"},{code:266,ime:"Gabon"},{code:268,ime:"Gruzija"},{code:270,ime:"Gambija"},{code:275,ime:"Palestina"},{code:276,ime:"Nemčija"},{code:288,ime:"Gana"},{code:292,ime:"Gibraltar"},{code:296,ime:"Kiribati"},{code:300,ime:"Grčija"},{code:304,ime:"Grenlandija"},{code:308,ime:"Grenada"},{code:312,ime:"Guadeloupe"},{code:316,ime:"Guam"},{code:320,ime:"Gvatemala"},{code:324,ime:"Gvineja"},{code:328,ime:"Gvajana"},{code:332,ime:"Haiti"},{code:334,ime:"OtokHeardinotočjeMcDonald"},{code:336,ime:"Vatikan"},{code:340,ime:"Honduras"},{code:344,ime:"HongKong"},{code:348,ime:"Madžarska"},{code:352,ime:"Islandija"},{code:356,ime:"Indija"},{code:360,ime:"Indonezija"},{code:364,ime:"Iran"},{code:368,ime:"Irak"},{code:372,ime:"Irska"},{code:376,ime:"Izrael"},{code:380,ime:"Italija"},{code:384,ime:"Slonokoščenaobala"},{code:388,ime:"Jamajka"},{code:392,ime:"Japonska"},{code:398,ime:"Kazahstan"},{code:400,ime:"Jordanija"},{code:404,ime:"Kenija"},{code:408,ime:"SevernaKoreja"},{code:410,ime:"JužnaKoreja"},{code:414,ime:"Kuvajt"},{code:417,ime:"Kirgizistan(Kirgizija)"},{code:418,ime:"Laos"},{code:422,ime:"Libanon"},{code:426,ime:"Lesoto"},{code:428,ime:"Latvija"},{code:430,ime:"Liberija"},{code:434,ime:"Libija"},{code:438,ime:"Lihtenštajn"},{code:440,ime:"Litva"},{code:442,ime:"Luksemburg"},{code:446,ime:"Makao"},{code:450,ime:"Madagaskar"},{code:454,ime:"Malavi"},{code:458,ime:"Malezija"},{code:462,ime:"Maldivi"},{code:466,ime:"Mali"},{code:470,ime:"Malta"},{code:474,ime:"Martinik"},{code:478,ime:"Mavretanija"},{code:480,ime:"Mauricius(Moris)"},{code:484,ime:"Mehika"},{code:492,ime:"Monako"},{code:496,ime:"Mongolija"},{code:498,ime:"Moldavija"},{code:499,ime:"ČrnaGora"},{code:500,ime:"Montserat"},{code:504,ime:"Maroko"},{code:508,ime:"Mozambik"},{code:512,ime:"Oman"},{code:516,ime:"Namibija"},{code:520,ime:"Nauru"},{code:524,ime:"Nepal"},{code:528,ime:"Nizozemska"},{code:531,ime:"Kurasao"},{code:533,ime:"Aruba"},{code:534,ime:"Otoksvetega.Martina(Nizozemska)"},{code:535,ime:"OtočjeBonaire,Sv.EustatijinSaba"},{code:540,ime:"NovaKaledonija"},{code:548,ime:"RepublikaVanuatu"},{code:554,ime:"NovaZelandija"},{code:558,ime:"Nikaragva"},{code:562,ime:"Niger"},{code:566,ime:"Nigerija"},{code:570,ime:"Niu"},{code:574,ime:"OtokNorflok"},{code:578,ime:"Norveška"},{code:580,ime:"SeverniMarianskiotoki"},{code:581,ime:"ZDAzunanjiotoki"},{code:583,ime:"Mikronezija"},{code:584,ime:"Maršaloviotoki"},{code:585,ime:"Palau"},{code:586,ime:"Pakistan"},{code:591,ime:"Panama"},{code:598,ime:"PapuaNovaGvineja"},{code:600,ime:"Paragvaj"},{code:604,ime:"Peru"},{code:608,ime:"Filipini"},{code:612,ime:"Pitcairnoviotoki"},{code:616,ime:"Poljska"},{code:620,ime:"Portugalska"},{code:624,ime:"Gvineja-Bissau"},{code:626,ime:"VzhodniTimor"},{code:630,ime:"Portoriko"},{code:634,ime:"Katar"},{code:638,ime:"FrancoskaskupnostReunion"},{code:642,ime:"Romunija"},{code:643,ime:"Ruskafederacija"},{code:646,ime:"Ruanda"},{code:652,ime:"SvetiBartolomej"},{code:654,ime:"SvetaHelena"},{code:659,ime:"SvetiKitsinNevis"},{code:660,ime:"Angvila"},{code:662,ime:"SvetaLucija"},{code:663,ime:"OtoksvetegaMartina"},{code:666,ime:"SvetaPierreinMiquelon"},{code:670,ime:"SvetiVincentinGrenadini"},{code:674,ime:"SanMarino"},{code:678,ime:"SaoTomeinPrincipe"},{code:682,ime:"SavdskaArabija"},{code:686,ime:"Senegal"},{code:688,ime:"Srbija"},{code:690,ime:"Sejšeli"},{code:694,ime:"SieraLeone"},{code:702,ime:"Singapur"},{code:703,ime:"Slovaška"},{code:704,ime:"Vietnam"},{code:705,ime:"Slovenija"},{code:706,ime:"Somalija"},{code:710,ime:"Južnaafrika"},{code:716,ime:"Zimbabve"},{code:724,ime:"Španija"},{code:728,ime:"JužniSudan"},{code:729,ime:"Sudan"},{code:732,ime:"ZahodnaSahara"},{code:740,ime:"Surinam"},{code:744,ime:"SvalbardinJanMajen"},{code:748,ime:"Svazi"},{code:752,ime:"Švedska"},{code:756,ime:"Švica"},{code:760,ime:"Sirija"},{code:762,ime:"Tadžikistan"},{code:764,ime:"Tajska"},{code:768,ime:"Togo"},{code:772,ime:"Tokelau"},{code:776,ime:"Tonga"},{code:780,ime:"TrinidadinTobago"},{code:784,ime:"ZdruženiArabskiEmirati"},{code:788,ime:"Tunizija"},{code:792,ime:"Turčija"},{code:795,ime:"Turkmenistan"},{code:796,ime:"TirškiinKajkoškiotoki"},{code:798,ime:"Tuvalu"},{code:800,ime:"Uganda"},{code:804,ime:"Ukrajina"},{code:807,ime:"Makedonija"},{code:818,ime:"Egipt"},{code:826,ime:"VelikaBritanija"},{code:831,ime:"OtokGuernsey"},{code:832,ime:"OtokJersey"},{code:833,ime:"OtokMan"},{code:834,ime:"Tanzanija"},{code:840,ime:"ZdruženedržaveAmerike"},{code:850,ime:"AmeriškiDeviškiotoki"},{code:854,ime:"BurkinaFaso"},{code:858,ime:"Urugvaj"},{code:860,ime:"Uzbekistan"},{code:862,ime:"Venezuela"},{code:876,ime:"OtočjeValisinFutuna"},{code:882,ime:"Samoa"},{code:887,ime:"Jemen"},{code:894,ime:"Zambija"}];
    $scope.obcine = [{code:213,ime:"Ankaran"},{code:1, ime: "Ajdovščina"},{code:195, ime:  "Apače "},{code:2, ime: "Beltinci "},{code:148, ime:    "Benedikt "},{code:149, ime:    "Bistrica ob Sotli "},{code:3, ime: "Bled "},{code:150, ime:    "Bloke" },{code:4, ime: "Bohinj "},{code:5, ime:    "Borovnica "},{code:6, ime: "Bovec "},{code:151, ime:   "Braslovče" },{code:7, ime: "Brda "},{code:8, ime:  "Brezovica" },{code:9, ime: "Brežice "},{code:152, ime: "Cankova "},{code:11,ime:"Celje "},{code:12   ,ime: "Cerklje na Gorenjskem "},{code:13    ,ime: "Cerknica "},{code:14 ,ime: "Cerkno "},{code:153,ime: "   Cerkvenjak "},{code:196,ime: "  Cirkulane "},{code:15   ,ime: "Črenšovci "},{code:16    ,ime: "Črna na Koroškem "},{code:17 ,ime: "Črnomelj "},{code:18 ,ime: "Destrnik "},{code:19 ,ime: "Divača "},{code:154,ime: "   Dobje "},{code:20   ,ime: "Dobrepolje "},{code:155,ime: "   Dobrna "},{code:21  ,ime: "Dobrova - Polhov Gradec "},{code:156,ime: "  Dobrovnik "},{code:22   ,ime: "Dol pri Ljubljani "},{code:157,ime: "    Dolenjske Toplice "},{code:23   ,ime: "Domžale "},{code:24  ,ime: "Dornava "},{code:25  ,ime: "Dravograd "},{code:26    ,ime: "Duplek "},{code:27   ,ime: "Gorenja vas - Poljane "},{code:28    ,ime: "Gorišnica "},{code:207,ime: "    Gorje "},{code:29   ,ime: "Gornja Radgona "},{code:30   ,ime: "Gornji Grad "},{code:31  ,ime: "Gornji Petrovci "},{code:158,ime: "  Grad "},{code:32    ,ime: "Grosuplje "},{code:159,ime: "    Hajdina "},{code:160,ime: " Hoče - Slivnica "},{code:161,ime: " Hodoš "},{code:162,ime: "   Horjul "},{code:34  ,ime: "Hrastnik "},{code:35 ,ime: "Hrpelje - Kozina "},{code:36 ,ime: "Idrija "},{code:37   ,ime: "Ig "},{code:38   ,ime: "Ilirska Bistrica "},{code:39 ,ime: "Ivančna Gorica "},{code:40   ,ime: "Izola "},{code:41    ,ime: "Jesenice "},{code:163,ime: " Jezersko "},{code:42    ,ime: "Juršinci "},{code:43 ,ime: "Kamnik "},{code:44   ,ime: "Kanal "},{code:45    ,ime: "Kidričevo "},{code:46    ,ime: "Kobarid "},{code:47  ,ime: "Kobilje "},{code:48  ,ime: "Kočevje "},{code:49  ,ime: "Komen "},{code:164,ime: "    Komenda "},{code:50 ,ime: "Koper "},{code:197,ime: "    Kostanjevica na Krki "},{code:165,ime: "    Kostel "},{code:51  ,ime: "Kozje "},{code:52    ,ime: "Kranj "},{code:53    ,ime: "Kranjska Gora "},{code:166,ime: "    Križevci "},{code:54    ,ime: "Krško "},{code:55    ,ime: "Kungota "},{code:56  ,ime: "Kuzma "},{code:57    ,ime: "Laško "},{code:58    ,ime: "Lenart "},{code:59   ,ime: "Lendava "},{code:60  ,ime: "Litija "},{code:61   ,ime: "Ljubljana "},{code:62    ,ime: "Ljubno "},{code:63   ,ime: "Ljutomer "},{code:208,ime: " Log - Dragomer "},{code:64,ime: "Logatec "},{code:65,ime: "Loška dolina "},{code:66,ime: "Loški Potok "},{code:167,ime: "   Lovrenc na Pohorju "},{code:67,ime: "Luče "},{code:68,ime: "Lukovica "},{code:69,ime: "Majšperk "},{code:198,ime: " Makole "},{code:70,ime: "Maribor "},{code:168,ime: "    Markovci "},{code:71,ime: "Medvode "},{code:72,ime: "Mengeš "},{code:73,ime: "Metlika "},{code:74,ime: "Mežica "},{code:169,ime: "  Miklavž na Dravskem polju"},{code:75,ime: "Miren - Kostanjevica "},{code:212,ime: "Mirna "},{code:170,ime: "Mirna Peč "},{code:76   ,ime: "Mislinja "},{code:199,ime: "Mokronog - Trebelno "},{code:77,ime: "Moravče "},{code:78,ime: "Moravske Toplice "},{code:79,ime: "Mozirje "},{code:80,ime: "Murska Sobota "},{code:81,ime: "Muta "},{code:82,ime: "Naklo "},{code:83,ime: "Nazarje "},{code:84,ime: "Nova Gorica "},{code:85,ime: "Novo mesto "},{code:86,ime: "Odranci "},{code:171,ime: " Oplotnica "},{code:87,ime: "Ormož "},{code:88,ime: "Osilnica "},{code:89,ime: "Pesnica "},{code:90,ime: "Piran "},{code:91,ime: "Pivka "},{code:92,ime: "Podčetrtek "},{code:172,ime: "Podlehnik "},{code:93,ime:"Podvelka "},{code:200,ime: "Poljčane "},{code:173,ime: "Polzela "},{code:94,ime:"Postojna "},{code:174,ime: "Prebold "},{code:95,ime:"Preddvor "},{code:175,ime:"Prevalje "},{code:96,ime:"Ptuj "},{code:97,ime:"Puconci "},{code:98,ime:"Rače - Fram "},{code:99,ime:"Radeče "},{code:100,ime: "Radenci "},{code:101,ime: "Radlje ob Dravi "},{code:102,ime: "Radovljica "},{code:103,ime: "Ravne na Koroškem "},{code:176,ime: "Razkrižje "},{code:209,ime: "Rečica ob Savinji "},{code:201,ime: "Renče - Vogrsko "},{code:104,ime: "Ribnica "},{code:177,ime: "Ribnica na Pohorju "},{code:106,ime: "Rogaška Slatina "},{code:105,ime: "Rogašovci "},{code:107,ime: "Rogatec "},{code:108,ime: "Ruše "},{code:178,ime: "Selnica ob Dravi "},{code:109,ime: "Semič "},{code:110,ime: "Sevnica "},{code:111,ime: "Sežana "},{code:112,ime: "Slovenj Gradec "},{code:113,ime: "Slovenska Bistrica "},{code:114,ime: "Slovenske Konjice "},{code:179,ime: "Sodražica "},{code:180,ime: "Solčava "},{code:202,ime: "Središče ob Dravi "},{code:115,ime: "Starše "},{code:203,ime: "Straža "},{code:204,ime: "Sv. Trojica v Slov. Goricah "},{code:181,ime: "Sveta Ana "},{code:182,ime: "Sveti Andraž v Slov. Goricah "},{code:116,ime: "Sveti Jurij ob Ščavnici "},{code:210,ime: "Sveti Jurij v Slov. Goricah "},{code:205,ime: "Sveti Tomaž "},{code:33,ime: "Šalovci "},{code:183,ime: "Šempeter - Vrtojba "},{code:117,ime: "Šenčur "},{code:118,ime: "Šentilj "},{code:119,ime: "Šentjernej "},{code:120,ime: "Šentjur pri Celju "},{code:211,ime: "Šentrupert "},{code:121,ime: "Škocjan "},{code:122,ime: "Škofja Loka "},{code:123,ime: "Škofljica "},{code:124,ime: "Šmarje pri Jelšah "},{code:206,ime: "Šmarješke Toplice "},{code:125,ime: "Šmartno ob Paki "},{code:194,ime: "Šmartno pri Litiji "},{code:126,ime: "Šoštanj "},{code:127,ime: "Štore "},{code:184,ime: "Tabor "},{code:10,ime: "Tišina "},{code:128,ime: "Tolmin "},{code:129,ime: "Trbovlje "},{code:130,ime: "Trebnje "},{code:185,ime: "Trnovska vas "},{code:186,ime: "Trzin "},{code:131,ime: "Tržič "},{code:132,ime: "Turnišče "},{code:133,ime: "Velenje "},{code:187,ime: "Velika Polana "},{code:134,ime: "Velike Lašče "},{code:188,ime: "Veržej "},{code:135,ime: "Videm "},{code:136,ime: "Vipava "},{code:137,ime: "Vitanje "},{code:138,ime: "Vodice "},{code:139,ime: "Vojnik "},{code:189,ime: "Vransko "},{code:140,ime: "Vrhnika "},{code:141,ime: "Vuzenica "},{code:142,ime: "Zagorje ob Savi "},{code:143,ime: "Zavrč "},{code:144,ime: "Zreče "},{code:190,ime: "Žalec "},{code:146,ime: "Železniki "},{code:191,ime: "Žetale "},{code:147,ime: "Žiri "},{code:192,ime: "Žirovnica "},{code:193,ime: "Žužemberk"}];
    $scope.poste = [{code:8341,ime:'Adlešiči'},{code:5270,ime:'Ajdovščina'},{code:6280,ime:'Ankaran/Ancarano'},{code:9253,ime:'Apače'},{code:8253,ime:'Artiče'},{code:4275,ime:'Begunje na Gorenjskem'},{code:1382,ime:'Begunje pri Cerknici'},{code:9231,ime:'Beltinci'},{code:2234,ime:'Benedikt'},{code:2345,ime:'Bistrica ob Dravi'},{code:3256,ime:'Bistrica ob Sotli'},{code:8259,ime:'Bizeljsko'},{code:1223,ime:'Blagovica'},{code:8283,ime:'Blanca'},{code:4260,ime:'Bled'},{code:4273,ime:'Blejska Dobrava'},{code:9265,ime:'Bodonci'},{code:9222,ime:'Bogojina'},{code:4263,ime:'Bohinjska Bela'},{code:4264,ime:'Bohinjska Bistrica'},{code:4265,ime:'Bohinjsko jezero'},{code:1353,ime:'Borovnica'},{code:8294,ime:'Boštanj'},{code:5230,ime:'Bovec'},{code:5295,ime:'Branik'},{code:3314,ime:'Braslovče'},{code:5223,ime:'Breginj'},{code:8280,ime:'Brestanica'},{code:2354,ime:'Bresternica'},{code:4243,ime:'Brezje'},{code:1351,ime:'Brezovica pri Ljubljani'},{code:8250,ime:'Brežice'},{code:4210,ime:'Brnik - Aerodrom'},{code:8321,ime:'Brusnice'},{code:3255,ime:'Buče'},{code:8276,ime:'Bučka '},{code:9261,ime:'Cankova'},{code:3000,ime:'Celje '},{code:3001,ime:'Celje - poštni predali'},{code:4207,ime:'Cerklje na Gorenjskem'},{code:8263,ime:'Cerklje ob Krki'},{code:1380,ime:'Cerknica'},{code:5282,ime:'Cerkno'},{code:2236,ime:'Cerkvenjak'},{code:2215,ime:'Ceršak'},{code:2326,ime:'Cirkovce'},{code:2282,ime:'Cirkulane'},{code:5273,ime:'Col'},{code:8251,ime:'Čatež ob Savi'},{code:1413,ime:'Čemšenik'},{code:5253,ime:'Čepovan'},{code:9232,ime:'Črenšovci'},{code:2393,ime:'Črna na Koroškem'},{code:6275,ime:'Črni Kal'},{code:5274,ime:'Črni Vrh nad Idrijo'},{code:5262,ime:'Črniče'},{code:8340,ime:'Črnomelj'},{code:6271,ime:'Dekani'},{code:5210,ime:'Deskle'},{code:2253,ime:'Destrnik'},{code:6215,ime:'Divača'},{code:1233,ime:'Dob'},{code:3224,ime:'Dobje pri Planini'},{code:8257,ime:'Dobova'},{code:1423,ime:'Dobovec'},{code:5263,ime:'Dobravlje'},{code:3204,ime:'Dobrna'},{code:8211,ime:'Dobrnič'},{code:1356,ime:'Dobrova'},{code:9223,ime:'Dobrovnik/Dobronak '},{code:5212,ime:'Dobrovo v Brdih'},{code:1431,ime:'Dol pri Hrastniku'},{code:1262,ime:'Dol pri Ljubljani'},{code:1273,ime:'Dole pri Litiji'},{code:1331,ime:'Dolenja vas'},{code:8350,ime:'Dolenjske Toplice'},{code:1230,ime:'Domžale'},{code:2252,ime:'Dornava'},{code:5294,ime:'Dornberk'},{code:1319,ime:'Draga'},{code:8343,ime:'Dragatuš'},{code:3222,ime:'Dramlje'},{code:2370,ime:'Dravograd'},{code:4203,ime:'Duplje'},{code:6221,ime:'Dutovlje'},{code:8361,ime:'Dvor'},{code:2343,ime:'Fala'},{code:9208,ime:'Fokovci'},{code:2313,ime:'Fram'},{code:3213,ime:'Frankolovo'},{code:1274,ime:'Gabrovka'},{code:8254,ime:'Globoko'},{code:5275,ime:'Godovič'},{code:4204,ime:'Golnik'},{code:3303,ime:'Gomilsko'},{code:4224,ime:'Gorenja vas'},{code:3263,ime:'Gorica pri Slivnici'},{code:2272,ime:'Gorišnica'},{code:9250,ime:'Gornja Radgona'},{code:3342,ime:'Gornji Grad'},{code:4282,ime:'Gozd Martuljek'},{code:6272,ime:'Gračišče'},{code:9264,ime:'Grad'},{code:8332,ime:'Gradac'},{code:1384,ime:'Grahovo'},{code:5242,ime:'Grahovo ob Bači'},{code:5251,ime:'Grgar'},{code:3302,ime:'Griže'},{code:3231,ime:'Grobelno'},{code:1290,ime:'Grosuplje'},{code:2288,ime:'Hajdina'},{code:8362,ime:'Hinje'},{code:2311,ime:'Hoče'},{code:9205,ime:'Hodoš/Hodos'},{code:1354,ime:'Horjul'},{code:1372,ime:'Hotedršica'},{code:1430,ime:'Hrastnik'},{code:6225,ime:'Hruševje'},{code:4276,ime:'Hrušica'},{code:5280,ime:'Idrija'},{code:1292,ime:'Ig'},{code:6250,ime:'Ilirska Bistrica'},{code:6251,ime:'Ilirska Bistrica-Trnovo'},{code:1295,ime:'Ivančna Gorica'},{code:2259,ime:'Ivanjkovci'},{code:1411,ime:'Izlake'},{code:6310,ime:'Izola/Isola'},{code:2222,ime:'Jakobski Dol'},{code:2221,ime:'Jarenina'},{code:6254,ime:'Jelšane'},{code:4270,ime:'Jesenice'},{code:8261,ime:'Jesenice na Dolenjskem'},{code:3273,ime:'Jurklošter'},{code:2223,ime:'Jurovski Dol'},{code:2256,ime:'Juršinci'},{code:5214,ime:'Kal nad Kanalom'},{code:3233,ime:'Kalobje'},{code:4246,ime:'Kamna Gorica'},{code:2351,ime:'Kamnica'},{code:1241,ime:'Kamnik'},{code:5213,ime:'Kanal'},{code:8258,ime:'Kapele'},{code:2362,ime:'Kapla'},{code:2325,ime:'Kidričevo'},{code:1412,ime:'Kisovec'},{code:6253,ime:'Knežak'},{code:5222,ime:'Kobarid'},{code:9227,ime:'Kobilje'},{code:1330,ime:'Kočevje'},{code:1338,ime:'Kočevska Reka'},{code:2276,ime:'Kog'},{code:5211,ime:'Kojsko'},{code:6223,ime:'Komen'},{code:1218,ime:'Komenda'},{code:6000,ime:'Koper/Capodistria '},{code:6001,ime:'Koper/Capodistria - poštni predali'},{code:8282,ime:'Koprivnica'},{code:5296,ime:'Kostanjevica na Krasu'},{code:8311,ime:'Kostanjevica na Krki'},{code:1336,ime:'Kostel'},{code:6256,ime:'Košana'},{code:2394,ime:'Kotlje'},{code:6240,ime:'Kozina'},{code:3260,ime:'Kozje'},{code:4000,ime:'Kranj '},{code:4001,ime:'Kranj - poštni predali'},{code:4280,ime:'Kranjska Gora'},{code:1281,ime:'Kresnice'},{code:4294,ime:'Križe'},{code:9206,ime:'Križevci'},{code:9242,ime:'Križevci pri Ljutomeru'},{code:1301,ime:'Krka'},{code:8296,ime:'Krmelj'},{code:4245,ime:'Kropa'},{code:8262,ime:'Krška vas'},{code:8270,ime:'Krško'},{code:9263,ime:'Kuzma'},{code:2318,ime:'Laporje'},{code:3270,ime:'Laško'},{code:1219,ime:'Laze v Tuhinju'},{code:2230,ime:'Lenart v Slovenskih goricah'},{code:9220,ime:'Lendava/Lendva'},{code:4248,ime:'Lesce'},{code:3261,ime:'Lesično'},{code:8273,ime:'Leskovec pri Krškem'},{code:2372,ime:'Libeliče'},{code:2341,ime:'Limbuš'},{code:1270,ime:'Litija'},{code:3202,ime:'Ljubečna'},{code:1000,ime:'Ljubljana '},{code:1001,ime:'Ljubljana - poštni predali'},{code:1231,ime:'Ljubljana - Črnuče'},{code:1261,ime:'Ljubljana - Dobrunje'},{code:1260,ime:'Ljubljana - Polje'},{code:1210,ime:'Ljubljana - Šentvid'},{code:1211,ime:'Ljubljana - Šmartno'},{code:3333,ime:'Ljubno ob Savinji'},{code:9240,ime:'Ljutomer'},{code:3215,ime:'Loče'},{code:5231,ime:'Log pod Mangartom'},{code:1358,ime:'Log pri Brezovici'},{code:1370,ime:'Logatec'},{code:1371,ime:'Logatec'},{code:1434,ime:'Loka pri Zidanem Mostu'},{code:3223,ime:'Loka pri Žusmu'},{code:6219,ime:'Lokev'},{code:1318,ime:'Loški Potok'},{code:2324,ime:'Lovrenc na Dravskem polju'},{code:2344,ime:'Lovrenc na Pohorju'},{code:3334,ime:'Luče'},{code:1225,ime:'Lukovica'},{code:9202,ime:'Mačkovci'},{code:2322,ime:'Majšperk'},{code:2321,ime:'Makole'},{code:9243,ime:'Mala Nedelja'},{code:2229,ime:'Malečnik'},{code:6273,ime:'Marezige'},{code:2000,ime:'Maribor '},{code:2001,ime:'Maribor - poštni predali'},{code:2206,ime:'Marjeta na Dravskem polju'},{code:2281,ime:'Markovci'},{code:9221,ime:'Martjanci'},{code:6242,ime:'Materija'},{code:4211,ime:'Mavčiče'},{code:1215,ime:'Medvode'},{code:1234,ime:'Mengeš'},{code:8330,ime:'Metlika'},{code:2392,ime:'Mežica'},{code:2204,ime:'Miklavž na Dravskem polju'},{code:2275,ime:'Miklavž pri Ormožu'},{code:5291,ime:'Miren'},{code:8233,ime:'Mirna'},{code:8216,ime:'Mirna Peč'},{code:2382,ime:'Mislinja'},{code:4281,ime:'Mojstrana'},{code:8230,ime:'Mokronog'},{code:1251,ime:'Moravče'},{code:9226,ime:'Moravske Toplice'},{code:5216,ime:'Most na Soči'},{code:1221,ime:'Motnik'},{code:3330,ime:'Mozirje'},{code:9000,ime:'Murska Sobota '},{code:9001,ime:'Murska Sobota - poštni predali'},{code:2366,ime:'Muta'},{code:4202,ime:'Naklo'},{code:3331,ime:'Nazarje'},{code:1357,ime:'Notranje Gorice'},{code:3203,ime:'Nova Cerkev'},{code:5000,ime:'Nova Gorica '},{code:5001,ime:'Nova Gorica - poštni predali'},{code:1385,ime:'Nova vas'},{code:8000,ime:'Novo mesto'},{code:8001,ime:'Novo mesto - poštni predali'},{code:6243,ime:'Obrov'},{code:9233,ime:'Odranci'},{code:2317,ime:'Oplotnica'},{code:2312,ime:'Orehova vas'},{code:2270,ime:'Ormož'},{code:1316,ime:'Ortnek'},{code:1337,ime:'Osilnica'},{code:8222,ime:'Otočec'},{code:2361,ime:'Ožbalt'},{code:2231,ime:'Pernica'},{code:2211,ime:'Pesnica pri Mariboru'},{code:9203,ime:'Petrovci'},{code:3301,ime:'Petrovče'},{code:6330,ime:'Piran/Pirano'},{code:8255,ime:'Pišece'},{code:6257,ime:'Pivka'},{code:6232,ime:'Planina'},{code:3225,ime:'Planina pri Sevnici'},{code:6276,ime:'Pobegi'},{code:8312,ime:'Podbočje'},{code:5243,ime:'Podbrdo'},{code:3254,ime:'Podčetrtek'},{code:2273,ime:'Podgorci'},{code:6216,ime:'Podgorje'},{code:2381,ime:'Podgorje pri Slovenj Gradcu'},{code:6244,ime:'Podgrad'},{code:1414,ime:'Podkum'},{code:2286,ime:'Podlehnik'},{code:5272,ime:'Podnanos'},{code:4244,ime:'Podnart'},{code:3241,ime:'Podplat'},{code:3257,ime:'Podsreda'},{code:2363,ime:'Podvelka'},{code:2208,ime:'Pohorje'},{code:2257,ime:'Polenšak'},{code:1355,ime:'Polhov Gradec'},{code:4223,ime:'Poljane nad Škofjo Loko'},{code:2319,ime:'Poljčane'},{code:1272,ime:'Polšnik'},{code:3313,ime:'Polzela'},{code:3232,ime:'Ponikva'},{code:6320,ime:'Portorož/Portorose'},{code:6230,ime:'Postojna'},{code:2331,ime:'Pragersko'},{code:3312,ime:'Prebold'},{code:4205,ime:'Preddvor'},{code:6255,ime:'Prem'},{code:1352,ime:'Preserje'},{code:6258,ime:'Prestranek'},{code:2391,ime:'Prevalje'},{code:3262,ime:'Prevorje'},{code:1276,ime:'Primskovo '},{code:3253,ime:'Pristava pri Mestinju'},{code:9207,ime:'Prosenjakovci/Partosfalva'},{code:5297,ime:'Prvačina'},{code:2250,ime:'Ptuj'},{code:2323,ime:'Ptujska Gora'},{code:9201,ime:'Puconci'},{code:2327,ime:'Rače'},{code:1433,ime:'Radeče'},{code:9252,ime:'Radenci'},{code:2360,ime:'Radlje ob Dravi'},{code:1235,ime:'Radomlje'},{code:4240,ime:'Radovljica'},{code:8274,ime:'Raka'},{code:1381,ime:'Rakek'},{code:4283,ime:'Rateče - Planica'},{code:2390,ime:'Ravne na Koroškem'},{code:9246,ime:'Razkrižje'},{code:3332,ime:'Rečica ob Savinji'},{code:5292,ime:'Renče'},{code:1310,ime:'Ribnica'},{code:2364,ime:'Ribnica na Pohorju'},{code:3272,ime:'Rimske Toplice'},{code:1314,ime:'Rob'},{code:5215,ime:'Ročinj'},{code:3250,ime:'Rogaška Slatina'},{code:9262,ime:'Rogašovci'},{code:3252,ime:'Rogatec'},{code:1373,ime:'Rovte'},{code:2342,ime:'Ruše'},{code:1282,ime:'Sava'},{code:6333,ime:'Sečovlje/Sicciole'},{code:4227,ime:'Selca'},{code:2352,ime:'Selnica ob Dravi'},{code:8333,ime:'Semič'},{code:8281,ime:'Senovo'},{code:6224,ime:'Senožeče'},{code:8290,ime:'Sevnica'},{code:6210,ime:'Sežana'},{code:2214,ime:'Sladki Vrh'},{code:5283,ime:'Slap ob Idrijci'},{code:2380,ime:'Slovenj Gradec'},{code:2310,ime:'Slovenska Bistrica'},{code:3210,ime:'Slovenske Konjice'},{code:1216,ime:'Smlednik'},{code:5232,ime:'Soča'},{code:1317,ime:'Sodražica'},{code:3335,ime:'Solčava'},{code:5250,ime:'Solkan'},{code:4229,ime:'Sorica'},{code:4225,ime:'Sovodenj'},{code:5281,ime:'Spodnja Idrija'},{code:2241,ime:'Spodnji Duplek'},{code:9245,ime:'Spodnji Ivanjci'},{code:2277,ime:'Središče ob Dravi'},{code:4267,ime:'Srednja vas v Bohinju'},{code:8256,ime:'Sromlje '},{code:5224,ime:'Srpenica'},{code:1242,ime:'Stahovica'},{code:1332,ime:'Stara Cerkev'},{code:8342,ime:'Stari trg ob Kolpi'},{code:1386,ime:'Stari trg pri Ložu'},{code:2205,ime:'Starše'},{code:2289,ime:'Stoperce'},{code:8322,ime:'Stopiče'},{code:3206,ime:'Stranice'},{code:8351,ime:'Straža'},{code:1313,ime:'Struge'},{code:8293,ime:'Studenec'},{code:8331,ime:'Suhor'},{code:2233,ime:'Sv. Ana v Slovenskih goricah'},{code:2235,ime:'Sv. Trojica v Slovenskih goricah'},{code:2353,ime:'Sveti Duh na Ostrem Vrhu'},{code:9244,ime:'Sveti Jurij ob Ščavnici'},{code:3264,ime:'Sveti Štefan'},{code:2258,ime:'Sveti Tomaž'},{code:9204,ime:'Šalovci'},{code:5261,ime:'Šempas'},{code:5290,ime:'Šempeter pri Gorici'},{code:3311,ime:'Šempeter v Savinjski dolini'},{code:4208,ime:'Šenčur'},{code:2212,ime:'Šentilj v Slovenskih goricah'},{code:8297,ime:'Šentjanž'},{code:2373,ime:'Šentjanž pri Dravogradu'},{code:8310,ime:'Šentjernej'},{code:3230,ime:'Šentjur'},{code:3271,ime:'Šentrupert'},{code:8232,ime:'Šentrupert'},{code:1296,ime:'Šentvid pri Stični'},{code:8275,ime:'Škocjan'},{code:6281,ime:'Škofije'},{code:4220,ime:'Škofja Loka'},{code:3211,ime:'Škofja vas'},{code:1291,ime:'Škofljica'},{code:6274,ime:'Šmarje'},{code:1293,ime:'Šmarje - Sap'},{code:3240,ime:'Šmarje pri Jelšah'},{code:8220,ime:'Šmarješke Toplice'},{code:2315,ime:'Šmartno na Pohorju'},{code:3341,ime:'Šmartno ob Dreti'},{code:3327,ime:'Šmartno ob Paki'},{code:1275,ime:'Šmartno pri Litiji'},{code:2383,ime:'Šmartno pri Slovenj Gradcu'},{code:3201,ime:'Šmartno v Rožni dolini'},{code:3325,ime:'Šoštanj'},{code:6222,ime:'Štanjel'},{code:3220,ime:'Štore'},{code:3304,ime:'Tabor'},{code:3221,ime:'Teharje'},{code:9251,ime:'Tišina'},{code:5220,ime:'Tolmin'},{code:3326,ime:'Topolšica'},{code:2371,ime:'Trbonje'},{code:1420,ime:'Trbovlje'},{code:8231,ime:'Trebelno '},{code:8210,ime:'Trebnje'},{code:5252,ime:'Trnovo pri Gorici'},{code:2254,ime:'Trnovska vas'},{code:1222,ime:'Trojane'},{code:1236,ime:'Trzin'},{code:4290,ime:'Tržič'},{code:8295,ime:'Tržišče'},{code:1311,ime:'Turjak'},{code:9224,ime:'Turnišče'},{code:8323,ime:'Uršna sela'},{code:1252,ime:'Vače'},{code:3320,ime:'Velenje '},{code:3322,ime:'Velenje - poštni predali'},{code:8212,ime:'Velika Loka'},{code:2274,ime:'Velika Nedelja'},{code:9225,ime:'Velika Polana'},{code:1315,ime:'Velike Lašče'},{code:8213,ime:'Veliki Gaber'},{code:9241,ime:'Veržej'},{code:1312,ime:'Videm - Dobrepolje'},{code:2284,ime:'Videm pri Ptuju'},{code:8344,ime:'Vinica'},{code:5271,ime:'Vipava'},{code:4212,ime:'Visoko'},{code:1294,ime:'Višnja Gora'},{code:3205,ime:'Vitanje'},{code:2255,ime:'Vitomarci'},{code:1217,ime:'Vodice'},{code:3212,ime:'Vojnik'},{code:5293,ime:'Volčja Draga'},{code:2232,ime:'Voličina'},{code:3305,ime:'Vransko'},{code:6217,ime:'Vremski Britof'},{code:1360,ime:'Vrhnika'},{code:2365,ime:'Vuhred'},{code:2367,ime:'Vuzenica'},{code:8292,ime:'Zabukovje '},{code:1410,ime:'Zagorje ob Savi'},{code:1303,ime:'Zagradec'},{code:2283,ime:'Zavrč'},{code:8272,ime:'Zdole '},{code:4201,ime:'Zgornja Besnica'},{code:2242,ime:'Zgornja Korena'},{code:2201,ime:'Zgornja Kungota'},{code:2316,ime:'Zgornja Ložnica'},{code:2314,ime:'Zgornja Polskava'},{code:2213,ime:'Zgornja Velka'},{code:4247,ime:'Zgornje Gorje'},{code:4206,ime:'Zgornje Jezersko'},{code:2285,ime:'Zgornji Leskovec'},{code:1432,ime:'Zidani Most'},{code:3214,ime:'Zreče'},{code:4209,ime:'Žabnica'},{code:3310,ime:'Žalec'},{code:4228,ime:'Železniki'},{code:2287,ime:'Žetale'},{code:4226,ime:'Žiri'},{code:4274,ime:'Žirovnica'},{code:8360,ime:'Žužemberk'}];

    function formatDate(isoDateString) {
      var newDate = new Date(isoDateString);
      return newDate.getFullYear() + "-" +('0' + (newDate.getMonth() + 1)).slice(-2)+"-"+('0' + newDate.getDate()).slice(-2);
    }
    
    //če država ni slovenija nastavi pošto/občino na -1
    $scope.sprememba_drzava_rojstva = function() {
        if ($scope.vl_drzava_rojstva.ime != 'Slovenija') {
            $scope.skrij_obcinaposta_rojstva = true;
            $scope.vl_obcina_rojstva = -1;
        } else $scope.skrij_obcinaposta_rojstva = false;
    }
    $scope.sprememba_drzava_stalno = function() {
        if ($scope.vl_drzava_stalno.ime != 'Slovenija') {
            $scope.skrij_obcinaposta_stalno = true;
            $scope.vl_obcina_stalno = -1;
            $scope.vl_posta_stalno = -1;

        } else $scope.skrij_obcinaposta_stalno = false;
    }
    $scope.sprememba_drzava_zacasno = function() {
        if ($scope.vl_drzava_zacasno.ime != 'Slovenija') {
            $scope.skrij_obcinaposta_zacasno = true;
            $scope.vl_obcina_zacasno = -1;
            $scope.vl_posta_zacasno = -1;
        } else $scope.skrij_obcinaposta_zacasno = false;
    }

    $scope.potrdi_osebne_podatke = function() {
        if (!$scope.vl_ime || !$scope.vl_priimek || !$scope.vl_emso || !$scope.vl_davcna || !$scope.vl_rojstvo || //!$scope.vl_spol || !$scope.vl_telefonska ||
            !$scope.vl_email || !$scope.vl_drzava_rojstva || !$scope.vl_kraj_rojstva || 
            !$scope.vl_drzava_stalno || !$scope.vl_naslov_stalno) {
            $scope.napaka_vpisnilist = "prosim dopolni obrazec";
            return;
        }
        $scope.napaka_vpisnilist = "";

        vpislist.service_osebni_podatki(
            JSON.parse($window.atob($window.localStorage['studis'].split('.')[1])).uid,
            $scope.vl_ime, $scope.vl_priimek, $scope.vl_emso, 
            $scope.vl_davcna, formatDate($scope.vl_rojstvo),$scope.vl_spol.code, $scope.vl_telefonska,
            $scope.vl_email, $scope.vl_drzava_rojstva.ime, $scope.vl_kraj_rojstva,$scope.vl_obcina_rojstva.ime,
            $scope.vl_drzava_stalno.code, $scope.vl_posta_stalno.code, $scope.vl_obcina_stalno.code,
            $scope.vl_naslov_stalno).then(function(response){
            
            console.log(response);
            if (response.data.message) $scope.napaka_vpisnilist = response.data.message;

        }).catch(function(err, status) {
            $scope.napaka_vpisnilist = err;
            console.log("napaka pri service_kandidat");
        });

    }






    //zamenja char v stringu
    function setCharAt(str,index,chr) {
        if(index > str.length-1) return str;
        return str.substring(0,index) + chr + str.substring(index+1);
    }
    function dopolni_emso(emso) {
        if (emso == null) emso = "1";
        var manjka = 13 - emso.length;
        for (var i = 0; i < manjka; i++)
            emso = emso + "1";
        return emso;
    }
    
    $scope.primerjaj_rojstvo_emso = function() {
        var rojstvo = document.getElementById("date").value;
        var emso = $scope.vl_emso;
        emso = dopolni_emso(emso);
        
        emso = setCharAt(emso, 0, rojstvo.charAt(8));//dan rojstva
        emso = setCharAt(emso, 1, rojstvo.charAt(9));
        emso = setCharAt(emso, 2, rojstvo.charAt(5));//mesec rojstva
        emso = setCharAt(emso, 3, rojstvo.charAt(6));
        emso = setCharAt(emso, 4, rojstvo.charAt(1));//leto rojstva
        emso = setCharAt(emso, 5, rojstvo.charAt(2));
        emso = setCharAt(emso, 6, rojstvo.charAt(3));

        //TOLE JE HARDODIRANO - NUJNO SPREMENI(KODA OBČINE ROJSTVA)
        emso = setCharAt(emso, 7, '5');
        emso = setCharAt(emso, 8, '0');
        $scope.vl_emso = emso; 
    }
    $scope.primerjaj_emso_rojstvo = function() {
        var emso = $scope.vl_emso;
        emso = dopolni_emso(emso);

        var leto = 1+emso.substring(4,7);
        var mesec = (parseInt(emso.substring(2,4))-1);
        var dan = emso.substring(0,2);
        $scope.vl_rojstvo = new Date(leto, mesec, dan);        
    }


    $scope.logout = function() {
        $window.localStorage.removeItem('studis');
        $window.localStorage.removeItem("tip");
        $window.location.href = '/#/prijava';
      }        


    var trenutni_logirani_uporabnik = function() {
        if ($window.localStorage['studis']) {
          var zeton = $window.localStorage['studis'];
          return JSON.parse($window.atob(zeton.split('.')[1]));
        }
        else return null;
    }

    //v primeru da do vpisnega lista dostopa kandidat
    if (trenutni_logirani_uporabnik().tip === "Kandidat") {
        studen.service_kandidat(trenutni_logirani_uporabnik().uid).then(function(response){
            $scope.vl_vpisna = response.data.vpisnaStevilka;
            $scope.vl_ime = response.data.ime;
            $scope.vl_priimek = response.data.priimek;
            $scope.vl_email = response.data.email;

            $scope.vl_letnik = "1."; //kandidat se mnde vedno vpiše v prvi letnik
            $scope.vl_vrsta_vpisa = "Prvi vpis v letnik"; //mnde konstantno?
            $scope.vl_program_naziv = response.data.studijskiProgram.naziv;
            
        }).catch(function(err, status) {
            console.log("napaka pri service_kandidat");
        });
    }

    //v primeru da do vpisnega lista dostopa študent z žetonom
    else if (trenutni_logirani_uporabnik().tip === "Student") {
        studen.service_student(trenutni_logirani_uporabnik().uid).then(function(response){
            var id = $routeParams.id-1; //1 ali 2, odvisno do kterega vpisnega lista dostopamo
            $scope.vl_vpisna = response.data[id].student.vpisnaStevilka;
            $scope.vl_ime = response.data[id].student.ime;
            $scope.vl_priimek = response.data[id].student.priimek;
            $scope.vl_email = response.data[id].student.email;
            $scope.vl_telefonska = response.data[id].student.telefonskaStevilka;

            $scope.vl_letnik = response.data[id].letnik.letnik;
            $scope.vl_program_naziv = response.data[id].studijskiProgram.naziv;
            $scope.vl_vrsta_vpisa = response.data[id].vrstaVpisa.vrstaVpisa;
            $scope.vl_nacin_studija = response.data[id].nacinStudija.opis;//*/
            
        }).catch(function(err, status) {
            console.log("napaka pri service_kandidat");
        });
    }



}

