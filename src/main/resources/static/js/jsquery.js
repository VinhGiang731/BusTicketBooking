const listTrip = [];
document.addEventListener("DOMContentLoaded", function () {
    //http://localhost:8080/busbooking/trip
    const routeCards = document.querySelector(".route-cards");
    console.log(routeCards);

    const routesGrid = document.querySelector(".routes-grid");
    console.log(routesGrid);

    const scheduleTable = document.querySelector(".schedule-table");
    console.log(scheduleTable);

    fetch('http://localhost:8080/busbooking/trip')
        .then(response => response.json())
        .then(data => {
            if (data.code === 0 && Array.isArray(data.result)) {
                listTrip.push(...data.result);
                var i = 0;
                if (routeCards) {
                    i = 0;
                    data.result.forEach(trip => {
                        if (i === 3) return;
                        const card = document.createElement("div")
                        card.className = "route-card";
                        card.innerHTML = `
                           <div class="route-img" th:style="|background-image: url('https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6j-RnaTlCeJ3XTwmeZ3UUZ5u7aSb_9qryXw&s')|"></div>
                            <div class="route-info">
                                <h3>${trip.fromLocation} - ${trip.toLocation}</h3>
                                <p>Khởi hành: ${new Date(trip.departurTime).toLocaleString()}</p>
                                <p>Đến nơi: ${new Date(trip.arrivalTime).toLocaleString()}</p>
                                <div class="route-price">Giá vé: ${trip.price.toLocaleString()} VNĐ</div>
                                <p>Số ghế: ${trip.totalSeats}</p>
                                <a href="/busbooking/busticket/schedule" class="route-btn book-btn">Đặt ngay</a>
                            </div>
                        `;
                        routeCards.appendChild(card);
                        i++;
                    })
                }


                if (routesGrid) {
                    i = 0;
                    data.result.forEach(trip => {
                        if (i === 7) return;
                        const card = document.createElement("div")
                        card.className = "route-card";
                        card.innerHTML = `
                            <div class="route-info">
                                <h3>${trip.fromLocation} - ${trip.toLocation}</h3>
                                 <p>Khởi hành: ${new Date(trip.departurTime).toLocaleString()}</p>
                                <p>Đến nơi: ${new Date(trip.arrivalTime).toLocaleString()}</p>
                            </div>
                            <td><a class="book-btn">Đặt vé</a></td>
                        `;
                        routesGrid.appendChild(card);
                        i++;
                    })
                }
                if (scheduleTable) {
                    i = 0;
                    data.result.forEach(trip => {
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
                                <td><a class="book-btn">Đặt vé</a></td>
                            </tr>
                        `;
                        scheduleTable.appendChild(tbody);
                        i++;
                    })
                }
            }
            else {
                console.warn("Error fetching data");
            }
        }).catch(error => {
            console.error("Error fetching data", error);
        })
});