
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
                        
- kandidati:
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
            `localhost:8080/api/v1/zeton?student=33&vrsta-vpisa=1`