/**
 * Authorization service.
 */
define(["angular", "common"], function (angular) {
    var mod = angular.module("authorization.services", ["yourprefix.common"]);
    mod.factory("authorizationService", ["$http", "$q", "playRoutes", function ($http, $q, playRoutes) {
        var AuthorizationService = function () {
            var self = this;

            self.login = function (provider) {
                return playRoutes.controllers.Authorization.login(provider).post().then(function (response) {
                    return response.data;
                });
            };

            self.logout = function () {
                return playRoutes.controllers.Authorization.logout().post().then(function (response) {
                    return response.data;
                });
            };

            self.ping = function () {
                /*return playRoutes.controllers.Application.ping().get().then(function (response) {
                 return response;
                 }); */
                return $http.get("/ping")
                    .success(function (data, status, headers, response) {
                    })
                    .error(function (data, status, response) {
                    });
            };

        };
        return new AuthorizationService();
    }]);

    /**
     * If the current route does not resolve, go back to the start page.
     */
    var handleRouteError = function ($rootScope, $location) {
        $rootScope.$on("$routeChangeError", function (e, next, current) {
            $location.path("/");
        });
    };
    handleRouteError.$inject = ["$rootScope", "$location"];
    mod.run(handleRouteError);
    return mod;
});
