package guruspringframework.sdjpamultidb.domain.cardholder;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "credit_card_holder")
public class CreditCardHolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String zipCode;

    @Column(name = "credit_card_id")
    private Long creditCardId;
}