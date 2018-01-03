package org.usfirst.frc.team6193.robot.commands;

import org.usfirst.frc.team6193.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShooterStepSpeedCommand extends Command {
	double m_stepVelocity = 0.0;
    public ShooterStepSpeedCommand(double step) {
        requires(Robot.shooter);
        m_stepVelocity = step;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	double setpoint = Robot.shooter.getSetPoint();
    	Robot.shooter.setVelocity(setpoint + m_stepVelocity);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
