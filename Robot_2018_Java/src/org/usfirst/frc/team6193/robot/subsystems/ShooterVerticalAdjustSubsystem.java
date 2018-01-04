package org.usfirst.frc.team6193.robot.subsystems;

import org.usfirst.frc.team6193.robot.commands.ShooterVerticalAdjustDefaultCommand;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Responsible for changing the vertical angle of the shooter.
 */
public class ShooterVerticalAdjustSubsystem extends Subsystem {

    public void initDefaultCommand() {
    	setDefaultCommand(new ShooterVerticalAdjustDefaultCommand());
    }
}

