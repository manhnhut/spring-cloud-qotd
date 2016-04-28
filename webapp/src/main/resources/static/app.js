var appModule = angular.module('myApp', []);

appModule.controller('MainCtrl', ['mainService','$scope', '$interval', function(mainService, $scope, $interval) {
    $scope.update = function() {
        mainService.random().then(function(quote) {
            $scope.quote = quote;
        });
    }

    $scope.update();

    $scope.stop = $interval(function() {
        $scope.update();
    }, 5000);
}]);

appModule.service('mainService', function($http) {
    return {
        random : function() {
            return $http.get('/quote').then(function(response){
                return response.data;
            });
        }
    };
});