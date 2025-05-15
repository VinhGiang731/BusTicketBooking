const listTrip = [];
document.addEventListener("DOMContentLoaded", function () {
    //http://localhost:8080/busbooking/trip
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

    fetch('http://localhost:8080/busbooking/trip')
        .then(response => response.json())
        .then(data => {
            if (data.code === 0 && Array.isArray(data.result)) {
                listTrip.push(...data.result);
                var i = 0;
                if (routeCards) {
                    i = 0;
                    data.result.slice(0, 3).forEach(trip => {
                        if (i === 3) return;
                        const card = document.createElement("div")
                        card.className = "route-card";
                        card.innerHTML = `
                           <div class="route-img" style="background-image: url('https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6j-RnaTlCeJ3XTwmeZ3UUZ5u7aSb_9qryXw&s')"></div>
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
                        i++;
                    })
                }


                if (routesGrid) {
                    i = 0;
                    data.result.slice(0, 6).forEach(trip => {
                        if (i === 7) return;
                        const card = document.createElement("div")
                        card.className = "route-card";
                        card.innerHTML = `
                            <div class="route-info">
                                <h3>${trip.fromLocation} - ${trip.toLocation}</h3>
                                 <p>Khởi hành: ${new Date(trip.departurTime).toLocaleString()}</p>
                                <p>Đến nơi: ${new Date(trip.arrivalTime).toLocaleString()}</p>
                            </div>
                            <td><button class="route-btn book-btn" type="button" data-trip='${JSON.stringify(trip)}'>Đặt ngay</button></td>
                        `;
                        routesGrid.appendChild(card);
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
    document.addEventListener('click', function (e) {
        if (e.target.classList.contains('book-btn')) {
            const tripData = JSON.parse(e.target.getAttribute('data-trip'));
            openPaymentModal(tripData);
        }
    });

    // Đảm bảo chỉ gán 1 lần, không bị mất khi innerHTML
    const modal = document.getElementById('payment-modal');
    if (modal) {
        modal.addEventListener('click', function (e) {
            if (e.target === modal) {
                modal.style.display = 'none';
            }
        });
    }
});

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

function disPlay(i, data, scheduleTable) {
    i = 0;
    data.result.slice(0, 10).forEach(trip => {
        if (i === 10) return;
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
        i++;
    });
}

function openPaymentModal(trip) {
    if (!trip) {
        console.error('No trip data provided');
        return;
    }

    const layoutPayment = document.getElementById('payment-modal')
    layoutPayment.style.display = 'flex';

    layoutPayment.innerHTML = ` <div class="modal-content">
                    <h2>Xác nhận thanh toán</h2>
                    <div class="payment-modal-flex">
                        <div class="payment-info">
                            <p><strong>Điểm đi:</strong> ${trip.fromLocation}</p>
                            <p><strong>Điểm đến:</strong> ${trip.toLocation}</p>
                            <p><strong>Giờ khởi hành:</strong> ${new Date(trip.departurTime).toLocaleString()}</p>
                            <p><strong>Giờ đến:</strong> ${new Date(trip.arrivalTime).toLocaleString()}</p>
                            <p><strong>Giá vé:</strong> ${trip.price.toLocaleString()} VNĐ</p>
                            <p><strong>Số ghế còn lại:</strong> ${trip.totalSeats}</p>
                        </div>
                        <div class="payment-qr">
                            <img
                                src="https://api.vietqr.io/image/970423-88868088888-W2Fzq1W.jpg?accountName=Nguyen%20Van%20Cuong&amount=${trip.price}&addInfo=oapi%20vn%20free%20api"
                                alt="QR Thanh toán"
                                style="max-width:180px; width:100%; border-radius:12px; box-shadow:0 4px 16px #0002;">
                        </div>
                    </div>
                    <button id="cancel-payment" class="cancel-btn-modal">Cancel</button>
                </div>`;

    // Add click event for cancel button
    const cancelBtn = document.getElementById('cancel-payment');
    if (cancelBtn) {
        cancelBtn.addEventListener('click', function () {
            layoutPayment.style.display = 'none';
        });
    }
}