'use strict';

angular
    .module('studis')
    .controller('ReferentkaCtrl', ReferentkaCtrl);
 

function ReferentkaCtrl($scope, $http, $window, refe) {
  var vsebina_datoteke = null;	

  $scope.logout = function() {
    $window.localStorage.removeItem('studis');
    $window.localStorage.removeItem("tip");
    $window.location.reload();
    $window.location.href = '/#/prijava';
  }


  $scope.prikazi_zetone = function() {
  	$scope.pogled_zetoni = true;

  	$http.get('http://localhost:8080/api/v1/zeton')
    .then(function(response){
      console.log(response);
      $scope.zetoni = response.data;      
    })
    .catch(function(err){
      $scope.error_zetoni = "prišlo je do napake pri prikazi_zetone";
    });
  }

  $scope.zbrisi_zeton = function(zeton) {
  	$http.delete('http://localhost:8080/api/v1/zeton/'+zeton.student.id+'?vrsta-vpisa='+zeton.vrstaVpisa.sifraVpisa)
    .then(function(response){
    	var index = $scope.zetoni.indexOf(zeton);
  		$scope.zetoni.splice(index, 1);      
    })
    .catch(function(err){
      $scope.error_zetoni = "prišlo je do napake pri prikazi_zetone";
    });
  }

};