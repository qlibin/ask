package ask.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by alibin on 9/24/15.
 *
 * @author alibin
 */
public class AbstractEntity implements Serializable {

    @Id
    private String id;

    @CreatedDate
    private Date created;

    @LastModifiedDate
    private Date lastUpdated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
