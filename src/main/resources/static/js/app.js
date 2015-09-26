angular.module('ask', [ 'ngRoute' ])
    .config(function($routeProvider, $httpProvider) {

        $routeProvider.when('/', {
            templateUrl : 'home.html',
            controller : 'home'
        }).when('/page/:page', {
            templateUrl : 'home.html',
            controller : 'home'
        }).otherwise('/');

        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

    }).controller('home', function($scope, $http, $timeout, $route, $routeParams, $location) {

        console.log($routeParams);

        var alertDanger = function (text) {
            var a = {text: text, type: 'danger'};
            $scope.alerts ? $scope.alerts.push(a) : $scope.alerts = [a];
            $timeout(function () {
                $scope.alerts = [];
            }, 3000);
        };
        $scope.ask = function ask(text) {
            $http.post('/api/questions', {text: text}).then(function (response) {
                //$route.updateParams({});
                //$scope.load();
                $location.path('/');
                $scope.newQuestionText = '';
            }, function (response) {
                console.log('error posting question', response);
                alertDanger('Question rejected');
            });
        };

        $scope.load = function () {
            $http.get('/api/questions', {params:$routeParams}).success(function(response) {
                $scope.response = response;
                if (response._embedded && response._embedded.questionList)
                    $scope.questions = response._embedded.questionList;
                if (response._links) {
                    if (response._links.next) {
                        // {href: "http://localhost:8080/api/questions?page=1&size=20&sort=lastUpdated,desc"}
                        var href = response._links.next.href;
                        if (href) {
                            $scope.next = href.substr(href.indexOf('?'));
                        }
                    }
                    if (response._links.prev) {
                        // {href: "http://localhost:8080/api/questions?page=1&size=20&sort=lastUpdated,desc"}
                        var href = response._links.prev.href;
                        if (href) {
                            $scope.prev = href.substr(href.indexOf('?'));
                        }
                    }
                }
            });
        };

        $scope.load();

    }
);
