package com.jpmc.midascore.consumer;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TransactionListener {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @KafkaListener(topics = "${general.kafka-topic}")
    public void listen(Transaction transaction) {

        UserRecord sender = userRepository.findById(transaction.getSenderId());
        UserRecord receiver = userRepository.findById(transaction.getRecipientId());

        if(sender == null || receiver == null) {
            return;
        }

        float amount = transaction.getAmount();

        if(sender.getBalance() < amount) {
            return;
        }

        // CALL INCENTIVE API
        Incentive incentive = restTemplate.postForObject(
                "http://localhost:8080/incentive",
                transaction,
                Incentive.class
        );

        float incentiveAmount = 0;

        if(incentive != null) {
            incentiveAmount = incentive.getAmount();
        }

        // UPDATE BALANCES
        sender.setBalance(sender.getBalance() - amount);

        receiver.setBalance(
                receiver.getBalance() + amount + incentiveAmount
        );

        userRepository.save(sender);
        userRepository.save(receiver);
    }
}