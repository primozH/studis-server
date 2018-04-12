
    var searchProfile = function($http){

        function isNumber(n) {
            return !isNaN(parseFloat(n)) && !isNaN(n - 0)
        }

        var getSearchRes = function (query) {
            //var res = Promise.resolve();
            // if(isNumber(query)){
            //     res = $http.get("http://localhost:8080/api/v1/student?offset=0&order=vpisnaStevilka&where=vpisnaStevilka:LIKE:" + query + "%");
            // }
            // else if(!/[^a-z]/i.test(query)){
            //     res = $http.get("http://localhost:8080/api/v1/student?offset=0&order=vpisnaStevilka&where=priimek:LIKEIC:" + query + "%");
            // }
            var res = $http.get("/api/v1/student?filter=" + query);
            console.log(res);
            return res;
        };

        // var student = null;
        // var setStudent = function(st){
        //     student = st;
        // };

        var getStudent = function(vpisnaStevilka){
            return $http.get("/api/v1/student?filter=" + vpisnaStevilka );
        };

        var getVpis = function(id){
            return $http.get("/api/v1/student/" + id + "/vpis");
        };

        return{
            getVpis: getVpis,
            getSearchRes: getSearchRes,
            //setStudent: setStudent,
            getStudent: getStudent
        };
    };

    angular
        .module('studis')
        .service('searchProfile', searchProfile);

