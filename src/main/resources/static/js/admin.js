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

document.getElementById('policyLink').addEventListener('click', function(event) {
    event.preventDefault();

    var target = document.querySelector(this.getAttribute('data-bs-target'));
    if (target) {
        // 현재 상태 확인
        var isExpanded = this.getAttribute('aria-expanded') === 'true';

        console.log(isExpanded)

        // 상태 토글
        if (isExpanded) {
            this.setAttribute('aria-expanded', 'false');
            console.log(this.getAttribute("ara-expanded") + " 닫혔습ㄴ다.")
        } else {
            this.setAttribute('aria-expanded', 'true');
            console.log(this.getAttribute("ara-expanded") + " 열렸ㄴ다.")
        }
    }
});