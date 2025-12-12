// http://localhost:8080/busbooking/auth/login

document.addEventListener('DOMContentLoaded', function () {
    const frmLogin = document.getElementById('frm-login')
    const frmSignUp = document.getElementById('frm-signup');

    const stateLogin = document.getElementById("state__login");

    if (frmLogin) {
        loginEvent(frmLogin);
    }
    if (frmSignUp) {
        signUpEvent(frmSignUp);
    }
    if (stateLogin) {
        welcomeToU(stateLogin);
    }
})
function loginEvent(form) {
    if (form) {
        console.log("ok");
        form.addEventListener('submit', async (event) => {
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

function signUpEvent(form) {
    if (form) {
        form.addEventListener('submit', async (event) => {
            event.preventDefault();

            let userName = document.getElementById('userName').value.trim();
            let firstName = document.getElementById('firstName').value.trim();
            let lastName = document.getElementById('lastName').value.trim();
            let email = document.getElementById('email').value.trim();
            let phone = document.getElementById('phone').value.trim();
            let password = document.getElementById('password').value.trim();
            let confirmpass = document.getElementById('confirmpass').value.trim();

            if (password !== confirmpass) {
                alert('Passwords do not match!');
                return;
            }

            const userData = {
                userName: userName,
                password: password,
                firstName: firstName,
                lastName: lastName,
                email: email,
                phone: phone
            };

            const response = await fetch('http://localhost:8080/busbooking/users', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(userData)
            });

            const data = await response.json();

            if (response.ok && data.code === 1000) {
                alert('Sign up successful');
                form.reset();

            } else {
                alert(`Notification: ${data.message}`);
                return;
            }

        });
    }
    return;
}

document.addEventListener("DOMContentLoaded", function () {

})

function welcomeToU(form) {
    fetch('http://localhost:8080/busbooking/busticket/session-info', {
        method: "GET",
        credentials: "include"
    })
        .then(result => result.json())
        .then(data => {
            if (data.userName) {
                form.innerHTML = ` 
                    <li class="user-dropdown">
                        <a href="" class="user-welcome">Welcome ${data.userName} ▼</a>
                        <div class="dropdown-content">
                            <a href="/busbooking/busticket/profile">Profile</a>
                            <a href="/busbooking/busticket/logout">Logout</a>
                        </div>
                    </li>
                    `;
                localStorage.setItem('token', data.token);
            } else {
                form.innerHTML = `<a href="/busbooking/busticket/login" class="login-btn">Đăng nhập</a>`;
            }
        })
}
