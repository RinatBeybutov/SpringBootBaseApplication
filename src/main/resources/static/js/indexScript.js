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

function loadUsers() {

        checkValidityToken();

        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                var users = JSON.parse(this.responseText);
                var html = '<tr>\n' +
                    '        <th> №</th>\n' +
                    '        <th> name</th>\n' +
                    '        <th> surname</th>\n' +
                    '        <th> role</th>\n' +
                    //'        <th> project</th>\n' +
                    '    </tr>';
                for (var i = 0; i < users.length; i++) {
                    var user = users[i];
                    html = html + '<tr><td> <a href="User.html" onClick="loadSingleUser('+user.id+')">' + (i+1) + '</a> </td>\n' +
                        '        <td>  ' + user.name + ' </td>\n' +
                        '        <td>' + user.surname + '</td>\n' +
                        '        <td>' + user.role + '</td>\n' ;
                        //'        <td>' + user.project + '</td>\n';
                }
                document.getElementById("usersList").innerHTML = html;
            }
        };
        xhttp.open("GET", "http://localhost:8080/users", true);
        var token = "Bearer " + localStorage.getItem('access_token');
        xhttp.setRequestHeader("Authorization", token);
        xhttp.send();
}

loadUsers();

function createUser() {
 document.querySelector('#formRegistration').classList.add('close')
 var userName = document.getElementById("userName").value;
 var userLogin = document.getElementById("userSurname").value;
 var userRole = document.getElementById("selectRole").value;
 var userProject = document.getElementsByName("formProjects");
 var userPassword = document.getElementById("userPassword").value;
 var responseProjectsArr =  new Array();

  for(var i = 0; i< userProject.length; i++)
  {
     if(userProject[i].checked) {
        responseProjectsArr.push(userProject[i].value);
     }

  }
checkValidityToken();
 var xmlhttp = new XMLHttpRequest();   // new HttpRequest instance
        xmlhttp.open("POST", "http://localhost:8080/users");
        xmlhttp.setRequestHeader("Content-Type", "application/json");
        var token = "Bearer " + localStorage.getItem('access_token');
        xmlhttp.setRequestHeader("Authorization", token);
        xmlhttp.send(JSON.stringify({name: userName, surname: userLogin, password: userPassword, role: userRole, project: responseProjectsArr}));
        loadUsers();
}

function createProject() {
 document.querySelector('#formProject').classList.add('close')
 var projectName = document.getElementById("projectName").value;

checkValidityToken();
 var xmlhttp = new XMLHttpRequest();   // new HttpRequest instance
        xmlhttp.open("POST", "http://localhost:8080/projects");
        xmlhttp.setRequestHeader("Content-Type", "application/json");
        var token = "Bearer " + localStorage.getItem('access_token');
        xmlhttp.setRequestHeader("Authorization", token);
        xmlhttp.send(JSON.stringify({name: projectName}));
}

function loadSingleUser(id) {
localStorage.setItem('id', id);
}

function loadProjects()
{
        checkValidityToken();
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                var projects = JSON.parse(this.responseText);
                var html = "";
                for (var i = 0; i < projects.length; i++) {
                    var project = projects[i];
                    html = html + '<br><input type=checkbox name="formProjects" value="'+ project.name +'"/>'+ project.name +'\n';
                }
                document.getElementById("selectProject").innerHTML = html;
            }
        };
        xhttp.open("GET", "http://localhost:8080/projects", true);
        var token = "Bearer " + localStorage.getItem('access_token');
        xhttp.setRequestHeader("Authorization", token);
        xhttp.send();

}

function logOut() {

    var xmlhttp = new XMLHttpRequest();
    var userName = localStorage.getItem('name');
    xmlhttp.open("POST", "http://localhost:8080/logout?name=" + userName);
    xmlhttp.send();

}