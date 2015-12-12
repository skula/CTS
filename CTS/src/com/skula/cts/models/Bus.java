package com.skula.cts.models;

import com.skula.cts.R;

public class Bus {

	public static int getPict(String code) {
		if (code.equals("A")) {
			return R.drawable.tram_a;
		} else if (code.equals("B")) {
			return R.drawable.tram_b;
		} else if (code.equals("C")) {
			return R.drawable.tram_c;
		} else if (code.equals("D")) {
			return R.drawable.tram_d;
		} else if (code.equals("E")) {
			return R.drawable.tram_e;
		} else if (code.equals("F")) {
			return R.drawable.tram_f;
		} else if (code.equals("G")) {
			return R.drawable.tram_g;
		} else if (code.equals("2")) {
			return R.drawable.bus_2;
		} else if (code.equals("4")) {
			return R.drawable.bus_4;
		} else if (code.equals("4A")) {
			return R.drawable.bus_4a;
		} else if (code.equals("6")) {
			return R.drawable.bus_6;
		} else if (code.equals("6A")) {
			return R.drawable.bus_6a;
		} else if (code.equals("6B")) {
			return R.drawable.bus_6b;
		} else if (code.equals("7")) {
			return R.drawable.bus_7;
		} else if (code.equals("10")) {
			return R.drawable.bus_10;
		} else if (code.equals("12")) {
			return R.drawable.bus_12;
		} else if (code.equals("13")) {
			return R.drawable.bus_13;
		} else if (code.equals("14")) {
			return R.drawable.bus_14;
		} else if (code.equals("15")) {
			return R.drawable.bus_15;
		} else if (code.equals("15A")) {
			return R.drawable.bus_15a;
		} else if (code.equals("17")) {
			return R.drawable.bus_17;
		} else if (code.equals("19")) {
			return R.drawable.bus_19;
		} else if (code.equals("21")) {
			return R.drawable.bus_21;
		} else if (code.equals("22")) {
			return R.drawable.bus_22;
		} else if (code.equals("24")) {
			return R.drawable.bus_24;
		} else if (code.equals("27")) {
			return R.drawable.bus_27;
		} else if (code.equals("29")) {
			return R.drawable.bus_29;
		} else if (code.equals("30")) {
			return R.drawable.bus_30;
		} else if (code.equals("31")) {
			return R.drawable.bus_31;
		} else if (code.equals("40")) {
			return R.drawable.bus_40;
		} else if (code.equals("50")) {
			return R.drawable.bus_50;
		} else if (code.equals("50A")) {
			return R.drawable.bus_50a;
		} else if (code.equals("62")) {
			return R.drawable.bus_62;
		} else if (code.equals("62A")) {
			return R.drawable.bus_62a;
		} else if (code.equals("63")) {
			return R.drawable.bus_63;
		} else if (code.equals("65")) {
			return R.drawable.bus_65;
		} else if (code.equals("66")) {
			return R.drawable.bus_66;
		} else if (code.equals("70")) {
			return R.drawable.bus_70;
		} else if (code.equals("71")) {
			return R.drawable.bus_71;
		} else if (code.equals("71A")) {
			return R.drawable.bus_71a;
		} else if (code.equals("72")) {
			return R.drawable.bus_72;
		} else if (code.equals("72A")) {
			return R.drawable.bus_72a;
		} else {
			return R.drawable.bus_unknown;
		}
	}
}