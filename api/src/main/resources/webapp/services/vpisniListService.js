angular.module('studis')
.service('vpislist', ['$http', '$window', function($http, $window) {
    this.service_osebni_podatki = function(id, ime, priimek, emso, davcnaStevilka, rojstvo, spol,
    telefonska, email, drzavaRojstva, krajRojstva, obcinaRojstva, drzavaStalno, postaStalno, obcinaStalno,
    naslovStalno) {
    	return $http.put("/api/v1/student/" + id, 
            {
           "id": 31,
           "ime": ime,
           "priimek": priimek,
           "emso": "2301996500052",
           "davcnaStevilka": davcnaStevilka,
           "datumRojstva": rojstvo,
           "spol": spol,
           "email": email,
           "telefonskaStevilka": telefonska,
           "drzavaRojstva": drzavaRojstva,
           "krajRojstva": krajRojstva,
           "obcinaRojstva": obcinaRojstva,
           "drzavaStalno": drzavaStalno,
           "postaStalno": postaStalno,
           "obcinaStalno": obcinaStalno,
           "naslovStalno": naslovStalno,
           "drzavaZacasno": 705,
           "postaZacasno": 1000,
           "obcinaZacasno": 61,
           "naslovZacasno": "Gosposvetska 12",
           "naslovZaPosiljanjePoste": "Krajčeva ulica 15"
            }


            ).then(function(response) {
            return response;
	    }).catch(function (err, status) {
            return err;        
        });
    }
}]);