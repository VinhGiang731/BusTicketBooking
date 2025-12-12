document.addEventListener('DOMContentLoaded', function () {
    // Lấy tham chiếu đến các phần tử dropdown
    const departureSelect = document.getElementById('departure_select');
    const arrivalSelect = document.getElementById('arrival_select');

    // URL API để lấy danh sách tỉnh thành
    const provincesApiUrl = 'https://open.oapi.vn/location/provinces?page=0&size=63';

    // Hàm để lấy tỉnh thành từ API
    async function fetchProvinces() {
        try {
            const response = await fetch(provincesApiUrl);

            if (!response.ok) {
                throw new Error(`Yêu cầu API thất bại với mã trạng thái ${response.status}`);
            }

            const data = await response.json();
            return data.data || []; // Trả về mảng tỉnh thành hoặc mảng rỗng nếu không tìm thấy
        } catch (error) {
            console.error('Lỗi khi lấy dữ liệu tỉnh thành:', error);
            return []; // Trả về mảng rỗng trong trường hợp có lỗi
        }
    }

    // Hàm để điền dropdown với danh sách tỉnh thành
    function populateDropdown(selectElement, provinces) {
        // Xóa tất cả các option hiện có
        selectElement.innerHTML = '';

        // Tạo option mặc định
        const defaultOption = document.createElement('option');
        defaultOption.value = '';
        defaultOption.textContent = '-- Chọn tỉnh/thành phố --';
        defaultOption.disabled = true;
        defaultOption.selected = true;
        selectElement.appendChild(defaultOption);

        // Thêm các tỉnh/thành vào dropdown
        provinces.forEach(province => {
            const option = document.createElement('option');
            option.value = province.slug.toLowerCase(); // Sử dụng slug làm giá trị
            option.textContent = province.name; // Sử dụng tên tỉnh làm văn bản hiển thị
            selectElement.appendChild(option);
        });
    }

    // Khởi tạo các dropdown khi trang tải
    async function initializeDropdowns() {
        const provinces = await fetchProvinces();

        if (provinces.length > 0) {
            populateDropdown(departureSelect, provinces);
            populateDropdown(arrivalSelect, provinces);
        } else {
            console.error('Không có dữ liệu tỉnh thành');
        }
    }

    // Bắt đầu khởi tạo
    initializeDropdowns();
});
