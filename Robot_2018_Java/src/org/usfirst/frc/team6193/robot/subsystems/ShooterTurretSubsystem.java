package org.usfirst.frc.team6193.robot.subsystems;

import org.usfirst.frc.team6193.robot.commands.ShooterTurretDefaultCommand;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Responsible for rotating the shooter angle 
 *
 */
public class ShooterTurretSubsystem extends Subsystem {

    public void initDefaultCommand() {
        setDefaultCommand(new ShooterTurretDefaultCommand());
    }
}

