package guru.springframwork.msscbeerservice.web.domain;

import guru.springframwork.msscbeerservice.web.model.BeerStyleEnum;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Beer {
    @Id
    @GenericGenerator(name = "UUID",  strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator="UUID")
    @Column(length = 36, columnDefinition = "varchar", nullable = false, updatable = false)
    private UUID id;

    @Version
    private Integer version;

    @CreationTimestamp
    @Column(updatable = false)
    private OffsetDateTime createDate;

    @LastModifiedDate
    @Column(updatable = false)
    private OffsetDateTime lastModifiedDate;

    private String beerName;
    private BeerStyleEnum beerStyle;

    @Column(unique = true)
    private Long upc;
    private BigDecimal price;
    private Integer quantityToBrew;
    private Integer minOnHand;


}
