angular.module('studis')
.service('refe', ['$http', function($http) {

    //vrne vse žetone
    this.service_zetoni = function() {
        return $http.get('/api/v1/zeton').then(function(response) {
            return response.data;
        });
    }
}]);