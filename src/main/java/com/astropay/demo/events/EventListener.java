package com.astropay.demo.events;

import com.astropay.demo.model.Transaction;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;


@Component
@Log4j2
public class EventListener {

  @TransactionalEventListener(
      phase = TransactionPhase.AFTER_COMMIT
  )
  public void onTransactionCompleted(Transaction transaction) {
    log.info("message= Transaction event sent, txId={}, amount={}", transaction.getId(), transaction.getAmount());
  }

}
