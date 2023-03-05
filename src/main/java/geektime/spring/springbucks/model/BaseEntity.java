package geektime.spring.springbucks.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;

/**
 * 咖啡和订单实体的超类
 * 为了实现redis缓存, 此处注意需实现序列化接口
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -6332117972924689245L;

    private Long id;

    private Date createTime;

    private Date updateTime;

}
