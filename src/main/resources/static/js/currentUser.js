var userName = '';
var userSurname='';
var userRole='';
var userProjects;

function changeToken() {
    var http = new XMLHttpRequest();
    var refresh_token = "refresh_token";
    var refresh_token_value = localStorage.getItem('refresh_token');

    var body = 'grant_type=' + encodeURIComponent(refresh_token) +
    '&refresh_token=' + encodeURIComponent(refresh_token_value);

    http.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                var tokensParameters = JSON.parse(this.responseText);
                var access_token = tokensParameters.access_token;
                var old_token = localStorage.getItem('access_token');
                localStorage.setItem('old_token', old_token);
                localStorage.setItem('access_token', access_token);
            }
        };

    http.open("POST", "http://localhost:8080/oauth/token", false);
    http.setRequestHeader("Authorization", "Basic dGVzdDpjbGllbnQtc2VjcmV0");
    http.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    http.send(body);
}

function checkValidityToken() {
        var oldLength = localStorage.getItem('expires_in');
        var oldDate = localStorage.getItem('dateToken');
        if((new Date() - Date.parse(oldDate)) >= oldLength * 1000) {
            changeToken();
        }
}

function loadUser() {
    var userId = localStorage.getItem('id');

    var xhttp = new XMLHttpRequest();

    checkValidityToken();

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
                var user = JSON.parse(this.responseText);
                userName = user.name;
                userSurname=user.surname;
                userRole=user.role;
                userProjects=user.projects;
                loadForm(userName, userSurname, userRole, userProjects);
        } else if(this.readyState == 4 && this.status == 403) {
            window.location.href = "mainPage.html";
        }
    };
    var authName = localStorage.getItem('name');
    xhttp.open("GET", "http://localhost:8080/users/"+userId +"?name="+authName, true);
    var token = "Bearer " + localStorage.getItem('access_token');
    xhttp.setRequestHeader("Authorization", token);
    xhttp.send();
}

function loadForm(name, surname, role, projectsArray) {

    var xhttp = new XMLHttpRequest();
    var html = '<label>' + name + '</label><br>\n' +
               '<label>' + surname + '</label><br>\n' +
               '<select id="selectRole">\n' +
               '<option>' + role + '</option>\n' +  //<select id="selectRole">
               '<option>' + (role == "MANAGER" ? "DEVELOPER" : "MANAGER") + '</option>\n' +
               '</select>\n';
               var i=0;
    for(i; i < projectsArray.length; i++) {
        html = html + '<br><input type=checkbox name="formProjects" value='+ projectsArray[i] +' checked/>'+ projectsArray[i] +'\n'
    }

    checkValidityToken();

    xhttp.onreadystatechange = function () {
    if (this.readyState == 4 && this.status == 200) {
        var projects = JSON.parse(this.responseText);
        for (var j = 0; j < projects.length; j++) {
            var project = projects[j];
            if(projectsArray.indexOf(project.name) != -1)  {
                continue;
            }
            //<input type="checkbox" name="formWheelchair" value="Yes" />
            html = html + '<br><input type=checkbox name="formProjects" value="'+ project.name +'"/>'+ project.name +'\n'      //'<option>' + project.name + '</option>\n';
            i++;
        }
        html = html +
        "<br><button id=hideRegistration type=submit onclick=updateUser() name=submitButton >Save </button>";
        document.getElementById("editUser").innerHTML = html;
        }
    };
    xhttp.open("GET", "http://localhost:8080/projects", true);
    var token = "Bearer " + localStorage.getItem('access_token');
    xhttp.setRequestHeader("Authorization", token);
    xhttp.send();

}

function updateUser()
{
 var userId = localStorage.getItem('id');
 var userRole = document.getElementById("selectRole").value;
 var userProjects = document.getElementsByName("formProjects");
 var responseProjectsArr =  new Array();

 for(var i = 0; i< userProjects.length; i++)
 {
    if(userProjects[i].checked) {
       responseProjectsArr.push(userProjects[i].value);
    }

 }

checkValidityToken();

 var xmlhttp = new XMLHttpRequest();
 xmlhttp.open("PUT", "http://localhost:8080/users");
 xmlhttp.setRequestHeader("Content-Type", "application/json");
 var token = "Bearer " + localStorage.getItem('access_token');
 xhttp.setRequestHeader("Authorization", token);
 xmlhttp.send(JSON.stringify({id:userId, role: userRole, projects:responseProjectsArr}));
}

function deleteUser()
{

 checkValidityToken();

 var userId = localStorage.getItem('id');
 var xmlhttp = new XMLHttpRequest();
 xmlhttp.open("DELETE", "http://localhost:8080/users/"+userId);
 var token = "Bearer " + localStorage.getItem('access_token');
 xhttp.setRequestHeader("Authorization", token);
 xmlhttp.send();

}

loadUser();
