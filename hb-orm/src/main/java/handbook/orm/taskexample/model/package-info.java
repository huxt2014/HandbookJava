@TypeDefs(
        @TypeDef(name="json", typeClass = JsonType.class)
)
package handbook.orm.taskexample.model;

import com.vladmihalcea.hibernate.type.json.JsonType;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;