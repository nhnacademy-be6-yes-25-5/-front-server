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

    // 페이지 콘텐츠를 비동기적으로 로드
    fetch('/coupons/policy?page=0&size=10')
        .then(response => response.text())
        .then(html => {
            const contentContainer = document.querySelector('#main-content');
            if (contentContainer) {
                contentContainer.innerHTML = html;
            }
            history.pushState(null, '', '/coupons/policy?page=0&size=10');
        })
        .catch(error => console.error('Error:', error));
});