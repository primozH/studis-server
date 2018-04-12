'use strict';

angular
    .module('studis')
    .controller('SkrbnikCtrl', SkrbnikCtrl);
 
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

angular.module('studis').directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;
            
            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]).config(['$compileProvider', function ($compileProvider) {
    $compileProvider.aHrefSanitizationWhitelist(/^\s*(|blob|):/);
}]);


function SkrbnikCtrl($scope, $http, $window) {
  




  var vsebina_datoteke = null;
  //prikaže neuspešno uvožene
  $scope.napaka_uvozeni_zapisi = false;

  $scope.logout = function() {
    $window.localStorage.removeItem('studis');
    $window.localStorage.removeItem("tip");
    $window.location.reload();
    $window.location.href = '/#/prijava';
  }

	$scope.showContent = function($fileContent){
    $scope.prikaz_datoteke = $fileContent;
    vsebina_datoteke = $fileContent;

    $scope.dovoliUvoz = true;
  };
  	
  $scope.uploadFile = function(){
    $scope.error_uvoz = "nalaganje poteka..."
    var file = $scope.myFile;
    if (!file) {
      $scope.error_uvoz = "Izberi datoteko za uvoz";
      return;
    }

    var fd = new FormData();
    fd.append('file', file);
    $http.post('/api/v1/kandidat/nalozi', fd, {
        transformRequest: angular.identity,
        headers: {'Content-Type': undefined}
    })
    .then(function(response){
      console.log(response);
      $scope.error_uvoz = "uspešno uvoženi podatki"
      $scope.uvozeni_zapisi_naslov = true;
      $scope.vrnjeni_zapisi = response.data;


      $scope.steviloUvozenih = response.data.length;
      $scope.trenutnaStran = 1;
      $scope.zapisovNaStran = 5;
      $scope.$watch("trenutnaStran", function() {
        nastaviStranZapise($scope.trenutnaStran);
      });

      function nastaviStranZapise(page) {
        var pagedData = response.data.slice(
          (page - 1) * $scope.zapisovNaStran,
          page * $scope.zapisovNaStran
        );
        $scope.aZapisi = pagedData;
      }


        //prikaz neuspešnih
        $http.get('/api/v1/kandidat/neuspesni')
        .then(function(response){
          $scope.neuspesni_fajl = response.data;
          
        })
        .catch(function(err){
          $scope.error_uvoz = "prišlo je do napake pri prenosu neuspešnih";
        });
    })
    .catch(function(err){
      $scope.error_uvoz = "prišlo je do napake pri uvozu"
    });

  };
};