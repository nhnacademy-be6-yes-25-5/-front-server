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
        let errorMessage = "알 수 없는 오류가 발생했습니다.";
        try {
          const parsedError = JSON.parse(error.message);
          const nestedParsedError = JSON.parse(parsedError.message);
          errorMessage = nestedParsedError.message;
        } catch (e) {
          console.error("Error parsing error message:", e);
          errorMessage = error.message;
        }
        alert(`에러: ${errorMessage}`);
      });
    });
  });
});
