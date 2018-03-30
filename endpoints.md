
Base endpoint:
localhost:8080/api/v1/

- prijava:
    - POST: avtorizacija/prijava
        - json body: {email: "<posta>", geslo: "<geslo_zaenkrat_plain_text>"}

    - GET: avtorizacija/pozabljeno-geslo
        - json body: /   
         
- podatki o študentih:
    - GET student[?limit=10&offset=20&order=...] [Dokumentacija](https://github.com/kumuluz/kumuluzee-rest)
        - vsi podatki o študentih (glede na filter)
    - GET student/{id}
