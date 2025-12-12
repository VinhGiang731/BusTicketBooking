// File scroll.js - Cài đặt hiệu ứng xuất hiện khi lướt trang
document.addEventListener('DOMContentLoaded', function() {
  // Chọn tất cả các phần tử cần áp dụng hiệu ứng
  const elementsToAnimate = document.querySelectorAll(
    '.hero-content, .search-form, .features-container, .feature-card, .popular-routes .section-title, .route-card'
  );
  
  // Thêm class ban đầu để ẩn các phần tử
  elementsToAnimate.forEach(element => {
    element.classList.add('hidden-element');
  });
  
  // Tạo Intersection Observer
  const observer = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
      // Khi phần tử đi vào viewport
      if (entry.isIntersecting) {
        // Thêm các class animation
        entry.target.classList.add('animate__animated');
        
        // Xác định hiệu ứng dựa trên loại phần tử
        if (entry.target.classList.contains('hero-content')) {
          entry.target.classList.add('animate__fadeIn');
        } else if (entry.target.classList.contains('search-form')) {
          entry.target.classList.add('animate__fadeInUp');
        } else if (entry.target.classList.contains('features-container')) {
          entry.target.classList.add('animate__fadeIn');
        } else if (entry.target.classList.contains('feature-card')) {
          entry.target.classList.add('animate__fadeInUp');
        } else if (entry.target.classList.contains('section-title')) {
          entry.target.classList.add('animate__fadeIn');
        } else if (entry.target.classList.contains('route-card')) {
          entry.target.classList.add('animate__fadeInUp');
        }
        
        // Xóa class hidden-element
        entry.target.classList.remove('hidden-element');
      }
      // Không có xử lý else để giữ các phần tử hiển thị sau khi đã xuất hiện
    });
  }, {
    // Phần tử sẽ được kích hoạt khi hiển thị 20% trong viewport
    threshold: 0.2,
    // Giảm giá trị rootMargin để hiệu ứng kích hoạt khi phần tử gần hơn với viewport
    rootMargin: '0px 0px -20px 0px'
  });
  
  // Bắt đầu quan sát các phần tử
  elementsToAnimate.forEach(element => {
    observer.observe(element);
  });
  
  // Thêm reference đến file CSS cho animation
  if (!document.getElementById('animation-styles')) {
    const linkElem = document.createElement('link');
    linkElem.id = 'animation-styles';
    linkElem.rel = 'stylesheet';
    linkElem.href = 'animation.css'; // Đường dẫn đến file CSS animation
    document.head.appendChild(linkElem);
  }
});