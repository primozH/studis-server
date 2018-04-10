
Base endpoint:
localhost:8080/api/v1/

- prijava:
    - POST: avtorizacija/prijava
        - json body: {uporabniskoIme: "<uporabniskoIme>", geslo: "<geslo>"}

    - POST: avtorizacija/pozabljeno-geslo
        - json body: {email: "<email>"}
         
- podatki o študentih:
    - GET student[?filter=]
        - vsi podatki o študentih (glede na filter)
    - GET student/{id}
    - PUT: /student/{id}
        - posodobi podatke o studentu. Obvezen je ujemanje idja v JSON in v poti. Podatki, ki jih lahko spreminjamo:
        ```json 
        {
            "id": 31, // se ne spreminja
            "ime": "Primož",
            "priimek": "Hrovat",
            "emso": "2301996500052", // veljaven emšo, skladen z datumom rojstva in spolom
            "davcnaStevilka": "12345678", //
            "datumRojstva": "1996-01-23",
            "spol": "MOSKI",
            "telefonskaStevilka": "070123123",
            "drzavaRojstva": "Slovenija", // prost vnos
            "krajRojstva": "Novo mesto", // prost vnos
            "obcinaRojstva": "Novo mesto", // prost vnos
            "drzavaStalno": 705, // numericna oznaka drzave
            "postaStalno": 8000, // postna stevilka
            "obcinaStalno": 85, // sifra obcine
            "naslovStalno": "Krajčeva ulica 15", // prost vnos
            "drzavaZacasno": 705, 
            "postaZacasno": 1000,
            "obcinaZacasno": 61,
            "naslovZacasno": "Gosposvetska 12",
            "naslovZaPosiljanjePoste": "ZACASNI"
        } 
        ```
        
        naslovZaPosiljanjePoste: [STALNI|ZACASNI]
        spol: [MOSKI|ZENSKI]
        
    - GET: student/{id}/vpis:
        - vrne tabelo vpisov za danega študenta
    - POST: student/{id}/vpis:
        - vpiše študenta
        - json body:
        ```json
        {
        	"zeton": {},
        	"strokovniPredmet" : {},
        	"splosniPredmeti" : [],
        	"modulskiPredmeti": []
        }
        ```
        **Example 1 (vpis v 2. letnik)**:
        ```http request
        POST localhost:8080/api/v1/student/31/vpis
        
        {
        	"zeton": {
        		"vrstaVpisa": {
        			"sifraVpisa": 1
        		},
        		"student": {
        			"id": 31
        		}
        	},
        	"strokovniPredmet" : { 
        		"sifra": 63219	
        	},
        	"splosniPredmeti" : [
        		{
        			"sifra": 63222
        		},
        		{
        			"sifra": 63241
        		}
        	]
        }
        ```
        
        **Example 2 (vpis v 3. letnik, prosta izbira)**:
        ```
        POST localhost:8080/api/v1/student/33/vpis
        {
        	"zeton": {
        		"vrstaVpisa": {
        			"sifraVpisa": 1
        		},
        		"student": {
        			"id": 33
        		}
        	},
        	"splosniPredmeti" : [
        		{
        			"sifra": 63225
        		}
        	],
        	"modulskiPredmeti": [
        		{
        			"sifra": 63249
        		},
        		{
        			"sifra": 63252
        		},	
        		{
        			"sifra": 63254
        		},
        		{
        			"sifra": 63255
        		},
        		{
        			"sifra": 63260
        		},
        		{
        			"sifra": 63268
        		}
        	]
        }
        ```
        
        **Example 3 (vpis v 3. letnik):**
        ```
        POST localhost:8080/api/v1/student/34/vpis
        
        {
        	"zeton": {
        		"vrstaVpisa": {
        			"sifraVpisa": 1
        		},
        		"student": {
        			"id": 34
        		}
        	},
        	"splosniPredmeti" : [
        		{
        			"sifra": 63225
        		}
        	],
        	"modulskiPredmeti": [
        		{
        			"sifra": 63249
        		},
        		{
        			"sifra": 63250
        		},	
        		{
        			"sifra": 63251
        		},
        		{
        			"sifra": 63252
        		},
        		{
        			"sifra": 63226
        		},
        		{
        			"sifra": 63253
        		}
        	]
        }
        ```
                        
- kandidati:
    - GET: kandidat
        - vrne vse kandidate
    - GET: kandidat/{id}
        - vrne kandidata z idjem {id}
    - POST: kandidat/nalozi
        - pričakuje datoteko ".txt" s kandidati, vrne seznam uspešno uvoženih kandidatov. Header `X-Total-Count` je nastavljen 
        na število uspešno uvoženih
    - GET: kandidat/neuspesni
        - vrne .txt datoteko z neuspešnimo uvoženimi kandidati

- žeton:
    - GET: zeton
        - vrne vse izdane žetone
    - GET: zeton/{student}[?vrsta-vpisa={vrsta-vpisa}]
        - vrne vse žetone za izbranega študenta in vrsto vpisa, če obstaja (če je query parameter podan, vrne samo enega).
            Primer: 
            `localhost:8080/api/v1/zeton/31?vrsta-vpisa=1`
    - POST: zeton/{student}
        - ustvari žeton za izbranega študenta. Vrne ustvarjeni žeton.
    - PUT: http://localhost:8080/api/v1/zeton/32
        - posodobi žeton
        - body: 
        ```json
        {
            "vrstaVpisa": {
                "sifraVpisa": 2 // sifrant
            },
            "student": {
                "id": 32 
            },
            "studijskiProgram": {
                "sifraEVS": 1000470 // sifrant
            },
            "letnik": {
                "letnik": 1
            },
            "studijskoLeto": {
            	"id": 2018 // studijsko leto - sifrant v bazi
            },
            "nacinStudija": {
                "sifra": 2 // sifrant
            },
            "oblikaStudija": {
                "sifra": 2 // sifrant
            },
            "prostaIzbira": false
        }
        ```
    - DELETE: zeton/{student}?vrsta-vpisa={vrsta-vpisa}
        - izbriše žeton.
            Primer: 
            `localhost:8080/api/v1/zeton/32?vrsta-vpisa=1`