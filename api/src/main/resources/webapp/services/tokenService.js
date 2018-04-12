
var tokenService = function($http){

    var getToken = function(id, vrstaVpisa){
        return $http.get("/api/v1/zeton/" + id + "?vrsta-vpisa=" + vrstaVpisa);
    };

    var deleteToken = function(id, vrstaVpisa){
        return $http.delete("/api/v1/zeton/" + id + "?vrsta-vpisa=" + vrstaVpisa);
    };

    var putToken = function(id, vrstaVpisa, data){
        console.log("sending put request to " + "/api/v1/zeton/" + id + "?vrsta-vpisa=" + vrstaVpisa);
        return $http.put("/api/v1/zeton/" + id + "?vrsta-vpisa=" + vrstaVpisa, data);
    };

    var getTokens = function(){
        return $http.get("/api/v1/zeton");
    };

    var postToken = function(id){
        return $http.post("/api/v1/zeton/" + id);
    };

    return{
        postToken: postToken,
        putToken: putToken,
        getToken: getToken,
        getTokens: getTokens,
        deleteToken: deleteToken
    };
};


angular
    .module('studis')
    .service('tokenService', tokenService);