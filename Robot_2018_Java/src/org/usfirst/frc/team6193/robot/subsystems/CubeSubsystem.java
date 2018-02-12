package org.usfirst.frc.team6193.robot.subsystems;

import org.usfirst.frc.team6193.robot.commands.CubeCommand;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class CubeSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new CubeCommand(0, 0, 0));
    }
}

