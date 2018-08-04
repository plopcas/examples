'use strict';


// Declare app level module which depends on filters, and services
angular.module('myApp', [
  'ngRoute',
  'myApp.controllers'
]).
config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view1', {templateUrl: 'partials/view1.html', controller: 'MyCtrl1'});
  $routeProvider.otherwise({redirectTo: '/view1'});
}]);
