angular.module('studis')
.service('auth', ['$http', '$window', function($http, $window) {
    this.service_login = function(uporabnisko_ime, geslo) {
        return $http.post('/api/v1/avtorizacija/prijava',
	     {uporabniskoIme: uporabnisko_ime, geslo: geslo}).then(function(response) {
     		return response.data;
	    }).catch(function (err) {
            return err;
        });
    }


    this.service_geslo_reset = function(email2) {
    	return $http.post('/api/v1/avtorizacija/pozabljeno-geslo',
            {email: email2}).then(function(response) {
                return 200;
	    }).catch(function (err, status) {
            return 404;        
        });
    }
}]);