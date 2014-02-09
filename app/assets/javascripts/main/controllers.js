/**
 * Main controllers.
 */
define([], function () {
    "use strict";

    /** Controls the index page */
    var HomeCtrl = function ($scope, $rootScope, $location, helper, $cookies, authorizationService, $http) {

        console.log(helper.sayHi());
        $location.path("/");
        $rootScope.pageTitle = "Welcome";

        var token = $cookies["XSRF-TOKEN"];
        if (token !== undefined)
            $http.defaults.headers.common["X-AUTH-TOKEN"] = token;

        $scope.login = function (provider) {
            authorizationService.login(provider).then(function (path) {
                //$location.url(path);
                window.location.replace(path);
            });
        };

        $scope.logout = function () {
            $cookies["XSRF-TOKEN"] = undefined;
            $http.defaults.headers.common["X-AUTH-TOKEN"] = undefined;
            authorizationService.logout();
            //$location.path("/");
        };

        $scope.ping = function () {
            authorizationService.ping().success(function (response) {
                console.log(response);
                alert(response.message);
            }).error(function (response) {
                console.log(response);
                alert(response.err);
            });
        };

    };
    HomeCtrl.$inject = ["$scope", "$rootScope", "$location", "helper", "$cookies", "authorizationService", "$http"];

    /** Controls the header */
    var HeaderCtrl = function ($scope, authorizationService, helper, $location, $cookies, $http) {


    };
    HeaderCtrl.$inject = ["$scope", "authorizationService", "helper", "$location", "$cookies", "$http"];

    /** Controls the footer */
    var FooterCtrl = function ($scope) {
    };
    FooterCtrl.$inject = ["$scope"];

    return {
        HeaderCtrl: HeaderCtrl,
        FooterCtrl: FooterCtrl,
        HomeCtrl: HomeCtrl
    };

});
