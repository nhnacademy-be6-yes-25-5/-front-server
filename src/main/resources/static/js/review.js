document.addEventListener('DOMContentLoaded', function () {
  const bookId = document.getElementById('bookId').value;
  fetch('/reviews/books/' + bookId)
  .then(response => response.text())
  .then(html => {
    document.getElementById('review-section').innerHTML = html;
  })
  .catch(error => handleError(error));
});

function openReviewModal() {
  document.getElementById('reviewId').value = '';
  document.getElementById('review-subject').value = '';
  document.getElementById('review-message').value = '';
  document.getElementById('review-rating').value = '5';
  document.getElementById('review-images').value = '';
  document.getElementById('preview-container').innerHTML = '';
  var myModal = new bootstrap.Modal(document.getElementById('exampleModal'), {});
  myModal.show();
}

function submitReview() {
  const reviewId = document.getElementById('reviewId').value;
  const reviewName = document.getElementById('review-name').value;
  const reviewSubject = document.getElementById('review-subject').value;
  const reviewRating = document.getElementById('review-rating').value;
  const reviewMessage = document.getElementById('review-message').value;
  const reviewImages = document.getElementById('review-images').files;
  const bookId = document.getElementById('bookId').value;

  if (!reviewName || !reviewSubject || !reviewRating || !reviewMessage) {
    alert('모든 필드를 작성해주세요.');
    return;
  }

  const formData = new FormData();
  formData.append('review', new Blob([JSON.stringify({
    name: reviewName,
    subject: reviewSubject,
    rating: reviewRating,
    message: reviewMessage,
    bookId: bookId
  })], { type: 'application/json' }));

  for (let i = 0; i < reviewImages.length; i++) {
    formData.append('images', reviewImages[i]);
  }

  const url = reviewId ? `/reviews/${reviewId}` : '/reviews';
  const method = reviewId ? 'PUT' : 'POST';

  fetch(url, {
    method: method,
    body: formData,
  })
  .then(response => {
    if (!response.ok) {
      return response.json().then(error => {
        throw new Error(JSON.stringify(error));
      });
    }
    const message = reviewId ? '리뷰를 성공적으로 수정하였습니다.' : '리뷰를 성공적으로 작성하였습니다.';
    alert(message);
    location.reload();
  })
  .catch(error => handleError(error));
}

function previewImages() {
  const previewContainer = document.getElementById('preview-container');
  previewContainer.innerHTML = '';

  const files = document.getElementById('review-images').files;

  Array.from(files).forEach(file => {
    const reader = new FileReader();

    reader.onload = function(e) {
      const img = document.createElement('img');
      img.src = e.target.result;
      img.style.maxWidth = '100px';
      img.style.margin = '10px';
      previewContainer.appendChild(img);
    };

    reader.readAsDataURL(file);
  });
}

function editReview(element) {
  const reviewId = element.getAttribute('data-review-id');
  const title = element.getAttribute('data-review-title');
  const content = element.getAttribute('data-review-content');
  const rating = element.getAttribute('data-review-rating');

  document.getElementById('reviewId').value = reviewId;
  document.getElementById('review-subject').value = title;
  document.getElementById('review-message').value = content;
  document.getElementById('review-rating').value = rating;

  var myModal = new bootstrap.Modal(document.getElementById('exampleModal'), {});
  myModal.show();
}

function deleteReview(element) {
  const reviewId = element.getAttribute('data-review-id');
  if (confirm('정말로 삭제하시겠습니까?')) {
    fetch(`/reviews/${reviewId}`, {
      method: 'DELETE',
    })
    .then(response => {
      if (!response.ok) {
        throw new Error('리뷰 삭제에 실패했습니다.');
      }
      alert('리뷰가 성공적으로 삭제되었습니다.');
      location.reload();
    })
    .catch(error => handleError(error));
  }
}

function handleError(error) {
  let errorMessage = "알 수 없는 오류가 발생했습니다.";
  try {
    const parsedError = JSON.parse(error.message);
    console.log(parsedError);
    const nestedParsedError = JSON.parse(parsedError.message);
    errorMessage = nestedParsedError.message;
  } catch (e) {
    console.error("Error parsing error message:", e);
    errorMessage = error.message;
  }
  alert(`${errorMessage}`);
}
