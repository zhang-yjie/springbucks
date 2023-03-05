package geektime.spring.springbucks.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.joda.money.Money;

import java.io.Serializable;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Coffee extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 978771241664730304L;

    private String name;

    private Money price;
}
