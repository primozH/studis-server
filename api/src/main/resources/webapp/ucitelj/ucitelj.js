'use strict';

angular
    .module('studis')
    .controller('UciteljCtrl', UciteljCtrl);
 
angular.module('studis').directive('onReadFile', function ($parse) {
  return {
    restrict: 'A',
    scope: false,
    link: function(scope, element, attrs) {
      var fn = $parse(attrs.onReadFile); 
      element.on('change', function(onChangeEvent) {
        var reader = new FileReader(); 
        reader.onload = function(onLoadEvent) {
          scope.$apply(function() {
            fn(scope, {$fileContent:onLoadEvent.target.result});
          });
        }; 
        reader.readAsText((onChangeEvent.srcElement || onChangeEvent.target).files[0]);
      });
    }
  };
})

function UciteljCtrl($scope, $http, $window, refe) {
  var vsebina_datoteke = null;	

  $scope.logout = function() {
    $window.localStorage.removeItem('studis');
    $window.localStorage.removeItem("tip");
    $window.location.reload();
    $window.location.href = '/#/prijava';
  }

	$scope.showContent = function($fileContent){
    $scope.prikaz_datoteke = $fileContent;
    vsebina_datoteke = $fileContent;
  };
  	
  /*$scope.uvoz_podatkov = function(file){
    var formData = new FormData();
    formData.append('file', file);
    $http.post('http://localhost:8080/api/v1/kandidat/nalozi', formData, {
       transformRequest: angular.identity,
       headers: {'Content-Type': undefined}
    }).then(function (response) {
       console.log("uspešno");
    });
  }; //*/


};