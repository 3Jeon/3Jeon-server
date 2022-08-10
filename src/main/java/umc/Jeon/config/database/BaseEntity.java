package umc.Jeon.config.database;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass //BaseEntity를 상속한 엔티티들은 아래 필드들을 컬럼으로 인식
@EntityListeners({AuditingEntityListener.class})
public abstract class BaseEntity {
    protected boolean status;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

//    @Enumerated(EnumType.STRING)
//    @Column(length = 10, columnDefinition = "varchar(10) default 'ACTIVE'")
//    private Status status = Status.ACTIVE;
//
//    public void changeStatus(Status status) {
//        this.status = status;
//    }

}
