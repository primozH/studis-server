
    angular
        .module('studis')
        .controller('searchCtrl', searchCtrl);

    function searchCtrl($location, searchProfile, $scope, $window){
        var vm = this;

        //za header
        $scope.tip_referentka = $window.localStorage.getItem("tip") === "Referent";
        $scope.tip_ucitelj = $window.localStorage.getItem("tip") === "Ucitelj";

        $scope.logout = function() {
            $window.localStorage.removeItem('studis');
            $window.localStorage.removeItem("tip");
            $window.location.reload();
            $window.location.href = '/#/prijava';
        };
        //za header

        vm.executeSearch = function(query){
            searchProfile.getSearchRes(query)
                .then(
                    function success(response){
                        console.log("response in searchController:");
                        console.log(response);
                        if(response !== undefined) {
                            vm.searchRes = response.data;
                            $scope.emptyRes = response.data.length === 0;
                        }
                    },
                    function error(error){
                        console.log(error);
                    }
                );
            $scope.query = null;
        };

        vm.openProfile = function(vpisnaStevilka){
            $location.path("/profil/" + vpisnaStevilka);
        };
    }
