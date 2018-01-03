package org.usfirst.frc.team6193.robot;

public class Cals {
	
	// Both gears need calibration. Drag is much more in 1st gear
	public static double[] k_StoppingAngle1stGear_Y_Inch = {0,0,0,0,0,0,0,0,0,0};
	public static double[] k_StoppingAngle2ndGear_Y_Inch = {0,0,0,0,0,0,0,0,0,0};
	public static double[] k_StoppingDistance1stGear_Y_Inch = {0,0,0,0,0,0,0,0,0,0};
	public static double[] k_StoppingDistance2ndGear_Y_Inch = {0,0,0,0,0,0,0,0,0,0};
	public static double[] k_StoppingPercentVoltage_X_Uls = {0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1.0};
	
	
	public static double k_DrivelineAutoRampTime_Sec = 0.5;
	public static final double k_DrivelineAutoShiftDownSpeed = 1.0;
	public static final double k_DrivelineAutoShiftRotateLimit = 0.05;
	public static final double k_DrivelineAutoShiftUpSpeed = 3.5;
	// Encoder is a Greyhill 256 Cnts/Rev. Quad encoder into TalonSRX means 1024 Cnts/Rev
	// Encoder is on output shaft before 60:24 3rd stage
	// 4 inch wheels = C=pi*d = 12.57 inch
	// Rotate wheel once = 60/24 = 2.5 rotations 
	// Encoder is on a 36:12 gear ratio to output shaft before 3rd stage
	// Counts per rotation of wheel = 1024 *3 * 2.5 = 7680
	// Counts per Inch = 7680/12.57 = 611.16
	public static final double k_DrivelineVeltoFtPerSec_Scale = 0.001364;
	public static final double k_DrivelineWheelCircumference_Inch = 12.57;
	public static final double k_DrivelineEncoder_CntPerWheelRev = 7680.0;
	public static final double k_DrivelineEncoder_InchPerCnt = 1.0/611.16;
	public static final double k_DrivelineShiftDelay = 0.5;
	public static final double k_DrivelineAutoAngleCompensationFactor = -0.05;
	
	public static final double k_ShooterDefaultVelocity = 4000.0;
	public static double k_Driveline_PID_DRIVE_P = 1;
	public static double k_Driveline_PID_DRIVE_I = 0;
	public static double k_Driveline_PID_DRIVE_D = 0;
	
	public static double k_Driveline_PID_ROTATE_P = 1;
	public static double k_Driveline_PID_ROTATE_I = 0;
	public static double k_Driveline_PID_ROTATE_D = 0;
	/*
	 * Stopping distance comments:
	 * Instead of using PID, 
	 * 1. Drive straight at a certain power setting -1 to 1.
	 * 2. calibrate how long it takes to stop once power is removed.
	 * 3. Remove that value from the distance to be traveled.
	 * 4. 
	 * 
	 * Possible Issues,
	 * 1. 20ms loop has a variable of 20ms before removing power.
	 *   WHat is 20ms travel time at full power if traveling at 13ft/Sec
	 *   13*12 = 156inch/sec
	 *   156 * 0.02 = 3.12 inches
	 *   Not bad. We would never run at full power. Usually around 50%
	 *   We can set the loop rate to 10ms in autonomous mode
	 *   
	 *   How can this be calculated.
	 *   1. Drive at a certain power 
	 *   2. Read encoder and Power setting
	 *   3. Determine distance from power removal to complete stop.
	 *      Make sure the robot does not skid.
	 *   4. 
	 *   
	 *   
	 */
}
