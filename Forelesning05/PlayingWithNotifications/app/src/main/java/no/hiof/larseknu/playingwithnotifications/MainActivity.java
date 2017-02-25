package no.hiof.larseknu.playingwithnotifications;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    private int _mNotificationCount = 0;
    private int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnNotificationOnClick(View view) {
        String title = "Avengers";
        String text = "Assemble";

        Intent intent = new Intent("no.hiof.playinwithnotifications.action.DETAILS");

        intent.putExtra(DetailsActivity.TITLE_EXTRA, title);
        intent.putExtra(DetailsActivity.BODY_TEXT_EXTRA, text);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addNextIntentWithParentStack(intent);

        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0,0);

        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.ic_avengers)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true);

        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(1, notification);
    }

    public void btnMultipleNotification(View view) {
        String title = "Messages from Hulk";
        String text = "I need help";

        Intent intent = new Intent(this, DetailsActivity.class);
        intent.setAction("MultipleNotification");
        intent.putExtra(DetailsActivity.TITLE_EXTRA, title);
        intent.putExtra(DetailsActivity.BODY_TEXT_EXTRA, text);

        Notification.Builder builder = initBasicBuilder(title, text, intent);


        // Make multi
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_hulk))
                .setNumber(++_mNotificationCount)
                .setTicker("You received another message from Hulk");

        Notification notification = builder.build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID+1, notification);

    }

    public void btnBigTextNotificationOnClick(View view) {
        String title = "Tony Stark";
        String bigText = "I'm not saying I'm responsible for this country's longest run of uninterrupted peace in 35 years! I'm not saying that from the ashes of captivity, never has a Phoenix metaphor been more personified! I'm not saying Uncle Sam can kick back on a lawn chair, sipping on an iced tea, because I haven't come across anyone man enough to go toe to toe with me on my best day! It's not about me. It's not about you, either. It's about legacy, the legacy left behind for future generations. It's not about us! ";
        String summary = "Phoenix metaphor personified";

        Intent intent = new Intent(this, DetailsActivity.class);
        intent.setAction("BigTextNotification");
        intent.putExtra(DetailsActivity.TITLE_EXTRA, title);
        intent.putExtra(DetailsActivity.BODY_TEXT_EXTRA, bigText);

        Notification.Builder builder = initBasicBuilder(title + " small", summary, intent);

        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),
                                R.drawable.ic_iron_man));

        // Add the Big Text Style
        Notification.BigTextStyle bigTextStyle = new Notification.BigTextStyle();
        bigTextStyle.setBigContentTitle(title)
                .setSummaryText(summary)
                .bigText(bigText);
        builder.setStyle(bigTextStyle);

        Notification notification = builder.build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID+2, notification);
    }

    public void btnBigPictureNotificationOnClick (View view) {
        String title = "Avengers - to New York";
        String text = "New York is under attack!";

        // Create the Intent to display the picture in an Activity
        Intent intent = new Intent(this, PictureActivity.class);
        intent.setAction("BigPictureNotification");
        intent.putExtra(PictureActivity.TITLE_EXTRA, title);
        intent.putExtra(PictureActivity.IMAGE_TEXT_EXTRA, text);
        intent.putExtra(PictureActivity.IMAGE_RESOURCE_ID_EXTRA, R.drawable.avengers_new_york);

        // Create Builder with basic notification info
        Notification.Builder builder = initBasicBuilder(title, text, intent);

        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_hulk));

        // Add the Big Picture Style
        Notification.BigPictureStyle bigPictureStyle = new Notification.BigPictureStyle();
        bigPictureStyle.bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.avengers_new_york))
                .setSummaryText(text)
                .setBigContentTitle(title);

        builder.setStyle(bigPictureStyle);

        // Construct the Notification
        Notification notification = builder.build();

        // Display the Notification
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID + 2, notification);
    }

    private Notification.Builder initBasicBuilder(String title, String text, Intent intent) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.ic_avengers)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true);

        if (intent != null){
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
            builder.setContentIntent(pendingIntent);
        }

        return builder;
    }

}
