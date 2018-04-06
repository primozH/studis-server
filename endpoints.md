
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

- kandidati:
    - POST: kandidat/nalozi
        - pričakuje datoteko ".txt" s kandidati, vrne seznam uspešno uvoženih kandidatov. Header `X-Total-Count` je nastavljen 
        na število uspešno uvoženih
    - GET: kandidat/neuspesni
        - vrne .txt datoteko z neuspešnimo uvoženimi kandidati

- žeton:
    - GET: zeton
        - vrne vse izdane žetone
    - GET: zeton/{student}/{vrsta_vpisa}
        - vrne žeton za izbranega študenta in vrsto vpisa, če obstaja.
            Primer: 
            `localhost:8080/api/v1/zeton/31/1`
    - POST: zeton/{student}
        - ustvari žeton za izbranega študenta. Vrne ustvarjeni žeton.
    - PUT: zeton
        - posodobi žeton
        - body: zeton
    - DELETE: zeton?student={student}&vrsta-vpisa={vrsta-vpisa}
        - izbriše žeton.
            Primer: 
            `localhost:8080/api/v1/zeton?student=33&vrsta-vpisa=1`