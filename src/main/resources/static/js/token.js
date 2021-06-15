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
                localStorage.setItem('access_token', access_token);

                window.setTimeout(changeToken, 4000, refresh_token);
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

function changeToken(token) {
    var http = new XMLHttpRequest();
    var refresh_token = "refresh_token";

    var body = 'grant_type=' + encodeURIComponent(refresh_token) +
    '&refresh_token=' + encodeURIComponent(token);

    http.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                var tokensParameters = JSON.parse(this.responseText);
                var access_token = tokensParameters.access_token;
                var old_token = localStorage.getItem('access_token');
                localStorage.setItem('old_token', old_token);
                localStorage.setItem('access_token', access_token);
                //alert("string");
            }
        };

    http.open("POST", "http://localhost:8080/oauth/token", false);
    http.setRequestHeader("Authorization", "Basic dGVzdDpjbGllbnQtc2VjcmV0");
    http.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    http.send(body);
}