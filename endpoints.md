
Base endpoint:
localhost:8080/api/v1/

- prijava:
    - POST: prijava/preveriPrijavo
        - json body: {email: "<posta>", geslo: "<geslo_zaenkrat_plain_text>"}

    - GET: prijava/posljiGesloNaMail
        - json body: /   
         
- podatki o študentih:
    - GET student[?limit=10&offset=20&order=...] [Dokumentacija](https://github.com/kumuluz/kumuluzee-rest)
        - vsi podatki o študentih (glede na filter)
    - GET student/{id}
