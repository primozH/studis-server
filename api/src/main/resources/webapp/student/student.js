'use strict';


angular
    .module('studis')
    .controller('StudentCtrl', StudentCtrl);

function StudentCtrl($scope, $window, $http, studen, $location) {

	$scope.logout = function() {
		$window.localStorage.removeItem('studis');
	    $window.localStorage.removeItem("tip");
	    $window.localStorage.removeItem("zeton");
        $location.path("/prijava");
	};

	var trenutni_logirani_uporabnik = function() {
		if ($window.localStorage['studis']) {
	      var zeton = $window.localStorage['studis'];
	      return JSON.parse($window.atob(zeton.split('.')[1]));
	    }
	    else return null;
	}

	var vpisan = trenutni_logirani_uporabnik();
	if (vpisan.tip === 'Kandidat') {
		$scope.kandidat = true;

        studen.service_kandidat(vpisan.uid).then(function(response){
            console.log(response);
            $scope.vpisna_kandidata = response.data.vpisnaStevilka;
            $scope.ime_kandidata = response.data.ime;
            $scope.priimek_kandidata = response.data.priimek;
            $scope.vpisni_list1 = response.data.studijskiProgram.naziv;
        }).catch(function(err, status) {
            console.log("napaka pri service_kandidat");
        });
	}

    else if (vpisan.tip == 'Student') {
        studen.service_profil(vpisan.uid).then(function(response){
        	$scope.vpisna_studenta = response.data.vpisnaStevilka;
        	$scope.ime_studenta = response.data.ime;
        	$scope.priimek_studenta = response.data.priimek;
        }).catch(function(err, status) {
        	console.log("napaka pri service_profil");
        });

        //preverjamo če ima žeton
        studen.service_zeton(vpisan.uid).then(function(response){
            if (response.data.length > 0) {
                $scope.student_zeton1 = response.data[0];
                $scope.ima_zeton1 = true;
                $scope.vpisni_list1 = response.data[0].vrstaVpisa.vrstaVpisa;
                //ta vrstica nastavi da studentu dovolimo dostop do /vpisnilist/1
                $window.localStorage.setItem("zeton", "ima1");
                //ker ima lahko več kot 1 žeton
                if (response.data.length > 1) {
                    $scope.student_zeton2 = response.data[1];
                    $scope.ima_zeton2 = true;
                    $scope.vpisni_list2 = response.data[1].vrstaVpisa.vrstaVpisa;
                    $window.localStorage.setItem("zeton", "ima2");
                }
            }
            else
                $scope.student_zeton = "trenutno nimaš na voljo nobenega žetona za vpis";
        }).catch(function(err, status) {
            console.log("napaka pri service_zeton");
        });
    }



    //prikaže podrobnejši profil na /profil/{vpisnaStevilka}
    $scope.prikazi_moj_profil = function() {
    	$location.path('/profil/'+$scope.vpisna_studenta);
    }
	
};