
var refresh_token_value = "";

var timeOutId;
var keySize = 128/32;
var iterationCount = 1000;

function generateKey(salt, passPhrase) {
  var key = CryptoJS.PBKDF2(
      passPhrase,
      CryptoJS.enc.Hex.parse(salt),
      { keySize: keySize, iterations: iterationCount });
  return key;
}

function aesEnc(salt, iv, passPhrase, plainText) {
  var key = generateKey(salt, passPhrase);
  var encrypted = CryptoJS.AES.encrypt(
      plainText,
      key,
      { iv: CryptoJS.enc.Hex.parse(iv) });
  return encrypted.ciphertext.toString(CryptoJS.enc.Base64);
}

function encrypt(passwordEntered) {

        var iv = CryptoJS.lib.WordArray.random(128/8).toString(CryptoJS.enc.Hex);
        var salt = CryptoJS.lib.WordArray.random(128/8).toString(CryptoJS.enc.Hex);

        var ciphertext = aesEnc(salt, iv, "privateKey123456", passwordEntered);

        var aesPassword = (iv + "::" + salt + "::" + ciphertext);
        var password = btoa(aesPassword);
        return password;
}

function authentication() {

    var userName = document.getElementById("userName").value;
    var userPassword = document.getElementById("userPassword").value;
    var password = "password";
    var encryptedPassword = encrypt(userPassword);

    var body = 'grant_type=' + encodeURIComponent(password) +
    '&username=' + encodeURIComponent(userName) +
    '&password=' + encodeURIComponent(encryptedPassword);

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

            }
        };
try{
    http.open("POST", "http://localhost:8080/login", false); //oauth/token", false);
    http.setRequestHeader("Authorization", "Basic dGVzdDpjbGllbnQtc2VjcmV0");
    http.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    http.send(body);
    localStorage.setItem('name', userName);
    }
    catch(error) {
    console.log(error)
    }
}