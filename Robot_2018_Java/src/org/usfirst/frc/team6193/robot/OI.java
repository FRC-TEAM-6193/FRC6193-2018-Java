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

    OI(){
    	buttonDrivelineManualLowGear.whenPressed(new DrivelineGearManualShiftCommand(1));
    	buttonDrivelineManualHighGear.whenPressed(new DrivelineGearManualShiftCommand(2));
    	buttonDrivelineAutoShiftSelect.whenPressed(new DrivelineGearAutoShiftCommand());
    	
    }

}
