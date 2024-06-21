const discountTypeRadios = document.querySelectorAll('input[name="discount-type"]');
const discountPercentInput = document.getElementById('discount-percent');
const discountAmountInput = document.getElementById('discount-amount');

discountTypeRadios.forEach(radio => {
    radio.addEventListener('change', () => {
        if (radio.value === 'percent') {
            discountPercentInput.classList.remove('d-none');
            discountAmountInput.classList.add('d-none');
        } else {
            discountPercentInput.classList.add('d-none');
            discountAmountInput.classList.remove('d-none');
        }
    });
});