function createPaymentService(serviceType) {
  let clientKey = 'test_ck_5OWRapdA8dJYOKZkAR06Vo1zEqZK';

  if (serviceType === 'toss') {
    return new TossPaymentService(clientKey);
  }

  if (serviceType === 'naver') {
    alert('현재 개발중입니다. 다른 결제 수단을 사용해주세요.');
    throw new Error("Naver Pay service is currently under development");
  }

  if (serviceType === 'kakao') {
    alert('현재 개발중입니다. 다른 결제 수단을 사용해주세요.');
    throw new Error("Kakao Pay service is currently under development");
  }

  if (serviceType === 'general') {
    return new GeneralPaymentService(clientKey);
  }

  throw new Error("Unknown payment service type");
}