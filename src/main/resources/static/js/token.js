
var refresh_token_value = "";

var timeOutId;

function authentication() {
    var userName = document.getElementById("userName").value;
    var userPassword = document.getElementById("userPassword").value;
    var password = "password";

    var body = 'grant_type=' + encodeURIComponent(password) +
    '&username=' + encodeURIComponent(userName) +
    '&password=' + encodeURIComponent(userPassword);

try {

    var http = new XMLHttpRequest();
    http.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                var tokensParameters = JSON.parse(this.responseText);
                var access_token = tokensParameters.access_token;
                var refresh_token = tokensParameters.refresh_token;
                var length = tokensParameters.expires_in;
                var dateToken = new Date();
                localStorage.setItem('access_token', access_token);
                localStorage.setItem('refresh_token', refresh_token);
                localStorage.setItem('expires_in', length);
                localStorage.setItem('dateToken', dateToken);

                //timeOutId = setInterval(changeToken, 4000);
            }
        };

    http.open("POST", "http://localhost:8080/oauth/token", false);
    http.setRequestHeader("Authorization", "Basic dGVzdDpjbGllbnQtc2VjcmV0");
    http.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    http.send(body);
    localStorage.setItem('name', userName);

}
catch(error)
{
console.log(error);
console.log("");
 }
    // отправляем http://localhost:8080/oauth/token
    // берем аксес токен и в локал сторадже
}

function changeToken() {
    var http = new XMLHttpRequest();
    var refresh_token = "refresh_token";

    var body = 'grant_type=' + encodeURIComponent(refresh_token) +
    '&refresh_token=' + encodeURIComponent(refresh_token_value);

    http.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                var tokensParameters = JSON.parse(this.responseText);
                var access_token = tokensParameters.access_token;
                var old_token = localStorage.getItem('access_token');
                localStorage.setItem('old_token', old_token);
                localStorage.setItem('access_token', access_token);
                alert("string");
            }
        };

    http.open("POST", "http://localhost:8080/oauth/token", false);
    http.setRequestHeader("Authorization", "Basic dGVzdDpjbGllbnQtc2VjcmV0");
    http.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    http.send(body);
}