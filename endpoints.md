
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
        - posodobi podatke o študentu. Obvezen je ujemanje idja v JSON in v poti. 
        Ob uspešni posodobitvi je odgovor posodobljen objekt.
        
        Primer JSONa:
        `PUT /student/32`
        ```json 
        {
            "id": 32,
            "email": "pavel.krpan@student.fri.si",
            "ime": "Pavel",
            "priimek": "Krpan",
            "emso": "2301996500052",
            "davcnaStevilka": "12345678",
            "datumRojstva": "1996-01-23",
            "spol": 1,
            "vpisnaStevilka": 63150002,
            "telefonskaStevilka": "070123123",
            "drzavaRojstva": "Slovenija",
            "obcinaRojstva": "Novo mesto",
            "krajRojstva": "Novo mesto",
            "drzavaStalno": {
            	"numericnaOznaka": 705
            },
            "obcinaStalno": {
            	"sifra": 85
            },
            "postaStalno": {
            	"postnaStevilka": 8000
            },
            "naslovStalno": "Šmihelska cesta 12",
            "drzavaZacasno": {
            	"numericnaOznaka": 705
            },
            "obcinaZacasno": {
            	"sifra": 61
            },
            "postaZacasno": {
            	"postnaStevilka": 1000
            },
            "naslovZacasno": "Mariborska 14",
            "naslovZaPosiljanjePoste": "Mariborska 15"
        }
        ```        
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
        `POST localhost:8080/api/v1/student/31/vpis`
        ```json
        {
        	"zeton": {
        		"vrstaVpisa": {
        			"sifraVpisa": 1
        		},
        		"student": {
        			"id": 31
        		}
        	},
        	"strokovniPredmeti" : [
  	           { 
        		    "sifra": 63219	
        	   }
            ],
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
        `POST localhost:8080/api/v1/student/33/vpis`
        ```json
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
        ```json
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
    - GET: kandidat/{id}/ustvariStudenta
        - se kliče, ko se kandidat prijavi v sistem; spremeni tip iz kandidata v študenta,
        ustvari potrebni žeton za vpis in ga vrne (naj se kliče takoj ob prijavi kandidata, ki se ga 
        potem preusmeri na stran za vpis študenta (vpisni list))

- žeton:
    - GET: zeton
        - vrne vse izdane žetone
    - GET: zeton/{student}[?vrsta-vpisa={vrsta-vpisa}]
        - vrne vse žetone za izbranega študenta in vrsto vpisa, če obstaja (če je query parameter podan, vrne samo enega).
            Primer: 
            `localhost:8080/api/v1/zeton/31?vrsta-vpisa=1`
    - POST: zeton/{student}
        - ustvari žeton za izbranega študenta. Vrne ustvarjeni žeton.
    - PUT: http://localhost:8080/api/v1/zeton/{studentid}?vrsta-vpisa={vrsta_vpisaid}
        - posodobi žeton za študenta studentid in vrsto vpisa vrsta_vpisaid na vrednosti,
        podane v json. Študenta ni možno spreminjati.
        - body: 
        ```json
        {
            "vrstaVpisa": {
                "sifraVpisa": 2
            },
            "student": {
                "id": 32 
            },
            "studijskiProgram": {
                "sifraEVS": 1000470
            },
            "letnik": {
                "letnik": 1
            },
            "studijskoLeto": {
            	"id": 2018
            },
            "nacinStudija": {
                "sifra": 2
            },
            "oblikaStudija": {
                "sifra": 2
            },
            "prostaIzbira": false
        }
        ```
    - DELETE: zeton/{student}?vrsta-vpisa={vrsta-vpisa}
        - izbriše žeton.
            Primer: 
            `localhost:8080/api/v1/zeton/32?vrsta-vpisa=1`
            
- predmetnik:
    -POST predmetnik/obvezni   ali   predmetnik/neobvezni
        - body: zeton
        ```json
        {
        	"studijskiProgram": {
        	    "sifraEVS": 1000468
        	},
        	"letnik": {
        	    "letnik": 2
        	},
        	"studijskoLeto": {
        	    "id": 2018
        	}
        }
        ```

- vpis (potrdila o vpisu)
	- GET student/{id}/zadnji-vpis
 	response:
	1.) succesful: 
	{ 
		json vpisa
	}
	2.) fail - izredni vpis:
	Http error - Conflict (409)
	{ 
		"err": 10,
		"msg": "izredni"
	}
	3.) fail - nepotrjen vpis:
	Http error - Conflict (409)
	{
		"err": 20,
		"msg": "nepotrjen"
	}

- vrni vse vpisane studente za zadnje studijsko leto
	- GET student/seznam-vpisanih
	response:
	[
		{student1},
		{student2},
		.
		.
		.
	]
-šifranti:
    - GET /sifranti/[drzava|obcina|posta]
        - vrne vse pošte/občine/države
        
- izvoz seznamov (pdf/csv):
    - POST /izvoz
        ```json
        {
        	"name": "Test",
        	"documentType": "CSV",
        	"metadata": {
        		"subject": {
        			"sifra": 63214,
                    "naziv": "Osnove umetne inteligence"
        		},
        		"yearOfStudy": {
        			"letnik": 3
        		},
        		"studyYear": {
        			"id": 2018,
        			"studijskoLeto": "2018/2019"
        		},
  		        "studyProgramme": {
                    "sifraEVS": 1000468,
                    "naziv": "Računalništvo in informatika UNI-1.st"
                }
        	},
        	"tableHeader": {
        		"row": ["Ime", "Priimek", "Ocena"]
        	},
        	"tableRows": [
        		{
        			"row": ["Aljaž", "Črni", "20"]
        		}
  		      // ...
        	]
        }
        ```
        
        Elemeneti objekta *metadata* niso obvezni, v kolikor pa je element naveden,
        mora zadostiti zgornjemu zgledu.

- podatki o izpitu:
    - POST /izpit
    /roki, /stevilo-polaganj, /izpit-za-leto, /zadnja-prijava (podatki o zadnji prijavi pri predmetu),
    /brisi-prijavo (Error.NOT_ACCEPTABLE ce ni v bazi oz. ce je prepozen za odjavo),
    /prijavljeni (vrne vse prijavljene studente na izpitni rok)


    Error.BAD_GATEWAY (ce katerakoli od spodnjih informacij manjka v jsonu)
    ```json
    {
        "predmetStudent": {
            "predmet":{
                "sifra": sifra_predmeta
            },
            "vpis":{
                "student":{
                    "id_uporabnik": student_id
                },
                "studijskoLeto":{
                    "id": studijsko_leto_letnica
                }
            }
        }
    }
    ```

    POST
    /vnos-rezultatov (BAD_REQUEST v kolikor so podatki(sifra predmeta, student id in studijsko leto) napacni)
    ```json
        {
            "izpit" : {
                "prijavaIzpit" : {
                    "predmetStudent": {
                        "predmet":{
                            "sifra": sifra_predmeta
                        },
                        "vpis":{
                            "student":{
                                "id_uporabnik": student_id
                            },
                            "studijskoLeto":{
                                "id": studijsko_leto_letnica
                            }
                        }
                    }
                },
                "ocenaPisno": [ocena]
            }
        }
        ```
    /vrni-vpisane-ocene (BAD_REQUEST v kolikor so podatki(sifra predmeta in studijsko leto) napacni)
    Enak POST request kot zgoraj, razen, da se ne potrebuje "id_uporabnika" in "ocenaPisno".

    Vrne se List<Student> v JSON obliki

    /razveljavi-oceno
    Enak POST request kot zgoraj, razen, da se ne potrebuje "ocenaPisno"