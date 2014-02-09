/**
 * Authorization package module.
 * Manages all sub-modules so other RequireJS modules only have to import the package.
 */
define(["angular", "./services"], function (angular) {
    return angular.module("yourprefix.authorization", ["ngCookies", "authorization.services"]);
});
