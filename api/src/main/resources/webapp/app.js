
    var app = angular.module('studis', ['ngRoute', 'ui.bootstrap']);

    app.config(function($routeProvider, $windowProvider) {
        var $window = $windowProvider.$get();

        $routeProvider
            .when('/iskanje', {
                templateUrl: "/search/search.html",
                controller: "searchCtrl",
                controllerAs: "vm"
            })
            .when('/profil/:vpisnaStevilka', {
                templateUrl: "/profile/profile.html",
                controller: "profileCtrl",
                controllerAs: "vm"
            })
            .when('/prijava', {
            templateUrl: 'prijava/prijava.html',
            controller: 'PrijavaCtrl'
            })
            .when('/referentka', {
                templateUrl: 'referentka/referentka.html',
                controller: 'ReferentkaCtrl',
                resolve: {
                    function(){
                        if (!$window.localStorage.getItem("tip") || !($window.localStorage.getItem("tip") === "Referent"))  {
                            console.log("Vpiši se kot referentka, drugače nemoreš do /referentka");
                            $window.location.href = '/#/prijava';
                        }
                    }
                }                
            })
            .when('/student', {
                templateUrl: 'student/student.html',
                controller: 'StudentCtrl',
                resolve: {
                    function(){
                        console.log($window.localStorage.getItem("tip"));
                        if (!$window.localStorage.getItem("tip") || !($window.localStorage.getItem("tip") === "Student" || $window.localStorage.getItem("tip") === "Kandidat"))  {
                            console.log("Vpiši se kot student, drugače nemoreš do /student");
                            $window.location.href = '/#/prijava';
                        }
                    }
                } 
            })
            .when('/skrbnik', {
                templateUrl: 'skrbnik/skrbnik.html',
                controller: 'SkrbnikCtrl',
                resolve: {
                    function(){
                        if (!$window.localStorage.getItem("tip") || !($window.localStorage.getItem("tip") === "Skrbnik"))  {
                            console.log("Vpiši se kot skrbnik, drugače nemoreš do /skrbnik");
                            $window.location.href = '/#/prijava';
                        }
                    }
                }                
            })
            .when('/ucitelj', {
                templateUrl: 'ucitelj/ucitelj.html',
                controller: 'UciteljCtrl',
                resolve: {
                    function(){
                        if (!$window.localStorage.getItem("tip") || !($window.localStorage.getItem("tip") === "Ucitelj"))  {
                            console.log("Vpiši se kot ucitelj, drugače nemoreš do /ucitelj");
                            $window.location.href = '/#/prijava';
                        }
                    }
                }
            })
            .when('/vpisnilist/:id', {
                templateUrl: 'vpisniList/vpisniList.html',
                controller: 'VpisniListCtrl',
                resolve: {
                    function(){
                        if (!$window.localStorage.getItem("tip") || !($window.localStorage.getItem("tip") === "Kandidat" || $window.localStorage.getItem("zeton") === "ima1" 
                            || $window.localStorage.getItem("zeton") === "ima2"))  {
                            console.log("Vpiši se kot kandidat ali študent z žetonom, drugače nemoreš do /vpisnilist");
                            $window.location.href = '/#/prijava';
                        }
                    }
                }                
            })
            .when('/vpisnilistpredmetnik/:id', {
                templateUrl: 'vpisniList/vpisnilistpredmetnik.html',
                controller: 'VpisniListCtrl',
                resolve: {
                    function(){
                        if (!$window.localStorage.getItem("tip") || !($window.localStorage.getItem("tip") === "Kandidat" || $window.localStorage.getItem("zeton") === "ima1"
                            || $window.localStorage.getItem("zeton") === "ima2"))  {
                            console.log("Vpiši se kot kandidat ali študent z žetonom, drugače nemoreš do /vpisnilistpredmetnik");
                            $window.location.href = '/#/prijava';
                        }
                    }
                }
            })
            .when('/zeton/:id/:vrstaVpisa', {
                templateUrl: 'token/token.html',
                controller: 'tokenCtrl',
                controllerAs: "vm"
            })
            .when('/zetoni', {
                templateUrl: 'tokens/tokens.html',
                controller: 'tokensCtrl',
                controllerAs: "vm"
            })
            .when('/zeton_uredi/:id', {
                templateUrl: 'zeton/zeton.html',
                controller: 'ZetonCtrl',
                resolve: {
                    function(){
                        if (!$window.localStorage.getItem("tip") || !($window.localStorage.getItem("tip") === "Referent"))  {
                            console.log("Vpiši se kot referentka, drugače nemoreš do /zeton_uredi");
                            $window.location.href = '/#/prijava';
                            return;
                        }
                    }
                }      
                          
            })
            .otherwise({redirectTo: '/prijava'});

    });
