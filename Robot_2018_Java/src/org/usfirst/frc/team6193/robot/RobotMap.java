/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6193.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	// Driveline 
	public static final int k_DrivelineLeftMotCtrl_1_CANID = 0;
	public static final int k_DrivelineLeftMotCtrl_2_CANID = 1;
	public static final int k_DrivelineRightMotCtrl_1_CANID = 2;
	public static final int k_DrivelineRightMotCtrl_2_CANID = 3;
	public static final int k_DrivelineShiftSolenoidForwardPort = 1;

	// END Driveline 

	
	// Autonomous
	public static final int k_AutonomousDIO_1_Port = 1;
	public static final int k_AutonomousDIO_2_Port = 2;
	public static final int k_AutonomousDIO_3_Port = 3;
	public static final int k_AutonomousDIO_4_Port = 4;
	// END Autonomous
	
	
	// Shooter
	public static final int k_ShooterMotCtrl_1_CANID = 4;

	// END Shooter


}
