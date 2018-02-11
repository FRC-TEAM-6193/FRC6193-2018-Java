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
	public static final int k_DrivelineLeftMotCtrl_1_CANID = 12;
	public static final int k_DrivelineLeftMotCtrl_2_CANID = 5;
	public static final int k_DrivelineRightMotCtrl_1_CANID = 11;
	public static final int k_DrivelineRightMotCtrl_2_CANID = 7;
	public static final int k_DrivelineShiftSolenoidForwardPort = 0;

	// END Driveline 
	
	
	//PowerUp Commands
	public static final int k_IntakeOuttakeRightMotCtrl_1 =1 ;
	public static final int k_IntakeOutakeLeftMotCtrl_2 = 4;
	public static final int k_RampMotCtrl_1 = 6;
	public static final int k_StageOneMotCtrl_1 =7 ;
	public static final int k_StageTwoMotCtrl_1 = 8;
	//END PowerUp Commands

	
	// Autonomous
	public static final int k_liftDownLimitSwitch_DIO_Port = 1;
	public static final int k_liftUpLimitSwitch_DIO_Port = 2;
	public static final int k_AutonomousFieldPlacementSide_Port = 3;
	public static final int k_AutonomousSwitch_DIO_Port = 4;
	public static final int k_AutonomousScale_DIO_Port = 5;
	// END Autonomous
	
	
	// Cube Stage
	public static final int k_LiftRightMotCtrl_CANID = 6;
	public static final int k_LiftLeftMotCtrl_CANID = 3;
	// END Shooter
	


}
