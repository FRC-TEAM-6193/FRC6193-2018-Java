package org.usfirst.frc.team6193.robot.commands;

import org.usfirst.frc.team6193.robot.Cals;
import org.usfirst.frc.team6193.robot.Enums;
import org.usfirst.frc.team6193.robot.Robot;
import org.usfirst.frc.team6193.robot.lib.Interpolation;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DrivelineRotateSDCommand extends Command {

	private double m_angle;
	private double m_percentVoltage;
	private int m_gear;
	
    public DrivelineRotateSDCommand(double angle, double percentVoltage, int gear) {
        m_angle = angle;
        m_percentVoltage = percentVoltage;
        m_gear = gear;
        requires(Robot.driveline);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveline.initDrivelineAngle();
    	Robot.driveline.drivelineRequestedGear = m_gear;
    	Robot.driveline.drivelineShiftMode = Enums.SHIFT_MODE_MANUAL;
    	Robot.driveline.setGear();
    	this.setInterruptible(true);
    	Robot.driveline.drivelineAutoCommandRampInitTime = Timer.getFPGATimestamp();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveline.Drive(0.0, m_percentVoltage);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	double stoppingDistance = 0.0;
    	if(m_gear == 1) {
    		stoppingDistance = Interpolation.LnrIntrpn(Cals.k_StoppingPercentVoltage_X_Uls, Cals.k_StoppingAngle1stGear_Y_Inch, Math.abs(m_percentVoltage));
    	}else {
    		stoppingDistance = Interpolation.LnrIntrpn(Cals.k_StoppingPercentVoltage_X_Uls, Cals.k_StoppingAngle2ndGear_Y_Inch, Math.abs(m_percentVoltage));
    	}
    	double currentAngle = Robot.driveline.getDrivelineAngleFromInitialization();
    	if(Math.abs(currentAngle) >= m_angle - stoppingDistance) {
    		SmartDashboard.putNumber("RobotAngleBegin", currentAngle);
    		m_percentVoltage = 0.0;
    		Robot.driveline.Drive(0.0, 0.0);
    		return true;
    	}
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveline.Drive(0.0, 0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    	this.cancel();
    }
}
