package com.tutego.date4u;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Access( AccessType.FIELD )
@EntityListeners( AuditingEntityListener.class )
public class Likes {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long liker_fk;

    @Column(name = "likee_fk")
    private long likee_fk;

    @Override
    public String toString() {
        return "Like{" +
                "liker_fk=" + liker_fk +
                ", likee_fk=" + likee_fk +
                '}';
    }

    public long getLiker_fk() {
        return liker_fk;
    }

    public void setLiker_fk(long liker_fk) {
        this.liker_fk = liker_fk;
    }

    public long getLikee_fk() {
        return likee_fk;
    }

    public void setLikee_fk(long likee_fk) {
        this.likee_fk = likee_fk;
    }
}
