
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
