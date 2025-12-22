package com.ecommerce.sb_ecom.todo_app.model;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "todo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long todoId;

    @NotBlank(message = "Todo name must not be blank")
    @Size(min = 3, message = "Todo name must contain atleast 3 characters")
    private String todoName;

    private String todoDescription;
    private boolean isCompleted;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        this.isCompleted = false;
        this.createdAt = Instant.now();
    }

    @LastModifiedDate
    private Instant updatedAt;

    public boolean getIsCompleted() {
        return this.isCompleted;
    }
}
