document.addEventListener('DOMContentLoaded', function () {
    const loadingOverlay = document.getElementById('loadingOverlay');

    setTimeout(() => {
        if (loadingOverlay) {
            loadingOverlay.classList.add('fade-out');

            setTimeout(() => {
                loadingOverlay.style.display = 'none';
            }, 500);
        }

        initializeProfile();
    }, 1500);

    const contactLink = document.querySelector('a[href="#lien-he"]');
    const helpLink = document.querySelector('a[href="#tro-giup"]');
    const footer = document.querySelector('footer');

    function scrollToFooter(e) {
        e.preventDefault();
        if (footer) {
            footer.scrollIntoView({ behavior: 'smooth' });
        }
    }

    if (contactLink) {
        contactLink.addEventListener('click', scrollToFooter);
    }

    if (helpLink) {
        helpLink.addEventListener('click', scrollToFooter);
    }

    function initializeProfile() {
        const profileCard = document.querySelector('.profile-card');
        if (profileCard) {
            setTimeout(() => {
                profileCard.classList.add('animated');
            }, 300);
        }

        async function fetchAndInitUser() {
            try {
                const response = await fetch('http://localhost:8088/busbooking/users/myinfo', {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer ${localStorage.getItem('token')}`,
                    }
                });
                if (!response.ok) throw new Error('Không lấy được thông tin người dùng');
                const userData = await response.json();
                console.log(userData);

                const profileDisplay = document.getElementById('profile-display');
                const profileEdit = document.getElementById('profile-edit');
                const editBtn = document.getElementById('edit-btn');
                const cancelEditBtn = document.getElementById('cancel-edit-btn');
                const editForm = document.getElementById('edit-profile-form');
                const successMessage = document.getElementById('success-message');

                function populateProfileData() {
                    const fields = ['firstName', 'lastName', 'email', 'phone'];

                    fields.forEach((field, index) => {
                        const element = document.getElementById(field);
                        if (element) {
                            const value = userData.result[field] || '';
                            element.textContent = '';

                            setTimeout(() => {
                                let i = 0;
                                const typing = setInterval(() => {
                                    element.textContent += value[i] || '';
                                    i++;
                                    if (i >= value.length) {
                                        clearInterval(typing);
                                        setTimeout(() => {
                                            element.classList.add('highlight');
                                            setTimeout(() => {
                                                element.classList.remove('highlight');
                                            }, 500);
                                        }, 200);
                                    }
                                }, 50);
                            }, index * 400);
                        }
                    });
                }

                function populateEditForm() {
                    document.getElementById('edit-firstName').value = userData.result.firstName || '';
                    document.getElementById('edit-lastName').value = userData.result.lastName || '';
                    document.getElementById('edit-email').value = userData.result.email || '';
                    document.getElementById('edit-phone').value = userData.result.phone || '';
                }

                function showEditForm() {
                    profileDisplay.classList.remove('fade-in', 'fade-out');
                    profileEdit.classList.remove('fade-in', 'fade-out');

                    profileDisplay.classList.add('fade-out');
                    setTimeout(() => {
                        profileDisplay.style.display = 'none';
                        profileEdit.style.display = 'block';
                        profileEdit.classList.add('fade-in');

                        populateEditForm();
                    }, 300);
                }

                function hideEditForm() {
                    profileEdit.classList.remove('fade-in', 'fade-out');
                    profileDisplay.classList.remove('fade-in', 'fade-out');

                    profileEdit.classList.add('fade-out');
                    setTimeout(() => {
                        profileEdit.style.display = 'none';
                        profileDisplay.style.display = 'block';
                        profileDisplay.classList.add('fade-in');
                    }, 300);
                }

                function showSuccessMessage() {
                    successMessage.style.display = 'block';

                    setTimeout(() => {
                        successMessage.classList.add('fade-out');
                        setTimeout(() => {
                            successMessage.style.display = 'none';
                            successMessage.classList.remove('fade-out');
                        }, 300);
                    }, 3000);
                }

                // Update profile data
                function updateProfileData(formData) {
                    userData.result.firstName = formData.firstName;
                    userData.result.lastName = formData.lastName;
                    userData.result.email = formData.email;
                    userData.result.phone = formData.phone;

                    document.getElementById('firstName').textContent = formData.firstName;
                    document.getElementById('lastName').textContent = formData.lastName;
                    document.getElementById('email').textContent = formData.email;
                    document.getElementById('phone').textContent = formData.phone;

                    const fields = ['firstName', 'lastName', 'email', 'phone'];
                    fields.forEach(field => {
                        const element = document.getElementById(field);
                        if (element) {
                            element.classList.add('highlight');
                            setTimeout(() => {
                                element.classList.remove('highlight');
                            }, 1000);
                        }
                    });
                }

                function setupDropdownMenu() {
                    const dropdownToggle = document.querySelector('.dropdown-toggle');
                    if (dropdownToggle) {
                        dropdownToggle.addEventListener('click', function (e) {
                            e.preventDefault();
                            const dropdownMenu = this.nextElementSibling;

                            dropdownMenu.classList.toggle('active');

                            const icon = this.querySelector('i.fa-chevron-down');
                            if (icon) {
                                if (dropdownMenu.classList.contains('active')) {
                                    icon.style.transform = 'rotate(180deg)';
                                } else {
                                    icon.style.transform = 'rotate(0)';
                                }
                            }
                        });
                    }

                    document.addEventListener('click', function (e) {
                        if (!e.target.closest('.dropdown')) {
                            const dropdownMenu = document.querySelector('.dropdown-menu.active');
                            const icon = document.querySelector('.dropdown-toggle i.fa-chevron-down');

                            if (dropdownMenu) {
                                dropdownMenu.classList.remove('active');
                                if (icon) {
                                    icon.style.transform = 'rotate(0)';
                                }
                            }
                        }
                    });
                }

                function setupInfoGroupEffects() {
                    const infoGroups = document.querySelectorAll('.info-group');

                    infoGroups.forEach(group => {
                        group.addEventListener('mouseenter', function () {
                            const icon = this.querySelector('.info-label i');
                            if (icon) {
                                icon.classList.add('fa-bounce');
                            }
                        });

                        group.addEventListener('mouseleave', function () {
                            const icon = this.querySelector('.info-label i');
                            if (icon) {
                                icon.classList.remove('fa-bounce');
                            }
                        });
                    });
                }

                function setupAvatarEffect() {
                    const avatar = document.querySelector('.profile-avatar');
                    if (avatar) {
                        avatar.addEventListener('click', function () {
                            this.classList.add('avatar-pulse');
                            setTimeout(() => {
                                this.classList.remove('avatar-pulse');
                            }, 1000);
                        });
                    }
                }

                function setupEditFunctionality() {
                    if (editBtn) {
                        editBtn.addEventListener('click', function () {
                            showEditForm();
                            const bookedTickets = document.getElementById('booked-tickets');
                            if (bookedTickets) bookedTickets.style.display = 'none';
                        });
                    }

                    // Cancel button click
                    if (cancelEditBtn) {
                        cancelEditBtn.addEventListener('click', function () {
                            hideEditForm();
                        });
                    }

                    // Form submission
                    if (editForm) {
                        editForm.addEventListener('submit', async function (e) {
                            e.preventDefault();

                            // Get form data
                            const formData = {
                                firstName: document.getElementById('edit-firstName').value,
                                lastName: document.getElementById('edit-lastName').value,
                                email: document.getElementById('edit-email').value,
                                phone: document.getElementById('edit-phone').value
                            };

                            if (!formData.firstName || !formData.lastName || !formData.email || !formData.phone) {
                                alert('Vui lòng điền đầy đủ thông tin.');
                                return;
                            }

                            const isConfirmed = confirm('Bạn có chắc chắn muốn lưu thay đổi thông tin cá nhân?');
                            if (!isConfirmed) {
                                return;
                            }

                            const loadingOverlay = document.getElementById('loadingOverlay');
                            if (loadingOverlay) {
                                loadingOverlay.style.display = 'flex';
                                loadingOverlay.classList.remove('fade-out');
                            }

                            try {
                                const response = await fetch('http://localhost:8088/busbooking/users/update/info', {
                                    method: 'PUT',
                                    headers: {
                                        'Content-Type': 'application/json',
                                        'Authorization': `Bearer ${localStorage.getItem('token')}`,
                                    },
                                    body: JSON.stringify(formData)
                                });
                                if (!response.ok) throw new Error('Cập nhật thông tin thất bại!');
                                updateProfileData(formData);
                                hideEditForm();
                                showSuccessMessage();
                            } catch (err) {
                                alert('Lỗi khi cập nhật thông tin: ' + err.message);
                            } finally {
                                if (loadingOverlay) {
                                    loadingOverlay.classList.add('fade-out');
                                    setTimeout(() => {
                                        loadingOverlay.style.display = 'none';
                                    }, 500);
                                }
                            }
                        });
                    }
                }

                const style = document.createElement('style');
                style.textContent = `
                    .profile-card.animated {
                        animation: cardEntrance 0.8s cubic-bezier(0.23, 1, 0.32, 1) forwards;
                    }
                    
                    @keyframes cardEntrance {
                        from {
                            opacity: 0;
                            transform: translateY(40px) scale(0.95);
                        }
                        to {
                            opacity: 1;
                            transform: translateY(0) scale(1);
                        }
                    }
                    
                    .highlight {
                        background-color: rgba(26, 115, 232, 0.1);
                        transition: background-color 0.5s ease;
                    }
                    
                    .avatar-pulse {
                        animation: avatarPulse 1s ease;
                    }
                    
                    @keyframes avatarPulse {
                        0% { transform: scale(1); }
                        50% { transform: scale(1.1); }
                        100% { transform: scale(1); }
                    }
                `;
                document.head.appendChild(style);

                // Initialize
                populateProfileData();
                setupDropdownMenu();
                setupEditFunctionality();
                setupInfoGroupEffects();
                setupAvatarEffect();
            } catch (err) {
                alert('Lỗi khi lấy thông tin người dùng: ' + err.message);
            }
        }
        fetchAndInitUser();
    }

    // Bookings functionality
    const viewBookingsBtn = document.getElementById('view-bookings-btn');
    const bookedTickets = document.getElementById('booked-tickets');
    const bookingsList = document.getElementById('bookings-list');

    let allBookings = [];
    const BOOKINGS_PER_PAGE = 5;

    viewBookingsBtn.addEventListener('click', async function () {
        try {
            const token = localStorage.getItem('token');
            if (!token) {
                alert('Vui lòng đăng nhập để xem vé đã đặt');
                return;
            }

            const response = await fetch('http://localhost:8088/busbooking/booking', {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });

            if (!response.ok) {
                throw new Error('Failed to fetch bookings');
            }

            let data = await response.json();
            let bookings = data;
            if (data.result) {
                bookings = data.result;
            }
            allBookings = Array.isArray(bookings) ? bookings : [bookings];
            displayBookingsPage(1);
            bookedTickets.style.display = 'block';
        } catch (error) {
            console.error('Error fetching bookings:', error);
            alert('Có lỗi xảy ra khi tải thông tin vé đã đặt');
        }
    });

    function displayBookingsPage(page) {
        bookingsList.innerHTML = '';
        const startIdx = (page - 1) * BOOKINGS_PER_PAGE;
        const endIdx = startIdx + BOOKINGS_PER_PAGE;
        const bookingsToShow = allBookings.slice(startIdx, endIdx);
        bookingsToShow.forEach(booking => {
            const bookingCard = document.createElement('div');
            bookingCard.className = 'booking-card';
            const departureTime = booking.departureTime ? new Date(booking.departureTime).toLocaleString() : '';
            const arrivalTime = booking.arrivalTime ? new Date(booking.arrivalTime).toLocaleString() : '';
            const bookingTime = booking.bookingTime ? new Date(booking.bookingTime).toLocaleString() : '';
            bookingCard.innerHTML = `
                <div class="booking-header">
                    <span class="booking-id">Mã đặt vé: ${booking.bookingId || ''}</span>
                </div>
                <div class="booking-details">
                    <div class="detail-item">
                        <span class="detail-label">Nhà xe:</span>
                        <span class="detail-value">${booking.busOperatorName || ''}</span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Số điện thoại:</span>
                        <span class="detail-value">${booking.contactPhone || ''}</span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Điểm đi:</span>
                        <span class="detail-value">${booking.fromLocation || ''}</span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Điểm đến:</span>
                        <span class="detail-value">${booking.toLocation || ''}</span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Giờ khởi hành:</span>
                        <span class="detail-value">${departureTime}</span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Giờ đến:</span>
                        <span class="detail-value">${arrivalTime}</span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Số ghế:</span>
                        <span class="detail-value">${booking.seats_number || ''}</span>
                    </div>
                </div>
                <div class="booking-time">
                    Thời gian đặt vé: ${bookingTime}
                </div>
            `;
            bookingsList.appendChild(bookingCard);
        });
        renderPagination(page);
    }

    function renderPagination(currentPage) {
        let paginationDiv = document.getElementById('bookings-pagination');
        if (!paginationDiv) {
            paginationDiv = document.createElement('div');
            paginationDiv.id = 'bookings-pagination';
            paginationDiv.style.textAlign = 'center';
            paginationDiv.style.marginTop = '20px';
            bookingsList.parentNode.appendChild(paginationDiv);
        }
        paginationDiv.innerHTML = '';
        const totalPages = Math.ceil(allBookings.length / BOOKINGS_PER_PAGE);
        for (let i = 1; i <= totalPages; i++) {
            const pageBtn = document.createElement('button');
            pageBtn.textContent = i;
            pageBtn.className = 'pagination-btn' + (i === currentPage ? ' active' : '');
            pageBtn.style.margin = '0 5px';
            pageBtn.style.padding = '6px 12px';
            pageBtn.style.borderRadius = '4px';
            pageBtn.style.border = '1px solid #007bff';
            pageBtn.style.background = i === currentPage ? '#007bff' : '#fff';
            pageBtn.style.color = i === currentPage ? '#fff' : '#007bff';
            pageBtn.style.cursor = 'pointer';
            pageBtn.addEventListener('click', () => displayBookingsPage(i));
            paginationDiv.appendChild(pageBtn);
        }
    }
}); 