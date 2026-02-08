package com.eztrad.servercomp.model;

import com.eztrad.servercomp.domain.OrderStatus;
import com.eztrad.servercomp.domain.OrderType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// Step 64 - Order model
@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // Step 67 - additional objects creation

    @ManyToOne     // one user can have many order
    private User user;

    // Step 68 - go and create a domain for OrderType and come back
    // Step 69 - go and create a domain for OrderStatus and come back

    @Column(nullable = false) // means this field cant be null and it's important
    private OrderType orderType;

    @Column(nullable = false)
    private BigDecimal price;

    private LocalDateTime timestamp = LocalDateTime.now();

    @Column(nullable = false)
    private OrderStatus status;

    // Step 70 - go and create OrderItem model and come back

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private OrderItem orderItem;

    // so go again for WalletServiceImplement
}
