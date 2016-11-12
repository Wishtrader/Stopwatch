package stopwatch.com.craneglee.stopwatch;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StopwatchActivity extends AppCompatActivity {

    private int seconds = 0; //Переменная хранит уоличество прошедших секунд
    private boolean running; // флаг работы секундомера
    private boolean wasRunning; // в переменной хранится информация о том,
    //работал ли секундомер перед вызовом onStop().

    @Override
    public void onSaveInstanceState (Bundle savedInstanceState) {
        //Значения переменных seconds и running сохраняются в Bundle.
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        //Сщхранить состояние переменной wasRunning.
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    @Override
    protected void onStop() {
        super.onStop();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (wasRunning) {
            running = true;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        if (savedInstanceState != null) {
            //Получить значения переменных seconds и running из Bundle.
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            //Восстановить состояние переменной wasRunning, если активность создается заново.
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer(); //Для обновления секундомера используется отдельный метод.
        //Он запускается при сощдании активности.
    }

    //Запустить секундомер при щелчке на кнопке Start.
    public void onClickStart(View view) { //Вызывается при щелчке на кнопке Start
        running = true; //Запустить секундомер.
    }

    //Остановить секундомер при щелчке на кнопке Spot.
    public void onClickStop(View view) {
        running = false; //Остановить секундомер
    }

    //Обнулить секундомер при щелчке на кнопке Reset.
    public void onClickReset(View view) {
        running = false; //Остановить секундомер
        seconds = 0; //Обнулить секундомер
    }

    private void runTimer() {
        final TextView timeView = (TextView)findViewById(R.id.time_view);
        final Handler handler = new Handler();
        //Создать новый объект Handler. Вызов метода post() с передачей нового объекта Runnable.
        // Метод post() обеспечивает выполнение без задержки,
        // так что код в Runnable будет выполнен практически немедленно
        handler.post(new Runnable() {
            //Использовать Handler для передачи кода на выполнение.
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;
                //Форматировать seconds в часы, минуты и секунды.
                String time = String.format("%d:%02d:%02d",
                        hours, minutes, secs);
                //Задать текст надписи.
                timeView.setText(time);
                if (running) {
                    //Если значение running истинно, увеличить переменную seconds.
                    seconds++;
                }
                //Запланировать повторное выполнение кода с задержкой в 1 секунду.
                handler.postDelayed(this, 1000);
                //Код из объекта Runnable передается на повторное выполнение после истечения
                //задержки в 1000 миллисекунд (1 секунда).
                //Так как эта строка кода включена в метод run() объекта Runnable, код будет
                //вызываться снова и снова.
            }
        });
    }

}
