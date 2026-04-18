package com.jpmc.midascore.entity;

import jakarta.persistence.*;
import com.jpmc.midascore.entity.UserRecord;
@Entity
public class TransactionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private float amount;

    @ManyToOne
    private UserRecord sender;

    @ManyToOne
    private UserRecord recipient;

    public TransactionRecord(){}

    public TransactionRecord(UserRecord sender, UserRecord recipient, float amount){
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
    }

    public Long getId(){
        return id;
    }

    public float getAmount(){
        return amount;
    }

    public UserRecord getSender(){
        return sender;
    }

    public UserRecord getRecipient(){
        return recipient;
    }
}