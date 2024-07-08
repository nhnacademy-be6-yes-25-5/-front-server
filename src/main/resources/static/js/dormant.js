document.addEventListener("DOMContentLoaded", function() {
  const getVerificationCodeBtn = document.getElementById("getVerificationCodeBtn");
  const submitVerificationCodeBtn = document.getElementById("submitVerificationCodeBtn");
  const timerDisplay = document.getElementById("timer");
  const emailInput = document.getElementById("email");
  const verificationCodeInput = document.getElementById("verificationCode");

  getVerificationCodeBtn.addEventListener("click", function() {
    if (!emailInput.value) {
      alert("이메일을 입력해주세요.");
      return;
    }
    startTimer(3 * 60, timerDisplay); // 3분 타이머 시작
    sendAuthRequest();
    getVerificationCodeBtn.textContent = "재발급";
  });

  submitVerificationCodeBtn.addEventListener("click", function() {
    if (!emailInput.value || !verificationCodeInput.value) {
      alert("이메일과 인증번호를 모두 입력해주세요.");
      return;
    }
    sendVerificationCode();
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
    const email = emailInput.value;

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

  function sendVerificationCode() {
    const email = emailInput.value;
    const verificationCode = verificationCodeInput.value;

    fetch("/dormant/validate", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ email: email, verificationCode: verificationCode })
    })
    .then(response => response.json())
    .then(data => {
      if (data === true) {
        alert("인증번호가 확인되어 휴면이 해제되었습니다. 다시 로그인해주세요.");
        window.location.href = "/auth/login";
      } else {
        alert("인증번호가 일치하지 않습니다.");
      }
    })
    .catch(error => handleError(error));
  }
});