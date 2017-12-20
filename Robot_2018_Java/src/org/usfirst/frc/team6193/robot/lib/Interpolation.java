package org.usfirst.frc.team6193.robot.lib;

public class Interpolation {

	public static double LnrIntrpn(double[] XTbl, double[] YTbl, double Inp) {
		/* Local variables that only exist inside this method */
		int index;
		double Xdif;
		double Ydif;
		double XInpDif;

		/* Output variable */
		double Outp;

		/**/
		if (Inp <= XTbl[0]) {
			Outp = YTbl[0];
		} else if (Inp >= XTbl[XTbl.length - 1]) {
			Outp = YTbl[YTbl.length - 1];
		} else {
			index = 0;
			while (XTbl[index + 1] < Inp) {
				index++;
			}
			Xdif = XTbl[index + 1] - XTbl[index];
			Ydif = YTbl[index + 1] - YTbl[index];
			XInpDif = Inp - XTbl[index];

			Outp = Ydif * XInpDif / Xdif + YTbl[index];
		}

		return Outp;
	}
}
