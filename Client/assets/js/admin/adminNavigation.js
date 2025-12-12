document.addEventListener('DOMContentLoaded', function () {
    const menuItems = document.querySelectorAll('.sidebar li a');
    const dynamicContent = document.getElementById('dynamic-content');

    menuItems.forEach(item => {
        item.addEventListener('click', function (e) {
            e.preventDefault();

            menuItems.forEach(menuItem => {
                menuItem.parentElement.classList.remove('active');
            });

            this.parentElement.classList.add('active');

            const target = this.getAttribute('data-target');

            loadLayout(target);
        });
    });

    function loadLayout(target) {
        dynamicContent.innerHTML = '<div class="loading"><h3>Loading...</h3></div>';

        const endpointMap = {
            'dashboard': '/busbooking/busticket/admin/dashboard',
            'busmanage': '/busbooking/busticket/admin/busmanage',
            'ticketmanage': '/busbooking/busticket/admin/ticketmanage',
            'usermanage': '/busbooking/busticket/admin/usermanage',
            'statistics': '/busbooking/busticket/admin/statistics'
        };

        const endpoint = endpointMap[target];

        fetch(endpoint)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.text();
            })
            .then(html => {
                dynamicContent.innerHTML = html;
            })
            .catch(error => {
                console.error('Detailed error:', error);
                dynamicContent.innerHTML = `
                    <div class="error">
                        <h3>Error loading content</h3>
                        <p>Error details: ${error.message}</p>
                        <p>Please check if you are logged in as admin</p>
                    </div>`;
            });
    }

    loadLayout('dashboard');
}); 