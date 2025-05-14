document.addEventListener('DOMContentLoaded', function () {
    const frmLogin = document.getElementById('frm-login')
    const stateLogin = document.getElementById("state__login");

    if (frmLogin) {
        loginEvent(frmLogin);
    }
    if (stateLogin) {
        welcomeToU(stateLogin);
    }

})

function loginEvent(frmLogin) {
    if (frmLogin) {
        console.log("ok");
        frmLogin.addEventListener('submit', async (event) => {
            event.preventDefault();

            const userName = document.getElementById('Username').value;
            const password = document.getElementById('Password').value;

            const loginData = {
                "userName": userName,
                "password": password
            };

            try {
                const response = await fetch('http://localhost:8080/busbooking/auth/login', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(loginData)
                });

                const data = await response.json();

                if (response.ok && data.code === 0 && data.result.authenticated) {

                    fetch('http://localhost:8080/busbooking/busticket/session-info', {
                        method: "GET",
                        credentials: "include"
                    })
                        .then(result => result.json())
                        .then(data => {
                            if (data.scope === 'USER') {
                                window.location.href = "/busbooking/busticket/homepage";
                            } else {
                                window.location.href = "/busbooking/busticket/admin/home";
                            }
                        });

                } else {
                    alert('Username or password incorrect');
                }
            } catch (error) {
                alert('Fetch API Failure');
            }
        });
    }
    else console.log("not");
}


function welcomeToU(stateLogin) {
    fetch('http://localhost:8080/busbooking/busticket/session-info', {
        method: "GET",
        credentials: "include"
    })
        .then(result => result.json())
        .then(data => {
            if (data.userName) {
                stateLogin.innerHTML = ` 
                    <p>Welcome: ${data.userName}</p>
                    <button class="btn__logout">Đăng xuất</button>
                    `;
            }
        })
}
