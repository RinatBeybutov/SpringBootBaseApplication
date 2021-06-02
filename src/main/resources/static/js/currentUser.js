
var userName = '';
var userSurname='';
var userRole='';
var userProject='';

function loadUser() {
    var userId = localStorage.getItem('id');

    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
                var user = JSON.parse(this.responseText);
                //html = user.name + "<br>"
                //+ user.surname + "<br>"
                //+ user.role + "<br>"
                //+ user.project;
                //document.getElementById("userInfo").innerHTML = html;
                userName = user.name;
                userSurname=user.surname;
                userRole=user.role;
                userProject=user.project;
                loadForm(userName, userSurname, userRole, userProject );
        }
    };
    xhttp.open("GET", "http://localhost:8080/users/"+userId, true);
    xhttp.send();


}

function loadForm(name, surname, role, project) {

    var xhttp = new XMLHttpRequest();
    var html = '<label>' + name + '</label><br>\n' +
               '<label>' + surname + '</label><br>\n' +
               '<select id="selectRole">\n' +
               '<option>' + role + '</option>\n' +  //<select id="selectRole">
               '<option>' + (role == "MANAGER" ? "DEVELOPER" : "MANAGER") + '</option>\n' +
               '</select><br>\n' +
               '<select id="selectProject">\n' +
               '<option>' + project + '</option>\n';

    xhttp.onreadystatechange = function () {
    if (this.readyState == 4 && this.status == 200) {
        var projects = JSON.parse(this.responseText);
        for (var i = 0; i < projects.length; i++) {
            var project = projects[i];
            if(project.name === userProject)  {
                continue;
            }
            html = html + '<option>' + project.name + '</option>\n';
        }
        html = html + '</select><br>\n' +
        "<button id=hideRegistration type=submit onclick=updateUser() name=submitButton >Save </button>";
        document.getElementById("editUser").innerHTML = html;
        }
    };
    xhttp.open("GET", "http://localhost:8080/projects", true);
    xhttp.send();

}

function updateUser()
{
 var userId = localStorage.getItem('id');
 var userRole = document.getElementById("selectRole").value;
 var userProject = document.getElementById("selectProject").value;

 var xmlhttp = new XMLHttpRequest();
 xmlhttp.open("PUT", "http://localhost:8080/users");
 xmlhttp.setRequestHeader("Content-Type", "application/json");
 xmlhttp.send(JSON.stringify({id:userId, role: userRole, project:userProject}));
}

function deleteUser()
{
 var userId = localStorage.getItem('id');
 var xmlhttp = new XMLHttpRequest();
 xmlhttp.open("DELETE", "http://localhost:8080/users/"+userId);
 xmlhttp.send();

}

loadUser();
