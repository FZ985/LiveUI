package io.live.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.live.ui.kit.LiveType;
import io.live.ui.kit.entry.LiveMessage;
import io.live.ui.kit.entry.LiveUiMessage;
import io.live.ui.kit.entry.body.LiveTextBody;

/**
 * by JFZ
 * 2024/4/12
 * desc：
 **/
public class LiveTest {


    public static List<LiveUiMessage> msgs() {
        List<LiveUiMessage> list = new ArrayList<>();
        list.add(randomText("哈"));
        list.add(randomText("哈"));
//        list.add(randomText("哈"));
//        list.add(randomText("哈"));
//        list.add(randomText("哈"));
//        list.add(randomText("哈"));
//        list.add(randomText("哈"));
//        list.add(randomText("哈"));
//        list.add(randomText("哈"));
        return list;
    }

    public static LiveUiMessage randomText(String text) {
        int count = randomNumber(5, 30);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(text);
        }
        LiveTextBody body = LiveTextBody.obtain(sb.toString());
        LiveMessage message = LiveMessage.obtain(LiveType.Text, body);
        return new LiveUiMessage(message);
    }


    public static int randomNumber(int min, int max) {
        Random rand = new Random();
        return rand.nextInt(max - min) + min;
    }
}
