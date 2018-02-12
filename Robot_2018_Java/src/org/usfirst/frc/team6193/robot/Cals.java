package org.usfirst.frc.team6193.robot;

public class Cals {
	
	
	// Stopping distance tables for driveline move and rotate commands
	public static double[] k_StoppingAngle1stGear_Y_Inch = {0,0,0,0,0,0,0,0,0,0};
	public static double[] k_StoppingAngle2ndGear_Y_Inch = {0,0,0,0,0,0,0,0,0,0};
	public static double[] k_StoppingDistance1stGear_Y_Inch = {0,0,0,0,0,0,0,0,0,0};
	public static double[] k_StoppingDistance2ndGear_Y_Inch = {0,0,0,0,0,0,0,0,0,0};
	public static double[] k_StoppingPercentVoltage_X_Uls = {0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1.0};
	
	// Driveline shifiting and performance calibrations
	public static final double k_DrivelineAutoRampTime_Sec = 0.5;
	public static final double k_DrivelineAutoShiftDownSpeed = 1.0;
	public static final double k_DrivelineAutoShiftRotateLimit = 0.05;
	public static final double k_DrivelineAutoShiftUpSpeed = 3.5;
	public static final double k_DrivelineShiftDelay = 0.5;
	public static final double k_DrivelineAutoAngleCompensationFactor = -0.05;
	public static final double k_DrivelineDeadband = -0.250;

	// Driveline sensor calibrations
	public static final double k_DrivelineVeltoFtPerSec_Scale = 1;
	public static final double k_DrivelineWheelCircumference_Inch = 12.57;
	public static final double k_DrivelineEncoder_CntPerWheelRev = 7680.0;
	public static final double k_DrivelineEncoder_InchPerCnt = 0.00243956043956;
	
}
	// Encoder is a Greyhill 256 Cnts/Rev. Quad encoder into TalonSRX means 1024 Cnts/Rev
	// Encoder is on output shaft before 60:24 3rd stage
	// 4 inch wheels = C=pi*d = 12.57 inch
	// Rotate wheel once = 60/24 = 2.5 rotations 
	// Encoder is on a 36:12 gear ratio to output shaft before 3rd stage
	// Counts per rotation of wheel = 1024 *3 * 2.5 = 7680
	// Counts per Inch = 7680/12.57 = 611.16