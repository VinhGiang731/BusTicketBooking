// http://localhost:8080/busbooking/auth/login
function loginEvent() {
    const frmLogin = document.getElementById('frm-login')
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
                                alert("ADMIN");
                            }
                        })
                } else {
                    alert('User or password is incorrect');
                }
            } catch (error) {
                console.error('Error:', error);
            }
        });
    }
    else console.log("not");
}

function signUpEvent() {
    const frmSignUp = document.getElementById('frm-signup');
    if (frmSignUp) {
        frmSignUp.addEventListener('submit', async (event) => {
            event.preventDefault();
            const userName = document.getElementById('username').value;
            const email = document.getElementById('email').value;
            const phone = document.getElementById('phone').value;
            const password = document.getElementById('password').value;
            const confirmpass = document.getElementById('confirmpass').value;

            const create = {
                "username": userName,
                "email": email,
                "phone": phone,
                "password": password
            }

            if (password === confirmpass) {
                try {
                    fetch('http://localhost:8080/busbooking/users', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify(loginData)
                    })
                        .then(result => result.json())
                        .then(data => {
                            if (data.ok && data.code === 0) {
                                alert('System', 'Sign up successful');
                            } else {
                                alert('System', 'Sign up is incorrect');
                            }
                        })
                        .catch(error => {
                            alert('System', error);
                        });
                } catch (error) {
                    console.error('Error:', error);
                }
            }
        });
    }
}

document.addEventListener("DOMContentLoaded", function () {
    fetch('http://localhost:8080/busbooking/busticket/session-info', {
        method: "GET",
        credentials: "include"
    })
        .then(result => result.json())
        .then(data => {
            const stateLogin = document.getElementById("state__login");
            if (data.userName) {
                stateLogin.innerHTML = ` 
                    <li class="user-dropdown">
                        <a href="" class="user-welcome">Welcome ${data.userName} ▼</a>
                        <div class="dropdown-content">
                            <a href="profile.html">Profile</a>
                            <a href="logout.html">Logout</a>
                        </div>
                    </li>
`;
            } else {
                stateLogin.innerHTML = `<a href="/busbooking/busticket/login" class="login-btn">Đăng nhập</a>`;
            }
        })
})
