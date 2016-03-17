package com.hengyu.ticket.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public final class MatrixToImageWriter {
	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;

	private MatrixToImageWriter() {
	}

	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

	public static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, file)) {
			throw new IOException("Could not write an image of format " + format + " to " + file);
		}
	}

	public static void writeToStream(BitMatrix matrix, String format, OutputStream stream) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, stream)) {
			throw new IOException("Could not write an image of format " + format);
		}
	}
	
	public static String write(String path,String text,int width,int height,String name) throws Exception {
		String format = "png";
		Map<EncodeHintType, String> hints = new Hashtable<EncodeHintType,String>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(text , BarcodeFormat.QR_CODE, width, height, hints);
		String realpathname = null;
		if(name==null){
			realpathname = DateUtil.getCurrentLongDateTimeString() + ".png";
		}else{
			realpathname = name+ ".png";;
		}
		File outputFile = new File(path + realpathname);
		MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
		return realpathname;
	}
	public static String write(String path,String text,int width,int height) throws Exception {
		return write(path, text, width, height,null);
	}
}
