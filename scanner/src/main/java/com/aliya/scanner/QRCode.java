package com.aliya.scanner;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.text.TextUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;

/**
 * QRCode
 *
 * @author a_liYa
 * @date 2019-06-18 16:36.
 */
public class QRCode {

    private String mContent;
    private int mWidth;
    private int mHeight;

    private int mMargin;
    private String mCharacterSet = "UTF-8";
    private ErrorCorrectionLevel mErrorCorrectionLevel = ErrorCorrectionLevel.H;
    private int mColorBlack = Color.BLACK;
    private int mColorWhite = Color.WHITE;
    private Bitmap.Config mConfig = Bitmap.Config.ARGB_8888;

    public QRCode(String content, int width, int height) {
        this.mContent = content;
        this.mWidth = width;
        this.mHeight = height;
    }

    /*
     * 单位像素，实际并非如此
     */
    public QRCode setMargin(int margin) {
        this.mMargin = margin;
        return this;
    }

    public QRCode setCharacterSet(String characterSet) {
        this.mCharacterSet = characterSet;
        return this;
    }

    /**
     * 容错率 L：7% M：15% Q：25% H：35%
     *
     * @param errorCorrectionLevel 默认为H
     * @return this
     */
    public QRCode setErrorCorrectionLevel(ErrorCorrectionLevel errorCorrectionLevel) {
        this.mErrorCorrectionLevel = errorCorrectionLevel;
        return this;
    }

    public QRCode setColorBlack(@ColorInt int colorBlack) {
        this.mColorBlack = colorBlack;
        return this;
    }

    public QRCode setColorWhite(@ColorInt int colorWhite) {
        this.mColorWhite = colorWhite;
        return this;
    }

    public QRCode setConfig(Bitmap.Config config) {
        if (config != null)
            mConfig = config;
        return this;
    }

    public Bitmap createQRCode() {
        if (TextUtils.isEmpty(mContent)) {
            return null;
        }
        if (mWidth < 0 || mHeight < 0) {
            return null;
        }
        try {
            /** 1.设置二维码相关配置 */
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();
            // 字符转码格式设置
            if (!TextUtils.isEmpty(mCharacterSet)) {
                hints.put(EncodeHintType.CHARACTER_SET, mCharacterSet);
            }
            // 容错率设置
            if (mErrorCorrectionLevel != null) {
                hints.put(EncodeHintType.ERROR_CORRECTION, mErrorCorrectionLevel.name());
            }
            // 空白边距设置
            hints.put(EncodeHintType.MARGIN, String.valueOf(mMargin));

            /** 2.将配置参数传入到QRCodeWriter的encode方法生成BitMatrix(位矩阵)对象 */
            BitMatrix bitMatrix = new QRCodeWriter().encode(
                    mContent, BarcodeFormat.QR_CODE, mWidth, mHeight, hints);

            /** 3.创建像素数组,并根据BitMatrix(位矩阵)对象为数组元素赋颜色值 */
            int[] pixels = new int[mWidth * mHeight];
            for (int y = 0; y < mHeight; y++) {
                for (int x = 0; x < mWidth; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * mWidth + x] = mColorBlack; // 黑色色块像素设置
                    } else {
                        pixels[y * mWidth + x] = mColorWhite; // 白色色块像素设置
                    }
                }
            }
            /** 4.创建Bitmap对象,根据像素数组设置Bitmap每个像素点的颜色值,并返回Bitmap对象 */
            Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, mConfig);
            bitmap.setPixels(pixels, 0, mWidth, 0, 0, mWidth, mHeight);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

}
