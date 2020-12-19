package ayoub.anbara.yoga;

import android.content.pm.ActivityInfo;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import ayoub.anbara.yoga.Custom.WorkoutDayDecorate;
import ayoub.anbara.yoga.Database.YogaDB;

public class Calendar extends AppCompatActivity {
    MaterialCalendarView materialCalendarView;
    HashSet<CalendarDay> list = new HashSet<>();

    YogaDB yogaDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_calendar);
        yogaDB = new YogaDB(this);
        materialCalendarView = findViewById(R.id.calendar);
        List<String> workoutDay = yogaDB.getWorkoutDay();
        HashSet<CalendarDay> convertedList = new HashSet<>();
        for (String value : workoutDay)
           // convertedList.add(CalendarDay.from(new Date(Long.parseLong(value))));
            convertedList.add(CalendarDay.from( org.threeten.bp.LocalDate.parse(value)));
        materialCalendarView.addDecorator(new WorkoutDayDecorate(convertedList));

    }
}
