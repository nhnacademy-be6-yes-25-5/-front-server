/*
* 전체 물품 목록에서 장바구니에 담는 기능입니다.
* */
document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('.add-to-cart-button').forEach(function(button) {
        button.addEventListener('click', function() {
            var bookId = this.getAttribute('data-book-id');
            var quantity = 1;

      fetch('/carts', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          bookId: bookId,
          quantity: quantity
        })
      })
      .then(response => {
        if (!response.ok) {
          return response.json().then(error => {
            throw new Error(JSON.stringify(error));
          });
        }
        return response.json();
      })
      .then(data => {
        alert('장바구니에 성공적으로 추가되었습니다.');
      })
      .catch(error => {
        handleError(error);
    });
});
