package com.example.jf.cardapp.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by jf on 2017/2/23.
 */
public class RandomCode {
    private static final char[] CHARS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'

    };
    private static RandomCode randomCode;
    private int mPaddingLeft,mPaddingTop;
    private StringBuilder stringBuilder = new StringBuilder();
    private Random random = new Random();
    private static String code;

    //各项数据设置
    private static final int DEFAULT_CODE_LENGTH = 4;//验证码长度
    private static final int DEFAULT_FONT_SIZE = 80;//字体大小
    private static final int DEFAULT_LINE_NUMBER = 3;//干扰线
    private static final int BASE_PADDING_LEFT = 20;//左边距
    private static final int RANGE_PADDING_LEFT = 5;//左边距范围值
    private static final int BASE_PADDING_TOP = 40;//上边距
    private static final int RANGE_PADDING_TOP = 10;//上边距范围值
    private static final int DEFAULT_WIDTH = 150;//默认宽度.图片的总宽
    private static final int DEFAULT_HEIGHT = 300;//默认高度.图片的总高
    private static final int DEFAULT_COLOR = 0xDF;//默认背景颜色值


    public static RandomCode getInstance() {
        if(randomCode == null) {
            randomCode = new RandomCode();
        }
        return randomCode;
    }

    //生成验证码图片
    public Bitmap createBitmap(){
        mPaddingLeft = 0;//初始化
        mPaddingTop = 0;
        Bitmap bitmap = Bitmap.createBitmap(DEFAULT_WIDTH,DEFAULT_HEIGHT, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);

        //生成验证码
        code = createCode();

        canvas.drawColor(Color.rgb(DEFAULT_COLOR,DEFAULT_COLOR,DEFAULT_COLOR));
        Paint paint = new Paint();
        paint.setTextSize(DEFAULT_FONT_SIZE);

        for (int i = 0; i < code.length(); i++){
            randomTextStyle(paint);
            randomPadding();
            canvas.drawText(code.charAt(i)+"",mPaddingLeft,mPaddingTop,paint);
        }
        //干扰线
        for (int i = 0; i < DEFAULT_LINE_NUMBER; i++){
            drawLine(canvas,paint);
        }

        canvas.save(Canvas.ALL_SAVE_FLAG);//保存
        canvas.restore();
        return bitmap;
    }
    public static String getCode(){
        return code;
    }
    //验证码
    private String createCode() {
        stringBuilder.delete(0,stringBuilder.length());//清空一下
        for (int i = 0; i < DEFAULT_CODE_LENGTH; i++){
            stringBuilder.append(CHARS[random.nextInt(CHARS.length)]);
        }
        return stringBuilder.toString();
    }
    //干扰线
    private void drawLine(Canvas canvas,Paint paint){
        int color = randomColor();
        int startX = random.nextInt(DEFAULT_WIDTH);
        int startY = random.nextInt(DEFAULT_HEIGHT);
        int stopX = random.nextInt(DEFAULT_WIDTH);
        int stopY = random.nextInt(DEFAULT_HEIGHT);
        paint.setStrokeWidth(1);
        paint.setColor(color);
        canvas.drawLine(startX,startY,stopX,stopY,paint);
    }
    //随机颜色
    private int randomColor() {
        stringBuilder.delete(0,stringBuilder.length());//清空内容

        String haxString;
        for (int i = 0; i < 3; i++){
            haxString = Integer.toHexString(random.nextInt(0xFF));
            if (haxString.length() == 1){
                haxString = "0"+haxString;
            }
            stringBuilder.append(haxString);
        }
        return Color.parseColor("#"+stringBuilder.toString());
    }
    //随机文本样式
    private void randomTextStyle(Paint paint){
        int color = randomColor();
        paint.setColor(color);
        paint.setFakeBoldText(true);//true为粗体random.nextBoolean()
        float skewX = random.nextInt(11)/10;
        skewX = random.nextBoolean()?skewX:-skewX;
        paint.setTextSkewX(skewX);//负数右斜。正数左斜
        //        paint.setUnderlineText(true); //true为下划线，false为非下划线
        //        paint.setStrikeThruText(true); //true为删除线，false为非删除线
    }
    //随机间距
    private void randomPadding(){
        //左边距，左边距范围值
        mPaddingLeft += BASE_PADDING_LEFT + random.nextInt(RANGE_PADDING_LEFT);
        mPaddingTop += BASE_PADDING_TOP + random.nextInt(RANGE_PADDING_TOP);
    }
}
