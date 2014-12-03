package com.rnd.mobilepayment.printer;

public class PrinterUtil {

	public static String smallFont()// font B
	{
		return "" + ((char) 0x1B) + ((char) 0x21) + ((char) 0x01);
	}

	public static String bigFont()// font A
	{
		return "" + ((char) 0x1B) + ((char) 0x21) + ((char) 0x00);
	}

	public static String leftAlign() {
		return "" + ((char) 27) + ((char) 97) + ((char) 0);
	}

	public static String centerAlign() {
		return "" + ((char) 27) + ((char) 97) + ((char) 1);
	}

	public static String rightAlign() {
		return "" + ((char) 27) + ((char) 97) + ((char) 2);
	}

	public static String bold() {
		return "" + ((char) 27) + ((char) 69) + ((char) 1);
	}

	public static String cancelBold() {
		return "" + ((char) 27) + ((char) 69) + ((char) 0);
	}

	public static String underline() {
		return "" + ((char) 27) + ((char) 45) + ((char) 1);
	}

	public static String cancelUnderline() {
		return "" + ((char) 27) + ((char) 45) + ((char) 0);
	}

	public static String strike() {
		return "" + ((char) 27) + ((char) 71) + ((char) 1);
	}

	public static String cancelStrike() {
		return "" + ((char) 27) + ((char) 71) + ((char) 0);
	}
}
