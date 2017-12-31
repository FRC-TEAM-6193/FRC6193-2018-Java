package org.usfirst.frc.team6193.robot;

public class Cals {
	
	public static double[] k_StoppingAngle1stGear_Y_Inch = {0,0,0,0,0,0,0,0,0,0};
	public static double[] k_StoppingAngle2ndGear_Y_Inch = {0,0,0,0,0,0,0,0,0,0};
	public static double[] k_StoppingDistance1stGear_Y_Inch = {0,0,0,0,0,0,0,0,0,0};
	public static double[] k_StoppingDistance2ndGear_Y_Inch = {0,0,0,0,0,0,0,0,0,0};
	public static double[] k_StoppingPercentVoltage_X_Uls = {0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1.0};
	
	
	public static double k_DrivelineAutoRampTime_Sec = 0.5;
	public static final double k_DrivelineAutoShiftDownSpeed = 50;
	public static final double k_DrivelineAutoShiftRotateLimit = 0.05;
	public static final double k_DrivelineAutoShiftUpSpeed = 5000;
	public static final double k_Driveline1stGear_InchPerCnt = 1.0;
	public static final double k_Driveline2ndGear_InchPerCnt = 1.0;
	public static final double k_DrivelineShiftDelay = 0.5;
	public static final double k_DrivelineAutoAngleCompensationFactor = -0.05;
	
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
