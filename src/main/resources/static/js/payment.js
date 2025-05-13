// JavaScript cho chức năng đặt vé
document.addEventListener('DOMContentLoaded', function() {
    // Lấy modal
    const modal = document.getElementById('ticketModal');
    
    // Lấy phần tử đóng modal
    const closeBtn = document.getElementsByClassName('close')[0];
    const cancelBtn = document.querySelector('.cancel-btn');
    
    // Lấy tất cả các nút "Đặt vé"
    const bookButtons = document.querySelectorAll('.book-btn, button.đặt-vé, a.view-btn');
  
    // Các trường thông tin để hiển thị trong modal
    const routeInfo = document.getElementById('route-info');
    const companyInfo = document.getElementById('company-info');
    const departureTime = document.getElementById('departure-time');
    const arrivalTime = document.getElementById('arrival-time');
    const ticketPrice = document.getElementById('ticket-price');
    const totalAmount = document.getElementById('total-amount');
    const quantityInput = document.getElementById('ticket-quantity');
    
    // Form xác nhận đặt vé
    const passengerForm = document.getElementById('passenger-form');
    
    // Thêm sự kiện click cho tất cả các nút "Đặt vé"
    bookButtons.forEach(button => {
      button.addEventListener('click', function(e) {
        e.preventDefault();
        
        // Lấy thông tin từ hàng chứa nút đặt vé
        const row = this.closest('tr');
        
        if (row) {
          // Nếu là từ bảng lịch trình
          const cells = row.getElementsByTagName('td');
          
          if (cells.length >= 5) {
            routeInfo.textContent = cells[0].textContent;
            departureTime.textContent = cells[1].textContent;
            arrivalTime.textContent = cells[2].textContent;
            companyInfo.textContent = cells[3].textContent;
            ticketPrice.textContent = cells[4].textContent;
            totalAmount.textContent = cells[4].textContent;
          }
        } else {
          // Nếu là từ thẻ route-card
          const card = this.closest('.route-card');
          
          if (card) {
            const routeTitle = card.querySelector('h3');
            const timeInfo = card.querySelector('.route-info p:first-of-type');
            
            if (routeTitle) routeInfo.textContent = routeTitle.textContent;
            if (timeInfo) {
              const timeText = timeInfo.textContent.replace('⏱️', '').trim();
              departureTime.textContent = "Xem lịch chi tiết";
              arrivalTime.textContent = timeText;
            }
            companyInfo.textContent = "Nhiều nhà xe";
            ticketPrice.textContent = "Xem chi tiết";
            totalAmount.textContent = "Cần chọn chuyến";
          }
        }
        
        // Hiển thị modal
        modal.style.display = 'block';
      });
    });
    
    // Cập nhật tổng tiền khi thay đổi số lượng vé
    quantityInput.addEventListener('change', function() {
      const price = ticketPrice.textContent;
      if (price.includes('đ')) {
        const priceValue = parseInt(price.replace(/\D/g, ''));
        const quantity = parseInt(this.value);
        totalAmount.textContent = (priceValue * quantity).toLocaleString() + 'đ';
      }
    });
    
    // Đóng modal khi nhấn vào nút đóng
    closeBtn.addEventListener('click', function() {
      modal.style.display = 'none';
    });
    
    // Đóng modal khi nhấn vào nút hủy
    cancelBtn.addEventListener('click', function() {
      modal.style.display = 'none';
    });
    
    // Đóng modal khi nhấn ra ngoài modal
    window.addEventListener('click', function(event) {
      if (event.target == modal) {
        modal.style.display = 'none';
      }
    });
    
    // Xử lý form đặt vé
    passengerForm.addEventListener('submit', function(e) {
      e.preventDefault();
      
      // Hiển thị thông báo đặt vé thành công
      alert('Đặt vé thành công! Chúng tôi sẽ liên hệ với bạn sớm nhất.');
      modal.style.display = 'none';
      
      // Reset form
      this.reset();
      quantityInput.value = 1;
    });
  });