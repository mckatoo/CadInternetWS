angular.module("CadInternetApp", [])
        .value('urlBase', 'http://localhost:8084/CadInternetWS/ws/')
        .controller("UserController", function ($http, urlBase) {
            var self = this;
            self.usuario = 'Milton Carlos Katoo';

            self.users = [];
            self.user = undefined;

            self.novo = function () {
                self.user = {};
            };

            self.salvar = function () {
                var metodo = 'POST';
                if (self.user.id) {
                    metodo = 'PUT';
                }

                $http({
                    method: metodo,
                    url: urlBase + 'users/',
                    data: self.user
                }).then(function successCallback(response) {
                    self.atualizarTabela();
                }, function errorCallback(response) {
                    self.ocorreuErro();
                });
            };

            self.alterar = function (user) {
                self.user = user;
            };

            self.deletar = function (user) {
                self.user = user;

                $http({
                    method: 'DELETE',
                    url: urlBase + 'users/' + self.user.id + '/'
                }).then(function successCallback(response) {
                    self.atualizarTabela();
                }, function errorCallback(response) {
                    self.ocorreuErro();
                });
            };

            self.ocorreuErro = function () {
                alert("Ocorreu um erro inesperado!");
            };

            self.atualizarTabela = function () {
                $http({
                    method: 'GET',
                    url: urlBase + 'users/'
                }).then(function successCallback(response) {
                    self.users = response.data;
                    self.user = undefined;
                }, function errorCallback(response) {
                    self.ocorreuErro();
                });
            };

            self.activate = function () {
                self.atualizarTabela();
            };
            self.activate();
});