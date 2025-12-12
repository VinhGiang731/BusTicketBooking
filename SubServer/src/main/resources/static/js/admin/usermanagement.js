const userModal = document.getElementById("userModal");
const userForm = document.getElementById("userForm");

function openUserModal() {
    userModal.style.display = "flex";
}

function closeUserModal() {
    userModal.style.display = "none";
}

if (userForm) {
    userForm.addEventListener("submit", function (e) {
        e.preventDefault();

        const table = document.querySelector('#userTable tbody');
        const row = document.createElement("tr");

        row.innerHTML = `
          <td>${document.getElementById("new-id").value}</td>
          <td>${document.getElementById("new-first").value}</td>
          <td>${document.getElementById("new-last").value}</td>
          <td>${document.getElementById("new-email").value}</td>
          <td>${document.getElementById("new-phone").value}</td>
        `;

        table.appendChild(row);
        closeUserModal();
    });

    // Đóng modal khi click ra ngoài
    window.addEventListener("click", function (e) {
        if (e.target === userModal) {
            closeUserModal();
        }
    });
}
