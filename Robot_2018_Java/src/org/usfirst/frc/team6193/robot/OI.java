/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6193.robot;

import org.usfirst.frc.team6193.robot.commands.DrivelineGearAutoShiftCommand;
import org.usfirst.frc.team6193.robot.commands.DrivelineGearManualShiftCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	public static Joystick joystickXBOX = new Joystick(0);
    public static Button buttonDrivelineManualLowGear = new JoystickButton(joystickXBOX, RobotMap.k_DrivelineGear_LOW_ManualButtonNumber);
    public static Button buttonDrivelineManualHighGear = new JoystickButton(joystickXBOX, RobotMap.k_DrivelineGear_HIGH_ManualButtonNumber);
    public static Button buttonDrivelineAutoShiftSelect = new JoystickButton(joystickXBOX, RobotMap.k_DrivelineGear_AutoButtonNumber);
	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:
    OI(){
    	buttonDrivelineManualLowGear.whenPressed(new DrivelineGearManualShiftCommand(1));
    	buttonDrivelineManualHighGear.whenPressed(new DrivelineGearManualShiftCommand(2));
    	buttonDrivelineAutoShiftSelect.whenPressed(new DrivelineGearAutoShiftCommand());
    	
    }
	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	 

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
}
