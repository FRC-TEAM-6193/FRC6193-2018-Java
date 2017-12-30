package org.usfirst.frc.team6193.robot;

public class Cals {
	public static double[] k_StoppingAngle_Y_Inch = {1,2,3,4,5,6,7,8,9,10};
	public static double[] k_StoppingDistance_Y_Inch = {1,2,3,4,5,6,7,8,9,10};
	
	public static double[] k_StoppingPercentVoltage_X_Uls = {0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1.0};
	/*
	 * Stopping distance comments:
	 * Instead of using PID, 
	 * 1. Drive straight at a certain power setting -1 to 1.
	 * 2. Calculate how long it takes to stop once power is removed.
	 * 3. Remove that value from the distance to be traveled.
	 * 4. 
	 * 
	 * Possible Issues,
	 * 1. 20ms loop has a variable of 20ms before removing power.
	 *   WHat is 20ms travel time at full power if traveling at 13ft/Sec
	 *   13*12 = 156inch/sec
	 *   156 * 0.02 = 3.12 inches
	 *   Not bad. We would never run at full power. Usually around 50%
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
