var cancelBtn;
var amountPayment;
const socket = new SockJS('/busbooking/ws')
const stompClient = Stomp.over(socket);

stompClient.connect({}, function (frame) {
    console.log('Connected: ' + frame);

    stompClient.subscribe('/topic/notification', function (messageOutput) {
        const message = JSON.parse(messageOutput.body);
        console.log("Received message: ", message);

        const json = {
            "bookingId": localStorage.getItem('bookingId')
        }

        console.log(json);

        if (parseFloat(message.amount) === parseFloat(amountPayment)) {
            fetch('http://localhost:8088/busbooking/payment/record', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(json)
            })
                .then(response => response.json())
                .then(data => {
                    if (data.code === 0) {
                        alert('Bạn đã thanh toán thành công');
                        window.location.href = "/busbooking/busticket/homepage";
                    }
                })
        } else {
            cancelBtn.click();
            alert('Số tiền thanh toán bị sai, vui lòng liên hệ hotline để được hoàn tiền!!!');
            window.location.href = "/busbooking/busticket/homepage";

        }

        localStorage.removeItem('bookingId');
    });
});

const listTrip = [];
document.addEventListener("DOMContentLoaded", function () {
    //http://localhost:8088/busbooking/trip
    const routeCards = document.querySelector(".route-cards");
    console.log(routeCards);

    const routesGrid = document.querySelector(".routes-grid");
    console.log(routesGrid);

    const scheduleTable = document.querySelector(".schedule-table");
    console.log(scheduleTable);

    const btnSearch = document.querySelector(".btn-search");
    console.log(btnSearch);

    const frmScheduleSearch = document.querySelector("#frm-schedule-search");
    console.log(frmScheduleSearch);

    fetch('http://localhost:8088/busbooking/trip')
        .then(response => response.json())
        .then(data => {
            if (data.code === 0 && Array.isArray(data.result)) {
                listTrip.push(...data.result);
                var i = 0;
                if (routeCards) {
                    i = 0;
                    //fun routeCards
                    data.result.slice(0, 3).forEach(trip => {
                        if (i === 3) return;
                        disPlayHintTrip(trip, routeCards);
                        i++;
                    })
                }

                if (routesGrid) {
                    i = 0;
                    //fun routesGrid
                    data.result.slice(0, 6).forEach(trip => {
                        if (i === 7) return;
                        disPlayTripSchedule(trip, routesGrid);
                        i++;
                    })
                }
                if (scheduleTable) {
                    const searchDataString = localStorage.getItem('searchData');
                    console.log("searchDataString:", searchDataString);

                    if (searchDataString) {
                        try {
                            const searchData = JSON.parse(searchDataString);
                            console.log("searchData parsed:", searchData);
                            disPlaySearch(searchData, scheduleTable);
                            localStorage.removeItem('searchData');
                        } catch (error) {
                            console.error("Error parsing searchData:", error);
                            disPlay(i, data, scheduleTable);
                        }
                    } else {
                        disPlay(i, data, scheduleTable);
                    }
                }
            }
            else {
                console.warn("Error fetching data");
            }
        }).catch(error => {
            console.error("Error fetching data", error);
        })

    if (btnSearch) {
        const searchTicketForm = document.querySelector("#search-ticket-form");
        if (searchTicketForm) {
            searchTicketForm.addEventListener('submit', async (event) => {
                event.preventDefault();
                searching(listTrip);
            });
        }
    }

    if (frmScheduleSearch) {
        const searchTicketForm = document.querySelector(".search-form");
        if (searchTicketForm) {
            searchTicketForm.addEventListener('submit', async (event) => {
                event.preventDefault();
                const departureSelect = document.getElementById('departure_select');
                const arrivalSelect = document.getElementById('arrival_select');
                const departureDate = document.getElementById('date');

                const departure = departureSelect.options[departureSelect.selectedIndex].text;
                const arrival = arrivalSelect.options[arrivalSelect.selectedIndex].text;

                const departureDateValue = departureDate.value;
                const searchData = {
                    departure: departure,
                    arrival: arrival,
                    departureDate: departureDateValue,
                    location: "table-trip"
                }

                disPlaySearch(searchData, scheduleTable);
            });
        }
    }

    // Add event delegation for book buttons
    document.addEventListener('click', async function (e) {
        if (e.target.classList.contains('book-btn')) {
            const tripData = JSON.parse(e.target.getAttribute('data-trip'));
            let response = await fetch('http://localhost:8088/busbooking/busticket/check/login');

            let state = await response.json();
            if (state.state === true) {
                openPaymentModal(tripData);
            } else {
                alert('Vui lòng login để book vé!');
            }
        }
    });
});

async function disPlayHintTrip(trip, routeCards) {
    const card = document.createElement("div");
    card.className = "route-card";

    let imageUrl = "https://via.placeholder.com/150"; // ảnh mặc định nếu lỗi

    try {
        const res = await fetch(`http://localhost:8088/busbooking/busoperator/img/${trip.busOperatorId}`);
        if (res.ok) {
            imageUrl = await res.text(); // lấy chuỗi URL ảnh
        } else {
            console.warn("Không thể lấy ảnh, status:", res.status);
        }
    } catch (error) {
        console.error("Lỗi gọi API ảnh:", error);
    }

    card.innerHTML = `
        <div class="route-img" style="background-image: url('${imageUrl}')"></div>
        <div class="route-info">
            <h3>${trip.fromLocation} - ${trip.toLocation}</h3>
            <p>Khởi hành: ${new Date(trip.departurTime).toLocaleString()}</p>
            <p>Đến nơi: ${new Date(trip.arrivalTime).toLocaleString()}</p>
            <div class="route-price">Giá vé: ${trip.price.toLocaleString()} VNĐ</div>
            <p>Số ghế: ${trip.totalSeats}</p>
            <button class="route-btn book-btn" type="button" data-trip='${JSON.stringify(trip)}'>Đặt ngay</button>
        </div>
    `;

    routeCards.appendChild(card);
}

function disPlayTripSchedule(trip, routesGrid) {
    const card = document.createElement("div")
    card.className = "route-card";
    card.innerHTML = `
                        <div class="route-info">
                            <h3>${trip.fromLocation} - ${trip.toLocation}</h3>
                            <p>Khởi hành: ${new Date(trip.departurTime).toLocaleString()}</p>
                            <p>Đến nơi: ${new Date(trip.arrivalTime).toLocaleString()}</p>
                            <p class="route-price">Giá vé: ${trip.price.toLocaleString()} VNĐ</p>
                            <p>Số ghế: ${trip.totalSeats}</p>
                        </div>
                        <td><button class="route-btn book-btn" type="button" data-trip='${JSON.stringify(trip)}'>Đặt ngay</button></td>
                        `;
    routesGrid.appendChild(card);
}

function searching(data) {
    const departureSelect = document.getElementById('departure_select');
    const arrivalSelect = document.getElementById('arrival_select');
    const departureDate = document.getElementById('departure-date');

    const departure = departureSelect.options[departureSelect.selectedIndex].text;
    const arrival = arrivalSelect.options[arrivalSelect.selectedIndex].text;

    const departureDateValue = departureDate.value;

    const searchData = {
        departure: departure,
        arrival: arrival,
        departureDate: departureDateValue,
        location: "table-trip"
    }

    console.log("Saving searchData:", searchData);
    localStorage.setItem('searchData', JSON.stringify(searchData));
    window.location.href = "/busbooking/busticket/schedule";
}

function disPlaySearch(dataFilter, scheduleTable) {
    const location = document.querySelector(`#${dataFilter.location}`);
    console.log(dataFilter.location);
    if (location) {
        location.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }
    scheduleTable.innerHTML = `<thead>
                            <tr>
                                <th>Tuyến đường</th>
                                <th>Giờ khởi hành</th>
                                <th>Giờ đến</th>
                                <th>Nhà xe</th>
                                <th>Giá vé</th>
                                <th>Số Lượng</th>
                                <th>Đặt vé</th>
                            </tr>
                        </thead>`;
    console.log("Filtering with:", dataFilter);
    if (!dataFilter || typeof dataFilter !== 'object') {
        console.error("Invalid dataFilter:", dataFilter);
        return;
    }

    const result = listTrip.filter(trip =>
        trip.fromLocation === dataFilter.departure &&
        trip.toLocation === dataFilter.arrival
    );

    console.log("Filtered results:", result);


    if (result.length === 0) {
        const tbody = document.createElement("tbody");
        tbody.innerHTML = `
            <tr>
                <td colspan="7">Không tìm thấy chuyến xe phù hợp</td>
            </tr>
        `;
        scheduleTable.appendChild(tbody);
        return;
    }

    result.forEach(trip => {
        const tbody = document.createElement("tbody")
        tbody.innerHTML = `
            <tr>
                <td>${trip.fromLocation} - ${trip.toLocation}</td>
                <td>${new Date(trip.departurTime).toLocaleString()}</td>
                <td>${new Date(trip.arrivalTime).toLocaleString()}</td>
                <td>Hoàng Long</td>
                <td>${trip.price}VND</td>
                <td>${trip.totalSeats}</td>
                <td><button class="route-btn book-btn" type="button" data-trip='${JSON.stringify(trip)}'>Đặt ngay</button></td>
            </tr>
        `;
        scheduleTable.appendChild(tbody);
    });
}

let allTrips = [];
const TRIPS_PER_PAGE = 10;

function disPlay(i, data, scheduleTable) {
    allTrips = Array.isArray(data.result) ? data.result : [];
    renderTripsPage(1, scheduleTable);
}

async function renderTripsPage(page, scheduleTable) {
    scheduleTable.innerHTML = `<thead>
        <tr>
            <th>Tuyến đường</th>
            <th>Giờ khởi hành</th>
            <th>Giờ đến</th>
            <th>Nhà xe</th>
            <th>Giá vé</th>
            <th>Số Lượng</th>
            <th>Đặt vé</th>
        </tr>
    </thead>`;

    const startIdx = (page - 1) * TRIPS_PER_PAGE;
    const endIdx = startIdx + TRIPS_PER_PAGE;
    const tripsToShow = allTrips.slice(startIdx, endIdx);

    for (const trip of tripsToShow) {
        let busOperatorName = 'Không xác định';

        try {
            const res = await fetch(`http://localhost:8088/busbooking/busoperator/name/${trip.busOperatorId}`);
            if (res.ok) {
                busOperatorName = await res.text();
            }
        } catch (err) {
            console.error(`Lỗi lấy tên nhà xe cho tripId=${trip.tripId}`, err);
        }

        const tbody = document.createElement("tbody");
        tbody.innerHTML = `
            <tr>
                <td>${trip.fromLocation} - ${trip.toLocation}</td>
                <td>${new Date(trip.departurTime).toLocaleString()}</td>
                <td>${new Date(trip.arrivalTime).toLocaleString()}</td>
                <td>${busOperatorName}</td>
                <td>${trip.price.toLocaleString()} VND</td>
                <td>${trip.totalSeats}</td>
                <td><button class="route-btn book-btn" type="button" data-trip='${JSON.stringify(trip)}'>Đặt ngay</button></td>
            </tr>
        `;
        scheduleTable.appendChild(tbody);
    }
    renderTripsPagination(page, scheduleTable);
}

function renderTripsPagination(currentPage, scheduleTable) {
    let paginationDiv = document.getElementById('trips-pagination');
    if (!paginationDiv) {
        paginationDiv = document.createElement('div');
        paginationDiv.id = 'trips-pagination';
        paginationDiv.className = 'pagination-bar';
        scheduleTable.parentNode.appendChild(paginationDiv);
    }
    paginationDiv.innerHTML = '';
    const totalPages = Math.ceil(allTrips.length / TRIPS_PER_PAGE);
    for (let i = 1; i <= totalPages; i++) {
        const pageBtn = document.createElement('button');
        pageBtn.textContent = i;
        pageBtn.className = 'pagination-btn' + (i === currentPage ? ' active' : '');
        pageBtn.addEventListener('click', () => renderTripsPage(i, scheduleTable));
        paginationDiv.appendChild(pageBtn);
    }
}

function openPaymentModal(trip) {
    if (!trip) {
        console.error('No trip data provided');
        return;
    }

    const layoutPayment = document.getElementById('payment-modal')
    layoutPayment.style.display = 'flex';

    layoutPayment.innerHTML = ` <div div class="modal-content" >
                    <h2>Xác nhận thanh toán</h2>
                    <div class="payment-modal-flex">
                        <div class="payment-info">
                            <p><strong>Điểm đi:</strong> ${trip.fromLocation}</p>
                            <p><strong>Điểm đến:</strong> ${trip.toLocation}</p>
                            <p><strong>Giờ khởi hành:</strong> ${new Date(trip.departurTime).toLocaleString()}</p>
                            <p><strong>Giờ đến:</strong> ${new Date(trip.arrivalTime).toLocaleString()}</p>
                            <p><strong>Giá vé:</strong> ${trip.price.toLocaleString()} VNĐ</p>
                            <p><strong>Số ghế còn lại:</strong> ${trip.totalSeats}</p>
                            <input id="ticket-quantity" class="input-ticket-quantity" type="number" min="1" max="${trip.totalSeats}" value ="1" placeholder="Nhập số lượng vé" />
                        </div>
                        <div class="payment-qr" id="payment-qr-block" style="display:none;">
                            <img id="img__qr"
                                alt="QR Thanh toán"
                                style="max-width:200px; width:100%; border-radius:12px; box-shadow:0 4px 16px #0002;">
                        </div>
                    </div>
                    <div class="payment-modal-actions">
                        <button id="pay-btn" class="pay-btn-modal">Thanh toán</button>
                        <button id="cancel-payment" class="cancel-btn-modal">Cancel</button>
                    </div>
                </div > `;

    // Add click event for cancel button
    cancelBtn = document.getElementById('cancel-payment');
    if (cancelBtn) {
        cancelBtn.addEventListener('click', function () {
            layoutPayment.style.display = 'none';
        });
    }
    // Add click event for pay button
    const payBtn = document.getElementById('pay-btn');
    if (payBtn) {
        payBtn.addEventListener('click', async function () {
            const userConfirm = confirm('Bạn có chắc muốn đặt vé?');
            if (userConfirm) {
                const userResponse = await fetch('http://localhost:8088/busbooking/users/myinfo', {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer ${localStorage.getItem('token')} `,
                        'Content-Type': 'application/json'
                    }
                });

                const seatsNumber = +document.getElementById('ticket-quantity').value;

                const requestBooking = {
                    "tripId": trip.tripId,
                    "seats_number": seatsNumber,
                }

                console.log(requestBooking);
                console.log(localStorage.getItem('token'));

                const createBooking = await fetch('http://localhost:8088/busbooking/booking', {
                    method: 'POST',
                    headers: {
                        'Authorization': `Bearer ${localStorage.getItem('token')} `,
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(requestBooking)
                });


                const dataBooking = await createBooking.json();
                console.log(dataBooking);

                if (createBooking.ok && dataBooking.code === 0) {
                    const qrBlock = document.getElementById('payment-qr-block');
                    const imgQR = document.getElementById('img__qr');

                    const totalPrice = parseFloat(trip.price) * parseFloat(seatsNumber);
                    window.amountPayment = totalPrice;

                    imgQR.src = `https://api.vietqr.io/image/970405-2008206211037-W2Fzq1W.jpg?accountName=Dinh%Vinh%20Giang&amount=${totalPrice}&addInfo=Group7%20CDIO%20bank%20`;

                    if (qrBlock) qrBlock.style.display = 'flex';
                    payBtn.style.display = 'none';
                    localStorage.setItem('bookingId', dataBooking.result.bookingId);
                } else {
                    layoutPayment.style.display = 'none';
                    alert('Không thể đặt vé, vui lòng thử lại!');
                    try {
                        fetch(`http://localhost:8088/busbooking/booking/delete/${dataBooking.result.bookingId}`, {
                            method: 'DELETE',
                            headers: {
                                'Authorization': `Bearer ${localStorage.getItem('token')}`,
                            }
                        })
                    } catch (err) {

                    }
                    layoutPayment.style.display = 'none';
                }

                cancelBtn.addEventListener('click', function () {
                    fetch(`http://localhost:8088/busbooking/booking/delete/${dataBooking.result.bookingId}`, {
                        method: 'DELETE',
                        headers: {
                            'Authorization': `Bearer ${localStorage.getItem('token')}`,
                        }
                    })
                    layoutPayment.style.display = 'none';
                });

            } else {
                layoutPayment.style.display = 'none';
            }

        });
    }
}