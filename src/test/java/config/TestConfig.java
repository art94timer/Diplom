package config;

import com.art.dip.schedule.ApplicantEmailSendRunnableTask;
import com.art.dip.schedule.CleanUpVerifyTokenTask;
import com.art.dip.schedule.IfTodayFacultyIsCloseTask;
import com.art.dip.schedule.UpdateFacultyInfoTask;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
//exclude scheduling
@ComponentScan(basePackages = "com.art.dip",
        excludeFilters =
        @ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE,classes =
                {CleanUpVerifyTokenTask.class, UpdateFacultyInfoTask.class, IfTodayFacultyIsCloseTask.class,ApplicantEmailSendRunnableTask.class}))
public class TestConfig {



}
