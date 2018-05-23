'use strict';

angular
    .module('studis')
    .controller('PrijavaCtrl', PrijavaCtrl);

	function PrijavaCtrl($scope, $http, $window, auth, $location) {
	$scope.klik_pozabljeno = false;

	$scope.jeVpisan = function() {
	    if($window.localStorage['studis']) return true;
	    return false;
	}

	$scope.trenutni_logirani_uporabnik = function() {
		if ($scope.jeVpisan()) {
	      var zeton = $window.localStorage['studis'];
	      return JSON.parse($window.atob(zeton.split('.')[1]));
	    }
	    else return null;
	}

	$scope.login_funkcija = function() {
	    if (!$scope.uporabnisko_ime || !$scope.geslo) {
	      $scope.pokazi_napako_login = true;
	      $scope.login_status = "Prosim vnesite uporabniško ime in geslo za logiranje v sistem";
	      return;
	    }
	    auth.service_login($scope.uporabnisko_ime, $scope.geslo).then(function(response){
        console.log(response);       
        //če prijava ni uspešna
        if (response.access_token === undefined) {
          $scope.pokazi_napako_login = true;
          if (response.status == 401) $scope.login_status = "Napačno geslo";
          if (response.status == 403 && document.getElementById("counter").style.display === "none"){
                  $scope.login_status = "Žal, nimate dostopa do našega sistema nadaljnih ";
                  start(response.data.preostalCas);
              }
          if (response.status == 404) $scope.login_status = "Napačno uporabniško ime";
        }

        //v primeru da prijava je uspešna (dobimo token nazaj)
    	else {
          $window.localStorage['studis'] = response.access_token;
        		if ($scope.trenutni_logirani_uporabnik().tip == "Student") {
        			$window.localStorage.setItem("tip", "Student");
                    $location.path("/student");
        		}
        		else if ($scope.trenutni_logirani_uporabnik().tip == "Referent") {
        			$window.localStorage.setItem("tip", "Referent");
        			$location.path("/referentka");
        		}
        		else if ($scope.trenutni_logirani_uporabnik().tip == "Skrbnik") {
        			$window.localStorage.setItem("tip", "Skrbnik");
        			$location.path("/skrbnik");
        		}
        		else if ($scope.trenutni_logirani_uporabnik().tip == "Ucitelj") {
        			$window.localStorage.setItem("tip", "Ucitelj");
        			$location.path("/ucitelj");
        		}
        		else if ($scope.trenutni_logirani_uporabnik().tip == "Kandidat") {
        			$window.localStorage.setItem("tip", "Kandidat");
        			$location.path("/student");
        		}
        }

	    }).catch(function(error) {	    	
	    });
	};

	function countdown(timerId, timeToCount){
        var now = new Date().getTime();
        var i = document.getElementById('counter');
        var s = document.getElementById('seconds');
        i.innerHTML = Math.floor((timeToCount - now)/1000);
        if (parseInt(i.innerHTML)<= 0) {
            clearInterval(timerId);
            i.style.display = 'none';
            s.style.display = 'none';
            $('#inputField').prop('disabled', false);
            $scope.$apply(function () {
                $scope.pokazi_napako_login = false;
            });
        }
    }
    function start(preostaliCas){
        var i = document.getElementById('counter');
        i.style.display = 'inline-block';
        i.innerHTML = preostaliCas;
        var s = document.getElementById('seconds');
        s.style.display = 'inline-block';
        var timeToCount = new Date().getTime() + 1000 * preostaliCas;
        var timerId = setInterval(function(){
            countdown(timerId, timeToCount);
        },1000);
    }


    $scope.logoutFunkcija = function() {
	    $window.localStorage.removeItem('studis');
	    $scope.vpisanStudent = false;
	}

	$scope.pozabljeno_geslo = function() {
		$scope.klik_pozabljeno = true;
	}

	$scope.poslji_geslo = function() {
		auth.service_geslo_reset($scope.pozabljeno_email).then(function(response){
			$scope.status_pozabljeno_geslo = "geslo ponastavljeno"
	    }).catch(function(err, status) {
	    	$scope.status_pozabljeno_geslo = "e-maila ni v bazi";
	    });
	}

};