package repository.testData;

import com.art.dip.model.Credential;
import com.art.dip.model.Person;
import com.art.dip.model.Role;

import java.time.LocalDate;

public class PersonData {

    public static final Person ADMIN = new Person("admin","art", LocalDate.now(),"admin@admin.by",new Credential(), Role.ADMIN,true);
    public static final Person ADMIN_FAKE = new Person("admiqwn","art", LocalDate.now(),"admin@admin.by",new Credential(), Role.ADMIN,true);
}
