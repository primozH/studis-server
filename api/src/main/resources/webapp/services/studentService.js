angular.module('studis')
.service('studen', ['$http', '$window', function($http, $window) {
    this.service_login = function(uporabnisko_ime, geslo) {
        return $http.post('/api/v1/avtorizacija/prijava',
	     {uporabniskoIme: uporabnisko_ime, geslo: geslo}).then(function(response) {
     		return response.data;
	    }).catch(function (err, status) {	    	
        });
    }


    this.service_profil = function(uid) {
    	return $http.get("/api/v1/student/" + uid).then(function(response) {
            return response;
	    }).catch(function (err, status) {
            return null;        
        });
    }

    this.service_zeton = function(uid) {
        return $http.get("/api/v1/zeton/" + uid).then(function(response) {
            return response;
        }).catch(function (err, status) {
            return null;        
        });
    }

    //dobi podatke o kandidatu, za vpisni list
    this.service_kandidat = function(uid) {
        return $http.get("/api/v1/kandidat/" + uid).then(function(response) {
            return response;
        }).catch(function (err, status) {
            return null;        
        });
    }

    //dobi podatke iz študentovega zetona, za vpisni list
    this.service_student = function(uid) {
        return $http.get("/api/v1/zeton/" + uid).then(function(response) {
            return response;
        }).catch(function (err, status) {
            return null;        
        });
    }


    this.service_predmeti = function (zeton) {
        return $http.post("http://localhost:8080/api/v1/predmetnik/", zeton).then(function(response) {
            console.log('ok');
            return response;
        }).catch(function (err, status) {
            console.log("null");
            return null;
        });
    }
}]);