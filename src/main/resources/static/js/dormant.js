document.addEventListener("DOMContentLoaded", function() {
  const getVerificationCodeBtn = document.getElementById("getVerificationCodeBtn");
  const timerDisplay = document.getElementById("timer");

  getVerificationCodeBtn.addEventListener("click", function() {
    startTimer(3 * 60, timerDisplay); // 3분 타이머 시작
    sendAuthRequest();
  });

  function startTimer(duration, display) {
    let timer = duration, minutes, seconds;
    display.style.display = "block"; // 타이머 표시
    const interval = setInterval(function() {
      minutes = parseInt(timer / 60, 10);
      seconds = parseInt(timer % 60, 10);

      minutes = minutes < 10 ? "0" + minutes : minutes;
      seconds = seconds < 10 ? "0" + seconds : seconds;

      display.textContent = minutes + ":" + seconds;

      if (--timer < 0) {
        clearInterval(interval);
        display.textContent = "시간 초과";
      }
    }, 1000);
  }

  function sendAuthRequest() {
    const email = document.getElementById("email").value;

    fetch("/dormant/auth", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ email: email })
    })
    .then(response => {
      if (response.ok) {
        alert("인증번호가 발송되었습니다.");
      } else {
        return response.json().then(data => {
          handleError(new Error(data.message || "인증번호 발송에 실패했습니다."));
        });
      }
    })
    .catch(error => handleError(error));
  }
});
