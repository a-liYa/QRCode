package com.aliya.scanner;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.text.TextUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;
import java.util.Map;

/**
 * QRCodeEncoder, 用于生成二维码
 *
 * @author a_liYa
 * @date 2019-06-18 16:36.
 */
public class QRCodeEncoder {

    private String mContent;
    private int mWidth;
    private int mHeight;

    private int mVersion;
    private int mMargin;
    private String mCharacterSet = "UTF-8";
    private ErrorCorrectionLevel mErrorCorrectionLevel = ErrorCorrectionLevel.H;
    private int mColorBlack = Color.BLACK;
    private int mColorWhite = Color.WHITE;
    private Bitmap.Config mConfig = Bitmap.Config.ARGB_8888;

    private Bitmap mLogoBitmap;
    private float mLogoScale;

    private Bitmap mBitmapBlack;

    public QRCodeEncoder(String content, int width, int height) {
        this.mContent = content;
        this.mWidth = width;
        this.mHeight = height;
    }

    /**
     * Version n 是 (21 + 4*(n - 1)) x (21 + 4*(n - 1)) 的矩阵
     * @param version [1, 40]
     * @return this
     */
    public QRCodeEncoder setVersion(int version) {
        if (version >= 1 && version <= 40)
            mVersion = version;
        return this;
    }

    /*
     * 单位像素，实际并非如此
     */
    public QRCodeEncoder setMargin(int margin) {
        this.mMargin = margin;
        return this;
    }

    public QRCodeEncoder setCharacterSet(String characterSet) {
        this.mCharacterSet = characterSet;
        return this;
    }

    /**
     * 容错率 L：7% M：15% Q：25% H：35%
     *
     * @param errorCorrectionLevel 默认为H
     * @return this
     */
    public QRCodeEncoder setErrorCorrectionLevel(ErrorCorrectionLevel errorCorrectionLevel) {
        this.mErrorCorrectionLevel = errorCorrectionLevel;
        return this;
    }

    public QRCodeEncoder setColorBlack(@ColorInt int colorBlack) {
        this.mColorBlack = colorBlack;
        return this;
    }

    public QRCodeEncoder setColorWhite(@ColorInt int colorWhite) {
        this.mColorWhite = colorWhite;
        return this;
    }

    public QRCodeEncoder setConfig(Bitmap.Config config) {
        if (config != null)
            mConfig = config;
        return this;
    }

    public QRCodeEncoder setLogoBitmap(Bitmap logoBitmap) {
        mLogoBitmap = logoBitmap;
        return this;
    }

    public QRCodeEncoder setLogoScale(float logoScale) {
        mLogoScale = logoScale;
        return this;
    }

    public QRCodeEncoder setBitmapBlack(Bitmap bitmapBlack) {
        mBitmapBlack = Bitmap.createScaledBitmap(bitmapBlack, mWidth, mHeight, false);
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
            Map<EncodeHintType, String> hints = new Hashtable<>();
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

            if (mVersion != 0) {
                hints.put(EncodeHintType.QR_VERSION, String.valueOf(mVersion));
            }

            /** 2.将配置参数传入到QRCodeWriter的encode方法生成BitMatrix(位矩阵)对象 */
            BitMatrix result = new MultiFormatWriter().encode(
                    mContent, BarcodeFormat.QR_CODE, mWidth, mHeight, hints);

            /** 3.创建像素数组,并根据BitMatrix(位矩阵)对象为数组元素赋颜色值 */
            int width = result.getWidth();
            int height = result.getHeight();
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int offset = y * width;
                    if (result.get(x, y)) {
                        if (mBitmapBlack != null) {
                            pixels[offset + x] = mBitmapBlack.getPixel(x, y);
                        } else {
                            pixels[offset + x] = mColorBlack; // 黑色色块像素设置
                        }
                    } else {
                        pixels[offset + x] = mColorWhite; // 白色色块像素设置
                    }
                }
            }
            /** 4.创建Bitmap对象,根据像素数组设置Bitmap每个像素点的颜色值,并返回Bitmap对象 */
            Bitmap bitmap = Bitmap.createBitmap(width, height, mConfig);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

            drawLogo(bitmap, mLogoBitmap, mLogoScale);

            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void drawLogo(Bitmap qrCode, Bitmap logo, float scale) {
        if (qrCode == null || logo == null) {
            return;
        }
        // 传值不合法时使用0.2f
        if (scale <= 0F || scale > 1F) {
            scale = 0.2F;
        }

        int qrCodeWidth = qrCode.getWidth();
        int qrCodeHeight = qrCode.getHeight();
        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();

        float scaleWidth = qrCodeWidth * scale / logoWidth;
        float scaleHeight = qrCodeHeight * scale / logoHeight;

        Canvas canvas = new Canvas(qrCode);
        canvas.scale(scaleWidth, scaleHeight, qrCodeWidth / 2, qrCodeHeight / 2);
        canvas.drawBitmap(logo, qrCodeWidth / 2 - logoWidth / 2,
                qrCodeHeight / 2 - logoHeight / 2, null);
    }

}
