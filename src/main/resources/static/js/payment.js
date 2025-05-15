function openPaymentModal() {
  document.getElementById('payment-modal').style.display = 'flex';
}

const modal = document.getElementById('payment-modal');
const cancelBtn = document.getElementById('cancel-payment');
if (modal) {
  modal.addEventListener('click', function (e) {
    if (e.target === modal) {
      modal.style.display = 'none';
    }
  });
}
if (cancelBtn) {
  cancelBtn.addEventListener('click', function () {
    modal.style.display = 'none';
  });
}