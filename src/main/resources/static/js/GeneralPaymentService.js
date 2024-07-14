class GeneralPaymentService extends PaymentService{
  constructor(clientKey) {
    super();
    this.clientKey = clientKey;
  }

  confirmPayment(paymentKey, orderId, amount) {
    return fetch("/payments/confirm", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ paymentKey, orderId, amount }),
    });
  }
}