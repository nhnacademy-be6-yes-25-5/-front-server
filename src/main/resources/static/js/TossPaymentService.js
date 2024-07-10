class TossPaymentService extends PaymentService {
  constructor(clientKey) {
    super();
    this.tossPayments = TossPayments(clientKey);
  }

  requestPayment(orderData) {
    const { orderId, totalAmount } = orderData;
    const customerKey = generateUUID();

    return this.tossPayments.requestPayment('카드', {
      amount: totalAmount,
      orderId: orderId,
      orderName: orderId,
      customerName: customerKey,
      successUrl: window.location.origin + "/payments/success",
      failUrl: window.location.origin + "/payments/fail",
    });
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
