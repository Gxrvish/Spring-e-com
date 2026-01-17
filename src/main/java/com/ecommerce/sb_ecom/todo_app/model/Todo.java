package com.ecommerce.sb_ecom.todo_app.model;

import java.time.Instant;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "todo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long todoId;

    @NotBlank(message = "Todo name must not be blank")
    @Size(min = 3, message = "Todo name must contain atleast 3 characters")
    @Column(unique = true)
    private String todoName;

    private String todoDescription;
    private boolean isCompleted;
    private Long estimatedDuration;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        this.isCompleted = false;
    }

    @LastModifiedDate
    private Instant updatedAt;

    public boolean getIsCompleted() {
        return this.isCompleted;
    }
}
