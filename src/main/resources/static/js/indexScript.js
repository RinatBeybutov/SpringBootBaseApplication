function loadUsers() {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                var users = JSON.parse(this.responseText);
                var html = '<tr>\n' +
                    '        <th> id</th>\n' +
                    '        <th> name</th>\n' +
                    '        <th> surname</th>\n' +
                    '        <th> role</th>\n' +
                    '        <th> project</th>\n' +
                    '    </tr>';
                for (var i = 0; i < users.length; i++) {
                    var user = users[i];
                    html = html + '<tr><td> <a href="User.html" onClick="loadSingleUser('+user.id+')">' + user.id + '</a> </td>\n' +
                        '        <td>  ' + user.name + ' </td>\n' +
                        '        <td>' + user.surname + '</td>\n' +
                        '        <td>' + user.role + '</td>\n' +
                        '        <td>' + user.project + '</td>\n';
                }
                document.getElementById("usersList").innerHTML = html;
            }
        };
        xhttp.open("GET", "http://localhost:8080/users", true);
        xhttp.send();
}

loadUsers();

function createUser() {
 document.querySelector('#formRegistration').classList.add('close')
 var userName = document.getElementById("userName").value;
 var userLogin = document.getElementById("userSurname").value;
 var userRole = document.getElementById("selectRole").value;
 var userProject = document.getElementById("selectProject").value;

 var xmlhttp = new XMLHttpRequest();   // new HttpRequest instance
        xmlhttp.open("POST", "http://localhost:8080/users");
        xmlhttp.setRequestHeader("Content-Type", "application/json");
        xmlhttp.send(JSON.stringify({name: userName, surname: userLogin, role: userRole, project:userProject}));
        loadUsers();
}

function loadSingleUser(id) {

console.log(id);
localStorage.setItem('id', id);


}