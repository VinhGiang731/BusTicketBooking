// const socket = new SockJS('/busbooking/ws')
// const stompClient = Stomp.over(socket);

// stompClient.connect({}, function (frame) {
//     console.log('Connected: ' + frame);

//     stompClient.subscribe('/topic/notification', function (messageOutput) {
//         const message = JSON.parse(messageOutput.body);
//         console.log("Received message: ", message);

//         alert('Có thông báo');
//     });

// });