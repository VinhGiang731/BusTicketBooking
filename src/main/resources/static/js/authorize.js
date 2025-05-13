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
        }
        )
    }
    else console.log("not");
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
